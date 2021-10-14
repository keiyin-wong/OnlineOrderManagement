package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Employee;
import sessionbean.EmployeeSessionBean;
import sessionbean.EmployeeSessionBeanLocal;

/**
 * Servlet implementation class AdminEmployeeNumberValidate
 */
@WebServlet("/AdminEmployeeNumberValidate")
public class AdminEmployeeNumberValidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @EJB
	 private EmployeeSessionBeanLocal employeeSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEmployeeNumberValidate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String empNumber = request.getParameter("empNumber");
		PrintWriter out = response.getWriter();
		Employee employee = employeeSessionBean.findEmployee(empNumber);
		if(employee!=null)
			out.print("Employee exists");
		else
			out.print("");
		
	}

}
