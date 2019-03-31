<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Order Confirmation</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<body>
		
		<div class="container">
		
			<h3>${returnMessage}</h3>
			
			<c:forEach var="orderItem" items="${order.orderItems}">				
				<table class="tbl noBorder" id="${orderItem.id}">
				
					<tr>
						<td></td>
						<td></td>
						<td width="30%"></td>
					</tr>
					<tr>
						<td colspan="3"><b><c:out value="${orderItem.performance.concert.name}"/></b></td>
							<td></td>
							<td></td>
					</tr>
					<tr>
						<td>Venue Name: </td>
						<td><c:out value="${orderItem.performance.venue.name}"/></td>
						<td rowspan="4"><img src="${orderItem.performance.concert.thumbnail}" width="100%"></td>
					</tr>
					<tr>
						<td>Showtime: </td>
						<td><c:out value="${orderItem.performance.date} at ${orderItem.performance.startTime}"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Number of Tickets: </td>
						<td><c:out value="${orderItem.quantity}"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Total Price: </td>
						<td><c:out value="${orderItem.getPrettyPrice()}"/></td>
						<td><button id="${orderItem.id}" onclick="Delete()" class="btn btn-sm right">Delete</button></td>
					</tr>
				</table>
				<br>
			</c:forEach>

			<br><br>
			<table class="tbl noBorder">
				<tr>
					<td>Total Cost: </td>
					<td><c:out value="${order.getPrettyPrice()}"/></td>
				</tr>
				<tr>
					<td>First Name: </td>
					<td><c:out value="${order.customer.firstName}"/></td>
				</tr>
				<tr>
					<td>Last Name: </td>
					<td><c:out value="${order.customer.lastName}"/></td>
				</tr>
				<tr>
					<td>Billing Address: </td>
					<td>
						<c:out value="
							${order.billingAddress.street},
							${order.billingAddress.city},
							${order.billingAddress.state}
							${order.billingAddress.zip}
						"></c:out>
					</td>
				</tr>
				
			</table>
		</div>
	</body>
	
</html>