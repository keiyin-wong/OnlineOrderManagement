package sessionbean;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import domain.CartList;
import domain.Order;
import domain.Orderdetail;

@Local
public interface OrderSessionBeanLocal {
	public int getNumberOfRows_order() throws EJBException;
	public List<Order> readOrderAccordingToPage(int currentPage) throws EJBException;
	public int getNumberOfRows_orderShipped() throws EJBException;
	public List<Order> readOrderAccordingToPageShipped(int currentPage) throws EJBException;
	public int getNumberOfRows_orderInProcess() throws EJBException; 
	public List<Order> readOrderAccordingToPageInProcess(int currentPage) throws EJBException;
	public int getNumberOfRows_orderOnHold() throws EJBException;
	public List<Order> readOrderAccordingToPageOnHold(int currentPage) throws EJBException;
	public Order findOrder(String orderNumber) throws EJBException;
	public List<Orderdetail> findOrderDetails(Order order) throws EJBException;
	public BigDecimal getCreditLimit(String orderNumber) throws EJBException;
	public BigDecimal getTotal(Order order) throws EJBException;
	public void updateOrder(String[] s) throws EJBException;
	public boolean createOrder(CartList cartlist) throws EJBException;

}
