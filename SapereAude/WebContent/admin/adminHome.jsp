<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>ADMIN HOME</title>
</head>
<body>
	<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
	  	<section id="filters">
	  		<ul class = "l">
				<li class = "navelement"><input type = "button" onclick = "show('addItem.jsp')" class = "filter" value= "Aggiungi Opera"></li>
				<li class = "navelement">Rimuovi Opera</li>
				<li class = "navelement">Utenti</li>
			</ul>
	  	</section>
	  		<iframe id="page" scrolling = "auto"></iframe>
  	
  	<script>
		function show(x){
			var f = document.getElementById("page");
			f.setAttribute("src", x);
		}
	</script>
</body>
</html>