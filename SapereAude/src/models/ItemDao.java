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

public class ItemDao implements ItemDaoInterface {

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
	
	
	private static final String TABLE_NAMEO = "opera";
	
	
	@Override
	public synchronized void doSave(ItemBean item) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ItemDao.TABLE_NAMEO
						+ " (ISBN, nome, casaEditrice, autore, categoria, photo) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getISBN());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getCasaEditrice());
			preparedStatement.setString(4, item.getAutore());
			preparedStatement.setString(5, item.getCategoria());
			
			InputStream foto = item.getFoto();
				if(foto != null) {
				preparedStatement.setBlob(6, foto);
			}
			
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
	public synchronized ItemBean doRetrieve(String ISBN) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ItemBean item = new ItemBean();
		
		String searchQuery = "select * from " + ItemDao.TABLE_NAMEO
							+ "	where ISBN = ?";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, ISBN);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					item.setISBN(rs.getString("ISBN"));
					item.setNome(rs.getString("nome"));
					item.setCasaEditrice(rs.getString("casaEditrice"));
					item.setAutore(rs.getString("autore"));
					item.setCategoria(rs.getString("categoria"));
					Blob blob = rs.getBlob("photo");
					item.setFoto(blob.getBinaryStream());
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

		return item;
	}
	
	@Override
	public synchronized ArrayList<ItemBean> doRetrieveAll(String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<ItemBean> items = new ArrayList<ItemBean>();

		String selectSQL = "SELECT * FROM " + ItemDao.TABLE_NAMEO;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				ItemBean item = new ItemBean();
				item.setISBN(rs.getString("ISBN"));
				item.setNome(rs.getString("nome"));
				item.setCasaEditrice(rs.getString("casaEditrice"));
				item.setAutore(rs.getString("autore"));
				item.setCategoria(rs.getString("categoria"));
				Blob blob = rs.getBlob("photo");
				item.setFoto(blob.getBinaryStream());
				items.add(item);
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
		return items;
	}
	
	
	@Override
	public synchronized void doUpdate(String isbn, ItemBean updated) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE " + ItemDao.TABLE_NAMEO 
				         + " SET nome = ?, casaEditrice = ?, autore = ?, categoria = ?, photo = ?"
				         + " WHERE ISBN = ?";
		
		String nome = updated.getNome();
		String casaEditrice = updated.getCasaEditrice();
		String autore = updated.getAutore();
		String categoria = updated.getCategoria();
		InputStream photo = updated.getFoto();
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(updateSQL);
			
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, casaEditrice);
			preparedStatement.setString(3, autore);
			preparedStatement.setString(4, categoria);
			preparedStatement.setBlob(5, photo);
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
		PreparedStatement c1 = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement c2 = null;
		
		String check1 = " set foreign_key_checks = 0 ";
		String insertSQL = "DELETE FROM " + ItemDao.TABLE_NAMEO
						+ " WHERE ISBN = ? ";
		String check2 = " set foreign_key_checks = 1 ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			c1 = connection.prepareStatement(check1);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, isbn);
			c2 = connection.prepareStatement(check2);
		
			c1.executeUpdate();
			preparedStatement.executeUpdate();
			c2.executeUpdate();
			
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