package control.common;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CreditCardDao;

/**
 * Servlet implementation class RemoveCreditCardServlet
 */
@WebServlet("/RemoveCreditCardServlet")
public class RemoveCreditCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("num");
		
		try {
				CreditCardDao cardDao = new CreditCardDao();
				cardDao.doDelete(number);
		}
		catch(SQLException ex) {
			System.out.println("DELETE operation failed: An Exception has occurred! " + ex); 
		}
	}

}
