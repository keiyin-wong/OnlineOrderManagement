package sessionbean;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.flow.builder.ReturnBuilder;
import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import domain.User;
import domain.UserRole;
import jdk.jshell.spi.ExecutionControl.UserException;

/**
 * Session Bean implementation class LoginSessionBean
 */
@Stateless
@LocalBean
public class LoginSessionBean implements LoginSessionBeanLocal {
	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;
    /**
     * Default constructor. 
     */
    public LoginSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    public List<User> getAllUsers() throws EJBException{ 
    	return em.createNamedQuery("User.findAll").getResultList();
    }
    
    public User userLogin(String name, String password, HttpServletRequest request) throws EJBException{
    	javax.persistence.Query q = null;
    	q = em.createNativeQuery("select * from classicmodels.users WHERE (username = ?) and (password = ?)", User.class);
    	q.setParameter(1, name);
    	q.setParameter(2, password);

    	User user = null;
    	
    	try {
    		user = (User) q.getSingleResult();
    		String userName = user.getUsername();
    		Query query_1 = em.createQuery("select u from UserRole u where (u.id.username = :username) and (u.id.role in (:selectedValues))", UserRole.class);    		
    		List<String> selectedValues = Arrays.asList("admin", "manager-gui","manager-script", "tomee-admin", "staff");
    		query_1.setParameter("username", userName);
    		query_1.setParameter("selectedValues", selectedValues);
    		List<UserRole> resultList = (List<UserRole>) query_1.getResultList();
    		
    		if(resultList.size() == 0) {
    			return null;
    		}else {
    			List<String> adminList = Arrays.asList("admin", "tomee-admin");
    			String role = resultList.get(0).getId().getRole();
    			if(adminList.contains(role)) {
    				request.getSession().setAttribute("admin", role);
    			}
    			return user;
    		}
    			 
    	}catch (NoResultException e) {
			// TODO: handle exception
    		return null;
		}
    	
    
    }
    

    public User customerLogin(String name, String password) throws EJBException{
    	javax.persistence.Query q = null;
    	q = em.createNativeQuery("select * from classicmodels.users WHERE (username = ?) and (password = ?)", User.class);
    	q.setParameter(1, name);
    	q.setParameter(2, password);

    	User user = null;
    	
    	try {
    		user = (User) q.getSingleResult();
    		String userName = user.getUsername();
    		Query query_1 = em.createQuery("select u from UserRole u where (u.id.username = :username) and (u.id.role in (:selectedValues))", UserRole.class);    		
    		List<String> selectedValues = Arrays.asList("user");
    		//String [] selectedValues = {"admin", "manager-gui","manager-script", "tomee-admin", "staff"};
    		query_1.setParameter("username", userName);
    		//query_1.setParameter(2, "staff");
    		
    		query_1.setParameter("selectedValues", selectedValues);
    		
    		if(query_1.getResultList().size() == 0) {
    			return null;
    		}else
    			return user; 
    		
    		
    	}catch (NoResultException e) {
			// TODO: handle exception
    		return null;
		}
    	
    
    }
    

}
