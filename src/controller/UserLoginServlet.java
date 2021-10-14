package controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import com.sun.source.tree.RequiresTree;

import domain.User;
import sessionbean.EmployeeSessionBean;
import sessionbean.LoginSessionBean;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private LoginSessionBean loginSessionBean;
	
	@EJB
	private EmployeeSessionBean employeeSessionBean;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String destPage = "login.jsp";
		
		try {
			User user = loginSessionBean.userLogin(userName, password, request);
			if(user!= null) {
				session.setAttribute("user", user);
				session.setAttribute("employee", employeeSessionBean.getEmployeeByUserName(user.getUsername()));
				destPage = "/EmployeeOrderServlet";
			}else {
				String message = "Invalid email/password";
                request.setAttribute("message", message);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);
            
		}catch (EJBException e) {
			// TODO: handle exception
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
