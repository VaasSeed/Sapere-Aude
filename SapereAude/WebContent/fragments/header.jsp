<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	if(logged == null){%>
	<div id = "header">
		<input class = "btn" type = "button" value= "ACCEDI" onclick = "loginFunction()">
 		<input style = "margin-right:30px" class = "btn" type = "button" value= "REGISTRATI" onclick = "registrationFunction()">

 	</div>
  <%}
  else{%>
	<div id = "header">
		<input class = "btn" type = "button" value= "ESCI" onclick = "logoutFunction()">
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
 			window.location.replace("../LogoutServlet");
 			return false;
 		}
 	</script>
</body>

</html>