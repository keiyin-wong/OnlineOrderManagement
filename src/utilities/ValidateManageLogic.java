package utilities;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public class ValidateManageLogic {

	public static String validateManager(HttpServletRequest request) {
		if (request.getParameter("UPDATE") != null && request.getParameter("UPDATE").equals("UPDATE")) {
			return "UPDATE";
		} else if (request.getParameter("DELETE") != null && request.getParameter("DELETE").equals("DELETE")) {
			return "DELETE";
		}
		return "ADD";
	}

	// this method is used to notify a user that a record has been updated and to
	// redirect to another page
	public static void UpdateSuccess(PrintWriter out) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Record has succesfully been updated!\")");
		out.println("window.location.assign(\"PaymentPaginationServlet?recordsPerPage=10%20&currentPage=1\")");
		out.println("</SCRIPT>");
	}
	
	public static void DeleteSuccess(PrintWriter out) {
		out.println("<SCRIPT type=\"text/javascript\">");
		out.println("alert(\"Record has succesfully been deleted!\")");
		out.println("window.location.assign(\"PaymentPaginationServlet?recordsPerPage=10%20&currentPage=1\")");
		out.println("</SCRIPT>");
	}

}
