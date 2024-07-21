<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>OPERA</title>
<script type="text/javascript" src="../scripts/digital&AudioBook-ajax-json.js"></script>
</head>
<body onload = "showBook()">
  	<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
  		<ul class = "toggle">
  			<li class = "i"><input type = "button" class = "switch" value= "Libro" onclick = "showBook()"></li>
  			<li class = "i"><input type = "button" class = "switch" value= "Audiolibro" onclick = "showAudioBook()"></li>
  		</ul>
  	<h1 id = "head"></h1>
  	<h3 id= "my-popup">Articolo aggiunto al carrello</h3>
	<div id ="content">
	</div>
	<div class= "db-btn">
	<input id = "buy" type = "button" class = "db-switch" value= "Acquista">
  	<input id = "rent" type = "button" class = "db-switch" value= "Noleggia">
  	</div>
  	
  	<% 
  		String isbn = request.getParameter("isbn");
  	%>
	
	<br>
	<br>
	<div class = "review">
	<h3>Lascia una recensione:</h3>
	<form method="post" action="../AddReviewServlet">
	<fieldset>
		<legend>Recensione:</legend>
		<input type = "hidden" name = "isbn" id = "isbn" value = <%=isbn%> required>
		
		<label for = "titolo">Inserisci il titolo: </label>
		<input type = "text" name = "titolo" id = "titolo" required><br>
		
		<label for = "testo">Inserisci il testo: </label><br>
		<textarea maxlength = "200" name = "testo" id = "testo"></textarea><br>
		<input type = "submit" class = "db-switch" value = "INVIA">
	</fieldset>
	</form>

	<br>
	<br>
	<div class = "review"> 
	<h3>Recensioni:</h3>
	<%
		ReviewDao reviewDao = new ReviewDao();
		ArrayList<ReviewBean> reviews = reviewDao.doRetrieveAll(isbn, null);
		if(reviews.size()== 0)
			System.out.println("Hello");
		for(ReviewBean review : reviews){
			String utente = review.getAccount();
			String titolo = review.getTitolo();
			String testo = review.getTesto();
			String data = review.getData();%>
		</div>
		<div class = "reviews">	
			<table style = "border-radius : 15px; width : 60%; text-align: center; border: 2px solid black; ">
				<tr> <th style = "border-bottom: 1px solid black;">Utente: <%=utente%></th></tr>
				<tr><td><b><%=titolo%><b></b></td><tr>
				<%if(testo != null && !testo.equals("")){ %>
					<tr><td style = "text-align: left;"><%=testo%></td></tr>
				<%} %>
			</table>
			<br>
			
		<%}
	%>
	</div>
	<%@include file="../fragments/footer.html"%>
</body>
</html>