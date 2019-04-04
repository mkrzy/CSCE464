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

import dataaccessors.PerformancesDB;
import models.Performance;

/**
 * Servlet implementation class VenueAndConcertSearchQuery
 */
@WebServlet("/VenueAndConcertSearchQuery")
public class VenueAndConcertSearchQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VenueAndConcertSearchQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String venue = request.getParameter("location");
		int venueId;
		if(!venue.isEmpty()) {
			venueId = Integer.parseInt(venue);
		} else {
			venueId = 0;
		}
		String searchTerm = request.getParameter("searchTerm");
		List<Performance> performances = new ArrayList<Performance>();
		performances = PerformancesDB.getPerformancesFromSearch(venueId, searchTerm);
		
		request.setAttribute("searchResults", performances);
	    String address = "ConcertSearchResults.jsp";
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
