<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
<%
	
	HttpSession s = request.getSession(); 
	Boolean logged = (Boolean) s.getAttribute("logged");
	Boolean admin = (Boolean) s.getAttribute("isAdmin");
	if(logged == null){%>
	<link rel="stylesheet" href = "./css/style.css" type = "text/css">
	<div id = "header">
	   	<img class = "logo" src="./images/logo.jpeg">
		<input class = "btn" type = "button" value= "ACCEDI" onclick = "loginFunction()">
 		<input class = "btn" type = "button" value= "REGISTRATI" onclick = "registrationFunction()">

 	</div>
  <%}
  else{%>
  	<link rel="stylesheet" href = "../css/style.css" type = "text/css">
	<div id = "header">
		<a href="#" onclick = "goHome()">
	   		<img class = "logo" src="../images/logo.jpeg">
	    </a>
		<a href="#" onclick = "cartFunction()">
 			<img class = "cart-btn" src="../images/cart.png">
		</a>
		<input class = "btn" type = "button" value= "ACCOUNT" onclick = "accountFunction()">
		<% if(admin == true){%>
		<input class = "btn" type = "button" value= "ADMIN" onclick = "adminFunction()">
		<% }%>
 	</div>
 	<% }%>
 	
 	<script>
 		function registrationFunction(){
 			window.location.assign("registrazione.jsp");
 			return false;
 		}
 		
 		function loginFunction(){
 			window.location.assign("accesso.jsp");
 			return false;
 		}
 		
 		function accountFunction(){
 			var currentLocation = window.location.href;
 			window.location.assign(currentLocation.substring(0, 33) + "common/accountHome.jsp");
 			return false;
 		}
 		
 		function goHome(){
 			var currentLocation = window.location.href;
 			window.location.assign(currentLocation.substring(0, 33) + "common/Home.jsp");
 			return false;
 		}
 		
 		function adminFunction(){
 			var currentLocation = window.location.href;
 			window.location.assign(currentLocation.substring(0, 33) + "admin/adminHome.jsp");
 			return false;
 		}
 		
 		function cartFunction(){
 			var currentLocation = window.location.href;
 			window.location.assign(currentLocation.substring(0, 33) + "common/Cart.jsp");
 			return false;
 		}
 	</script>
</body>

</html>