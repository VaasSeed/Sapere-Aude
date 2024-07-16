package control.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.BuyDigitalBookDao;
import models.OrderBean;
import models.OrderDao;
import models.OrderItem;
import models.RentDigitalBookDao;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
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
		int id = Integer.valueOf(request.getParameter("id"));
		String op = request.getParameter("op");
		
		HttpSession s = request.getSession();
		String user =(String) s.getAttribute("user");
		ArrayList<OrderItem> list = (ArrayList<OrderItem>) s.getAttribute("Cart");
		
		double costoItem = 0.00;
		int itemId;
		
		for(OrderItem item : list) {
			itemId = item.getId();
			costoItem = item.getCosto();
			if(itemId == id) {
				list.remove(item);
				break;
			}	
		}
		try {
			if(op.equals("acquisto")) {
				BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
				buyDao.doDelete(id);
			}
			else if(op.equals("noleggio")) {
				RentDigitalBookDao rentDao = new RentDigitalBookDao();
				rentDao.doDelete(id);
			}
			
			//update order cost
			OrderDao orderDao = new OrderDao();
			OrderBean order = orderDao.doRetrieve(user, 0);
			if(order != null) {
				int idOrdine = order.getIdOrdine();
				double old = order.getImportoTotale();
				double updated = old - costoItem;
				orderDao.doUpdateCost(idOrdine, updated);
			}
		}
		catch(SQLException ex) {
			System.out.println("DELETE operation failed: An Exception has occurred! " + ex); 
		}
		
	}

}
