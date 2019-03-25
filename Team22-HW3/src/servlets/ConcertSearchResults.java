package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataaccessors.PerformancesDB;
import dataaccessors.ReviewsDB;
import models.Performance;
import models.Review;

/**
 * Servlet implementation class ConcertSearchResults
 */
@WebServlet("/ConcertSearchResults")
public class ConcertSearchResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConcertSearchResults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int performanceId = Integer.parseInt(request.getParameter("concertChoice"));
		Performance performance = PerformancesDB.getPerformanceById(performanceId);
		
		int concertId = performance.getConcert().getId();
		List<Review> customerReviews = ReviewsDB.getReviewsByConcertId(concertId);
		
		request.setAttribute("performance", performance);
		request.setAttribute("reviews", customerReviews);

	    String address = "ConcertDetailsSelection.jsp";
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
