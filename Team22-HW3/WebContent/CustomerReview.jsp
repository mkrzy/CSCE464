<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Write a Review</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<body>
		<div class="container">
			<h3>Write a Review</h3>
			<form name="writeAReview" action="CustomerReview" method="post">
				<input type="hidden" name="concertId" value="${concert.id}">
				<div>What did you think of <b>${concert.name}</b>?</div>
				<br>
				
				<table class="tbl noBorder">
					<tr>
						<td>Review:</td>
						<td><input type="text" class="textInputLong" id="reviewBox" name="review"></td>				
					</tr>
					<tr>
						<td>Rating (out of 5):</td>
						<td><input type="text" class="textInput" name="rating"></td>
					</tr>
				</table>
				
				<br><br>
				
				<button type="button" class="backButton btn btn-sm" onclick="history.back()">Back</button>
				<button type="submit" class="btn btn-sm right">Submit</button>
			</form>
		
		</div>
		
	</body>
</html>