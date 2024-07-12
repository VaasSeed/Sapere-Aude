<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
  		<%@include file="../fragments/header.jsp"%>
  	</div>
  	<div id="tableContainer">
	<div id="tableRow">
	  	<section id="filters">
	  		<ul>
				<li class = "navelement"><input type = "button" class = "filter" value= "Avventura"></li>
				<li class = "navelement">Horror</li>
				<li class = "navelement">Fantasy</li>
				<li class = "navelement">Giallo</li>
			</ul>
	  	</section>
  	
		<section id ="hi">
		</section>
	</div>
	</div>
  </body>
</html>


