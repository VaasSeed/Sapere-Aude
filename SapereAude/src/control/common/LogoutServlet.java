package control.common;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BuyDigitalBookBean;
import models.BuyDigitalBookDao;
import models.OrderBean;
import models.OrderDao;
import models.OrderItem;
import models.RentDigitalBookBean;
import models.RentDigitalBookDao;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
		if(isAdmin == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
		
		String user = (String) request.getSession().getAttribute("user");
		
		try {
			saveCart(request, user);
		}
		catch (Exception ex) 
		{
			System.out.println("ERROR: An Exception has occurred! " + ex); 
		}
		
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void saveCart(HttpServletRequest request, String user) throws SQLException{
		ArrayList<OrderItem> items = null;
		items = (ArrayList<OrderItem>) request.getSession().getAttribute("Cart");
		
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.MONTH, 1);	  
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String todayAsString = dateFormat.format(today);
		String rentEndAsString = dateFormat.format(c.getTime()); 
		
		OrderDao daoOrder = new OrderDao();
		
		try {
			OrderBean order = null;
			if(items != null) {
				order = daoOrder.doRetrieve(user, 0);
				if(order == null){
					order = new OrderBean();
					order.setDataOrdine(todayAsString);
					order.setImportoTotale(0.00);
					order.setStato(0);
					order.setUtente(user);	
					daoOrder.doSave(order);
				}
				order = daoOrder.doRetrieve(user, 0);
				int idOrder = order.getIdOrdine();
				double total = order.getImportoTotale(); 
				
				for(OrderItem item : items) {
					String ISBNOpera = item.getISBNOpera();
					String nomeOpera = item.getNomeOpera();
					String tipoOpera = item.getTipoOpera();
					String operazione = item.getOperazione();
					double costo = item.getCosto();
						
					if(operazione.equals("acquisto")) {
						BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
						
						Boolean check = buyDao.alreadySaved(ISBNOpera, idOrder, tipoOpera);
						
						if(check) {
							BuyDigitalBookBean buy = new BuyDigitalBookBean();
							buy.setDataAcquisto(todayAsString);
							buy.setTipoOpera(tipoOpera);
							buy.setISBNOpera(ISBNOpera);
							buy.setNomeOpera(nomeOpera);
							buy.setOrdine(idOrder);
							buy.setCosto(costo);
							
							buyDao.doSave(buy);
							total = total + costo;
						}
					}
					else if(operazione.equals("noleggio")){
						RentDigitalBookDao rentDao = new RentDigitalBookDao();
						
						Boolean check = rentDao.alreadySaved(ISBNOpera, idOrder, tipoOpera);
						if(check) {
							RentDigitalBookBean rent = new RentDigitalBookBean();
							rent.setDataInizioNoleggio(todayAsString);
							rent.setDataFineNoleggio(rentEndAsString);
							rent.setTipoOpera(tipoOpera);
							rent.setISBNOpera(ISBNOpera);
							rent.setNomeOpera(nomeOpera);
							rent.setOrdine(idOrder);
							rent.setCosto(costo);	
							
							rentDao.doSave(rent);
							total = total + costo;
						}
					}
				}
					daoOrder.doUpdateCost(idOrder, total);
			}
		}
		catch (Exception ex) 
		{
			System.out.println("ERROR: An Exception has occurred! " + ex); 
		}
		
	}
}
