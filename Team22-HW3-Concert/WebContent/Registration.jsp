<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Register New User</title>
		<% String name=getServletContext().getInitParameter("webappName").toString();%>
		<link rel="stylesheet" href="/<%=name%>/css/style.css" type="text/css">
	</head>
	
	<script>
		function validateForm() {
			var x = document.forms["registration"]["username"].value;
			var y = document.forms["registration"]["password"].value;
			var z = document.forms["registration"]["confirmationPassword"].value;
			if (x == "") {
				alert("Username must be filled out");
				return false;
			}
			
			if (y == "") {
				alert("Password must be filled out");
				return false;
			}
			
			if (z == "") {
				alert("Confirmation password must be filled out");
				return false;
			}
			
			if (y != z) {
				alert("Password and confirmation password must match");
				return false;
			}
		}
	</script>
	 
	<body>
	
		<div id="navbar">
			<a href="Login.jsp"><button class="loginButton btn right">Login</button></a>
		</div>
		
		<div class="container">
		
			<h3>Register New User</h3>
		
			<form name="registration" action="Registration" onsubmit="return validateForm()" method="post">
				<div>
					<div class="left" style="width:50%">
						<table class="tbl noBorder">
							<tr>
								<td>
									Username: 
								</td>
								<td>
									<input type="text" class="textInput" name="username">
								</td>
							</tr>
							<tr>
								<td>
									Password: 
								</td>
								<td>
									<input type="password" class="textInput" name="password">
								</td>
							</tr>
							<tr>
								<td>
									Confirm Password: 
								</td>
								<td>
									<input type="password" class="textInput" name="confirmationPassword">
								</td>
							</tr>
						</table>
					</div>
					<div class="right" style="width:50%">
						<table class="tbl noBorder">
							<tr>
								<td>
									First Name: 
								</td>
								<td>
									<input type="text" class="textInput" name="firstName">
								</td>
							</tr>
							<tr>
								<td>
									Last Name: 
								</td>
								<td>
									<input type="text" class="textInput" name="lastName">
								</td>
							</tr>
		
						</table>
					</div>
				</div>
				<input type="submit" class="btn right" style="clear: both"value="Register">
			</form>
		</div>
	</body>

</html>