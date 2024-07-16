package control.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.OrderItem;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
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
		ArrayList<OrderItem> list = null;
		
		String isbn = request.getParameter("ISBN");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String op = request.getParameter("operation");
		String p = request.getParameter("price");
		Double price = Double.valueOf(p);
		
		OrderItem item = new OrderItem();
		item.setISBNOpera(isbn);
		item.setNomeOpera(name);
		item.setTipoOpera(type);
		item.setOperazione(op);
		item.setCosto(price);
		
		list = (ArrayList<OrderItem>) request.getSession().getAttribute("Cart");
		if(list == null) {
			list = new ArrayList<OrderItem>();
			list.add(item);
			request.getSession().setAttribute("Cart", list);
		}
		else {
			String elementISBN;
			String elementType;
			String elementOperation;
			Boolean check = Boolean.TRUE;
			for(OrderItem element : list) {
				elementISBN = element.getISBNOpera();
				elementType = element.getTipoOpera();
				elementOperation = element.getOperazione();
				if(elementISBN.equals(isbn) && elementType.equals(type) && elementOperation.equals(op)) {
					check = Boolean.FALSE;
					break;
				}
			}
			if(check) {
				list.add(item);
				request.getSession().setAttribute("Cart", list);
			}
		}
	}

}
