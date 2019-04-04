package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dataaccessors.ConcertsDB;
import dataaccessors.ReviewsDB;
import models.Concert;
import models.Review;
import models.User;

/**
 * Servlet implementation class CustomerReview
 */
@WebServlet("/CustomerReview")
public class CustomerReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerReview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String review = request.getParameter("review");
		double rating = Double.parseDouble(request.getParameter("rating"));
		User user = (User) session.getAttribute("userBean");
		int concertId = Integer.parseInt(request.getParameter("concertId"));
		Concert concert = ConcertsDB.getConcertById(concertId);
		Date reviewDate = new Date(new java.util.Date().getTime());
		
		System.out.println(user);
		System.out.println(user.getId());
		Review aReview = new Review(concert, user, review, rating, reviewDate);
		
		String returnMessage = ReviewsDB.addReview(aReview);
		request.setAttribute("message", returnMessage);

		response.setContentType("text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter(); 
		
		out.println(returnMessage);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
