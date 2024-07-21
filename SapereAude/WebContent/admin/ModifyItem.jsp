<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Modifica</title>
<script type="text/javascript" src="../scripts/addAndModifyItem-validate.js"></script>
</head>
<body>
		<%
			String isbn = request.getParameter("isbn");
			ItemDao itemDao = new ItemDao();
			DigitalBookDao bookDao = new DigitalBookDao();
			AudioBookDao audioDao = new AudioBookDao();
			ItemBean item = itemDao.doRetrieve(isbn); 
			DigitalBookBean book = bookDao.doRetrieve(isbn);
			AudioBookBean audio = audioDao.doRetrieve(isbn);
		%>
		
		<form id="modItemForm" method="post" enctype = "multipart/form-data" action="../ModifyItemServlet" role = "form">
		<fieldset>
			<legend>Informazioni Generali</legend>
			<input name = "isbn" id = "isbn" type = "hidden" value ="<%=isbn%>" required>
			<label for="nome">Modifica il nome:</label><br>
			<input id="nome" name="nome" type="text" value = "<%=item.getNome()%>"required><br> 
			
			<label for="casaEditrice">Modifica la casa Editrice:</label><br>
			<input id="casaEditrice" name="casaEditrice" type="text" value = "<%=item.getCasaEditrice()%>" required> <br>
			
			<label for="autore">Modifica l'autore:</label><br>
			<input id="autore" name="autore" type="text" value = "<%=item.getAutore()%>" required><br>
			
			<label for="categoria">Modifica la categoria:</label><br>
			<input id="categoria" name="categoria" type="text" value = "<%=item.getCategoria()%>" required pattern = "^(\D{1,})$"
			onblur = "validateFormElem(this, document.getElementById('errorCategoria'), categoriaErrorMessage)">
			<span id = "errorCategoria"></span><br>
			
			<label for="image">Seleziona l'immagine di copertina:</label><br>
			<input id="image" name="image" type="file" required><br> 
		</fieldset>
	
		<fieldset>
		<legend>Informazioni Libro Digitale</legend>
			<label for="pages">Modifica il numero di pagine:</label><br>
			<input id="pages" name="pages" type="text" value = "<%=book.getNumPagine()%>" required pattern = "^(\d{1,})$"
			onblur = "validateFormElem(this, document.getElementById('errorPage'), pageErrorMessage)">
			<span id = "errorPage"></span><br> 
			
			<label for="linguaB">Modifica la lingua del testo:</label><br>
			<input id="linguaB" name="linguaB" type="text" value = "<%=book.getLingua()%>" required pattern = "^(\D{1,})$"
			onblur = "validateFormElem(this, document.getElementById('errorLinguaB'), linguaErrorMessage)">
			<span id = "errorLinguaB"></span><br>
			
			<label for="aPriceB">Modifica il costo per l'acquisto del libro:</label><br>
			<input id="aPriceB" name="aPriceB" type="text" value = "<%=book.getCostoAcquisto()%>" required pattern = "^(\d{1,})\.(\d{1,2})$"
			onblur = "validateFormElem(this, document.getElementById('erroraPriceB'), priceErrorMessage)">
			<span id = "erroraPriceB"></span><br>  
			
			<label for="nPriceB">Modifica il costo per il noleggio del libro:</label><br>
			<input id="nPriceB" name="nPriceB" type="text" value = "<%=book.getCostoNoleggio()%>" required pattern = "^(\d{1,})\.(\d{1,2})$"
			onblur = "validateFormElem(this, document.getElementById('errornPriceB'), priceErrorMessage)">
			<span id = "errornPriceB"></span><br>
			 
			<label for="book">Seleziona il file del libro:</label><br> 
			<input id="book" name="book" type="file" required><br> 
		</fieldset>
		
		<fieldset>
		<legend>Informazioni Audiolibro</legend>
			<label for="durata">Modifica la durata dell'audio:</label><br> 
			<input id="durata" name="durata" type="text" value = "<%=audio.getDurata()%>" required pattern = "(\d{2}):(\d{2}):(\d{2})"
			onblur = "validateFormElem(this, document.getElementById('errorDurata'), durataErrorMessage)">
			<span id = "errorDurata"></span><br>
			
			<label for="linguaA">Modifica la lingua dell'audio:</label><br>
			<input id="linguaA" name="linguaA" type="text" value = "<%=audio.getLingua()%>" required pattern = "^(\D{1,})$"
			onblur = "validateFormElem(this, document.getElementById('errorLinguaA'), linguaErrorMessage)">
			<span id = "errorLinguaA"></span><br>
			
			<label for="aPriceA">Modifica il costo per l'acquisto dell'audiolibro:</label><br>
			<input id="aPriceA" name="aPriceA" type="text" value = "<%=audio.getCostoAcquisto()%>" required pattern = "^(\d{1,})\.(\d{1,2})$"
			onblur = "validateFormElem(this, document.getElementById('erroraPriceA'), priceErrorMessage)">
			<span id = "erroraPriceA"></span><br>  
			
			<label for="nPriceA">Modifica il costo per il noleggio dell'audiolibro:</label><br>
			<input id="nPriceA" name="nPriceA" type="text" value = "<%=audio.getCostoNoleggio()%>" required pattern = "^(\d{1,})\.(\d{1,2})$"
			onblur = "validateFormElem(this, document.getElementById('errornPriceA'), priceErrorMessage)">
			<span id = "errornPriceA"></span><br> 
			 
			<label for="audiobook">Seleziona il file dell'audiolibro:</label><br>
			<input id="audiobook" name="audiobook" type="file"required><br> 
		</fieldset>
		
		<input type="submit" value="MODIFICA" class = "db-switch" onclick = "return validate()">
	</form>
		
</body>
</html>