package models;

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
						+ " (ISBN, nome, casaEditrice, mediaVoti, autore, categoria) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, item.getISBN());
			preparedStatement.setString(2, item.getNome());
			preparedStatement.setString(3, item.getCasaEditrice());
			preparedStatement.setDouble(4, item.getMediaVoti());
			preparedStatement.setInt(5, item.getIdAutore());
			preparedStatement.setString(6, item.getCategoria());
		
			preparedStatement.executeUpdate();

			connection.commit();
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
	public synchronized ItemBean doRetrieve(String opera) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ItemBean item = new ItemBean();
		
		String searchQuery = "select * from " + ItemDao.TABLE_NAMEO
							+ "	where idOpera = ? ";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, opera);
			ResultSet rs = preparedStatement.executeQuery();
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					item.setISBN(rs.getString("ISBN"));
					item.setNome(rs.getString("nome"));
					item.setCasaEditrice(rs.getString("casaEditrice"));
					item.setMediaVoti(rs.getDouble("mediaVoti"));
					item.setIdAutore(rs.getInt("autore"));
					item.setCategoria(rs.getString("categoria"));
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
				item.setMediaVoti(rs.getDouble("mediaVoti"));
				item.setIdAutore(rs.getInt("autore"));
				item.setCategoria(rs.getString("categoria"));
				items.add(item);
			}
		}
		catch (Exception ex) 
		{
			System.out.println("SELECT operation for ALL failed: An Exception has occurred! " + ex); 
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
}
