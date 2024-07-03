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

public class UserDao implements UserDaoInterface {

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
	
	
	private static final String TABLE_NAME = "accountuser";
	
	
	@Override
	public synchronized void doSave(UserBean user) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UserDao.TABLE_NAME 
						+ " (username, nome, cognome, email, passw, dataNascita) VALUES (?, ?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getNome());
			preparedStatement.setString(3, user.getCognome());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setString(6, user.getDataDiNascita());
		
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

	public synchronized UserBean doRetrieveLog(String username, String password) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UserBean user = new UserBean();
		
		String searchQuery = "select * from " + UserDao.TABLE_NAME 
							+ "	where username = ? AND passw = ? ";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			boolean more = rs.next();
				if (!more) 
					user.setValid(false);
				else if (more) 
				{
					user.setValid(true);
					if(rs.getBoolean("isAdmin"))
						user.setAmministratore(true);
				}
			}
			catch (Exception ex) 
			{
				System.out.println("Log In failed: An Exception has occurred! " + ex); 
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

		return user;
	}
	

	@Override
	public synchronized UserBean doRetrieve(String username) throws SQLException {
		//preparing some objects for connecNon 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UserBean user = new UserBean();
		
		String searchQuery = "select * from " + UserDao.TABLE_NAME 
							+ "	where username = ? ";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			boolean more = rs.next();
			// if user does not exist set the valid variable to false
				if (!more) 
					user.setValid(false);
		
				//if user exists set the valid variable to true
				else if (more) 
				{
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("passw"));
					user.setEmail(rs.getString("email"));
					user.setNome(rs.getString("nome"));
					user.setCognome(rs.getString("cognome"));
					user.setDataDiNascita(rs.getString("dataNascita"));
					user.setAmministratore(rs.getBoolean("isAdmin"));
					user.setValid(true);
				}
			}
			catch (Exception ex) 
			{
				System.out.println("Log In failed: An Exception has occurred! " + ex); 
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

		return user;
	}
	
	@Override
	public synchronized ArrayList<UserBean> doRetrieveAll(String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<UserBean> users = new ArrayList<UserBean>();

		String selectSQL = "SELECT * FROM " + UserDao.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("passw"));
				user.setEmail(rs.getString("email"));
				user.setNome(rs.getString("nome"));
				user.setCognome(rs.getString("cognome"));
				user.setDataDiNascita(rs.getString("dataNascita"));
				user.setAmministratore(rs.getBoolean("isAdmin"));
				user.setValid(true);
				users.add(user);
			}
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
		
		return users;
	}
}
