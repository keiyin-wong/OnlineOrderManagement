package sessionbean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import domain.Employee;
import domain.Office;

/**
 * Session Bean implementation class EmployeeSessionBean
 */
@Stateless
@LocalBean
public class EmployeeSessionBean implements EmployeeSessionBeanLocal {
	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;

	/**
	 * Default constructor.
	 */
	public EmployeeSessionBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getNumberOfRows() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.employees");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}
	
	@Override
	public Employee findEmployee (String empNumber)throws EJBException  {
		// TODO Auto-generated method stub
		try {
			Query query = em.createNamedQuery("Employee.findbyId");
			query.setParameter("employeenumber", Integer.valueOf(empNumber));
			Employee emp = (Employee)query.getSingleResult();
			return emp;
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		} 
	}
	
	@Override
	public List<Employee> readEmployeeAccordingToPage(int currentPage) throws EJBException {
		// TODO Auto-generated method stub
		List<Employee> a = em.createNamedQuery("Employee.findAll").getResultList();
		int start = currentPage * 10 - 10;
		List<Employee> result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}

	@Override
	public Employee getEmployeeByUserName(String userFirstName)throws EJBException {
		Query query = em.createNativeQuery("select * from classicmodels.employees where (firstname = ?)",
				Employee.class);
		query.setParameter(1, userFirstName);
		List<Employee> employeeList = query.getResultList();
		if (employeeList.size() == 0) {
			return null;
		} else {
			return employeeList.get(0);
		}
	}

	@Override
	public void updateEmployee(String[] s, HttpServletRequest request)throws EJBException {
		Query query = em.createQuery("update Employee e set e.firstname= :firstname, " + "e.lastname = :lastname, "
				+ "e.email = :email, " + "e.extension = :extension, " + "e.office.officecode = :officecode, "
				+ "e.reportsto = :reportsto, " + "e.jobtitle = :jobtitle "
				+ "where e.employeenumber =: employeenumber");
		query.setParameter("firstname", s[1]);
		query.setParameter("lastname", s[2]);
		query.setParameter("email", s[3]);
		query.setParameter("extension", s[4]);
		query.setParameter("officecode", Integer.valueOf(s[5]));
		query.setParameter("reportsto", s[6]);
		query.setParameter("jobtitle", s[7]);
		query.setParameter("employeenumber", Integer.valueOf(s[0]));

		query.executeUpdate();

		HttpSession session = request.getSession();
		session.setAttribute("employee", getEmployeeByUserName(s[1]));

	}
	
	@Override
	public void updateEmployee(String[] s)throws EJBException {
		Query query = em.createQuery("update Employee e set e.firstname= :firstname, " + "e.lastname = :lastname, "
				+ "e.email = :email, " + "e.extension = :extension, " + "e.office.officecode = :officecode, "
				+ "e.reportsto = :reportsto, " + "e.jobtitle = :jobtitle "
				+ "where e.employeenumber =: employeenumber");
		query.setParameter("firstname", s[1]);
		query.setParameter("lastname", s[2]);
		query.setParameter("email", s[3]);
		query.setParameter("extension", s[4]);
		query.setParameter("officecode", Integer.valueOf(s[5]));
		query.setParameter("reportsto", s[6]);
		query.setParameter("jobtitle", s[7]);
		query.setParameter("employeenumber", Integer.valueOf(s[0]));

		query.executeUpdate();

	}
	
	@Override
	public void createEmployee(String[] s) throws EJBException{
		/*
		 * Query query = em.createNamedQuery("Productline.findbyId");
		 * query.setParameter("productline", s[2]);
		 */
		Office office = em.find(Office.class, Integer.valueOf(s[5]));
		
		Employee employee = new Employee();
		employee.setEmployeenumber(Integer.valueOf(s[0]));
		employee.setFirstname(s[1]);
		employee.setLastname(s[2]);
		employee.setEmail(s[3]);
		employee.setExtension(s[4]);
		employee.setOffice(office);
		employee.setReportsto(s[6]);
		employee.setJobtitle(s[7]);
		
		em.persist(employee);
		
	}
	
	@Override
	public void deleteEmployee(String empNumber) throws EJBException{
		Query query = em.createQuery("Delete from Employee e where e.employeenumber = :employeenumber");
		query.setParameter("employeenumber", Integer.valueOf(empNumber));
		query.executeUpdate();
	}
	
	@Override
	public List<String> getAllOfficeCode() throws EJBException{
		Query query = em.createQuery("select distinct o.officecode from Office o order by o.officecode asc");
		List<Integer> oldList = query.getResultList();
		List<String> resultList = new ArrayList<>(oldList.size());
		for (Integer myInt : oldList) { 
			  resultList.add(String.valueOf(myInt)); 
			}
		return resultList;
	}

}
