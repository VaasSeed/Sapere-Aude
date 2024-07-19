package control.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.BuyDigitalBookBean;
import models.BuyDigitalBookDao;
import models.ItemBean;
import models.ItemDao;
import models.OrderBean;
import models.OrderDao;
import models.RentDigitalBookBean;
import models.RentDigitalBookDao;

/**
 * Servlet implementation class UserItems
 */
@WebServlet("/UserItems")
public class UserItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getSession().getAttribute("user");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		OrderDao orderDao = new OrderDao();
		ItemDao itemDao = new ItemDao();
		BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
		RentDigitalBookDao rentDao = new RentDigitalBookDao();
		
		int idOrder;
		String isbn;
		try {
			ArrayList<OrderBean> orders = orderDao.doRetrieveAll(user, 1, "dataOrdine");
			JSONArray jArray= new JSONArray();
			
			for(OrderBean order : orders) {
				idOrder = order.getIdOrdine();
				ArrayList<RentDigitalBookBean> rentItems = rentDao.doRetrieveAll(idOrder, null);
				
				for(RentDigitalBookBean rentItem : rentItems) {
					isbn = rentItem.getISBNOpera(); 
					ItemBean item = itemDao.doRetrieve(isbn);
					
					if(item != null) {
						String nome = item.getNome();
						String autore = item.getAutore();
						String tipo = rentItem.getTipoOpera();
						String fineNoleggio = rentItem.getDataFineNoleggio();
						
						String end = fineNoleggio.substring(6);
						end += "-" + fineNoleggio.substring(3, 5);
						end += "-" + fineNoleggio.substring(0,2);
						
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c1 = Calendar.getInstance();
						Calendar c2 = Calendar.getInstance();
						
						c1.setTime(sdf.parse(end));
						c1.add(Calendar.DATE, 1);
						c2.setTime(c2.getTime());

						
						if(c1.after(c2) || c1.equals(c2)) {
							JSONObject json = new JSONObject();
							
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
							
							json.put("ISBN", isbn);
							json.put("Foto", base64Image);
							json.put("Nome", nome);
							json.put("Autore", autore);
							json.put("FineNoleggio", fineNoleggio);
							json.put("Tipo", tipo);
							json.put("Operazione", "noleggiata");
							jArray.add(json);
						}
					}
				}
				
				ArrayList<BuyDigitalBookBean> buyItems = buyDao.doRetrieveAll(idOrder, null);
				
				for(BuyDigitalBookBean buyItem : buyItems) {
					isbn = buyItem.getISBNOpera(); 
					ItemBean item = itemDao.doRetrieve(isbn);
					
					if(item != null) {
						String nome = item.getNome();
						String autore = item.getAutore();
						String tipo = buyItem.getTipoOpera();
						JSONObject json = new JSONObject();
						
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
						
						json.put("ISBN", isbn);
						json.put("Foto", base64Image);
						json.put("Nome", nome);
						json.put("Autore", autore);
						json.put("FineNoleggio", "no");
						json.put("Tipo", tipo);
						json.put("Operazione", "acquistata");
						jArray.add(json);
					}
				
				}
			}
			out.print(jArray.toString());
			
		} catch (Exception ex) {
			System.out.println("SELECT ALL USER'S ITEMS  failed: An Exception has occurred! " + ex); 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
