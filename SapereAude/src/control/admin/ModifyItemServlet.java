package control.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.AudioBookBean;
import models.AudioBookDao;
import models.BuyDigitalBookBean;
import models.BuyDigitalBookDao;
import models.DigitalBookBean;
import models.DigitalBookDao;
import models.ItemBean;
import models.ItemDao;
import models.OrderBean;
import models.OrderDao;
import models.OrderItem;
import models.RentDigitalBookBean;
import models.RentDigitalBookDao;

/**
 * Servlet implementation class ModifyItemServlet
 */
@WebServlet("/ModifyItemServlet")
@MultipartConfig(maxFileSize = 16177215)
public class ModifyItemServlet extends HttpServlet {
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
		
		String isbn = request.getParameter("isbn");
		
		//generic
		ItemDao itemDao = new ItemDao(); 
		ItemBean updatedItem = new ItemBean();
		
		String nome = request.getParameter("nome");
		updatedItem.setNome(nome);
		
		String casaEditrice = request.getParameter("casaEditrice");
		updatedItem.setCasaEditrice(casaEditrice);
		
		String autore = request.getParameter("autore");
		updatedItem.setAutore(autore);
		
		String categoria = request.getParameter("categoria");
		updatedItem.setCategoria(categoria);
		
		InputStream foto = null;
		Part filePartF = request.getPart("image");
		if(filePartF != null) {
			foto = filePartF.getInputStream();
			updatedItem.setFoto(foto);
		}

		
		//digital book
		DigitalBookDao bookDao = new DigitalBookDao(); 
		DigitalBookBean updatedBook = new DigitalBookBean();
		
		int pages = Integer.valueOf(request.getParameter("pages"));
		updatedBook.setNumPagine(pages);
	
		String linguaB = request.getParameter("linguaB");
		updatedBook.setLingua(linguaB);
		
		double aPriceB = Double.valueOf(request.getParameter("aPriceB"));
		updatedBook.setCostoAcquisto(aPriceB);
		
		double nPriceB = Double.valueOf(request.getParameter("nPriceB"));
		updatedBook.setCostoNoleggio(nPriceB);
		
		InputStream book = null;
		Part filePartB = request.getPart("book");
		if(filePartB != null) {
			book = filePartB.getInputStream();
			updatedBook.setBookFile(book);
		}
		
		
		//audiobook
		AudioBookDao audioDao = new AudioBookDao(); 
		AudioBookBean updatedAudio = new AudioBookBean();
		
		String durata = request.getParameter("durata");
		updatedAudio.setDurata(durata);
	
		String linguaA = request.getParameter("linguaA");
		updatedAudio.setLingua(linguaA);
		
		double aPriceA = Double.valueOf(request.getParameter("aPriceA"));
		updatedAudio.setCostoAcquisto(aPriceA);
		
		double nPriceA = Double.valueOf(request.getParameter("nPriceA"));
		updatedAudio.setCostoNoleggio(nPriceA);
		
		InputStream audiobook = null;
		Part filePartA = request.getPart("audiobook");
		if(filePartA != null) {
			audiobook = filePartA.getInputStream();
			updatedAudio.setAudioFile(audiobook);
		}

		ArrayList<OrderItem> list = (ArrayList<OrderItem>) request.getSession().getAttribute("Cart"); 
		for(OrderItem elem : list) {
			String isbnElem = elem.getISBNOpera();
			int idElem = elem.getId();
			String nomeElem = elem.getNomeOpera();
			String tipoElem = elem.getTipoOpera();
			String operazioneElem = elem.getOperazione();
			double costoElem = elem.getCosto();
			
			if(isbnElem.equals(isbn)) {
				OrderItem updated = new OrderItem();
				updated.setISBNOpera(isbnElem);
				updated.setId(idElem);
				updated.setNomeOpera(nome);
				updated.setOperazione(operazioneElem);
				updated.setTipoOpera(tipoElem);
				if(tipoElem.equals("libro")){
					if(operazioneElem.equals("acquisto")) {
						updated.setCosto(aPriceB);
					}
					else if(operazioneElem.equals("noleggio")) {
						updated.setCosto(nPriceB);
					}
				}
				else if(tipoElem.equals("audiolibro")){
					if(operazioneElem.equals("acquisto")) {
						updated.setCosto(aPriceA);
					}
					else if(operazioneElem.equals("noleggio")) {
						updated.setCosto(nPriceA);
					}
				}
				list.remove(elem);
				list.add(updated);
			}
		}
		request.getSession().setAttribute("Cart", list);
		
		OrderDao orderDao = new OrderDao();
		BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
		RentDigitalBookDao rentDao = new RentDigitalBookDao();
		
		try {
			ArrayList<OrderBean> orders = orderDao.doRetrieveAllAdmin(0, null);
			for(OrderBean order : orders){
				int idOrder = order.getIdOrdine();
				
				ArrayList<BuyDigitalBookBean> buyBooks= buyDao.doRetrieveAllAdmin(isbn, idOrder, null);
				for(BuyDigitalBookBean buyBook : buyBooks) {
					String type = buyBook.getTipoOpera();
					int idAcquisto = buyBook.getIdAcquisto();
					if(type.equals("libro"))
						buyDao.updateAdmin(idAcquisto, nome, aPriceB);
					else if(type.equals("audiolibro"))
						buyDao.updateAdmin(idAcquisto, nome, aPriceA);
					
				}
				
				ArrayList<RentDigitalBookBean> rentBooks= rentDao.doRetrieveAllAdmin(isbn, idOrder, null);
				for(RentDigitalBookBean rentBook : rentBooks) {
					String typeR = rentBook.getTipoOpera();
					int idNoleggio = rentBook.getIdNoleggio();
					if(typeR.equals("libro"))
						rentDao.updateAdmin(idNoleggio, nome, nPriceB);
					else if(typeR.equals("audiolibro"))
						buyDao.updateAdmin(idNoleggio, nome, nPriceA);	
				}
				
			}
			itemDao.doUpdate(isbn, updatedItem);
			
			bookDao.doUpdate(isbn, updatedBook);
			
			audioDao.doUpdate(isbn, updatedAudio);
			
		} catch (Exception ex) {
			System.out.println("UPDATE operation failed: An Exception has occurred! " + ex);
		}
		out.println("<center><h1>OPERA MODIFICATA</h1></center>");
	}

}
