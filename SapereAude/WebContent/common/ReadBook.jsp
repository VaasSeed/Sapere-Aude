<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*" import = "java.io.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>READ</title>
</head>
<body>

  	<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
  	
	<%
		String isbn = request.getParameter("isbn");
	
		DigitalBookDao bookDao = new DigitalBookDao();
		DigitalBookBean book = bookDao.doRetrieve(isbn);
		InputStream bookFile = book.getBookFile();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[2000];
		int bytesRead = -1;
		
		while ((bytesRead = bookFile.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, bytesRead);
		}
		 
		byte[] bookFileBytes = outputStream.toByteArray();
		
		String base64BookFile = Base64.getEncoder().encodeToString(bookFileBytes);
		String file = "data:application/pdf;base64," + base64BookFile + "#toolbar=0&navpanes=0&scrollbar=0";

	%>
	<embed src ='<%=file%>' style = "pointer-events: none; display : block; width : 100%; height : 100vh;">
	</embed>
</body>
</html>