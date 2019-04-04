package servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import models.User;
import other.PasswordUtil;
import dataaccessors.UsersDB;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(Registration.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropertyConfigurator.configure(getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("classes", "lib")+"log4j.properties");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		try {
			String hashPassword = PasswordUtil.hashPasswordAlternative(password);
			
			User aUser = new User(username, hashPassword, firstName, lastName);
							
			boolean userExists = UsersDB.userExists(username);
			
			if(!userExists) {
				UsersDB.registerUser(aUser);
			}
			
			response.sendRedirect("Login.jsp");
			
		} catch (NoSuchAlgorithmException e) {
			log.error("SQLException: ",e);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}