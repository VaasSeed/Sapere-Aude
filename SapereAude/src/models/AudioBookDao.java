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

public class AudioBookDao implements AudioBookDaoInterface {

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
	
	
	private static final String TABLE_NAME = "audiolibro";
	
	
	@Override
	public synchronized void doSave(AudioBookBean audioBook) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + AudioBookDao.TABLE_NAME
						+ " (ISBNOpera, audioFile, durata, linguaAudio, costoAcquisto, costoNoleggio) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, audioBook.getISBNOpera());
			preparedStatement.setBlob(2, audioBook.getAudioFile());
			preparedStatement.setString(3, audioBook.getDurata());
			preparedStatement.setString(4, audioBook.getLingua());
			preparedStatement.setDouble(5, audioBook.getCostoAcquisto());
			preparedStatement.setDouble(6, audioBook.getCostoNoleggio());
		
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
	public synchronized AudioBookBean doRetrieve(String ISBNOpera) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		AudioBookBean audioBook = new AudioBookBean();
		
		String searchQuery = "select * from " + AudioBookDao.TABLE_NAME
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
					audioBook.setISBNOpera(rs.getString("ISBNOpera"));
					Blob blob = rs.getBlob("audioFile");
					audioBook.setAudioFile(blob.getBinaryStream());
					audioBook.setDurata(rs.getString("durata"));
					audioBook.setLingua(rs.getString("linguaAudio"));
					audioBook.setCostoAcquisto(rs.getDouble("costoAcquisto"));
					audioBook.setCostoNoleggio(rs.getDouble("costoNoleggio"));
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

		return audioBook;
	}
	
	@Override
	public synchronized ArrayList<AudioBookBean> doRetrieveAll(String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<AudioBookBean> digitalBooks = new ArrayList<AudioBookBean>();

		String selectSQL = "SELECT * FROM " + AudioBookDao.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				AudioBookBean audioBook = new AudioBookBean();
				audioBook.setISBNOpera(rs.getString("ISBNOpera"));
				Blob blob = rs.getBlob("audioFile");
				audioBook.setAudioFile(blob.getBinaryStream());
				audioBook.setDurata(rs.getString("durata"));
				audioBook.setLingua(rs.getString("linguaAudio"));
				audioBook.setCostoAcquisto(rs.getDouble("costoAcquisto"));
				audioBook.setCostoNoleggio(rs.getDouble("costoNoleggio"));
				digitalBooks.add(audioBook);
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
	public synchronized void doUpdate(String isbn, AudioBookBean updated) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + AudioBookDao.TABLE_NAME 
				         + " SET audioFile = ?, durata = ?, linguaAudio = ?, costoAcquisto = ?, costoNoleggio = ?"
				         + " WHERE ISBNOpera = ?";
		
		InputStream audioFile = updated.getAudioFile();
		String durata = updated.getDurata();
		String lingua = updated.getLingua();
		double aPrice = updated.getCostoAcquisto();
		double nPrice = updated.getCostoNoleggio();
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setBlob(1, audioFile);
			preparedStatement.setString(2, durata);
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

		String insertSQL = "DELETE FROM " + AudioBookDao.TABLE_NAME
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