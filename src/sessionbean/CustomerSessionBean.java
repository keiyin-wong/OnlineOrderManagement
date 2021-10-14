package sessionbean;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;

import domain.Customer;;

/**
 * Session Bean implementation class CustomerSessionBean
 */
@Stateless
@LocalBean
public class CustomerSessionBean implements CustomerSessionBeanLocal {
	
	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;
	
	public Customer findCustomer(int customernumber) throws EJBException{
		Query q = em.createNamedQuery("Customer.findbyId");
		q.setParameter("customernumber", customernumber);
		return (Customer) q.getSingleResult();
		
	}

    /**
     * Default constructor. 
     */
    public CustomerSessionBean() {
        // TODO Auto-generated constructor stub
    }

}
