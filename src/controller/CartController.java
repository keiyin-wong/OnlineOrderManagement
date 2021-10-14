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
 * Servlet implementation class CartController
 */
@WebServlet("/CartController")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProductSessionBean productSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		String id=request.getParameter("id");
		if(!id.equalsIgnoreCase("view"))
		{
			Product product=productSessionBean.findProduct(id);
			Cart cart=new Cart(product.getProductcode(),product.getMsrp(),product.getProductname());
			if(session.getAttribute("CartList")==null) {
				cart.setQuantity(1);
				List<Cart> cartlist= new ArrayList<Cart>();
				cartlist.add(cart);
				session.setAttribute("CartList", cartlist);
			}
			else {
				List<Cart> cartlist=(List<Cart>) session.getAttribute("CartList");
				int index = exists(cartlist,id);
				if(index==-1) {
					cart.setQuantity(1);
					cartlist.add(cart);
				}
				else {
					cartlist.get(index).setQuantity(cartlist.get(index).getQuantity()+1);
				}
				session.setAttribute("CartList", cartlist);
			}
		}
		String dest="cart.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
		dispatcher.forward(request, response);
	}
	
	private int exists(List<Cart> cartlist,String id)
	{
		for(int i=0;i<cartlist.size();i++) {
			if(cartlist.get(i).getProductcode().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
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
			if (request.getParameter("UPDATE") != null && request.getParameter("UPDATE").equals("Update")) {
				for(Cart cart:cartlist)
				{
					if(!request.getParameter("quantity"+cart.getProductcode()).equalsIgnoreCase("")&&request.getParameter("quantity"+cart.getProductcode())!=null) {
						Integer quantity=Integer.parseInt(request.getParameter("quantity"+cart.getProductcode()));
						if(quantity>0) {
							cart.setQuantity(quantity);
							updatecartlist.add(cart);
						}
					}
				}
				session.setAttribute("CartList", updatecartlist);
				String dest="cart.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
				
			} else if (request.getParameter("RETURN") != null && request.getParameter("RETURN").equals("Continue shopping")) {
				String dest="ProductPaginationServlet?currentPage=1";
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
			} else if(request.getParameter("ADD") != null && request.getParameter("ADD").equals("Place Orders")){
				String dest="comfirmorder.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(dest);
				dispatcher.forward(request, response);
			}
		} catch (EJBException ex) {
		}
		
	}

}
