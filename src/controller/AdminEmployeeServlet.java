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

import domain.Employee;
import sessionbean.EmployeeSessionBean;
import sessionbean.EmployeeSessionBeanLocal;

/**
 * Servlet implementation class AdminEmployeeServlet
 */
@WebServlet("/AdminEmployeeServlet")
public class AdminEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmployeeSessionBeanLocal employeeSessionBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminEmployeeServlet() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		int nOfPages = 0;
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		String commit = request.getParameter("commit");
		String searchValue = request.getParameter("searchValue");
		String dest = "adminemployee.jsp";
		
		if(commit!=null && commit.equals("Search")) {
			if(searchValue.equals("")){
				response.sendRedirect("AdminEmployeeServlet?currentPage=1");
				return;
			}
			try {
				List<Employee> employeeList = new ArrayList<Employee>();
				Employee employee = employeeSessionBean.findEmployee(searchValue);
				if(employee!=null) {
					employeeList.add(employee);
				}
				request.setAttribute("employees", employeeList);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			try {

				// int rows = empbean.getNumberOfRows(keyword); 
				int rows = employeeSessionBean.getNumberOfRows();
				nOfPages = rows / 10;
				if (rows % 10 != 0) {
					nOfPages++;
				}
				if (currentPage > nOfPages && nOfPages != 0) {
					currentPage = nOfPages;
				} // List<EmployeeEntity> lists = empbean.readStaff(currentPage,
				// recordsPerPage,keyword); List<Product> lists =
				List<Employee> lists = employeeSessionBean.readEmployeeAccordingToPage(currentPage);

				request.setAttribute("employees", lists);

			} catch (EJBException ex) {

			}
		}

		
		request.setAttribute("nOfPages", nOfPages);
		request.setAttribute("currentPage", currentPage);
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
