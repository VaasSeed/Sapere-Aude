<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Orders</title>
</head>
<body>

<%
			OrderDao orderDao = new OrderDao();
			BuyDigitalBookDao buyDao = new BuyDigitalBookDao();
			RentDigitalBookDao rentDao = new RentDigitalBookDao();
			ArrayList<OrderBean> orders = orderDao.doRetrieveAllAdmin(1, null);
			int id = -1;
			if(orders.isEmpty()){%>
				<h2 style = "text-align : center;">Non sono presenti ordini</h2>
			<%}
			else{%>
				<% 
				for(OrderBean order : orders){%>
					<table>
				<%
					id = order.getIdOrdine();
					String utente = order.getUtente();
					double importo = order.getImportoTotale();
					String data = order.getDataOrdine();
					%>
					
					<tr style="border-top: 2px solid black;">
						<th style="border-bottom: 2px solid black;">IdOrdine: <%=id%>&emsp;Utente: <%=utente%>&emsp;Importo: <%=importo%>&emsp;Data: <%=data%></th>
					<tr> 
					<%
					ArrayList<BuyDigitalBookBean> buyBooks= buyDao.doRetrieveAll(id, null);
					for(BuyDigitalBookBean buyBook : buyBooks){
						String isbnBook = buyBook.getISBNOpera();
						String nomeBook = buyBook.getNomeOpera();
						String tipoBook = buyBook.getTipoOpera();
						double costoBook = buyBook.getCosto();%>
						<tr>
							<td>ISBN: <%=isbnBook%>&emsp;Nome: <%=nomeBook%>&emsp;Tipo: <%=tipoBook%>&emsp;Costo: <%=costoBook%>&emsp;ACQUISTATA</td>
						</tr>
					<%}
					%>
					
					<%
					ArrayList<RentDigitalBookBean> rentBooks= rentDao.doRetrieveAll(id, null);
					for(RentDigitalBookBean rentBook : rentBooks){
						String isbnRBook = rentBook.getISBNOpera();
						String nomeRBook = rentBook.getNomeOpera();
						String tipoRBook = rentBook.getTipoOpera();
						double costoRBook = rentBook.getCosto();
						String endRBook = rentBook.getDataFineNoleggio();%>
						<tr>
							<td>ISBN: <%=isbnRBook%>&emsp;Nome: <%=nomeRBook%>&emsp;Fine Noleggio: <%=endRBook%>&emsp;Tipo: <%=tipoRBook%>&emsp;Costo: <%=costoRBook%>&emsp;NOLEGGIATA</td>
						</tr>
					<%}%>
					</table>
					<br>
					<br>
				<%}
			}%>


</body>
</html>