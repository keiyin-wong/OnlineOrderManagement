package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Payment;
import sessionbean.PaymentSessionBeanLocal;
import utilities.ValidateManageLogic;


/**
 * Servlet implementation class PaymentController
 */
@WebServlet("/PaymentController")
public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PaymentSessionBeanLocal paybean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		try {
			Payment payment = paybean.findPaymentNumber(id);
			request.setAttribute("PAY", payment);
			RequestDispatcher req = request.getRequestDispatcher("PaymentUpdate.jsp");
			req.forward(request, response);
		} catch (EJBException ex) {
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String customernumber = request.getParameter("customernumber");
		String checknumber = request.getParameter("checknumber");
		String paymentdate = request.getParameter("paymentdate");
		String amount = request.getParameter("amount");
		BigDecimal amount1 = new BigDecimal(amount);
		PrintWriter out = response.getWriter();
		// this line is to package the whole values into one array string variable -
		// easier just pass one parameter object
				//String[] s = { eid, fname, lname, gender, dob, hiredate };

				try {
					if (ValidateManageLogic.validateManager(request).equals("UPDATE")) {
		// call session bean updateEmployee method
						//empbean.updateEmployee(s);
						paybean.updatePayment(customernumber, checknumber, paymentdate, amount1);
						ValidateManageLogic.UpdateSuccess(out);
					} else if (ValidateManageLogic.validateManager(request).equals("DELETE")) {
		// call session bean deleteEmployee method
						paybean.deletePayment(customernumber, checknumber);
						ValidateManageLogic.DeleteSuccess(out);
		// if ADD button is clicked
					} else {
		// call session bean addEmployee method
						//empbean.addEmployee(s);
					}
		// this line is to redirect to notify record has been updated and redirect to
		// another page
				} catch (EJBException ex) {
				}
	}

}
