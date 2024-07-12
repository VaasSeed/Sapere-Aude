<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href = "../css/style.css" type = "text/css">
<meta charset="ISO-8859-1">
</head>
<body>
<%
	
	HttpSession s = request.getSession(); 
	Boolean logged = (Boolean) s.getAttribute("logged");
	Boolean admin = (Boolean) s.getAttribute("isAdmin");
	if(logged == null){%>
	<div id = "header">
		<input class = "btn" type = "button" value= "ACCEDI" onclick = "loginFunction()">
 		<input style = "margin-right:30px" class = "btn" type = "button" value= "REGISTRATI" onclick = "registrationFunction()">

 	</div>
  <%}
  else{%>
	<div id = "header">
		<input class = "btn" type = "button" value= "ESCI" onclick = "logoutFunction()">
		<% if(admin != null){%>
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
 		
 		function logoutFunction(){
 			var currentLocation = window.location.href;
 			console.log(currentLocation);
 			window.location.assign(currentLocation.substring(0, 33) + "LogoutServlet");
 			return false;
 		}
 		function adminFunction(){
 			var currentLocation = window.location.href;
 			console.log(currentLocation);
 			window.location.assign(currentLocation.substring(0, 33) + "admin/adminHome.jsp");
 			return false;
 		}
 	</script>
</body>

</html>