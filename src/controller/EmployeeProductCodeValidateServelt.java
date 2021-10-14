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
import domain.Product;
import sessionbean.ProductSessionBeanLocal;

/**
 * Servlet implementation class EmployeeProductCodeValidateServelt
 */
@WebServlet("/EmployeeProductCodeValidateServelt")
public class EmployeeProductCodeValidateServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private ProductSessionBeanLocal productSessionBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeProductCodeValidateServelt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		String productCode = request.getParameter("productCode");
		PrintWriter out = response.getWriter();
		Product product = productSessionBean.findProduct(productCode);
		if(product!=null)
			out.print("Product exists");
		else
			out.print("");
	}

}
