package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataaccessors.AddressesDB;
import dataaccessors.CreditCardsDB;
import dataaccessors.OrdersDB;
import models.Address;
import models.CreditCard;
import models.Order;
import models.OrderItem;
import models.ShoppingCart;
import models.User;

/**
 * Servlet implementation class PlaceOrder
 */
@WebServlet("/PlaceOrder")
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaceOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("userBean");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String cardHolderName = firstName + " " + lastName;
		
		String cardType = request.getParameter("cardType");
		String creditCardNumber = request.getParameter("cardNumber");
		int securityCode = Integer.parseInt(request.getParameter("securityCode"));
		int expirationMonth = 0;
		int expirationYear = 0;
		if(!request.getParameter("expirationMonth").isEmpty()) {
			expirationMonth = Integer.parseInt(request.getParameter("expirationMonth"));
		}
		if(!request.getParameter("expirationYear").isEmpty()) {
			expirationYear = Integer.parseInt(request.getParameter("expirationYear"));
		}
		Date expirationDate = Date.valueOf(expirationYear+"-"+expirationMonth+"-1");
		
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		int zip = Integer.parseInt(request.getParameter("zip"));
		Address billAddress = new Address(street, city, state, zip);
		Address billingAddress = AddressesDB.getAddress(billAddress);
				
		CreditCard aCard = CreditCardsDB.getCreditCardByNumber(creditCardNumber);
		
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<OrderItem> orderItems = shoppingCart.getOrderItems();
		double totalCost = shoppingCart.getTotalCost();

		Date orderDate = new Date(new java.util.Date().getTime());
				
		Order anOrder = new Order(user, totalCost, aCard, billingAddress, orderDate, orderItems);
		String returnMessage = OrdersDB.addOrder(anOrder);
		session.setAttribute("order", anOrder);
		session.setAttribute("shoppingCart", new ShoppingCart());
		request.setAttribute("returnMessage", returnMessage);

		response.setContentType("text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter(); 
		
		out.println("<html>"+returnMessage+"</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
