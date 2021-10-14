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
import domain.Product;
import sessionbean.EmployeeSessionBean;
import sessionbean.ProductSessionBeanLocal;

/**
 * Servlet implementation class EmployeeProductServlet
 */
@WebServlet("/EmployeeProductServlet")
public class EmployeeProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private ProductSessionBeanLocal productSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		int nOfPages = 0;
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		String dest = null;
		
		try {

			// int rows = empbean.getNumberOfRows(keyword);
			int rows = productSessionBean.getNumberOfRows();
			nOfPages = rows / 10;
			if (rows % 10 != 0) {
				nOfPages++;
			}
			if (currentPage > nOfPages && nOfPages != 0) {
				currentPage = nOfPages;
			}
			// List<EmployeeEntity> lists = empbean.readStaff(currentPage,
			// recordsPerPage,keyword);
			List<Product> lists = productSessionBean.readProductAccordingToPage(currentPage);
			
			request.setAttribute("products", lists);
			dest = "employeeproduct.jsp";

		} catch (EJBException ex) {

		}
		request.setAttribute("nOfPages", nOfPages);
		request.setAttribute("currentPage", currentPage);
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
