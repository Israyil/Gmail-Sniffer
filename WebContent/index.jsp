<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>
<%@ page import="model.Ogrenci"%>
<%@ page import="model.OgrenciList"%>
<%
	List<Ogrenci> list = OgrenciList.getOgrenciList();
	request.setAttribute("list", list);
	response.setHeader("Refresh","10;url=/SytWebProject/refresh.jsp");
	
%>
<!DOCTYPE html>
<html>
<head>
<script src="js/index.js" type="text/javascript"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<link rel="stylesheet" src="cssFiles/login.css">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List</title>
</head>
<body>
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h3 id="myModalLabel">Delete</h3>
		</div>
		<div class="modal-body">
			<p></p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button data-dismiss="modal" class="btn red" id="btnYes">Confirm</button>
		</div>
	</div>
	<table class="table table-striped table-hover table-users">
		<thead>
			<form action="/SytWebProject/insert.jsp">
				<tr>

					<th class="hidden-phone">Group No</th>
					<th>Number</th>
					<th>Name Surname</th>
					<th class="hidden-phone">Note</th>
					<th>
					<td><button
							class="confirm-delete btn btn-success mini red-stripe"
							role="button" type="sumbit" data-title="johnny" data-id="1">Insert</button></td>
					</th>

				</tr>
			</form>
		</thead>

		<tbody>
			<c:forEach items="${list}" var="item">
				<tr>

					<td class="hidden-phone">${item.getGroup_no()}</td>
					<td>${item.getNumber()}</td>
					<td>${item.getNameSurname()}</td>
					<td class="hidden-phone">${item.getNote()}</td>

					<td><a class="btn mini blue-stripe"
						href="/SytWebProject/edit.jsp?number=${item.getNumber()}&group_no=${item.getGroup_no()}&nameSurname=${item.getNameSurname()}&note=${item.getNote()}">Edit</a></td>

					<td><a
						href="/SytWebProject/DeleteServlet?number=${item.getNumber() }"
						class="confirm-delete btn mini red-stripe" role="button"
						data-title="johnny" data-id="1">Delete</a></td>
				</tr>
			</c:forEach>

		</tbody>

	</table>


</body>
</html>