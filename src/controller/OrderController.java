package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import domain.CartList;
import sessionbean.OrderSessionBean;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	OrderSessionBean orderSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
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
		HttpSession session=request.getSession();
		List<Cart> cartlist=(List<Cart>) session.getAttribute("CartList");
		List<Cart> updatecartlist=new ArrayList<Cart>();
		try {
			if (request.getParameter("RETURN") != null && request.getParameter("RETURN").equals("Back to Cart")) {
				String dest="cart.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
			} else {
				CartList finalcartlist=new CartList();
				String requireddate=request.getParameter("requireddate");
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
			    cal.add(Calendar.DATE, 3);
			    Date date1=cal.getTime();
			    if(!request.getParameter("customernumber").equalsIgnoreCase(""))
			    {
			    	if(!requireddate.equalsIgnoreCase(""))
					{
						date1=new SimpleDateFormat("yyyy-MM-dd").parse(requireddate);
					}
					finalcartlist.setRequiredDate(date1);
					finalcartlist.setCustomernumber(Integer.parseInt(request.getParameter("customernumber")));
					finalcartlist.setCartList(cartlist);
					if(orderSessionBean.createOrder(finalcartlist))
					{
						session.removeAttribute("CartList");
						String dest="ProductPaginationServlet?currentPage=1";
						RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
						dispatcher.forward(request, response);
					}
			    }
				String dest="comfirmorder.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
				
			}
		} catch (EJBException ex) {
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
