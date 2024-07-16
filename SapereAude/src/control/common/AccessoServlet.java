package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.BuyDigitalBookBean;
import models.BuyDigitalBookDao;
import models.OrderBean;
import models.OrderDao;
import models.OrderItem;
import models.RentDigitalBookBean;
import models.RentDigitalBookDao;
import models.UserBean;
import models.UserDao;
/**
 * Servlet implementation class AccessoServlet
 */
@WebServlet("/AccessoServlet")
public class AccessoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDao();
		
		RequestDispatcher dispatcherToLogin = request.getRequestDispatcher("/accesso.jsp");
		List<String> errors = new ArrayList<String>(); 
		String username = request.getParameter("username");
		String pswd = request.getParameter("pswd");
		
		username = username.trim();
		pswd = pswd.trim();
		
		if(username == null || username.isEmpty())
			errors.add("Inserire lo username");
		if(pswd == null || pswd.isEmpty())
			errors.add("Inserire la password");
		
		if(!errors.isEmpty()){
			request.setAttribute("errors", errors);
			dispatcherToLogin.forward(request, response);
			return;
		}
		
		String hashPassword = toHash(pswd);
		
		try {
			UserBean user = dao.doRetrieveLog(username, hashPassword);
			if(user.isValid()) {
				HttpSession s = request.getSession();
				s.setAttribute("user", username);
				s.setAttribute("logged", Boolean.TRUE);
				
				restoreCart(request, username);
				
				if(user.isAmministratore()) {
					s.setAttribute("isAdmin", Boolean.TRUE);
					response.sendRedirect("common/Home.jsp");
				}
				else {
				s.setAttribute("isAdmin", Boolean.FALSE);
				response.sendRedirect("common/Home.jsp");
				}
			}
			else {
				errors.add("Username o Password errati.");
				request.setAttribute("errors", errors);
				dispatcherToLogin.forward(request, response);
				return;
			}
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
	
	private void restoreCart(HttpServletRequest request, String username) throws SQLException{
		ArrayList<OrderItem> cart = new ArrayList<OrderItem>();
		OrderDao daoOrder = new OrderDao();
		try {
			
			OrderBean order = daoOrder.doRetrieve(username, 0);
			double costo;
			
			if(order != null) {
				int idOrder = order.getIdOrdine();
				
				RentDigitalBookDao rentDao = new RentDigitalBookDao();
				ArrayList<RentDigitalBookBean> rentItems= rentDao.doRetrieveAll(idOrder, null);
				if(rentItems != null) {
					for(RentDigitalBookBean rentItem : rentItems) {
						OrderItem itemCart = new OrderItem();
						itemCart.setId(rentItem.getIdNoleggio());
						itemCart.setISBNOpera(rentItem.getISBNOpera());
						itemCart.setNomeOpera(rentItem.getNomeOpera());
						itemCart.setTipoOpera(rentItem.getTipoOpera());
						itemCart.setOperazione("noleggio");
						costo = rentItem.getCosto();
						itemCart.setCosto(costo);
						cart.add(itemCart);
					}
					request.getSession().setAttribute("Cart", cart);
				}
				
				BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
				ArrayList<BuyDigitalBookBean> buyItems = buyDao.doRetrieveAll(idOrder, null);
				if(buyItems != null) {
					for(BuyDigitalBookBean buyItem : buyItems) {
						OrderItem itemCart = new OrderItem();
						itemCart.setId(buyItem.getIdAcquisto());
						itemCart.setISBNOpera(buyItem.getISBNOpera());
						itemCart.setNomeOpera(buyItem.getNomeOpera());
						itemCart.setTipoOpera(buyItem.getTipoOpera());
						itemCart.setOperazione("acquisto");
						costo = buyItem.getCosto();
						itemCart.setCosto(costo);
						cart.add(itemCart);
					}
					request.getSession().setAttribute("Cart", cart);
				}
			}
		}
		catch (Exception ex) 
		{
			System.out.println("ERROR: An Exception has occurred in Restore Cart! " + ex); 
		}
	}
	
	private String toHash(String password) {
		String hashString = null;
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			hashString = "";
			for (int i = 0; i < hash.length; i++) {
				hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3);
			}
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return hashString;
	}

}
