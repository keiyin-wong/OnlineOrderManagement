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
import javax.swing.event.ListSelectionListener;

import com.sun.mail.imap.protocol.ID;

import domain.Order;
import sessionbean.EmployeeSessionBean;
import sessionbean.OrderSessionBeanLocal;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@EJB
	private OrderSessionBeanLocal orderSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String search = request.getParameter("search");
		String dest = "employeefilter.jsp";
		System.out.println(search);
		try {
			List<Order> lists = new ArrayList<Order>();
			Order order = orderSessionBean.findOrder(search);
			if(search.equals("")) {
				dest = "/EmployeeOrderServlet?currentPage=1";
			}
			else if(order!=null) {
				lists.add(order);
				request.setAttribute("searchFlag", "true");
			}
			
			request.setAttribute("orders", lists);
			request.setAttribute("nOfPages", 1);
			request.setAttribute("currentPage", 1);
			RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
			dispatcher.forward(request, response);

		} catch (EJBException ex) {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
