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

import domain.Order;
import sessionbean.OrderSessionBean;

/**
 * Servlet implementation class OrderPaginationServlet
 */
@WebServlet("/OrderPaginationServlet")
public class OrderPaginationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrderSessionBean orderSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderPaginationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int nOfPages = 0;
		String shipped = request.getParameter("shipped");
		String inProcess = request.getParameter("inProcess");
		String onHold = request.getParameter("onHold");
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		String dest = null;

		if (shipped != null && shipped.equals("SHIPPED")) {
			try {

				// int rows = empbean.getNumberOfRows(keyword);
				int rows = orderSessionBean.getNumberOfRows_orderShipped();
				nOfPages = rows / 10;
				if (rows % 10 != 0) {
					nOfPages++;
				}
				if (currentPage > nOfPages && nOfPages != 0) {
					currentPage = nOfPages;
				}
				// List<EmployeeEntity> lists = empbean.readStaff(currentPage,
				// recordsPerPage,keyword);
				List<Order> lists = orderSessionBean.readOrderAccordingToPageShipped(currentPage);
				request.setAttribute("orders", lists);
				dest = "employeeshipped.jsp";

			} catch (EJBException ex) {

			}
			request.setAttribute("nOfPages", nOfPages);
			request.setAttribute("currentPage", currentPage);

		}else {
			try {

				// int rows = empbean.getNumberOfRows(keyword);
				int rows = orderSessionBean.getNumberOfRows_order();
				nOfPages = rows / 10;
				if (rows % 10 != 0) {
					nOfPages++;
				}
				if (currentPage > nOfPages && nOfPages != 0) {
					currentPage = nOfPages;
				}
				// List<EmployeeEntity> lists = empbean.readStaff(currentPage,
				// recordsPerPage,keyword);
				List<Order> lists = orderSessionBean.readOrderAccordingToPage(currentPage);
				request.setAttribute("orders", lists);
				dest = "employee.jsp";

			} catch (EJBException ex) {

			}
			request.setAttribute("nOfPages", nOfPages);
			request.setAttribute("currentPage", currentPage);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
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
