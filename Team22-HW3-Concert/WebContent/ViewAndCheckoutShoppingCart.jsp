<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Shopping Cart</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<script>
		function Delete(){
			var oi = event.srcElement.id;
			document.getElementById("orderItemInput").value = oi;
		}
	</script>
	
	<body>

		<div class="container">
			
			<h3>Checkout</h3>
			<form name="updateShoppingCart" action="UpdateShoppingCart" method="post">
				<input type="hidden" name="requestType" value="delete">
				<input type="hidden" id="orderItemInput" name="orderItemDelete" value="">
								
				<c:forEach var="orderItem" items="${shoppingCart.orderItems}">				
					<table class="tbl noBorder" id="${orderItem.id}">
					
						<tr>
							<td></td>
							<td></td>
							<td width="30%"></td>
						</tr>
						<tr>
							<td colspan="3"><b>${orderItem.performance.concert.name}</b></td>
								<td></td>
								<td></td>
						</tr>
						<tr>
							<td>Venue: </td>
							<td>${orderItem.performance.venue.name}</td>
							<td rowspan="4"><img src="${orderItem.performance.concert.thumbnail}" width="100%"></td>
						</tr>
						<tr>
							<td>Showtime: </td>
							<td>${orderItem.performance.date} at ${orderItem.performance.startTime}</td>
							<td></td>
						</tr>
						<tr>
							<td>Number of Tickets: </td>
							<td>${orderItem.quantity}</td>
							<td></td>
						</tr>
						<tr>
							<td>Total Price: </td>
							<td>${orderItem.getPrettyPrice()}</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td><button id="${orderItem.id}" onclick="Delete()" class="btn btn-sm right">Delete</button></td>
						</tr>
					</table>
					<br>
				</c:forEach>
			</form>
			
			<br><br><br>
			
			<div class="pageRow">
				<p class="left"> Total Cost: <c:out value="${shoppingCart.getPrettyPrice()}"></c:out></p>
				<div class="right">
					<a href="CustomerHomePage.jsp"><button class="btn">Add More</button></a>
					<a href="ConfirmOrder.jsp"><button class="btn">Checkout</button></a>
				</div>
			</div>
		</div>
		
	</body>
	
</html>