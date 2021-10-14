package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Employee;
import domain.Product;
import sessionbean.EmployeeSessionBean;
import sessionbean.EmployeeSessionBeanLocal;
/**
 * Servlet implementation class AdminEmployeeDetailsServlet
 */
@WebServlet("/AdminEmployeeDetailsServlet")
public class AdminEmployeeDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       @EJB
       private EmployeeSessionBeanLocal employeeSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEmployeeDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String empNumber = request.getParameter("empNumber");
		String createEmployee = request.getParameter("createEmployee");
		String dest = null;
		if (createEmployee != null && createEmployee.equals("CREATEEMPLOYEE")) {
			dest = "adminemployeedetailcreate.jsp";
		} else {
			Employee employee = employeeSessionBean.findEmployee(empNumber);
			dest = "adminemployeedetailedit.jsp";
			request.setAttribute("employee", employee);
		}

		List<String> officeCodeList = employeeSessionBean.getAllOfficeCode();
		request.setAttribute("officeCodeList", officeCodeList);

		RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String create = request.getParameter("create");
		String delete = request.getParameter("delete");
		String empNumber = request.getParameter("empNumber");
		String empFirstName = request.getParameter("empFirstName");
		String empLastName = request.getParameter("empLastName");
		String empEmail = request.getParameter("empEmail");
		String empExtension = request.getParameter("empExtension");
		String empOfficeCode = request.getParameter("empOfficeCode");
		String empReportsto = request.getParameter("empReportsto");
		String empJobTitle = request.getParameter("empJobTitle");
		String [] s = {empNumber, empFirstName, empLastName, empEmail, empExtension, empOfficeCode, empReportsto, empJobTitle};
		PrintWriter out = response.getWriter();
		

		if (create != null && create.equals("Create")) {
			try {
				employeeSessionBean.createEmployee(s);
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Successfully create employee\")");
				out.println("window.location.assign(\"AdminEmployeeServlet?currentPage=1\")");
				out.println("</SCRIPT>");
			} catch (Exception e) {
				// TODO: handle exception
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Create employee failed\")");
				out.println("history.back()");
				out.println("</SCRIPT>");
				System.out.println(e.toString());
			}

		} else if (delete != null && delete.equals("Delete")) {
			try {
				employeeSessionBean.deleteEmployee(empNumber);
				out.println("Successfully delete");
			} catch (Exception e) {
				// TODO: handle exception
				out.println("Failed");
			}

		} else {

			try {
				employeeSessionBean.updateEmployee(s);
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Record has been updated\")");
				out.println(
						"window.location.assign(\"AdminEmployeeDetailsServlet?empNumber=" + empNumber + "\")");
				out.println("</SCRIPT>");

			} catch (Exception e) {
				// TODO: handle exception
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Update failed\")");
				out.println(
						"window.location.assign(\"AdminEmployeeDetailsServlet?empNumber=" + empNumber + "\")");
				out.println("</SCRIPT>");
			}
		}

	}
}

