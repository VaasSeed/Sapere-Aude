package control.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.AudioBookDao;
import models.BuyDigitalBookBean;
import models.BuyDigitalBookDao;
import models.DigitalBookDao;
import models.ItemDao;
import models.OrderBean;
import models.OrderDao;
import models.RentDigitalBookBean;
import models.RentDigitalBookDao;
import models.OrderItem;

/**
 * Servlet implementation class RemoveItem
 */
@WebServlet("/RemoveItem")
public class RemoveItem extends HttpServlet {
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
		String isbn = request.getParameter("isbn");
		String cartISBN=null;
		
		HttpSession s = request.getSession();
		ArrayList<OrderItem> list = (ArrayList<OrderItem>) s.getAttribute("Cart");
		for(OrderItem elem : list) {
			cartISBN = elem.getISBNOpera();
			if(cartISBN.equals(isbn))
				list.remove(elem);
		}
		s.setAttribute("Cart", list);
		
		
		OrderDao orderDao = new OrderDao();
		AudioBookDao audioDao = new AudioBookDao();
		DigitalBookDao bookDao = new DigitalBookDao();
		BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
		RentDigitalBookDao rentDao = new RentDigitalBookDao();
		ItemDao itemDao = new ItemDao();
		
		
		try {
			audioDao.doDelete(isbn);
			bookDao.doDelete(isbn);
			
			ArrayList<OrderBean> orders = orderDao.doRetrieveAllAdmin(0, null);
			for(OrderBean order : orders) {
				int idOrder  = order.getIdOrdine();
				double total = order.getImportoTotale();
				
				ArrayList<BuyDigitalBookBean> buyBooks = buyDao.doRetrieveAllAdmin(isbn, idOrder, null);
				for(BuyDigitalBookBean buyBook : buyBooks) {
					int idAcquisto = buyBook.getIdAcquisto();
					buyDao.doDelete(idAcquisto);
					double costo = buyBook.getCosto();
					total = total-costo;
				}
				
				ArrayList<RentDigitalBookBean> rentBooks = rentDao.doRetrieveAllAdmin(isbn, idOrder, null);
				for(RentDigitalBookBean rentBook : rentBooks) {
					int idNoleggio = rentBook.getIdNoleggio();
					buyDao.doDelete(idNoleggio);
					double costo = rentBook.getCosto();
					total = total-costo;
				}
				orderDao.doUpdateCost(idOrder, total);
			}
			
			itemDao.doDelete(isbn);
		}
		catch (Exception ex) 
		{
			System.out.println("REMOVE BOOK operation failed: An Exception has occurred! " + ex); 
		}
	}

}
