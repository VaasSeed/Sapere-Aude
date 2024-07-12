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
		<label for="ISBN">Inserisci ISBN:</label> 
		<input id="ISBN" name="ISBN" type="text"><br>
		
		<label for="name">Inserisci il nome:</label> 
		<input id="nome" name="nome" type="text" required><br> 
		
		<label for="casaEditrice">Inserisci la casa Editrice:</label> 
		<input id="casaEditrice" name="casaEditrice" type="text" required> <br>
		
		<label for="autore">Inserisci l'autore:</label> 
		<input id="autore" name="autore" type="text" required><br>
		
		<label for="categoria">Inserisci la categoria:</label> 
		<input id="categoria" name="categoria" type="text" required><br>
		
		<label for="image">Inserisci l'immagine:</label> 
		<input id="image" name="image" type="file" required><br> 
		
		<label for="pages">Inserisci il numero di pagine:</label> 
		<input id="pages" name="pages" type="text" required><br> 
		
		<label for="lingua">Indica la lingua del testo:</label> 
		<input id="lingua" name="lingua" type="text" required><br>
		
		<label for="aPrice">Indica il costo per l'acquisto:</label> 
		<input id="aPrice" name="aPrice" type="text" required><br>  
		
		<label for="nPrice">Indice il costo per il noleggio:</label> 
		<input id="nPrice" name="nPrice" type="text" required><br> 
		
		<input type="submit" value="AGGIUNGI"><br>
		<input type="reset" value="RESET">
	</form>
</body>
</html>