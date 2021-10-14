package sessionbean;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import domain.Customer;
import domain.Payment;
import domain.PaymentPK;;

@Local
public interface PaymentSessionBeanLocal {
	public List<Payment> getAllPayments() throws EJBException;
	public Payment findPayment(String customernumber) throws EJBException;
	public List<Payment> readPayment(int currentPage) throws EJBException;
	public int getNumberOfRows() throws EJBException ;
	public void deletePayment(String customernumber, String checknumeber) throws EJBException;
	public void addPayment(String customernumber, String checknumber, double amount) throws EJBException;
	public boolean checkPaymentNumber(String checknumber);
	public Payment getPayment(String checknumber);
	public Payment findPaymentNumber(String checknumber) throws EJBException;
	public Payment searchPaymentByCustNumDate(String customerNum, String checknumber);
	public void updatePayment(String customernumber, String checknumber, String paymentdate, BigDecimal amount);
}
