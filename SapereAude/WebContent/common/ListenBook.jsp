<%@ page language="java" contentType="text/html; charset=UTF-8" import = "models.*" import = "java.util.*" import = "java.io.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<title>LISTEN</title>
</head>
<body>
	<div>
  		<%@include file="../fragments/header.jsp"%>
  	</div>
	<%
		String isbn = request.getParameter("isbn");
		
		AudioBookDao audioDao = new AudioBookDao();
		AudioBookBean audiobook = audioDao.doRetrieve(isbn);
		InputStream audioFile = audiobook.getAudioFile();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[2000];
		int bytesRead = -1;
		
		while ((bytesRead = audioFile.read(buffer)) != -1) {
		    outputStream.write(buffer, 0, bytesRead);
		}
		 
		byte[] audioFileBytes = outputStream.toByteArray();
		
		String base64AudioFile = Base64.getEncoder().encodeToString(audioFileBytes);
		String file = "data:application/mp3;base64," + base64AudioFile;

	%>
	<div class = "audio">
		<audio controls class = "player">
	        <source src ='<%=file%>' type="audio/mpeg">
	    </audio>
    </div>

</body>
</html>