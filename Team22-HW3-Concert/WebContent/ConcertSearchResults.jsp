<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Concert Search Results</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>

	<script>
		function viewDetails(){
			var cc = event.srcElement.id;
			document.getElementById("concertChoiceInput").value = cc;
		}
	</script>

	<body>
		
		<div class="container">
		
			<h3>Search Results</h3>
			<form name="concertSearch" action="ConcertSearchResults" method="post">
			<input type="hidden" id="concertChoiceInput" name="concertChoice" value="">
			<c:forEach var="result" items="${searchResults}">
				<table class="tbl noBorder" id="${result.id}">
				
					<tr>
						<td></td>
						<td></td>
						<td width="30%"></td>
					</tr>
					<tr>
						<td colspan="3"><b><c:out value="${result.concert.name}"/></b></td>
							<td></td>
							<td></td>
					</tr>
					<tr>
						<td>Venue Name: </td>
						<td><c:out value="${result.venue.name}"/></td>
						<td rowspan="4"><img src="${result.concert.thumbnail}" width="100%"></td>
					</tr>
					<tr>
						<td>Showtime: </td>
						<td><c:out value="${result.date} at ${result.startTime}"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Available Seats: </td>
						<td><c:out value="${result.getAvailableSeats()}"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Ticket Price: </td>
						<td><c:out value="${result.getPrettyPrice()}"/></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td><button id="${result.id}" onclick="viewDetails()" class="btn btn-sm right">View Details</button></td>
					</tr>
				</table>
				<br>
			</c:forEach>
			</form>		
		</div>
	</body>
</html>