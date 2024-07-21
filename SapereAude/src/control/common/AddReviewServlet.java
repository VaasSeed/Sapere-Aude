package control.common;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ReviewBean;
import models.ReviewDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class AddReviewServlet
 */
@WebServlet("/AddReviewServlet")
public class AddReviewServlet extends HttpServlet {
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
		String user = (String) request.getSession().getAttribute("user");
		String isbn = request.getParameter("isbn");
		String titolo = request.getParameter("titolo");
		String testo = request.getParameter("testo");
		
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateAsString = df.format(c.getTime());
		
		ReviewDao reviewDao = new ReviewDao();
		ReviewBean review = new ReviewBean();
		
		review.setAccount(user);
		review.setData(dateAsString);
		review.setISBNOpera(isbn);
		review.setTitolo(titolo);
		review.setTesto(testo);
		
		try {
			reviewDao.doSave(review);
			response.sendRedirect(request.getContextPath() + "/common/DigitalBook.jsp?isbn="+isbn);
		}
		catch (Exception ex) 
		{
			System.out.println("ADD REVIEW operation failed: An Exception has occurred! " + ex); 
		}

	}

}
