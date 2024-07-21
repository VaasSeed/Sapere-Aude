package control.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import models.AudioBookBean;
import models.AudioBookDao;
import models.ItemBean;
import models.ItemDao;

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
		ItemDao itemDao = new ItemDao();
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			
			String ISBN = request.getParameter("ISBN");
			
			
			AudioBookBean audioBook = dao.doRetrieve(ISBN);
			ItemBean item = itemDao.doRetrieve(ISBN);

				JSONObject json = new JSONObject();
				
				String nome = item.getNome();
				String casaEditrice= item.getCasaEditrice();
				String categoria = item.getCategoria();
				String Autore = item.getAutore();
				InputStream foto = item.getFoto();
				
				
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				
				while ((bytesRead = foto.read(buffer)) != -1) {
				    outputStream.write(buffer, 0, bytesRead);
				}
				 
				byte[] imageBytes = outputStream.toByteArray();
				 
				String base64Image = Base64.getEncoder().encodeToString(imageBytes);
				 
				foto.close();
				outputStream.close();
				
				String durata = audioBook.getDurata();
				String lingua = audioBook.getLingua();
				double costoAcquisto = audioBook.getCostoAcquisto();
				double costoNoleggio = audioBook.getCostoNoleggio();    
				
				json.put("ISBN", ISBN);
				json.put("Nome", nome);
				json.put("CasaEditrice", casaEditrice);
				json.put("Categoria", categoria);
				json.put("Autore", Autore);
				json.put("Foto", base64Image);
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
