package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderDao implements OrderDaoInterface {

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
	
	
	private static final String TABLE_NAME = "ordine";
	
	
	@Override
	public synchronized void doSave(OrderBean order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + OrderDao.TABLE_NAME
						+ " (dataOrdine, importoTotale, stato, accountref, creditCard) VALUES (?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, order.getDataOrdine());
			preparedStatement.setDouble(2, order.getImportoTotale());
			preparedStatement.setInt(3, order.getStato());
			preparedStatement.setString(4, order.getUtente());
			preparedStatement.setString(5, order.getMetodoPagamento());
			
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
	public synchronized OrderBean doRetrieve(String user, int status) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		OrderBean order = new OrderBean();
		
		String searchQuery = "select * from " + OrderDao.TABLE_NAME
							+ "	where accountref = ? && stato = ?";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, user);
			preparedStatement.setInt(2, status);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date date = rs.getDate("dataOrdine");
					String dateAsString = df.format(date);
					
					order.setDataOrdine(dateAsString);
					order.setIdOrdine(rs.getInt("idOrdine"));
					order.setImportoTotale(rs.getDouble("importoTotale"));
					order.setStato(rs.getInt("stato"));
					order.setUtente(rs.getString("accountref"));
					order.setMetodoPagamento(rs.getString("creditCard"));
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

		return order;
	}

	
	@Override
	public synchronized OrderBean doRetrieveByKey(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		OrderBean order = new OrderBean();
		
		String searchQuery = "select * from " + OrderDao.TABLE_NAME
							+ "	where idOrdine = ? ";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date date = rs.getDate("dataOrdine");
					String dateAsString = df.format(date);
					
					order.setDataOrdine(dateAsString);
					order.setIdOrdine(rs.getInt("idOrdine"));
					order.setImportoTotale(rs.getDouble("importoTotale"));
					order.setStato(rs.getInt("stato"));
					order.setUtente(rs.getString("accountref"));
					order.setMetodoPagamento(rs.getString("creditCard"));
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

		return order;
	}
	
	
	@Override
	public synchronized ArrayList<OrderBean> doRetrieveAll(String organize) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<OrderBean> orders = new ArrayList<OrderBean>();

		String selectSQL = "SELECT * FROM " + OrderDao.TABLE_NAME;

		if (organize != null && !organize.equals("")) {
			selectSQL += " ORDER BY " + organize;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				OrderBean order = new OrderBean();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Date date = rs.getDate("dataOrdine");
				String dateAsString = df.format(date);
				
				order.setDataOrdine(dateAsString);
				order.setIdOrdine(rs.getInt("idOrdine"));
				order.setImportoTotale(rs.getDouble("importoTotale"));
				order.setStato(rs.getInt("stato"));
				order.setUtente(rs.getString("accountref"));
				order.setMetodoPagamento(rs.getString("creditCard"));
				orders.add(order);
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
		return orders;
	}
	

	public synchronized void doUpdateCost(int id, double cost) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		String insertSQL = "UPDATE " + OrderDao.TABLE_NAME
						+ " SET importoTotale = ? WHERE idOrdine = ?";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setDouble(1, cost);
			preparedStatement.setInt(2, id);
			
			preparedStatement.executeUpdate();
			connection.commit();
		}
		catch (Exception ex) 
		{
			System.out.println("UPDATE COST operation failed: An Exception has occurred! " + ex); 
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