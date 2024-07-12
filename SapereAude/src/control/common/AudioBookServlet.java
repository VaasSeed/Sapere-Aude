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

import models.AudioBookBean;
import models.AudioBookDao;

/**
 * Servlet implementation class AudioBookServlet
 */
@WebServlet("/AudioBookServlet")
public class AudioBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AudioBookDao dao = new AudioBookDao();
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			
			String ISBN = request.getParameter("ISBN");
			
			
			AudioBookBean audioBook = dao.doRetrieve(ISBN);

				JSONObject json = new JSONObject();
				
				String durata = audioBook.getDurata();
				String lingua = audioBook.getLingua();
				double costoAcquisto = audioBook.getCostoAcquisto();
				double costoNoleggio = audioBook.getCostoNoleggio();    
				
				json.put("Durata", durata);
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
