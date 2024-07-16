package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RentDigitalBookDao implements RentDigitalBookDaoInterface {

	private static DataSource ds;

	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/libreria");

		} 
		catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
	
	
	private static final String TABLE_NAME = "noleggiopera";
	
	
	@Override
	public synchronized void doSave(RentDigitalBookBean rentDigitalBook) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + RentDigitalBookDao.TABLE_NAME
						+ " (dataInizioNoleggio, dataFineNoleggio, tipoOpera, ISBNOpera, ordine, costo, nomeOpera) VALUES (?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, rentDigitalBook.getDataInizioNoleggio());
			preparedStatement.setString(2, rentDigitalBook.getDataFineNoleggio());
			preparedStatement.setString(3, rentDigitalBook.getTipoOpera());
			preparedStatement.setString(4, rentDigitalBook.getISBNOpera());
			preparedStatement.setInt(5, rentDigitalBook.getOrdine());
			preparedStatement.setDouble(6, rentDigitalBook.getCosto());
			preparedStatement.setString(7, rentDigitalBook.getNomeOpera());
			
			preparedStatement.executeUpdate();
			connection.commit();
		}
		catch (Exception ex) 
		{
			System.out.println("INSERT operation failed: An Exception has occurred! " + ex); 
		}
		 finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} 
				finally {
					if (connection != null)
						connection.close();
				}
		 }
	}


	@Override
	public synchronized RentDigitalBookBean doRetrieve(String ISBN, int order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		RentDigitalBookBean rentDigitalBook = new RentDigitalBookBean();
		
		String searchQuery = "select * from " + RentDigitalBookDao.TABLE_NAME
							+ "	where ISBNOpera = ? AND ordine = ?";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, ISBN);
			preparedStatement.setInt(2, order);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					rentDigitalBook.setIdNoleggio(rs.getInt("idNoleggio"));
					
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date begin = rs.getDate("dataInizioNoleggio");
					String beginAsString = df.format(begin);
					Date end = rs.getDate("dataFineNoleggio");
					String endAsString = df.format(end);
					
					rentDigitalBook.setDataInizioNoleggio(beginAsString);
					rentDigitalBook.setDataFineNoleggio(endAsString);
					rentDigitalBook.setTipoOpera(rs.getString("tipoOpera"));
					rentDigitalBook.setISBNOpera(rs.getString("ISBNOpera"));
					rentDigitalBook.setNomeOpera(rs.getString("nomeOpera"));
					rentDigitalBook.setOrdine(rs.getInt("ordine"));
					rentDigitalBook.setCosto(rs.getDouble("costo"));
				}
			}
			catch (Exception ex) 
			{
				System.out.println("SELECT operation failed: An Exception has occurred! " + ex); 
			}
			finally {
				try {
					if (preparedStatement != null)
							preparedStatement.close();
					} 
			finally {
				if (connection != null)
					connection.close();
			}
	 }

		return rentDigitalBook;
	}
	
	@Override
	public synchronized ArrayList<RentDigitalBookBean> doRetrieveAll(int order, String sort) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<RentDigitalBookBean> rentDigitalBooks = new ArrayList<RentDigitalBookBean>();

		String selectSQL = "SELECT * FROM " + RentDigitalBookDao.TABLE_NAME
						   + " WHERE ordine = ?";

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY " + sort;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, order);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				RentDigitalBookBean rentDigitalBook = new RentDigitalBookBean();
				
				rentDigitalBook.setIdNoleggio(rs.getInt("idNoleggio"));
				
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
				Calendar c = Calendar.getInstance();
				c.setTime(rs.getDate("dataInizioNoleggio"));
				String beginAsString = df.format(c.getTime());
				
				Calendar c2 = Calendar.getInstance();
				c2.setTime(rs.getDate("dataFineNoleggio"));
				String endAsString = df.format(c2.getTime());
				
				rentDigitalBook.setDataInizioNoleggio(beginAsString);
				rentDigitalBook.setDataFineNoleggio(endAsString);
				rentDigitalBook.setTipoOpera(rs.getString("tipoOpera"));
				rentDigitalBook.setISBNOpera(rs.getString("ISBNOpera"));
				rentDigitalBook.setNomeOpera(rs.getString("nomeOpera"));
				rentDigitalBook.setOrdine(rs.getInt("ordine"));
				rentDigitalBook.setCosto(rs.getDouble("costo"));
				rentDigitalBooks.add(rentDigitalBook);
			}
		}
		catch (Exception ex) 
		{
			System.out.println("SELECT ALL operation failed: An Exception has occurred! " + ex); 
		}
		 finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} 
				finally {
					if (connection != null)
						connection.close();
				}
		 }
		return rentDigitalBooks;
	}
	
	@Override
	public synchronized void doDelete(int id) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = " DELETE FROM " + RentDigitalBookDao.TABLE_NAME + " WHERE idNoleggio = ? ";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
		}
		catch (Exception ex) 
		{
			System.out.println("SELECT ALL operation failed: An Exception has occurred! " + ex); 
		}
		 finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} 
				finally {
					if (connection != null)
						connection.close();
				}
		 }
	}
	
	
	@Override
	public synchronized Boolean alreadySaved(String ISBN, int order, String type) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String selectSQL = "select * from " + RentDigitalBookDao.TABLE_NAME
						   + "	where ISBNOpera = ? AND ordine = ? AND tipoOpera = ?";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, ISBN);
			preparedStatement.setInt(2, order);
			preparedStatement.setString(3, type);

			ResultSet rs = preparedStatement.executeQuery();
			boolean more = rs.next();
			if (more) 
				return Boolean.FALSE;
		}
		catch (Exception ex) 
		{
			System.out.println("SELECT ALL operation failed: An Exception has occurred! " + ex); 
		}
		 finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} 
				finally {
					if (connection != null)
						connection.close();
				}
		 }
		return Boolean.TRUE;
	}
}