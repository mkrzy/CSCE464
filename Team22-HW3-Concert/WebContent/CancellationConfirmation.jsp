<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Cancellation Confirmation</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<body>
		<div class="container">
			<h3>Cancelled Concert Tickets from Order Number: <c:out value="${orderItemToCancel.containingOrder.id}"/></h3>
			Status: <span style="color: red">Cancelled</span>
			
			<br><br>
		
			<table class="tbl colOneRight">
				<tr>
					<td>Concert Name: </td>
					<td><c:out value="${orderItemToCancel.performance.concert.name }"/></td>
				</tr>
				<tr>
					<td>Ticket Quantity: </td>
					<td><c:out value="${orderItemToCancel.quantity}"/></td>
				</tr>
				<tr>
					<td>Total Price: </td>
					<td><c:out value="${orderItemToCancel.getPrettyPrice()}"/></td>
				</tr>
				<tr>
					<td>Venue Name: </td>
					<td><c:out value="${orderItemToCancel.performance.venue.name}"/></td>
				</tr>
				<tr>
					<td>Date and Time: </td>
					<td><c:out value="${orderItemToCancel.performance.date} at ${orderItemToCancel.performance.startTime}"/></td>
				</tr>
			</table>
		</div>
	</body>
	
</html>