<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String group_no = request.getParameter("group_no");
	String number = request.getParameter("number");
	String nameSurname = request.getParameter("nameSurname");
	String note = request.getParameter("note");
	System.out.println(nameSurname);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Page</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css">
</head>
<body>
	<div class="container">
		<h3>Student Information Page</h3>
		
		<form role="form" action="EditServlet" method="get">
			<div class="row">
				<div class="form-group">
					<label for="code">Group No</label> <input type="text"
						class="form-control input-lg" name="group_no" value=<%=group_no%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label for="code">Student Number</label> <input type="text"
						class="form-control input-lg" name="number" value=<%=number%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label for="code">Name Surname</label> <input type="text"
						class="form-control input-lg" name="nameSurname"
						value=<%=nameSurname%>>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label for="code">Note</label> <input type="text"
						class="form-control input-lg" name="note" value=<%=note%>>
				</div>
			</div>

			<div class="row">
				<div class="form-group">
					<input type="hidden" class="form-control input-lg" name="oldNumber"
						value=<%=number%> required="">
				</div>
			</div>
			<div class="row">
				<button type="submit" class="btn btn-default">Save changes</button>
			</div>
		</form>
	</div>
</body>
</html>