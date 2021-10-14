package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Employee;
import domain.User;
import sessionbean.EmployeeSessionBean;


/**
 * Servlet implementation class EmployeeEditPersonalInformation
 */
@WebServlet("/EmployeeEditPersonalInformation")
public class EmployeeEditPersonalInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	EmployeeSessionBean employeeSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEditPersonalInformation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeepersonalinformation.jsp");
        dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String empNumber = request.getParameter("empNumber");
		String empFirstName = request.getParameter("empFirstName");
		String empLastName = request.getParameter("empLastName");
		String empEmail = request.getParameter("empEmail");
		String empExtension = request.getParameter("empExtension");
		String empOfficeCode = request.getParameter("empOfficeCode");
		String empReportsto = request.getParameter("empReportsto");
		String empJobTitle = request.getParameter("empJobTitle");
		String [] s = {empNumber, empFirstName, empLastName, empEmail, empExtension, empOfficeCode, empReportsto, empJobTitle};
		
		try {
			employeeSessionBean.updateEmployee(s, request);
			PrintWriter out = response.getWriter();
			out.println("<SCRIPT type=\"text/javascript\">");
			out.println("alert(\"Record has been updated\")");
			out.println("window.location.assign(\"EmployeeEditPersonalInformation\")");
			out.println("</SCRIPT>");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
