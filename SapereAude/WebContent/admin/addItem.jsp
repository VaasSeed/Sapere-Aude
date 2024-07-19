	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AGGIUNGI OPERA</title>
</head>
<body>

	<form id="itemForm" method="post" enctype = "multipart/form-data" action="../AddItemServlet" role = "form">
		<fieldset>
			<legend>Informazioni Generali</legend>
			<label for="ISBN">Inserisci ISBN:</label><br>
			<input id="ISBN" name="ISBN" type="text"><br>
			
			<label for="nome">Inserisci il nome:</label><br>
			<input id="nome" name="nome" type="text" required><br> 
			
			<label for="casaEditrice">Inserisci la casa Editrice:</label><br>
			<input id="casaEditrice" name="casaEditrice" type="text" required> <br>
			
			<label for="autore">Inserisci l'autore:</label><br>
			<input id="autore" name="autore" type="text" required><br>
			
			<label for="categoria">Inserisci la categoria:</label><br>
			<input id="categoria" name="categoria" type="text" required><br>
			
			<label for="image">Inserisci l'immagine di copertina:</label><br>
			<input id="image" name="image" type="file" required><br> 
		</fieldset>
	
		<fieldset>
		<legend>Informazioni Libro Digitale</legend>
			<label for="pages">Inserisci il numero di pagine:</label><br>
			<input id="pages" name="pages" type="text" required><br> 
			
			<label for="linguaB">Inserisci la lingua del testo:</label><br>
			<input id="linguaB" name="linguaB" type="text" required><br>
			
			<label for="aPriceB">Inserisci il costo per l'acquisto del libro:</label><br>
			<input id="aPriceB" name="aPriceB" type="text" required><br>  
			
			<label for="nPriceB">Inserisci il costo per il noleggio del libro:</label><br>
			<input id="nPriceB" name="nPriceB" type="text" required><br> 
			<label for="book">Carica il file del libro:</label><br> 
			<input id="book" name="book" type="file" required><br> 
		</fieldset>
		
		<fieldset>
		<legend>Informazioni Audiolibro</legend>
			<label for="durata">Inserisci la durata dell'audio:</label><br> 
			<input id="durata" name="durata" type="text" required><br> 
			
			<label for="linguaA">Inserisci la lingua dell'audio:</label><br>
			<input id="linguaA" name="linguaA" type="text" required><br>
			
			<label for="aPriceA">Inserisci il costo per l'acquisto dell'audiolibro:</label><br>
			<input id="aPriceA" name="aPriceA" type="text" required><br>  
			
			<label for="nPriceA">Inserisci il costo per il noleggio dell'audiolibro:</label><br>
			<input id="nPriceA" name="nPriceA" type="text" required><br> 
			<label for="audiobook">Carica il file dell'audiolibro:</label><br>
			<input id="audiobook" name="audiobook" type="file" required><br> 
		</fieldset>
		
		<input type="submit" value="AGGIUNGI">
		<input type="reset" value="RESET">
	</form>
</body>
</html>