<%@ page language="java" contentType="text/html; charset=UTF-8"
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
  		<ul class = "l">
  			<li class = "i"><input type = "button" class = "switch" value= "Libro" onclick = "showBook()"></li>
  			<li class = "i"><input type = "button" class = "switch" value= "Audiolibro" onclick = "showAudioBook()"></li>
  		</ul>
  	<h1 id = "head"></h1>
  	<h3 id= "my-popup">Articolo aggiunto al carrello</h3>
	<div id ="content">
	</div>
	  			<input type = "button" class = "switch" value= "Acquista" onclick = "addToCart('acquisto')">
  				<input type = "button" class = "switch" value= "Noleggia" onclick = "addToCart('noleggio')">
</body>


</html>