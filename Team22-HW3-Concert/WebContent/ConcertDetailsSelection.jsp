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
			
			function add_review_function(){
				var concertId = $("#concertId").val();
				var reviewerName = $("#reviewerName").text();
				var date = $("#dateValue").text();
				var rating = $("#rating").val();
				var review = $("#review").val();
				
				$.post("../Team22-HW3-Concert/CustomerReview", {
					concertId:concertId,
					date:date,
					rating:rating,
					review:review
				}, function(data,status){
					
					alert(data);
					
					$("#results").html(data);
					if(data.indexOf("Error") == -1){
						var table = document.getElementById('reviewTable');
						var row = table.insertRow(table.rows.length - 1);
						var nameCell = row.insertCell(0);
						var dateCell = row.insertCell(1);
						var ratingCell = row.insertCell(2);
						var reviewCell = row.insertCell(3);
						
						nameCell.innerHTML = reviewerName;
						dateCell.innerHTML = date;
						ratingCell.innerHTML = rating;
						reviewCell.innerHTML = review;
					}
					

					
				});
				
			}
			
			function todays_date(){
				n =  new Date();
				y = n.getFullYear();
				m = ("0" + (n.getMonth() + 1)).slice(-2);
				d = ("0" + n.getDate()).slice(-2);

				document.getElementById('dateValue').innerHTML = y + "-" + m + "-" + d;
			}
		
		</script>
	</head>
	
	<body onload="todays_date()">
		
		<div class="container">
		
			<h3>Concert Details</h3>
			<form name="concertDetails" action="ConcertDetailsSelection" method="post">
				<input type="hidden" name="concertId" id="concertId" value="${performance.concert.id}">
				<table class="tbl noBorder">
				
					<tr>
						<td colspan="2"><b><c:out value="${performance.concert.name}"></c:out></b></td>
						<td></td>
						<td rowspan="8" width="40%"><img src="${performance.concert.thumbnail}" width="100%"></td>
					</tr>
					<tr>
						<td colspan="2"><c:out value="${performance.concert.description}"></c:out></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>Concert Rating</td>
						<td><c:out value="${performance.concert.rating}"></c:out></td>
						<td></td>
					</tr>
					<tr>
						<td>Venue Name</td>
						<td><c:out value="${performance.venue.name}"></c:out></td>
						<td></td>
					</tr>
					<tr>
						<td>Showtime</td>
						<td><c:out value="${performance.date} at ${performance.startTime}"></c:out></td>
						<td></td>
					</tr>
					<tr>
						<td>Ticket Price</td>
						<td><c:out value="${performance.getPrettyPrice()}"></c:out></td>
						<td></td>
					</tr>
					<tr>
						<td>Available Seats</td>
						<td><c:out value="${performance.getAvailableSeats()}"></c:out></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					
				</table><br>
				
				<h3>Concert Reviews</h3>
				
				<table class="tbl noBorder centerThird" id="reviewTable" style="width: 100%">
					<tr>
						<td nowrap="nowrap"> Viewer's Name </td>
						<td nowrap="nowrap"> Review Date </td>
						<td nowrap="nowrap" style="text-align: center"> Rating 0-5</td>
						<td colspan="2" nowrap="nowrap"> Review </td>
					</tr>
					<c:forEach var="review" items="${reviews}">
						<tr>
							<td class="colShrink">${review.user.getFullName()}</td>
							<td class="colShrink">${review.reviewDate}</td>
							<td class="colShrink">${review.rating}</td>
							<td colspan="2">${review.review}</td>
						</tr>
						
					</c:forEach>
					
					<tr>
						<td class="colShrink"><span id="reviewerName"><c:out value="${userBean.getFullName()}"/></span></td>
						<td class="colShrink"><span id="dateValue"></span></td>
						<td class="colShrink"><input type="range" min="0" max="5" step="0.5" class="textInputNiceSmall" name="rating" id="rating"></td>
						<td><input type="text" class="textInputNiceDynamic" id="review" name="review"></td>
						<td class="colShrink"><button type="button" onClick="add_review_function()" class="addButton btn btn-sm">Add Review</button></td>
					</tr>
				
				</table>
				
			</form><br><br><br>
			
			<button class="backButton btn btn-sm" onclick="history.back()">Back</button>
			
			<form name="addToCart" action="UpdateShoppingCart" method="post">
				<input type="hidden" name="requestType" id="requestType" value="add">
			
				
				<input type="hidden" name="performanceId" id="performanceId" value="${performance.id}">
				<span class="right">
					<input id="quantity" class="textInputNiceSmallest" type="text" value="1" name="quantity">
					<button type="button" class="btn btn-sm padLeft" onClick="checkout_function()">Add to Cart</button>
				</span>
			</form>
		</div>
	</body>
</html>