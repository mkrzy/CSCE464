package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.OrderItem;
import models.ShoppingCart;
import models.Venue;
import models.User;
import dataaccessors.VenuesDB;
import dataaccessors.UsersDB;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User aUser = new User();
		ShoppingCart aShoppingCart = new ShoppingCart(new ArrayList<OrderItem>());
				
		boolean userExists = UsersDB.userExists(username);
		boolean userPasswordMatches = UsersDB.verifyUser(username, password);
		
		if(userExists && userPasswordMatches) {
			aUser = UsersDB.getUserByUsername(username);
			HttpSession session = request.getSession();
		    session.setAttribute("userBean", aUser);
		    session.setAttribute("shoppingCart", aShoppingCart);
		    
		    List<Venue> venues = new ArrayList<Venue>();
			venues = VenuesDB.getAllVenues();
			session.setAttribute("locations", venues);
		    
		    String address = "CustomerHomePage.jsp";
		    RequestDispatcher dispatcher =
		      request.getRequestDispatcher(address);
		    dispatcher.forward(request, response);
		} else {
			response.sendRedirect("Registration.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
