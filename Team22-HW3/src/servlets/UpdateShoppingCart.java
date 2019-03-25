package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataaccessors.PerformancesDB;
import models.Performance;
import models.OrderItem;
import models.ShoppingCart;

/**
 * Servlet implementation class UpdateShoppingCart
 */
@WebServlet("/UpdateShoppingCart")
public class UpdateShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
		List<OrderItem> orderItems = shoppingCart.getOrderItems();
		String requestType = request.getParameter("requestType");
		if(requestType.equals("add")) {
			int performanceId = Integer.parseInt(request.getParameter("performanceId"));
			Performance performance = PerformancesDB.getPerformanceById(performanceId);
			int quantity = Integer.parseInt(request.getParameter("quantity"));
						
			if(performance.getAvailableSeats() >= quantity) {
				if(orderItems.isEmpty()) {
					OrderItem orderItem = new OrderItem(performance, quantity);
					orderItems.add(orderItem);
				} else {
					boolean orderItemExisted = false;	
					for(OrderItem item : orderItems) {
						if(item.getPerformance().getId() == performance.getId()) {
							int startQuantity = item.getQuantity();
							item.setQuantity(startQuantity + quantity);
							orderItemExisted = true;
							break;
						}
					}
					if(!orderItemExisted) {
						OrderItem orderItem = new OrderItem(performance, quantity);
						orderItems.add(orderItem);
					}
				}
			} 
		} else if (requestType.equals("delete")) {
			
			int orderItemDelete = Integer.parseInt(request.getParameter("orderItemDelete"));
			orderItems.remove(orderItemDelete);
			
		}
		
		shoppingCart.setOrderItems(orderItems);
		
		session.setAttribute("shoppingCart", shoppingCart);

	    String address = "ViewAndCheckoutShoppingCart.jsp";
	    RequestDispatcher dispatcher =
	      request.getRequestDispatcher(address);
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
