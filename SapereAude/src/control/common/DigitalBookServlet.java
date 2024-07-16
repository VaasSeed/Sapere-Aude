package control.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import models.DigitalBookBean;
import models.DigitalBookDao;

/**
 * Servlet implementation class DigitalBookServlet
 */
@WebServlet("/DigitalBookServlet")
public class DigitalBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DigitalBookDao dao = new DigitalBookDao();
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			
			String ISBN = request.getParameter("ISBN");
			
			DigitalBookBean digitalBook = dao.doRetrieve(ISBN);
			
				
				JSONObject json = new JSONObject();
				
				int numPagine = digitalBook.getNumPagine();
				String lingua = digitalBook.getLingua();
				double costoAcquisto = digitalBook.getCostoAcquisto();
				double costoNoleggio = digitalBook.getCostoNoleggio();    
				
				json.put("NumPagine", numPagine);
				json.put("Lingua", lingua);
				json.put("CostoAcquisto", costoAcquisto);
				json.put("CostoNoleggio", costoNoleggio);
				
				out.print(json.toString());

			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		};
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
