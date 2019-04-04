package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataaccessors.ConcertsDB;
import models.Concert;

/**
 * Servlet implementation class ConcertDetailsSelection
 */
@WebServlet("/ConcertDetailsSelection")
public class ConcertDetailsSelection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConcertDetailsSelection() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int concertId = Integer.parseInt(request.getParameter("concertId"));
		Concert concert = ConcertsDB.getConcertById(concertId);
		
		request.setAttribute("concert", concert);

	    String address = "CustomerReview.jsp";
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
