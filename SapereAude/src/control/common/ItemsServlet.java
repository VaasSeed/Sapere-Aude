package control.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import models.ItemBean;
import models.ItemDao;

/**
 * Servlet implementation class ItemsServlet
 */

@WebServlet("/ItemsServlet")
public class ItemsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		ItemDao dao = new ItemDao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
			ArrayList<ItemBean> items = dao.doRetrieveAll("mediaVoti");
			JSONArray jArray= new JSONArray();
			for(ItemBean item : items) {
				JSONObject json = new JSONObject();
				String ISBN = item.getISBN();
				String nome = item.getNome();
				String casaEditrice = item.getCasaEditrice();
				String categoria = item.getCategoria();
				double mediaVoti = item.getMediaVoti();
				String Autore = item.getAutore();    
				
				json.put("ISBN", ISBN);
				json.put("Nome", nome);
				json.put("CasaEditrice", casaEditrice);
				json.put("Categoria", categoria);
				json.put("MediaVoti", mediaVoti);
				json.put("Autore", Autore);
				jArray.add(json);
			}
				out.print(jArray.toString());

			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		};
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
