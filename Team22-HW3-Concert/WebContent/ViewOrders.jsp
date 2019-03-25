<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Orders</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<script>
		function viewOrder(){
			var cc = event.srcElement.id;
			document.getElementById("orderChoiceInput").value = cc;
		}
	</script>
	
	<body>

		<div class="container">
		
			<h3>Your Orders</h3>
			<form name="manageOrder" action="ManageOrder" method="post">
			<input type="hidden" id="orderChoiceInput" name="orderId" value="">
				<table class="tbl noBorder">
					<tr>
						<td>Order Number</td>
						<td>Order Total</td>
						<td>Ordered Date</td>
					</tr>
					<c:forEach var="order" items="${orders}">
						<tr>
							<td>${order.id}</td>
							<td>${order.getPrettyPrice()}</td>
							<td>${order.orderDate}</td>
							<td><button id="${order.id}" onclick="viewOrder()" class="btn btn-sm right">View</button></td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
	</body>
	
</html>