<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home Page</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
<body>
	
		<div class="container">
		
			<h3>Home Page</h3>
		
			<form action="VenueAndConcertSearchQuery" method="post">
				<table class="tbl noBorder">
					<tr>
						<td>
							Location:
						</td>
						<td>
							<select name="location" class="dropdownInput">
								<option value="0" selected>Any Location</option>
								 <c:forEach var="location" items="${locations}" >
								    <option value="${location.id}">${location.name}</option> 
								</c:forEach>
							</select>
						</td>
						<td rowspan="2">
							<button class="btn right" type="submit">Search</button>
						</td>
					</tr>
					<tr>
						<td>
							Enter a search term:
						</td>
						<td>
							<input type="text" class="textInput" name="searchTerm">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>