<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>OPERA</title>
<script type="text/javascript" src="../scripts/digital&AudioBook-ajax-json.js"></script>
</head>
<body onload = "showBook()">
  	<div>
  		<%@include file="/fragments/header.jsp"%>
  	</div>
  		<ul class = "l">
  			<li class = "i"><input type = "button" class = "switch" value= "Libro" onclick = "showBook()"></li>
  			<li class = "i"><input type = "button" class = "switch" value= "Audiolibro" onclick = "showAudioBook()"></li>
  		</ul>
  	
	<div id ="content">
	</div>
</body>


</html>