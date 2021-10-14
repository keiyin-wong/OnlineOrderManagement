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

import domain.Product;
import sessionbean.ProductSessionBeanLocal;

/**
 * Servlet implementation class EmployeeProductDetailsServlet
 */
@WebServlet("/EmployeeProductDetailsServlet")
public class EmployeeProductDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private ProductSessionBeanLocal productSessionBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeProductDetailsServlet() {
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
		String productCode = request.getParameter("productCode");
		String createProduct = request.getParameter("createProduct");
		String dest = null;
		if (createProduct != null && createProduct.equals("CREATEPRODUCT")) {
			dest = "employeeproductdetailcreate.jsp";
		} else {
			Product product = productSessionBean.findProduct(productCode);
			dest = "employeeproductdetailedit.jsp";
			request.setAttribute("product", product);
		}

		List<String> productLinesList = productSessionBean.getAllProductLine();
		request.setAttribute("productLinesList", productLinesList);

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
		// doGet(request, response);
		String create = request.getParameter("create");
		String delete = request.getParameter("delete");
		String productCode = request.getParameter("productCode");
		String productName = request.getParameter("productName");
		String productLine = request.getParameter("productLine");
		String productScale = request.getParameter("productScale");
		String productVendor = request.getParameter("productVendor");
		String productDescription = request.getParameter("productDescription");
		String quantityInStock = request.getParameter("quantityInStock");
		String buyPrice = request.getParameter("buyPrice");
		String msrp = request.getParameter("msrp");

		PrintWriter out = response.getWriter();
		String[] s = { productCode, productName, productLine, productScale, productVendor, productDescription,
				quantityInStock, buyPrice, msrp };

		if (create != null && create.equals("Create")) {
			try {
				productSessionBean.createProduct(s);
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Successfully create product\")");
				out.println("window.location.assign(\"EmployeeProductServlet?currentPage=1\")");
				out.println("</SCRIPT>");
			} catch (Exception e) {
				// TODO: handle exception
				Throwable rootCause = e;
				while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
					rootCause = rootCause.getCause();
				}
				String errorMessageString = (String) rootCause.getMessage();
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet servlet1</title>");

				out.println("</head>");
				out.println("<body>");
				out.println(errorMessageString);
				out.println("<button onclick=\"history.back()\">Back</button>");
				out.println("</body>");
				out.println("</html>");
			}

		} else if (delete != null && delete.equals("Delete")) {
			try {
				productSessionBean.deleteProduct(productCode);
				out.println("Successfully delete");
			} catch (Exception e) {
				// TODO: handle exception
				out.println("Failed");
			}

		} else {

			try {
				productSessionBean.updateProduct(s);
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Record has been updated\")");
				out.println(
						"window.location.assign(\"EmployeeProductDetailsServlet?productCode=" + productCode + "\")");
				out.println("</SCRIPT>");

			} catch (Exception e) {
				// TODO: handle exception
				out.println("<SCRIPT type=\"text/javascript\">");
				out.println("alert(\"Update failed\")");
				out.println(
						"window.location.assign(\"EmployeeProductDetailsServlet?productCode=" + productCode + "\")");
				out.println("</SCRIPT>");
			}
		}

	}

}
