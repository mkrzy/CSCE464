package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
		
		String creditCardNumber = request.getParameter("cardNumber");
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
		session.setAttribute("shoppingCart", new ShoppingCart(new ArrayList<OrderItem>()));
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
