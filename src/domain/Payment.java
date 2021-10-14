package domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the payments database table.
 * 
 */
@Entity
@Table(name="payments", schema = "classicmodels" )
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
@NamedQuery(name = "Payment.findNumber", query = "select p from Payment p where p.id.checknumber = :checknumber")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PaymentPK id;

	@Column(name="amount")
	private BigDecimal amount;

	@Column(name="paymentdate")
	private String paymentdate;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customernumber", insertable = false, updatable = false)
	private Customer customer;

	public Payment() {
	}

	public PaymentPK getId() {
		return this.id;
	}

	public void setId(PaymentPK id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentdate() {
		return this.paymentdate;
	}

	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}