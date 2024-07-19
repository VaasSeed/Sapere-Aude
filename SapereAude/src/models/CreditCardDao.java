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

public class CreditCardDao implements CreditCardDaoInterface {

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
	
	
	private static final String TABLE_NAME = "creditcard";
	
	
	@Override
	public synchronized void doSave(CreditCardBean card) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + CreditCardDao.TABLE_NAME
						+ " (numeroCarta, codiceSicurezza, scadenza, intestatario, accountref) VALUES (?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, card.getNumeroCarta());
			preparedStatement.setString(2, card.getCodiceSicurezza());
			preparedStatement.setString(3, card.getScadenza());
			preparedStatement.setString(4, card.getIntestatario());
			preparedStatement.setString(5, card.getAccount());
			
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
	public synchronized CreditCardBean doRetrieve(String cardNumber) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		CreditCardBean card = new CreditCardBean();
		
		String searchQuery = "select * from " + CreditCardDao.TABLE_NAME
							+ "	where numeroCarta = ?";
		
		try
			{
			//connect to DB 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(searchQuery);
			preparedStatement.setString(1, cardNumber);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			boolean more = rs.next();
				if (!more) 
					return null;
				else if (more) 
				{
					card.setNumeroCarta(rs.getString("numeroCarta"));
					card.setCodiceSicurezza(rs.getString("codiceSicurezza"));
					card.setScadenza(rs.getString("scadenza"));
					card.setIntestatario(rs.getString("intestatario"));
					card.setAccount(rs.getString("accountref"));
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

		return card;
	}
	
	@Override
	public synchronized void doDelete(String cardNumber) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = " DELETE FROM " + CreditCardDao.TABLE_NAME + " WHERE numeroCarta = ? ";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, cardNumber);

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
	public synchronized ArrayList<CreditCardBean> doRetrieveAll(String account, String sort) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<CreditCardBean> cards = new ArrayList<CreditCardBean>();

		String selectSQL = "SELECT * FROM " + CreditCardDao.TABLE_NAME
						   + " WHERE accountref = ?";

		if (sort != null && !sort.equals("")) {
			selectSQL += " ORDER BY " + sort;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1, account);
			
			ResultSet rs = preparedStatement.executeQuery();
				
			while (rs.next()) {
				CreditCardBean card = new CreditCardBean();
				card.setNumeroCarta(rs.getString("numeroCarta"));
				card.setCodiceSicurezza(rs.getString("codiceSicurezza"));
				card.setScadenza(rs.getString("scadenza"));
				card.setIntestatario(rs.getString("intestatario"));
				card.setAccount(rs.getString("accountref"));
				cards.add(card);
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
		return cards;
	}
}