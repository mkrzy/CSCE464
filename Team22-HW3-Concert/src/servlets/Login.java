package servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.Order;
import models.OrderItem;
import models.ShoppingCart;
import models.Venue;
import other.PasswordUtil;
import models.User;
import dataaccessors.VenuesDB;
import dataaccessors.OrdersDB;
import dataaccessors.UsersDB;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Login.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropertyConfigurator.configure(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			String hashPassword = PasswordUtil.hashPasswordAlternative(password);
			
			User aUser = new User();
			ShoppingCart aShoppingCart = new ShoppingCart(new ArrayList<OrderItem>());
			List<Order> orders = new ArrayList<Order>();
					
			boolean userExists = UsersDB.userExists(username);
			boolean userPasswordMatches = UsersDB.verifyUser(username, hashPassword);
			
			if(userExists && userPasswordMatches) {
				aUser = UsersDB.getUserByUsername(username);
				orders = OrdersDB.getOrdersByCustomerId(aUser.getId());
				HttpSession session = request.getSession();
			    session.setAttribute("userBean", aUser);
			    session.setAttribute("shoppingCart", aShoppingCart);
			    session.setAttribute("orders", orders);
			    
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
			
		} catch (NoSuchAlgorithmException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
