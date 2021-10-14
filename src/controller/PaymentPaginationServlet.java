package controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Payment;
import sessionbean.PaymentSessionBean;
import sessionbean.PaymentSessionBeanLocal;

/**
 * Servlet implementation class PaymentPaginationServlet
 */
@WebServlet("/PaymentPaginationServlet")
public class PaymentPaginationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private PaymentSessionBean paybean;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentPaginationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		// Write some codes here…
		int nOfPages = 0;
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		
		try {
			int rows = paybean.getNumberOfRows();
			nOfPages = rows / 10;
			System.out.println("At servlet" + nOfPages);
			if (rows % 10 != 0) {
				nOfPages++;
			}
			if(currentPage > nOfPages && nOfPages !=0)
			{
			currentPage = nOfPages;
			}
			List<Payment> lists = paybean.readPayment(currentPage);
			//List<Employee> lists = empbean.readStaff(currentPage, recordsPerPage, keyword);
			request.setAttribute("payments", lists);
		} catch (EJBException ex) {
		}
		request.setAttribute("nOfPages", nOfPages);
		request.setAttribute("currentPage", currentPage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("PaymentPagination.jsp");
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
