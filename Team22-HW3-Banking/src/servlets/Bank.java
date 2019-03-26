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

import dataaccessors.BankDB;
import models.BankAccount;



/**
 * Servlet implementation class Bank
 */
@WebServlet("/Bank")
public class Bank extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bank() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("userBean");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int userId = Integer.parseInt(request.getParameter("userId"));
		double shoppingCartTotal = Double.parseDouble(request.getParameter("shoppingCartTotal"));
		String cardHolderName = firstName + " " + lastName;
		
		System.out.println(cardHolderName);
		
		String cardType = request.getParameter("cardType");
		System.out.println(cardType);
		String creditCardNumber = request.getParameter("cardNumber");
		System.out.println(creditCardNumber);
		System.out.println(request.getParameter("securityCode"));
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
		
		
				
		BankAccount anAccount = new BankAccount(userId, cardHolderName, cardType, creditCardNumber, securityCode, expirationDate);
		BankAccount bankAccount = BankDB.addBankAccount(anAccount);
		String responseMessage = BankDB.adjustBankAccountBalance(bankAccount, -shoppingCartTotal);
				
		response.setContentType("text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter(); 
		
		out.println("<html>"+responseMessage+"</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
