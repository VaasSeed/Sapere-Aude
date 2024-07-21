<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Riepilogo Ordine</title>
<script type="text/javascript" src="../scripts/"></script>
</head>
<body>
<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
  		<% List<OrderItem> list = (List<OrderItem>) request.getSession().getAttribute("Cart"); 
			if(list == null){
				response.sendRedirect(request.getContextPath() + "/common/Cart.jsp");
			}
			else if(list.size() == 0){
				response.sendRedirect(request.getContextPath() + "/common/Cart.jsp");
			}
			else{%>
  				<h2 style = "text-align : center;">Seleziona il metodo di pagamento:</h2>
 					
 				<%String user = (String) request.getSession().getAttribute("user");
 				CreditCardDao cardDao = new CreditCardDao(); 
				ArrayList<CreditCardBean> cards = cardDao.doRetrieveAll(user, null); 
		
				if(cards.isEmpty()){%>
					<h4 style = "text-align : center;">Vai nelle sezione "ACCOUNT" ed aggiungi un metodo di pagamento per procedere con l'ordine</h4>
			  <%}
				else{%>
					<form method = "post" action = "../CheckoutServlet">
					 
					<%for(CreditCardBean card : cards){
						String num = card.getNumeroCarta();
						String scadenza = card.getScadenza();%>
						<div class = "card">
						<input type = "radio" class = "" value = "<%=num%>" name = "card" required>&ensp;Numero Carta: <%=num%> &emsp; Scadenza: <%=scadenza%><br>
			  	    	</div>
			  	    <%}%>
			  	    <div class = "cart-checkout">
			  		  <input type = "submit" value = "Completa l'ordine" class = "db-switch">
			  		 </div>
					  </form><br>
			 <%}%>
			
				<h2 style = "text-align : center;">Riepilogo Ordine</h2><br>
				<table class = "center">
				<%
				double total = 0;
				for(OrderItem item : list){
					int id = item.getId();
					double costo = item.getCosto();
					String op = item.getOperazione();
					total = total + costo;%>
				    <tr>
						<td style="border-bottom: 2px solid black; border-top: 2px solid black;">
							<h5>Nome: </h5> <%=item.getNomeOpera()%> <br>
							<h5>Tipologia: </h5> <%=item.getTipoOpera()%><br>
							<h5>Operazione: </h5> <%=op%><br>
							<h5>Prezzo: </h5> <%=costo%> &euro; <br>
						</td>
					</tr>
				<%}%>
				    <tr>
						<td style="border-bottom: 2px solid black; border-top: 2px solid black;">
							<h5>Importo Totale =</h5><%=total%> &euro;<br>
						</td>
					</tr>				
		  <%}%>
		</table>
	<%@include file="../fragments/footer.html"%>

</body>
</html>