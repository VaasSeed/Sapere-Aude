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
		
		String isbn = request.getParameter("ISBN");
		String nome = request.getParameter("nome");
		String casaEditrice = request.getParameter("casaEditrice");
		String autore = request.getParameter("autore");
		String categoria = request.getParameter("categoria");
		int pages = Integer.valueOf(request.getParameter("pages"));
		String lingua = request.getParameter("lingua");
		double aPrice = Double.valueOf(request.getParameter("aPrice"));
		double nPrice = Double.valueOf(request.getParameter("nPrice"));
		
		
		InputStream foto = null;
		Part filePart = request.getPart("image");
		if(filePart != null) {
			foto = filePart.getInputStream();
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
			digitalBook.setLingua(lingua);
			digitalBook.setCostoAcquisto(aPrice);
			digitalBook.setCostoNoleggio(nPrice);
			
			itemDao.doSave(item);
			digitalBookDao.doSave(digitalBook);
			
			out.println("<center><h1>OPERA AGGIUNTA</h1></center>");
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

}
