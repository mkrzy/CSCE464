<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login</title>
		<link rel="stylesheet" href="/Team22-HW3/css/style.css" type="text/css">
	</head>
	
	<script>
		function validateUser() {
			var x = document.forms["login"]["username"].value;
			var y = document.forms["login"]["password"].value;
			if (x == "") {
				alert("Username must be filled out");
				return false;
			}
			
			if (y == "") {
				alert("Password must be filled out");
				return false;
			}
		}
	</script>
		 
	<body>
		
		<div id="navbar">
			<a href="Registration.jsp"><button class="registerButton btn right">Register New User</button></a>
		</div>
		
		<div class="container">
		
			<h3>Login</h3>
		
			<form name="login" action="Login" onsubmit="return validateUser()" method="post">
			
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
				</table>
				
				<button class="btn right" type="submit">Login</button>
				
			</form>
		</div>
	</body>
	
</html>