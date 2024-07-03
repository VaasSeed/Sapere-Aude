<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <title>HOME</title>
    <script type="text/javascript" src="../scripts/items-ajax-json.js"></script>
  </head> 

  <body onload = "itemsFunction()">
  	
  	<div>
  		<%@include file="../frangments/header.jsp"%>
  	</div>
  	<div id="tableContainer">
	<div id="tableRow">
  	<section id="filters">
  		<input type = "checkbox" id = "Avventura" value = "Avventura"/>
  		<label for = "Avventura">Avventura</label><br>
   		<input type = "checkbox" id = "Horror" value = "Horror"/>
  		<label for = "Horror">Horror</label><br>
  		<input type = "checkbox" id = "Fantasy" value = "Fantasy"/>
  		<label for = "Fantasy">Fantasy</label><br>
  		<input type = "checkbox" id = "Giallo" value = "Giallo"/>
  		<label for = "Giallo">Giallo</label><br>
  	</section>
  	
		<section id ="hi">
		</section>
	
	</div>
	</div>
  </body>
</html>


