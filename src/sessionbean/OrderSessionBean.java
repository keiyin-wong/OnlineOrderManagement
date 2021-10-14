package sessionbean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Cart;
import domain.CartList;
import domain.Customer;
import domain.Order;
import domain.Orderdetail;
import domain.OrderdetailPK;
import domain.Payment;
import domain.Product;
import domain.Productline;
import domain.UserRole;
import utilities.OrderDetailSortByLineNumber;

/**
 * Session Bean implementation class OrderSessionBean
 */
@Stateless
@LocalBean
public class OrderSessionBean implements OrderSessionBeanLocal {
	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;
    /**
     * Default constructor. 
     */
    public OrderSessionBean() { 
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public int getNumberOfRows_order() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.orders");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}
    
    @Override
	public List<Order> readOrderAccordingToPage(int currentPage) throws EJBException {
		// Query q;
		List<Order> a = em.createNamedQuery("Order.findAll").getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}
    
    @Override
	public int getNumberOfRows_orderShipped() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.orders where status = ?");
		q.setParameter(1, "Shipped");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}
    
    @Override
	public List<Order> readOrderAccordingToPageShipped(int currentPage) throws EJBException {
		Query query = em.createNamedQuery("Order.findAllStatus");
		query.setParameter("status", "Shipped");
		List<Order> a = query.getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}
    
