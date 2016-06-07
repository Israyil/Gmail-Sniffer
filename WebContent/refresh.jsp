<%@page import="database.MongoManager"%>
<%@page import="util.MailCheck"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Refresh</title>
</head>
<body>
	<%
		MongoManager.connect();		
		MongoManager.readAllStudents();
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	%>
</body>
</html>