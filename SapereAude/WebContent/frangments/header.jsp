<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href = "../css/style.css" type = "text/css">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id = "header">
 		<input class = "btn" onclick = "accfunction()" type = "button" value= "ACCEDI">
 		<input style = "margin-right:30px" class = "btn" onclick = "regfunction()" type = "button" value= "REGISTRATI">
 	</div>
 	<script>
 		function regfunction(){
 			window.open("registrazione.jsp");
 		}
 		
 		function accfunction(){
 			window.open("accesso.jsp");
 		}
 	</script>
</body>

</html>