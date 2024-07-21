<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>Items</title>
<script type="text/javascript" src="../scripts/deleteItem-ajax.js"></script>
</head>
<body>
	<h1 style = "text-align : center;">RIMUOVI OPERA:</h1><br>
			<%
			ItemDao itemDao = new ItemDao();
			ArrayList<ItemBean> items = itemDao.doRetrieveAll(null); 
			if(items.isEmpty()){%>
				<h2 style = "text-align : center;">Non ci sono elementi da rimuovere</h2>
			<%}
			else{%>
				<table class = "center">
				<% 
				for(ItemBean item : items){
					String isbn = item.getISBN();
					String nome = item.getNome();
					String casaEditrice = item.getCasaEditrice();
					String autore = item.getAutore();
					String categoria = item.getCategoria();%>
				<tr>
					<td style="border-bottom: 2px solid black;">
						<h5>ISBN: </h5> <%=isbn%> <br>
						<h5>Nome: </h5> <%=nome%> <br>
						<h5>Casa Editrice: </h5> <%=casaEditrice%><br>
						<h5>Autore: </h5> <%=autore%><br>
						<h5>Categoria: </h5> <%=categoria%><br>
					</td>
					<td id = "bin" style="border-bottom: 2px solid black;">
						<a href="#" onclick = "deleteBook(<%=isbn%>)">
	   				  		<img class = "bin-btn" src="../images/bin.png">
	                	</a>
					</td>
				</tr>
				<%
			}%>
			</table>
		<%}%>

</body>
</html>