package control.common;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CreditCardBean;
import models.CreditCardDao;

/**
 * Servlet implementation class AddCreditCardServlet
 */
@WebServlet("/AddCreditCardServlet")
public class AddCreditCardServlet extends HttpServlet {
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
		CreditCardDao cardDao = new CreditCardDao();
		
		String user = (String) request.getSession().getAttribute("user");
		String numero = request.getParameter("numero");
		String codice = request.getParameter("code");
		String scadenza = request.getParameter("scadenza");
		String owner = request.getParameter("owner");
		
		try {
			CreditCardBean card = new CreditCardBean();
			card.setAccount(user);
			card.setNumeroCarta(numero);
			card.setCodiceSicurezza(codice);
			card.setScadenza(scadenza);
			card.setIntestatario(owner);
			
			cardDao.doSave(card);
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

}
