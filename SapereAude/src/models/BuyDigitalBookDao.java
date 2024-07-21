package models;

import java.sql.Connection;
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

public class BuyDigitalBookDao implements BuyDigitalBookDaoInterface {

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
	
	
	private static final String TABLE_NAME = "acquistopera";
	
	
	@Override
	public synchronized void doSave(BuyDigitalBookBean buyDigitalBook) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + BuyDigitalBookDao.TABLE_NAME
						+ " (dataAcquisto, tipoOpera, ISBNOpera, ordine, costo, nomeOpera) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, buyDigitalBook.getDataAcquisto());
			preparedStatement.setString(2, buyDigitalBook.getTipoOpera());
			preparedStatement.setString(3, buyDigitalBook.getISBNOpera());
			preparedStatement.setInt(4, buyDigitalBook.getOrdine());
			preparedStatement.setDouble(5, buyDigitalBook.getCosto());
			preparedStatement.setString(6, buyDigitalBook.getNomeOpera());
			
			preparedStatement.executeUpdate();
			connection.commit();
		}
		catch (Exception ex) 
		{
			System.out.println("INSERT BUY operation failed: An Exception has occurred! " + ex); 
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
	public synchronized BuyDigitalBookBean doRetrieve(String ISBN, int order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		BuyDigitalBookBean buyDigitalBook = new BuyDigitalBookBean();
		
		String searchQuery = "select * from " + BuyDigitalBookDao.TABLE_NAME
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
					buyDigitalBook.setIdAcquisto(rs.getInt("idAcquisto"));
					
					Calendar c = Calendar.getInstance();
					c.setTime(rs.getDate("dataAcquisto"));
					
					if(rs.getDate("dataAcquisto") != null) {
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						String dateAsString = df.format(c.getTime());
						buyDigitalBook.setDataAcquisto(dateAsString);
					}
				
					buyDigitalBook.setTipoOpera(rs.getString("tipoOpera"));
					buyDigitalBook.setISBNOpera(rs.getString("ISBNOpera"));
					buyDigitalBook.setNomeOpera(rs.getString("nomeOpera"));
					buyDigitalBook.setOrdine(rs.getInt("ordine"));
					buyDigitalBook.setCosto(rs.getDouble("costo"));
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

		return buyDigitalBook;
	}
	
	@Override
	public synchronized ArrayList<BuyDigitalBookBean> doRetrieveAll(int order, String sort) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<BuyDigitalBookBean> buyDigitalBooks = new ArrayList<BuyDigitalBookBean>();

		String selectSQL = "SELECT * FROM " + BuyDigitalBookDao.TABLE_NAME
						   + " WHERE ordine = ?";

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY " + sort;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, order);

			ResultSet rs = preparedStatement.executeQuery();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			while (rs.next()) {
				BuyDigitalBookBean buyDigitalBook = new BuyDigitalBookBean();
				
				buyDigitalBook.setIdAcquisto(rs.getInt("idAcquisto"));
				
				Calendar c = Calendar.getInstance();
				c.setTime(rs.getDate("dataAcquisto"));
				
				if(rs.getDate("dataAcquisto") != null) {
					String dateAsString = df.format(c.getTime());
					buyDigitalBook.setDataAcquisto(dateAsString);
				}
				
				buyDigitalBook.setTipoOpera(rs.getString("tipoOpera"));
				buyDigitalBook.setISBNOpera(rs.getString("ISBNOpera"));
				buyDigitalBook.setNomeOpera(rs.getString("nomeOpera"));
				buyDigitalBook.setOrdine(rs.getInt("ordine"));
				buyDigitalBook.setCosto(rs.getDouble("costo"));
				buyDigitalBooks.add(buyDigitalBook);
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
		return buyDigitalBooks;
	}
	
	@Override
	public synchronized ArrayList<BuyDigitalBookBean> doRetrieveAllAdmin(String isbn, int order, String sort) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<BuyDigitalBookBean> buyDigitalBooks = new ArrayList<BuyDigitalBookBean>();

		String selectSQL = "SELECT * FROM " + BuyDigitalBookDao.TABLE_NAME
						   + " WHERE ordine = ? AND ISBNOpera = ? ";

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY " + sort;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, order);
			preparedStatement.setString(2, isbn);
			
			ResultSet rs = preparedStatement.executeQuery();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			while (rs.next()) {
				BuyDigitalBookBean buyDigitalBook = new BuyDigitalBookBean();
				
				buyDigitalBook.setIdAcquisto(rs.getInt("idAcquisto"));
				
				Calendar c = Calendar.getInstance();
				c.setTime(rs.getDate("dataAcquisto"));
				
				if(rs.getDate("dataAcquisto") != null) {
					String dateAsString = df.format(c.getTime());
					buyDigitalBook.setDataAcquisto(dateAsString);
				}
				
				buyDigitalBook.setTipoOpera(rs.getString("tipoOpera"));
				buyDigitalBook.setISBNOpera(rs.getString("ISBNOpera"));
				buyDigitalBook.setNomeOpera(rs.getString("nomeOpera"));
				buyDigitalBook.setOrdine(rs.getInt("ordine"));
				buyDigitalBook.setCosto(rs.getDouble("costo"));
				buyDigitalBooks.add(buyDigitalBook);
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
		return buyDigitalBooks;
	}

	
	@Override
	public synchronized void doDelete(int id) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = " DELETE FROM " + BuyDigitalBookDao.TABLE_NAME + " WHERE idAcquisto = ? ";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
		}
		catch (Exception ex) 
		{
			System.out.println("DELETE operation failed: An Exception has occurred! " + ex); 
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

		String selectSQL = "select * from " + BuyDigitalBookDao.TABLE_NAME
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
	
	@Override
	public synchronized void updateDate(int id) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + BuyDigitalBookDao.TABLE_NAME 
						+ " SET dataAcquisto = ? WHERE idAcquisto = ?";
		
		try {
			
			Calendar c = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			String todayAsString = dateFormat.format(c.getTime());
			
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, todayAsString);
			preparedStatement.setInt(2, id);
			
			preparedStatement.executeUpdate();
			connection.commit();
		}
		catch (Exception ex) 
		{
			System.out.println("UPDATE DATE failed: An Exception has occurred! " + ex); 
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
	public synchronized void updateAdmin(int id, String name, double cost) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + BuyDigitalBookDao.TABLE_NAME 
						+ " SET nomeOpera = ?, costo = ? WHERE idAcquisto = ?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, cost);
			preparedStatement.setInt(3, id);
			
			preparedStatement.executeUpdate();
			connection.commit();
		}
		catch (Exception ex) 
		{
			System.out.println("UPDATE ADMIN failed: An Exception has occurred! " + ex); 
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
	
}