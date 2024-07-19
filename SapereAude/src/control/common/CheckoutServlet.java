package control.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
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
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String user = (String) request.getSession().getAttribute("user");
		
		ArrayList<OrderItem> items = (ArrayList<OrderItem>) request.getSession().getAttribute("Cart");
		
		String cardNumber = (String) request.getParameter("card");
		
		OrderDao orderDao = new OrderDao();
		BuyDigitalBookDao buyDao= new BuyDigitalBookDao();
		RentDigitalBookDao rentDao= new RentDigitalBookDao();
		
		try {
			OrderBean order = orderDao.doRetrieve(user, 0);
			
			if(order == null){
				order = new OrderBean();
				order.setImportoTotale(0.00);
				order.setDataOrdine(null);
				order.setStato(0);
				order.setUtente(user);	
				orderDao.doSave(order);
			}
			
			order = orderDao.doRetrieve(user, 0);
			int idOrder = order.getIdOrdine();
			double total = order.getImportoTotale();
			orderDao.updateDate(idOrder);
			orderDao.setCard(idOrder, cardNumber);
			orderDao.updateStatus(idOrder);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			for(OrderItem item : items) {
				String ISBNOpera = item.getISBNOpera();
				String nomeOpera = item.getNomeOpera();
				String tipoOpera = item.getTipoOpera();
				double costo = item.getCosto();
				int id = item.getId();
				String op = item.getOperazione();
				
				
				if(op.equals("acquisto")) {
					
					Boolean check = buyDao.alreadySaved(ISBNOpera, idOrder, tipoOpera);
					
					if(check) {
						Calendar c = Calendar.getInstance();
						String dateAsString = df.format(c.getTime());
						
						BuyDigitalBookBean buy = new BuyDigitalBookBean();
						
						buy.setTipoOpera(tipoOpera);
						buy.setDataAcquisto(dateAsString);
						buy.setISBNOpera(ISBNOpera);
						buy.setNomeOpera(nomeOpera);
						buy.setOrdine(idOrder);
						buy.setCosto(costo);
						
						buyDao.doSave(buy);
						total = total + costo;
					}
					else {
						buyDao.updateDate(id);
					}
				}
				else if(op.equals("noleggio")) {
					
					Boolean check = rentDao.alreadySaved(ISBNOpera, idOrder, tipoOpera);
					
					if(check) {
						Calendar c2 = Calendar.getInstance();
						String beginAsString = df.format(c2.getTime());
						c2.setTime(c2.getTime());
						c2.add(Calendar.MONTH, 1);
						String endAsString = df.format(c2.getTime());
						
						RentDigitalBookBean rent = new RentDigitalBookBean();
						
						rent.setTipoOpera(tipoOpera);
						rent.setDataInizioNoleggio(beginAsString);
						rent.setDataFineNoleggio(endAsString);
						rent.setISBNOpera(ISBNOpera);
						rent.setNomeOpera(nomeOpera);
						rent.setOrdine(idOrder);
						rent.setCosto(costo);	
						
						rentDao.doSave(rent);
						total = total + costo;
					}
					else {
						rentDao.updateDate(id);
					}
				}
			}
			orderDao.doUpdateCost(idOrder, total);
			request.getSession().removeAttribute("Cart");
			out.println("<center><h1>ORDINE COMPLETATO</h1></center>");
			
		} catch (Exception ex) {
			System.out.println("CHECKOUT operation failed: An Exception has occurred! " + ex);
		}
	}

}
