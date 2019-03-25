<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Payment</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
	</head>
	
	<script>
		function viewOrderItem(){
			var oi = event.srcElement.id;
			document.getElementById("orderItemInput").value = oi;
		}
	</script>
	
	<body>
		
		<div class="container">
		
			<h3>Payment and Shipping Information</h3>
			<form name="orderItemDetails" action="orderItemDetails" method="post">
				<input type="hidden" id="orderItemInput" name="orderItem" value="">
				<table class="tbl colWider noBorder">
					<tr>
						<td>Concert Name</td>
						<td>Ticket Quantity</td>
						<td>Total Price</td>
						<td>Venue Name</td>
					</tr>
					<c:forEach var="orderItem" items="${shoppingCart.orderItems}">
						<tr>
							<td>${orderItem.performance.concert.name}</td>
							<td>${orderItem.quantity}</td>
							<td>${orderItem.getPrettyPrice()}</td>
							<td>${orderItem.performance.venue.name}</td>
							<td><button id="${orderItem.id}" onclick="viewOrderItem()" class="btn btn-sm right">View Details</button></td>
						</tr>
					</c:forEach>
				</table>
			</form>
			<br><br>
			
			
			<form name="cardHoldersInfo" action="CustomerTransactionConfirmation" method="post">
				<table class="tbl noBorder">
					<tr>
						<td>Total Cost: ${shoppingCart.getPrettyPrice()}</td>
					</tr>
					<tr>
						<td>First Name: </td>
						<td><input type="text" name="firstName" class="textInput"></td>
					</tr>
					
					<tr>
						<td>Last Name: </td>
						<td><input type="text" name="lastName" class="textInput"></td>
					</tr>
					
					<tr>
						<td>Card Type: </td>
						<td>
							<select name="cardType" class="dropdownInput">
								<option value="Visa">Visa</option>
								<option value="Master">Master</option>
								<option value="Discover">Discover</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>Card Number: </td>
						<td><input type="text" name="cardNumber" class="textInput"></td>
					</tr>
					
					<tr>
						<td>Security Code: </td>
						<td><input type="text" name="securityCode" class="textInput"></td>
					</tr>
					 
					
					<tr>
						<td>Expiration Date:</td>
						<td>
							<select name="expirationMonth" class="dropdownInput halfInput">
								<option value="" disabled selected>Month</option>
								<option value="01">01 - January</option>
								<option value="02">02 - February</option>
								<option value="03">03 - March</option>
								<option value="04">04 - April</option>
								<option value="05">05 - May</option>
								<option value="06">06 - June</option>
								<option value="07">07 - July</option>
								<option value="08">08 - August</option>
								<option value="09">09 - September</option>
								<option value="10">10 - October</option>
								<option value="11">11 - November</option>
								<option value="12">12 - December</option>
							</select>
							<select name="expirationYear" class="dropdownInput halfInput">
								<option value="" disabled selected>Year</option>
								<option value="2018">2018</option>
								<option value="2019">2019</option>
								<option value="2020">2020</option>
								<option value="2021">2021</option>
								<option value="2022">2022</option>
								<option value="2023">2023</option>
								<option value="2024">2024</option>
								<option value="2025">2025</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">Billing Address: </td>
					</tr>
					<tr>
						<td>Street: </td>
						<td><input type="text" name="street" class="textInput streetInput"></td>
					</tr>
					<tr>
						<td>City: </td>
						<td><input type="text" name="city" class="textInput cityInput"></td>
					</tr>
					<tr>
						<td>State: </td>
						<td>
							<select name="state">
								<option value="AL">Alabama</option>
								<option value="AK">Alaska</option>
								<option value="AZ">Arizona</option>
								<option value="AR">Arkansas</option>
								<option value="CA">California</option>
								<option value="CO">Colorado</option>
								<option value="CT">Connecticut</option>
								<option value="DE">Delaware</option>
								<option value="DC">District Of Columbia</option>
								<option value="FL">Florida</option>
								<option value="GA">Georgia</option>
								<option value="HI">Hawaii</option>
								<option value="ID">Idaho</option>
								<option value="IL">Illinois</option>
								<option value="IN">Indiana</option>
								<option value="IA">Iowa</option>
								<option value="KS">Kansas</option>
								<option value="KY">Kentucky</option>
								<option value="LA">Louisiana</option>
								<option value="ME">Maine</option>
								<option value="MD">Maryland</option>
								<option value="MA">Massachusetts</option>
								<option value="MI">Michigan</option>
								<option value="MN">Minnesota</option>
								<option value="MS">Mississippi</option>
								<option value="MO">Missouri</option>
								<option value="MT">Montana</option>
								<option value="NE">Nebraska</option>
								<option value="NV">Nevada</option>
								<option value="NH">New Hampshire</option>
								<option value="NJ">New Jersey</option>
								<option value="NM">New Mexico</option>
								<option value="NY">New York</option>
								<option value="NC">North Carolina</option>
								<option value="ND">North Dakota</option>
								<option value="OH">Ohio</option>
								<option value="OK">Oklahoma</option>
								<option value="OR">Oregon</option>
								<option value="PA">Pennsylvania</option>
								<option value="RI">Rhode Island</option>
								<option value="SC">South Carolina</option>
								<option value="SD">South Dakota</option>
								<option value="TN">Tennessee</option>
								<option value="TX">Texas</option>
								<option value="UT">Utah</option>
								<option value="VT">Vermont</option>
								<option value="VA">Virginia</option>
								<option value="WA">Washington</option>
								<option value="WV">West Virginia</option>
								<option value="WI">Wisconsin</option>
								<option value="WY">Wyoming</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>Zip Code: </td>
						<td><input type="text" name="zip" class="textInput zipInput" maxlength="5"></td>
					</tr>
					
					
					
				</table>
				
				<button type="submit" class="btn">Confirm Payment</button>
				<button class="btn" onclick="history.back()">Cancel Payment</button>
				
			</form>
		</div>
	</body>
	
</html>