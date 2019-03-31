<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Manage Order</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<script>
		function cancelOrderItemFunc(){
			var cc = window.event.srcElement.id;
			document.getElementById("orderItemInput").value = cc;
		}
	</script>
	
	<body>
	
		<div class="container">
		
			<h3>Order Number: ${order.id}</h3>
			
				<div id="individualPerformances" style="margin-left: 30px">
				
					<c:forEach var="orderItem" items="${order.orderItems}">
						<table class="tbl noBorder">
							<tr>
								<td>Concert Name: </td>
								<td><c:out value="${orderItem.performance.concert.name}"/></td>
							</tr>
							<tr>
								<td>Ticket Quantity: </td>
								<td><c:out value="${orderItem.quantity}"/></td>
							</tr>
							<tr>
								<td>Total Price: </td>
								<td><c:out value="${orderItem.getPrettyPrice()}"/></td>
							</tr>
							<tr>
								<td>Venue Name: </td>
								<td><c:out value="${orderItem.performance.venue.name}"/></td>
							</tr>
							<tr>
								<td>Date and Time: </td>
								<td><c:out value="${orderItem.performance.date} at ${orderItem.performance.startTime}"/></td>
							</tr>
							<c:if test = "${orderItem.cancelled}">
								<tr>
									<td>Status: </td>
									<td><span style="color: red">Cancelled</span></td>
								</tr>
							</c:if>
							
							
							<tr>
								<td colspan="2">
									<c:if test = "${not orderItem.cancelled}">
										<form name="orderItems" action="CancelOrder" method="post">
											<input type="hidden" id="orderItemInput" name="orderItemId" value="">
											<button id="${orderItem.id}" onclick="cancelOrderItemFunc()" class="btn btn-sm right">Cancel</button>
										</form>
									</c:if>
									<form name="viewOrderItem" action="ConcertSearchResults" method="post">
										<input type="hidden" id="concertChoice" name="concertChoice" value="${orderItem.performance.id}">
										<button class="btn btn-sm right" type="submit">View</button>
									</form>
								</td>
							</tr>
							
						</table><br>
					</c:forEach>
					
				</div>
				
				<p><c:out value="Order Total: ${order.getPrettyPrice()}"/></p>
				<p><c:out value="Ordered Date: ${order.orderDate}"/></p>
		</div>
	</body>
	
</html>