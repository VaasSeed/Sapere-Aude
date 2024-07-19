<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Carrello</title>
<script type="text/javascript" src="../scripts/cart-ajax.js"></script>
</head>
<body>
  	<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
		<%
			List<OrderItem> list = (List<OrderItem>) request.getSession().getAttribute("Cart"); 
			if(list == null){%>
				<h2 style = "text-align : center;">Il carrello è vuoto</h2>
			<%}
			else if(list.size() == 0){%>
				<h2 style = "text-align : center;">Il carrello è vuoto</h2>
			<%}
			else{%>
				<table class = "center">
				<h2 style = "text-align : center;">Articoli aggiunti al carrello:</h2>
				<% 
				for(OrderItem item : list){
					int id = item.getId();
					String op = item.getOperazione();
					String image = item.getFoto();%>
				<tr>
					<td style="border-bottom: 2px solid black;">
						<h5>Nome: </h5> <%=item.getNomeOpera()%> <br>
						<h5>Tipologia: </h5> <%=item.getTipoOpera()%><br>
						<h5>Operazione: </h5> <%=op%><br>
						<h5>Prezzo: </h5> <%=item.getCosto()%> &euro; <br>
					</td>
					<td id = "bin" style="border-bottom: 2px solid black;">
						<a href="#" onclick = "deleteItem(<%=id%>, '<%=op%>')">
	   				  		<img class = "bin-btn" src="../images/bin.png">
	                	</a>
					</td>
				</tr>
				<%
			}%>
			</table>
			<input type = "button" value = "Checkout" class = "" onclick = "orderSummary()">
		<%}%>
		
		<script>
			function orderSummary(){
	 			var currentLocation = window.location.href;
	 			window.location.assign(currentLocation.substring(0, 33) + "common/OrderSummary.jsp");
	 			return false;
			}
		</script>
	
</body>
</html>