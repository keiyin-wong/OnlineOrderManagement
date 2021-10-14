package sessionbean;

import javax.ejb.EJBException;
import javax.ejb.Local;

import domain.Customer;

@Local
public interface CustomerSessionBeanLocal {
	public Customer findCustomer(int customernumber) throws EJBException;
}
