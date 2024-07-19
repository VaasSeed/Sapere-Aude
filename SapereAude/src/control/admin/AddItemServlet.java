package control.admin;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import models.AudioBookBean;
import models.AudioBookDao;
import models.DigitalBookBean;
import models.DigitalBookDao;
import models.ItemBean;
import models.ItemDao;

/**
 * Servlet implementation class AddItemServlet
 */
@WebServlet("/AddItemServlet")
@MultipartConfig(maxFileSize = 16177215)
public class AddItemServlet extends HttpServlet {
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
		
		ItemDao itemDao = new ItemDao();
		DigitalBookDao digitalBookDao = new DigitalBookDao();
		AudioBookDao audioBookDao = new AudioBookDao();
		
		//generic item info 
		String isbn = request.getParameter("ISBN");
		String nome = request.getParameter("nome");
		String casaEditrice = request.getParameter("casaEditrice");
		String autore = request.getParameter("autore");
		String categoria = request.getParameter("categoria");
		
		//digital book info
		int pages = Integer.valueOf(request.getParameter("pages"));
		System.out.println(pages);
		String linguaBook = request.getParameter("linguaB");
		double aPriceBook = Double.valueOf(request.getParameter("aPriceB"));
		double nPriceBook = Double.valueOf(request.getParameter("nPriceB"));
		
		//audio book info
		String durata = request.getParameter("durata");
		String linguaAudio = request.getParameter("linguaA");
		double aPriceAudio = Double.valueOf(request.getParameter("aPriceA"));
		double nPriceAudio = Double.valueOf(request.getParameter("nPriceA"));
		
		InputStream book = null;
		Part filePartB = request.getPart("book");
		if(filePartB != null) {
			book = filePartB.getInputStream();
		}
		
		InputStream audiobook = null;
		Part filePartA = request.getPart("audiobook");
		if(filePartA != null) {
			audiobook = filePartA.getInputStream();
		}
		
		InputStream foto = null;
		Part filePartF = request.getPart("image");
		if(filePartF != null) {
			foto = filePartF.getInputStream();
		}

		try {
			ItemBean item = new ItemBean();
			item.setISBN(isbn);
			item.setNome(nome);
			item.setCasaEditrice(casaEditrice);
			item.setAutore(autore);
			item.setCategoria(categoria);
			item.setFoto(foto);
			
			DigitalBookBean digitalBook = new DigitalBookBean();
			digitalBook.setISBNOpera(isbn);
			digitalBook.setNumPagine(pages);
			digitalBook.setLingua(linguaBook);
			digitalBook.setCostoAcquisto(aPriceBook);
			digitalBook.setCostoNoleggio(nPriceBook);
			digitalBook.setBookFile(book);
			
			AudioBookBean audioBook = new AudioBookBean();
			audioBook.setISBNOpera(isbn);
			audioBook.setDurata(durata);
			audioBook.setLingua(linguaAudio);
			audioBook.setCostoAcquisto(aPriceAudio);
			audioBook.setCostoNoleggio(nPriceAudio);
			audioBook.setAudioFile(audiobook);
			
			itemDao.doSave(item);
			digitalBookDao.doSave(digitalBook);
			audioBookDao.doSave(audioBook);
			
			out.println("<center><h1>OPERA AGGIUNTA</h1></center>");
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

}
