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
	  	<section id="filters">
	  		<ul class = "l">
				<li class = "navelement"><input type = "button" class = "filter" value= "Home" onclick = "home()"></li>
				<li class = "navelement"><input type = "button" class = "filter" value= "Le mie opere" onclick = "userItemsFunction()"></li>
			</ul>
	  	</section>
  	<div id="tableContainer">
	<div id="tableRow">
		<section id ="hi">
		</section>
	</div>
	</div>
  </body>
</html>


