<%@ page language="java" contentType="text/html; charset=UTF-8"
    import = "java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
		<form id = "accForm" method = "post" action = "AccessoServlet">
			<label for = "username" >USERNAME:</label>
			<input id = "username" type = "text" name = "username" required>
			<br>
			<label for="pswd">PASSWORD:</label>
			<input id = "pswd" name = "pswd" type = "password" required>
			<br>
			<input type="submit" value = "ACCEDI">
		</form>
</body>
</html>