    @Override
	public int getNumberOfRows_orderInProcess() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.orders where status = ?");
		q.setParameter(1, "In Process");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}
	
    @Override
	public List<Order> readOrderAccordingToPageInProcess(int currentPage) throws EJBException {
		Query query = em.createNamedQuery("Order.findAllStatus");
		query.setParameter("status", "In Process");
		List<Order> a = query.getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}
    
    @Override
	public int getNumberOfRows_orderOnHold() throws EJBException {
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.orders where status = ?");
		q.setParameter(1, "On Hold");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}
	
    @Override
	public List<Order> readOrderAccordingToPageOnHold(int currentPage) throws EJBException {
		Query query = em.createNamedQuery("Order.findAllStatus");
		query.setParameter("status", "On Hold");
		List<Order> a = query.getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}
    
    @Override
    public Order findOrder(String orderNumber) throws EJBException{
		try{
			Query query = em.createNamedQuery("Order.findbyId");
		query.setParameter("ordernumber", Integer.valueOf(orderNumber));
		return (Order) query.getSingleResult();
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

    @Override
	public List<Orderdetail> findOrderDetails(Order order) throws EJBException{
		List<Orderdetail> orderDetailsList = order.getOrderdetails();
		Collections.sort(orderDetailsList, new OrderDetailSortByLineNumber());
		return orderDetailsList;

	}

    @Override
	public BigDecimal getCreditLimit(String orderNumber) throws EJBException{ //Customer left credit limit
		Query query = em.createNamedQuery("Order.findbyId");
		query.setParameter("ordernumber", Integer.valueOf(orderNumber));
		Order order = (Order) query.getSingleResult();

		Query query1 = em.createNativeQuery("select * from classicmodels.orders where (customernumber = ?) and not shippeddate = ?",Order.class);
		query1.setParameter(1, order.getCustomernumber());
		query1.setParameter(2, "");
		List<Order> customerOrderList = query1.getResultList();
		BigDecimal creditLimitTotal = new BigDecimal("0");
		if (customerOrderList.size() != 0) {
			for(Order o : customerOrderList) {
				creditLimitTotal  = creditLimitTotal.add(getTotal(o));
			}
		}
		
		Query query2 = em.createNativeQuery("select * from classicmodels.payments where (customernumber = ?)",Payment.class);
		query2.setParameter(1, order.getCustomernumber());
		List<Payment> paymentList = query2.getResultList();
		BigDecimal paymentTotal = new BigDecimal("0");
		if(paymentList.size() != 0) {
			for(Payment p : paymentList) {
				paymentTotal = paymentTotal.add(p.getAmount());
			}
		}
		creditLimitTotal = creditLimitTotal.subtract(paymentTotal);
		
		Query query3 = em.createNamedQuery("Customer.findbyId");
		query3.setParameter("customernumber", order.getCustomernumber());
		Customer customer = (Customer)query3.getSingleResult();
		
		return customer.getCreditlimit().subtract(creditLimitTotal);
		
		
		// The formula is [creditLimitofCustomer - (allOrderNeedToPay - payment)]
	}

    @Override
	public BigDecimal getTotal(Order order) throws EJBException{
		BigDecimal total = new BigDecimal("0");
		List<Orderdetail> orderdetailList = order.getOrderdetails();
		if (orderdetailList.size() != 0) {
			for (Orderdetail t : orderdetailList) {
				BigDecimal sum = t.getPriceeach().multiply(new BigDecimal(t.getQuantityordered()));
				total = total.add(sum);
			}
			return total;
		} else
			return null;

	}
    
    @Override
	public void updateOrder(String[] s) throws EJBException{
		Query query = em.createQuery("update Order e set "
				+ "e.orderdate = :orderdate, "
				+ "e.requireddate = :requireddate, "
				+ "e.shippeddate = :shippeddate, "
				+ "e.status = :status, "
				+ "e.comments = :comments, "
				+ "e.customernumber = :customernumber "
				+ "where e.ordernumber =: ordernumber");
		query.setParameter("orderdate", s[1]);
		query.setParameter("requireddate", s[2]);
		query.setParameter("shippeddate", s[3]);
		query.setParameter("status", s[4]);
		query.setParameter("comments", s[5]);
		query.setParameter("customernumber", Integer.valueOf(s[6]));
		query.setParameter("ordernumber", Integer.valueOf(s[0]));
		
		query.executeUpdate();
		
		
	}
    
    @Override
	public boolean createOrder(CartList cartlist) throws EJBException{

    	Query query = em.createNamedQuery("Customer.findbyId");
		query.setParameter("customernumber", cartlist.getCustomernumber());
		List<Customer> customerlist = (List<Customer>) query.getResultList();
		BigDecimal total=BigDecimal.ZERO;
		
		if(customerlist.size() == 0) {
			return false;
		}else {
			Customer customer=customerlist.get(0);
			if (cartlist.size() != 0) {
				for (Cart t : cartlist.getCartList()) {
					total=total.add(t.itemTotal());
				}
			}
			if(total.compareTo(customer.getCreditlimit())!=1)
			{
				Query query1 = em.createNativeQuery("select * from classicmodels.orders Order by ordernumber DESC ;",Order.class);
				List<Order> Orderlist = query1.getResultList();
				Integer ordernumber = null;
				if (Orderlist.size() != 0) {
					ordernumber=Orderlist.get(0).getOrdernumber()+1;
				}
				Date date=new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
				String orderdate= formatter.format(date);
				String requireddate=formatter.format(cartlist.getRequiredDate());
				
				
		    	Order order= new Order();
				order.setOrdernumber(ordernumber);
				order.setOrderdate(orderdate);
				order.setRequireddate(requireddate);
				order.setStatus("In Process");
				order.setComments("");
				order.setShippeddate("");
				order.setCustomernumber(cartlist.getCustomernumber());
				
				em.persist(order);
				
				Orderdetail orderdetail=new Orderdetail();
				OrderdetailPK orderdetailPK = new OrderdetailPK();
				Integer orderlinenumber=1;
				for(Cart c:cartlist.getCartList())
				{
					orderdetailPK.setOrdernumber(ordernumber);
					orderdetailPK.setProductcode(c.getProductcode());
					orderdetail.setId(orderdetailPK);
					orderdetail.setOrderlinenumber(orderlinenumber);
					orderdetail.setPriceeach(c.getPriceEach());
					orderdetail.setQuantityordered(c.getQuantity());
					em.persist(orderdetail);
				}
				return true;
			}
			else {
				return false;
			}
		}
    }
}
