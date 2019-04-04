<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Payment</title>
		<jsp:include page="Header.jsp" />
		<%@ taglib prefix="c"  uri="http://java.sun.com/jstl/core_rt" %>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	
	<script>
		function viewOrderItem(){
			var oi = event.srcElement.id;
			document.getElementById("concertChoiceInput").value = oi;
		}
		
		function confirm_function(){
			
			var firstName = $("#firstName").val();
			var lastName  = $("#lastName").val();
			var userId = $("#userId").val();
			var shoppingCartTotal = $("#shoppingCartTotal").val();
			var cardType = $("#cardType").val();
			var cardNumber = $("#cardNumber").val();
			var securityCode = $("#securityCode").val();
			var expirationMonth = $("#expirationMonth").val();
			var expirationYear = $("#expirationYear").val();
		
		    $.post("../Team22-HW3-Banking/Bank", {
		    	firstName:firstName,
		    	lastName:lastName,
		    	userId:userId,
		    	shoppingCartTotal:shoppingCartTotal,
		    	cardType:cardType,
		    	cardNumber:cardNumber,
		    	securityCode:securityCode,
		    	expirationMonth:expirationMonth,
		    	expirationYear:expirationYear
		    }, function(data,status) {
			    
				$("#results").html(data);
				if(data.indexOf("not successful") == -1){
					place_order_function();
				}
			});
		}
		
		function place_order_function(){
			var username = $("#username").val();
			var password = $("#password").val();
			var cardNumber = $("#cardNumber").val();
			var street = $("#street").val();
			var city = $("#city").val();
			var state = $("#state").val();
			var zip = $("#zip").val();
			
			$.post("../Team22-HW3-Concert/PlaceOrder", {
		    	username:username,
		    	password:password,
				cardNumber:cardNumber,
				street:street,
				city:city,
				state:state,
				zip:zip
			}, function(data,status){
				alert(data);
				if(data.indexOf("success") > -1){
					window.location = "ViewOrders.jsp";
				}
			});
			
		}
		
		function reauthenticate_function(){
			$("#userFormData").show();
			$("#confirmButton").show();
			$("#reauthButton").hide();
		}
		
	</script>
	</head>
	
	<body>
		
		<div class="container">
		
			<h3>Payment and Shipping Information</h3>
				<form name="orderItemDetails" id="orderItemDetails" action="ConcertSearchResults" method="post">
				<input type="hidden" id="concertChoiceInput" name="concertChoice" value="">
				<table class="tbl colWider noBorder">
					<tr>
						<td>Concert Name</td>
						<td>Ticket Quantity</td>
						<td>Total Price</td>
						<td>Venue Name</td>
					</tr>
					<c:forEach var="orderItem" items="${shoppingCart.orderItems}">
						<tr>
							<td><c:out value="${orderItem.performance.concert.name}"/></td>
							<td><c:out value="${orderItem.quantity}"/></td>
							<td><c:out value="${orderItem.getPrettyPrice()}"/></td>
							<td><c:out value="${orderItem.performance.venue.name}"/></td>
							<td><button id="${orderItem.performance.id}" onclick="viewOrderItem()" class="btn btn-sm right">View Details</button></td>
						</tr>
					</c:forEach>
				</table>
			</form>
			<br><br>
			
			<div id="formData">
				<form name="cardHoldersInfo" id="cardHolderInfo" action="CustomerTransactionConfirmation" method="post">
					<input type="hidden" id="userId" name="userId" value="${userBean.id}">
					<input type="hidden" id="shoppingCartTotal" name="shoppingCartTotal" value="${shoppingCart.totalCost}">
					<table class="tbl noBorder">
						<tr>
							<td><c:out value="Total Cost: ${shoppingCart.getPrettyPrice()}"/></td>
						</tr>
						<tr>
							<td>First Name: </td>
							<td><input type="text" name="firstName" id="firstName" class="textInput"></td>
						</tr>
						
						<tr>
							<td>Last Name: </td>
							<td><input type="text" name="lastName" id="lastName" class="textInput"></td>
						</tr>
						
						<tr>
							<td>Card Type: </td>
							<td>
								<select name="cardType" id="cardType" class="dropdownInput">
									<option value="Visa">Visa</option>
									<option value="Master">Master</option>
									<option value="Discover">Discover</option>
								</select>
							</td>
						</tr>
						
						<tr>
							<td>Card Number: </td>
							<td><input type="text" name="cardNumber" id="cardNumber" class="textInput"></td>
						</tr>
						
						<tr>
							<td>Security Code: </td>
							<td><input type="text" name="securityCode" id="securityCode" class="textInput"></td>
						</tr>
						 
						
						<tr>
							<td>Expiration Date:</td>
							<td>
								<select name="expirationMonth" id="expirationMonth" class="dropdownInput halfInput">
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
								<select name="expirationYear" id="expirationYear" class="dropdownInput halfInput">
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
							<td><input type="text" name="street" id="street" class="textInput streetInput"></td>
						</tr>
						<tr>
							<td>City: </td>
							<td><input type="text" name="city" id="city" class="textInput cityInput"></td>
						</tr>
						<tr>
							<td>State: </td>
							<td>
								<select name="state" id="state">
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
							<td><input type="text" name="zip" id="zip" class="textInput zipInput" maxlength="5"></td>
						</tr>
						
						
						
					</table>
					
					<div id="userFormData" style="display:none">
						<br>
						<p>Please reauthenticate user:</p>
						Username: <input type="text" name="username" id="username" class="textInputNice">
						Password: <input type="text" name="password" id="password" class="textInputNice">
						<br><br><br>
					</div>
					
					
				</form>
			</div>
			
			<button type="button" id="confirmButton" style="display:none" class="btn" onClick="confirm_function()">Confirm Payment</button>
			<button type="button" id="reauthButton" class="btn" onClick="reauthenticate_function()">Confirm Payment</button>
			<button class="btn" onclick="history.back()">Cancel Payment</button>			
		</div>
	</body>
	
</html>