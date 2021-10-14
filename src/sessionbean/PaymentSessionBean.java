package sessionbean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Customer;
import domain.Payment;
import domain.PaymentPK;
import domain.Product;

/**
 * Session Bean implementation class PaymentSessionBean
 */
@Stateless
@LocalBean
public class PaymentSessionBean implements PaymentSessionBeanLocal {

	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;
	
	@EJB
	private CustomerSessionBeanLocal cusbean;
	
    @Override
	public List<Payment> getAllPayments() throws EJBException {
		// TODO Auto-generated method stub
		return em.createNamedQuery("Payment.findAll").getResultList();
	}

	@Override
	public Payment findPayment(String customernumber) throws EJBException {
		
		Query q = em.createNamedQuery("Payment.findCustomer");
		q.setParameter("customernumber", Long.valueOf(customernumber));
		return (Payment) q.getSingleResult();
	}

	@Override
	public List<Payment> readPayment(int currentPage) throws EJBException {
		List<Payment> a = em.createNamedQuery("Payment.findAll").getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}

	@Override
	public int getNumberOfRows() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.payments");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	@Override
	public void deletePayment(String customernumber, String checknumber) throws EJBException {

		Payment p = findPaymentNumber(checknumber);
		em.remove(p);
	}

	@Override
	public void addPayment(String customernumber, String checknumber, double amount) throws EJBException {
		
		String paymentdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		Payment e = new Payment();
		PaymentPK ee = new PaymentPK();
		int customernumberint = Integer.parseInt(customernumber);
		Customer cus = cusbean.findCustomer(customernumberint);		
		String message = "";
		
		ee.setCustomernumber(customernumberint);
		ee.setChecknumber(checknumber);
		if(checkPaymentNumber(checknumber)) {
			e.setId(ee);
			e.setCustomer(cus);
			BigDecimal realamount = BigDecimal.valueOf(amount);
			e.setAmount(realamount);
			e.setPaymentdate(paymentdate);
			em.persist(e);
		} 
	}
	
	public boolean checkPaymentNumber(String checknumber) {
		Query q = em.createNamedQuery("Payment.findAll");
		List<Payment> payment = (List<Payment>) q.getResultList();
		if(payment.size() != 0) {
			for (Payment t: payment) {
				if(t.getId().getChecknumber() == checknumber) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Payment getPayment(String checknumber) {
		Query q = em.createNamedQuery("Payment.findAll");
		List<Payment> payment = (List<Payment>) q.getResultList();
		if(payment.size() != 0) {
			for (Payment t: payment) {
				if(t.getId().getChecknumber() == checknumber) {
					return t;
				}
			}
		}
		return null;
	}

	/**
     * Default constructor. 
     */
    public PaymentSessionBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
	public Payment findPaymentNumber(String checknumber) throws EJBException {
		Query q = em.createNamedQuery("Payment.findNumber");
		q.setParameter("checknumber", checknumber);
		return (Payment) q.getSingleResult();
	}
    
    public Payment searchPaymentByCustNumDate(String customerNum, String checknumber) {

		List<Payment> h = em.createNamedQuery("Payment.findAll").getResultList();
		// System.out.println(d);
		PaymentPK prk = null;
		for (Payment p : h) {
			PaymentPK pk = p.getId();
			if (pk.getCustomernumber().equals(customerNum) && pk.getChecknumber().equals(checknumber)) {
				System.out.println(pk.getChecknumber());
				prk = pk;
				break;
			}
		}

		if (prk != null) {
			Payment p1 = em.find(Payment.class, prk);
			return p1;
		} else {
			return null;
		}

	}

	public void updatePayment(String customernumber, String checknumber, String paymentdate, BigDecimal amount) {
		Payment p = findPaymentNumber(checknumber);
		if (p != null) {
			p.getId().setChecknumber(checknumber);
			p.setPaymentdate(paymentdate);
			p.setAmount(amount);
			em.merge(p);
		}

	}
}
