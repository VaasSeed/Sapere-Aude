package models;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DigitalBookDao implements DigitalBookDaoInterface {

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
	
	
	private static final String TABLE_NAME = "operadigitale";
	
	
	@Override
	public synchronized void doSave(DigitalBookBean digitalBook) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + DigitalBookDao.TABLE_NAME
						+ " (ISBNOpera, bookFile, numeroPagine, linguaTesto, costoAcquisto, costoNoleggio) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			
			preparedStatement.setString(1, digitalBook.getISBNOpera());
			preparedStatement.setBlob(2, digitalBook.getBookFile());
			preparedStatement.setInt(3, digitalBook.getNumPagine());
			preparedStatement.setString(4, digitalBook.getLingua());
			preparedStatement.setDouble(5, digitalBook.getCostoAcquisto());
			preparedStatement.setDouble(6, digitalBook.getCostoNoleggio());
		
			preparedStatement.executeUpdate();

			connection.commit();
		}
		catch (SQLException ex) 
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
	public synchronized DigitalBookBean doRetrieve(String ISBNOpera) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		DigitalBookBean digitalBook = new DigitalBookBean();
		
		String searchQuery = "select * from " + DigitalBookDao.TABLE_NAME
							+ "	where ISBNOpera = ?";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			
			preparedStatement.setString(1, ISBNOpera);
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					digitalBook.setISBNOpera(rs.getString("ISBNOpera"));
					Blob blob = rs.getBlob("bookFile");
					digitalBook.setBookFile(blob.getBinaryStream());
					digitalBook.setNumPagine(rs.getInt("numeroPagine"));
					digitalBook.setLingua(rs.getString("linguaTesto"));
					digitalBook.setCostoAcquisto(rs.getDouble("costoAcquisto"));
					digitalBook.setCostoNoleggio(rs.getDouble("costoNoleggio"));
				}
			}
			catch (SQLException ex) 
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

		return digitalBook;
	}
	
	@Override
	public synchronized ArrayList<DigitalBookBean> doRetrieveAll(String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<DigitalBookBean> digitalBooks = new ArrayList<DigitalBookBean>();

		String selectSQL = "SELECT * FROM " + DigitalBookDao.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				DigitalBookBean digitalBook = new DigitalBookBean();
				digitalBook.setISBNOpera(rs.getString("ISBNOpera"));
				Blob blob = rs.getBlob("bookFile");
				digitalBook.setBookFile(blob.getBinaryStream());
				digitalBook.setNumPagine(rs.getInt("numeroPagine"));
				digitalBook.setLingua(rs.getString("linguaTesto"));
				digitalBook.setCostoAcquisto(rs.getDouble("costoAcquisto"));
				digitalBook.setCostoNoleggio(rs.getDouble("costoNoleggio"));
				digitalBooks.add(digitalBook);
			}
		}
		catch (SQLException ex) 
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
		return digitalBooks;
	}
	
	
	@Override
	public synchronized void doUpdate(String isbn, DigitalBookBean updated) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + DigitalBookDao.TABLE_NAME 
				         + " SET bookFile = ?, numeroPagine = ?, linguaTesto = ?, costoAcquisto = ?, costoNoleggio = ?"
				         + " WHERE ISBNOpera = ?";
		
		InputStream bookFile = updated.getBookFile();
		int numeroPagine = updated.getNumPagine();
		String lingua = updated.getLingua();
		double aPrice = updated.getCostoAcquisto();
		double nPrice = updated.getCostoNoleggio();
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setBlob(1, bookFile);
			preparedStatement.setInt(2, numeroPagine);
			preparedStatement.setString(3, lingua);
			preparedStatement.setDouble(4, aPrice);
			preparedStatement.setDouble(5, nPrice);
			preparedStatement.setString(6, isbn);
			preparedStatement.executeUpdate();

			connection.commit();
		}
		catch (SQLException ex) 
		{
			System.out.println("UPDATE operation failed: An Exception has occurred! " + ex); 
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
	public synchronized void doDelete(String isbn) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "DELETE FROM " + DigitalBookDao.TABLE_NAME
						+ " WHERE ISBNOpera = ? ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, isbn);
		
			preparedStatement.executeUpdate();

			connection.commit();
		}
		catch (SQLException ex) 
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
	
}