package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Cart;
import domain.Product;
import sessionbean.ProductSessionBean;

/**
 * Servlet implementation class ProductPaginationServlet
 */
@WebServlet("/ProductPaginationServlet")
public class ProductPaginationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ProductSessionBean productSessionBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductPaginationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
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
			dest = "product.jsp";

		} catch (EJBException ex) {

		}
		request.setAttribute("nOfPages", nOfPages);
		request.setAttribute("currentPage", currentPage);
		List<Cart> cartlist= new ArrayList<Cart>();
		session.setAttribute("CartList", cartlist);
		RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
