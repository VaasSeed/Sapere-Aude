<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Carrello</title>
<script type="text/javascript" src="../scripts/creditCard-ajax.js"></script>
</head>
<body>
		<%
			String user = (String) request.getSession().getAttribute("user");
			CreditCardDao cardDao = new CreditCardDao();
			ArrayList<CreditCardBean> cards = cardDao.doRetrieveAll(user, null);
			if(cards == null){%>
				<h2 style = "text-align : center;">Non hai aggiunto metodi di pagemento</h2>
			<%}
			else if(cards.size() == 0){%>
				<h2 style = "text-align : center;">Non hai aggiunto metodi di pagemento</h2>
			<%}
			else{%>
				<table class = "center">
				<h2 style = "text-align : center;">I tuoi metodi di pagamento:</h2>
				<% 
				for(CreditCardBean card : cards){
					String num = card.getNumeroCarta();%>
				<tr>
					<td style="border-bottom: 2px solid black;">
						<h5>Numero Carta: </h5> <%=num%> <br>
						<h5>Scadenza: </h5> <%=card.getScadenza()%><br>
						<h5>Intestatario: </h5> <%=card.getIntestatario()%><br>
					</td>
					<td id = "bin" style="border-bottom: 2px solid black;">
						<a href="#" onclick = "deleteCard('<%=num%>')">
	   				  		<img class = "bin-btn" src="../images/bin.png">
	                	</a>
					</td>
				</tr>
				<%
			}
		}
		%>
		</table><br>
		<div class = "addCard">
		<h3>Aggiungi una nuova carta:</h3>
		<form id = "cardForm" action = "../AddCreditCardServlet" method = "post">
			
			<lable for = "numero">Numero carta: </lable>
			<input type = "text" id = "numero" name = "numero" required pattern = "^(\d{16})$"
			onblur = "validateFormElem(this, document.getElementById('errorNumero'), numeroErrorMessage)">
			<span id = "errorNumero"></span><br>
			
			<lable for = "code">Codice di sicurezza: </lable>
			<input type = "text" id = "code" name = "code" required pattern = "^(\d{3})$"
			onblur = "validateFormElem(this, document.getElementById('errorCodice'), codiceErrorMessage)">
			<span id = "errorCodice"></span><br>
			
			<lable for = "scadenza">Scadenza: </lable>
			<input type = "text" id = "scadenza" name ="scadenza" placeholder = "mm-aa" required pattern = "^(\d{2})-(\d{2})$"
			onblur = "validateFormElem(this, document.getElementById('errorScadenza'), scadenzaErrorMessage)">
			<span id = "errorScadenza"></span><br>
			
			<lable for = "owner">Intestatario: </lable>
			<input type = "text" id = "owner" name = "owner" required pattern = "^(\D{1,})$"
			onblur = "validateFormElem(this, document.getElementById('errorOwner'), ownerErrorMessage)">
			<span id = "errorOwner"></span><br>
			<br>
			<input type = "submit" value = "INVIA" class = "db-switch" onclick = "return validate()">
		</form> 
		</div>
	<%@include file="../fragments/footer.html"%>
</body>
</html>