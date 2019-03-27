<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Concert Details</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		
		<script>
		
			function checkout_function(){
				var requestType = $("#requestType").val();
				var performanceId = $("#performanceId").val();
				var quantity = $("#quantity").val();
				
				$.post("../Team22-HW3-Concert/UpdateShoppingCart", {
					requestType:requestType,
					performanceId:performanceId,
					quantity:quantity
				}, function(data,status){
					
					alert(data);
					$('#navbar').load('Header.jsp #navbar');
					
				});
				
			}
		
		</script>
	</head>
	
	<body>
		
		<div class="container">
		
			<h3>Concert Details</h3>
			<form name="concertDetails" action="ConcertDetailsSelection" method="post">
				<input type="hidden" name="concertId" value="${performance.concert.id}">
				<table class="tbl noBorder">
				
					<tr>
						<td>Concert Name</td>
						<td><c:out value="${performance.concert.name}"></c:out></td>
					</tr>
					<tr>
						<td>Concert Description</td>
						<td width="100%"><c:out value="${performance.concert.description}"></c:out></td>
					</tr>
					<tr>
						<td>Concert Rating</td>
						<td><c:out value="${performance.concert.rating}"></c:out></td>
					</tr>
					<tr>
						<td>Concert Poster</td>
						<td><img src="${performance.concert.thumbnail}" width="50%"></td>
					</tr>
					<tr>
						<td>Venue Name</td>
						<td><c:out value="${performance.venue.name}"></c:out></td>
					</tr>
					<tr>
						<td>Showtime</td>
						<td><c:out value="${performance.date} at ${performance.startTime}"></c:out></td>
					</tr>
					<tr>
						<td>Ticket Price</td>
						<td><c:out value="${performance.getPrettyPrice()}"></c:out></td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td><c:out value="${performance.getAvailableSeats()}"></c:out></td>
					</tr>
					
				</table><br>
				
				<h3>Concert Reviews</h3>
				<button type="submit" class="addButton btn btn-sm right">Add Review</button>
				
				<table class="tbl noBorder">
				
					<tr>
						<td nowrap="nowrap"> Viewer's Name </td>
						<td nowrap="nowrap"> Review Date </td>
						<td nowrap="nowrap"> Rating </td>
						<td nowrap="nowrap"> Review </td>
					</tr>
					<c:forEach var="review" items="${reviews}">
						<tr>
							<td>${review.user.getFullName()}</td>
							<td>${review.reviewDate}</td>
							<td>${review.rating}</td>
							<td>${review.review}</td>
						</tr>
						
					</c:forEach>
				
				</table><br><br><br>
			</form>
			
			<form name="addToCart" action="UpdateShoppingCart" method="post">
				<input type="hidden" name="requestType" id="requestType" value="add">
			
				<a href="ConcertSearchResults.jsp"><button class="backButton btn btn-sm">Back</button></a>
				
				<input type="hidden" name="performanceId" id="performanceId" value="${performance.id}">
				<span class="right">
					<input id="quantity" class="textInputNiceSmall" type="text" value="1" name="quantity">
					<button type="button" class="btn btn-sm padLeft" onClick="checkout_function()">Add to Cart</button>
				</span>
			</form>
		</div>
	</body>
</html>