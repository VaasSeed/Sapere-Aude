package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.UserBean;
import models.UserDao;

/**
 * Servlet implementation class RegistrazioneServlet
 */
@WebServlet("/RegistrazioneServlet")
public class RegistrazioneServlet extends HttpServlet {
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
		
		String nome = request.getParameter("name");
		String cognome = request.getParameter("surname");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String pswd = request.getParameter("pswd");
		String bDate = request.getParameter("bDate");
		String birthDate = bDate.substring(6);
		birthDate += "-" + bDate.substring(3, 5);
		birthDate += "-" + bDate.substring(0,2);
		
		String hashPassword = toHash(pswd);
		try {
			UserBean user = new UserBean();
			user.setNome(nome);
			user.setCognome(cognome);
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(hashPassword);
			user.setDataDiNascita(birthDate);
			dao.doSave(user);
			response.sendRedirect(request.getContextPath() + "/accesso.jsp");
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
