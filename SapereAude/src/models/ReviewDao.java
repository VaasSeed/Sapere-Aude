package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReviewDao implements ReviewDaoInterface {

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
	
	
	private static final String TABLE_NAME = "recensione";
	
	
	@Override
	public synchronized void doSave(ReviewBean review) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ReviewDao.TABLE_NAME
						+ " (ISBNOpera, titolo, testo, data, accountref) VALUES (?, ?, ?, ?, ?) ";
		
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, review.getISBNOpera());
			preparedStatement.setString(2, review.getTitolo());
			preparedStatement.setString(3, review.getTesto());
			preparedStatement.setString(4, review.getData());
			preparedStatement.setString(5, review.getAccount());
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
	public synchronized ReviewBean doRetrieve(String ISBN) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ReviewBean review = new ReviewBean();
		
		String searchQuery = "select * from " + ReviewDao.TABLE_NAME
							+ "	where ISBNOpera = ?";
		
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
					Date date = rs.getDate("data");
					if(date != null) {
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						String dateAsString = df.format(date);
						review.setData(dateAsString);
					}
					
					review.setISBNOpera(rs.getString("ISBNOpera"));
					review.setTitolo(rs.getString("titolo"));
					review.setTesto(rs.getString("testo"));
					review.setAccount(rs.getString("accountref"));
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

		return review;
	}
	
	@Override
	public synchronized ArrayList<ReviewBean> doRetrieveAll(String isbn, String order) throws SQLException {
	
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();

		String selectSQL = "SELECT * FROM " + ReviewDao.TABLE_NAME
						 + " WHERE ISBNOpera = ?";

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order + " DESC";
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, isbn);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			while (rs.next()) {
				ReviewBean review = new ReviewBean();
				
				Date date = rs.getDate("data");
				if(date != null) {
					String dateAsString = df.format(date);
					review.setData(dateAsString);
				}
				
				review.setISBNOpera(rs.getString("ISBNOpera"));
				review.setTitolo(rs.getString("titolo"));
				review.setTesto(rs.getString("testo"));
				review.setAccount(rs.getString("accountref"));
				reviews.add(review);
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
		return reviews;
	}
}