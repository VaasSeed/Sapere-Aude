<%@ page language="java" contentType="text/html; charset=UTF-8"
    import = "java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>ACCESSO</title>
</head>
<body>
<%
	List<String> errors = (List<String>) request.getAttribute("errors"); 
	if(errors != null){
		for(String error : errors){%>
			<%=error%><br>
		<%
		}
	}
	%>
	<h1 class="welcome">Benvenuto nella pagina di accesso</h1>
	<div class = "acc">
	<h3> Accedi: </h3>
		<form id = "accForm" method = "post" action = "AccessoServlet">
			<label for = "username" >USERNAME:</label>
			<input id = "username" type = "text" name = "username" required>
			<br>
			<label for="pswd">PASSWORD:</label>
			<input id = "pswd" name = "pswd" type = "password" required>
			<br>
			<input class = "acc-btn" type="submit" value = "ACCEDI">
		</form>
	</div>
</body>
</html>