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

import domain.Order;
import domain.Orderdetail;
import sessionbean.EmployeeSessionBean;
import sessionbean.OrderSessionBeanLocal;

/**
 * Servlet implementation class EmployeeOrderDetailsServlet
 */
@WebServlet("/EmployeeOrderDetailsServlet")
public class EmployeeOrderDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrderSessionBeanLocal orderSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeOrderDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.getWriter().append("Served at: ").append(request.getParameter("orderNumber"));
		String orderNumber = request.getParameter("orderNumber");
		Order order = orderSessionBean.findOrder(orderNumber);
		List<Orderdetail> orderDetailsList = orderSessionBean.findOrderDetails(order);
		
		request.setAttribute("order", order);
		request.setAttribute("orderDetailsList", orderDetailsList);
		request.setAttribute("creditLimit", orderSessionBean.getCreditLimit(orderNumber));
		request.setAttribute("total", orderSessionBean.getTotal(order));
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("orderdetailedit.jsp");
        dispatcher.forward(request, response);
        return; 
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String orderNumber = request.getParameter("orderNumber");
		String orderDate = request.getParameter("orderDate");
		String requiredDate = request.getParameter("requiredDate");
		String shippedDate = request.getParameter("shippedDate");
		String status = request.getParameter("status");
		String comments = request.getParameter("comments");
		String customerNumber = request.getParameter("customerNumber");
		
		String [] s = {orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber};
		
		
		try {
			orderSessionBean.updateOrder(s);
			PrintWriter out = response.getWriter();
			out.println("<SCRIPT type=\"text/javascript\">");
			out.println("alert(\"Record has been updated\")");
			out.println("window.location.assign(\"EmployeeOrderDetailsServlet?orderNumber=" + orderNumber + "\")");
			out.println("</SCRIPT>");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}

}
