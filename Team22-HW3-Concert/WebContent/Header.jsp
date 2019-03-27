<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="/Team22-HW3-Concert/css/style.css" type="text/css">
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<body>
		<div id="navbar">
			<a href="CustomerHomePage.jsp"><button class="homeButton btn">Home</button></a>
			<form name="viewOrders" action="ViewOrders" method="post"><button class="viewOrdersButton btn">View Orders</button></form>
			<a href="Login.jsp"><button class="logoutButton btn right">Logout</button></a>
			<c:if test = "${shoppingCart.getTotalCost() > 0}">
				<a href="ConfirmOrder.jsp"><button class="checkoutButton btn right" style="text-decoration: underline">Checkout</button></a>
			</c:if>
		</div>
	</body>
</html>