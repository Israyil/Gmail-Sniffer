<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link rel="stylesheet" href="css/insert.css" type="text/css">
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css">
<meta charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-7">
            <div class="panel panel-default">
                <div class="panel-heading"> <strong class="">Insert Form</strong>

                </div>
                <div class="panel-body">
						<form class="form-horizontal" role="form" action="InsertServlet">
							<div class="form-group">
								<div class="col-sm-12">
									<input type="text" class="form-control" name="group_no"
										placeholder="Group No" required="">
								</div>
							</div>
							<div class="form-group">

								<div class="col-sm-12">
									<input type="text" class="form-control" name="number"
										placeholder="Number" required="">
								</div>
							</div>
							<div class="form-group">

								<div class="col-sm-12">
									<input type="text" class="form-control" name="nameSurname"
										placeholder="Name Surname" required="">
								</div>
							</div>
							<div class="form-group">

								<div class="col-sm-12">
									<input type="text" class="form-control" name="note"
										placeholder="Note" required="">
								</div>
							</div>

							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-success btn-sm">Insert</button>
									<button type="reset" class="btn btn-default btn-sm">Reset</button>
								</div>
							</div>
						</form>

					</div>
        </div>
    </div>
</div>
</body>
</html>