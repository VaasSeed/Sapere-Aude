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
				<li class = "navelement">Il mio profilo</li>
				<li class = "navelement"><input type = "button" onclick = "show('creditCard.jsp')" class = "filter" value= "Le mie carte"></li>
				<li class = "navelement"><input type = "button" onclick = "logoutFunction()" class = "filter" value= "Logout"></li>
			</ul>
	  	</section>
	  		<iframe id="page"></iframe>
	  	
  	
  	<script>
		function show(x){
			var f = document.getElementById("page");
			f.setAttribute("src", x);
		}
		
 		function logoutFunction(){
 			var currentLocation = window.location.href;
 			window.location.assign(currentLocation.substring(0, 33) + "LogoutServlet");
 			return false;
 		}
	</script>
</body>
</html>