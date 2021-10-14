package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;

import domain.Employee;

@Local
public interface EmployeeSessionBeanLocal {

	public int getNumberOfRows() throws EJBException;
	public Employee findEmployee(String empNumber) throws EJBException;
	public List<Employee> readEmployeeAccordingToPage(int currentPage) throws EJBException;
	public Employee getEmployeeByUserName(String userFirstName) throws EJBException;
	public void updateEmployee(String[] s, HttpServletRequest request) throws EJBException;
	public void updateEmployee(String[] s) throws EJBException;
	public void createEmployee(String[] s) throws EJBException;
	public void deleteEmployee(String empNumber) throws EJBException;
	public List<String> getAllOfficeCode() throws EJBException;
}
