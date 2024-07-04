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
			if(user.isValid() && user.isAmministratore()) {
				HttpSession s = request.getSession();
				s.setAttribute("isAdmin", Boolean.TRUE);
				s.setAttribute("logged", Boolean.TRUE);
				response.sendRedirect("common/Home.jsp");
			}
			else if(user.isValid()) {
				HttpSession s = request.getSession();
				s.setAttribute("isAdmin", Boolean.FALSE);
				s.setAttribute("logged", Boolean.TRUE);
				response.sendRedirect("common/Home.jsp");
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
