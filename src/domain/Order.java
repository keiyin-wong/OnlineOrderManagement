package domain;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.*;

import jdk.jfr.Name;

import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders", schema = "classicmodels" )
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o order by o.ordernumber asc")
@NamedQuery(name="Order.findAllStatus", query="SELECT o FROM Order o where o.status = :status order by o.ordernumber asc")
@NamedQuery(name="Order.findbyId", query = "select o from Order o where o.ordernumber = :ordernumber")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer ordernumber;

	@Column(name="comments")
	private String comments;

	@Column(name="customernumber")
	private Integer customernumber;

	@Column(name="orderdate")
	private String orderdate;

	@Column(name="requireddate")
	private String requireddate;

	@Column(name="shippeddate")
	private String shippeddate;

	@Column(name="status")
	private String status;

	//bi-directional many-to-one association to Orderdetail
	@OneToMany(mappedBy="order", fetch = FetchType.EAGER)
	private List<Orderdetail> orderdetails;

	public Order() {
	}

	public Order(Integer ordernumber, Integer customernumber, String orderdate, String requireddate, String status) {
		super();
		this.ordernumber = ordernumber;
		this.customernumber = customernumber;
		this.orderdate = orderdate;
		this.requireddate = requireddate;
		this.status = status;
	}

	public Integer getOrdernumber() {
		return this.ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getCustomernumber() {
		return this.customernumber;
	}

	public void setCustomernumber(Integer customernumber) {
		this.customernumber = customernumber;
	}

	public String getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getRequireddate() {
		return this.requireddate;
	}

	public void setRequireddate(String requireddate) {
		this.requireddate = requireddate;
	}

	public String getShippeddate() {
		return this.shippeddate;
	}

	public void setShippeddate(String shippeddate) {
		this.shippeddate = shippeddate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Orderdetail> getOrderdetails() {
		return this.orderdetails;
	}

	public void setOrderdetails(List<Orderdetail> orderdetails) {
		this.orderdetails = orderdetails;
	}

	public Orderdetail addOrderdetail(Orderdetail orderdetail) {
		getOrderdetails().add(orderdetail);
		orderdetail.setOrder(this);

		return orderdetail;
	}

	public Orderdetail removeOrderdetail(Orderdetail orderdetail) {
		getOrderdetails().remove(orderdetail);
		orderdetail.setOrder(null);

		return orderdetail;
	}

}