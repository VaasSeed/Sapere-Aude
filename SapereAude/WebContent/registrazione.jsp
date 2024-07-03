	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrazione</title>
<script type="text/javascript" src="scripts/validate.js"></script>
</head>
<body>

	<form id="regForm" method="post" action="RegistrazioneServlet">
		<label for="name">Inserisci il nome:</label> 
		<input id="name" name="name" type="text" required pattern = "^[A-Za-z]+$"
		onblur = "validateFormElem(this, document.getElementById('errorName'), nameOrSurnameErrorMessage)">
		<span id = "errorName"></span><br> 
		
		<label for="surname">Inserisci il cognome:</label> 
		<input id="surname" name="surname" type="text" required pattern = "^[A-Za-z]+$"
		onblur = "validateFormElem(this, document.getElementById('errorSurname'), nameOrSurnameErrorMessage)">
		<span id = "errorSurname"></span> <br> 
		
		<label for="username">Inserisci lo username:</label> 
		<input id="username" name="username" type="text"> <br>
		
		<label for="email">Inserisci la tua email:</label> 
		<input id="email" name="email" type="email"	required
		onblur="validateFormElem(this, document.getElementById('errorEmail'), emailErrorMessage)">
		<span id="errorEmail"></span> <br>
		
		<label for="pswd">Inserisci la password:</label> 
		<input id="pswd" name="pswd" type="password" required pattern = "(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"	
		onblur="validateFormElem(this, document.getElementById('errorPassword'), passwordErrorMessage)">
		<span id="errorPassword"></span> <br>
		
		<label for="bDate">Inserisci la tua data di nascita:</label> 
		<input id="bDate" name="bDate" type="text" placeholder = "gg-mm-aaaa" required pattern = "(\d{2,})-(\d{2,})-(\d{4,})"	
		onblur="validateFormElem(this, document.getElementById('errorDate'), dateErrorMessage)">
		<span id="errorDate"></span><br> 
		
		<br> <input type="submit" value="REGISTRATI" onclick = "return validate()">
	</form>
</body>
</html>