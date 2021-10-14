package domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Yap
 *
 */
public class Cart {

	private String productcode;
	private String productname;
	private int quantity;
	private BigDecimal priceeach;
	
	public Cart() {}
	public Cart(String productcode, int quantity, BigDecimal priceEach,String productname) {
		this.productcode = productcode;
		this.quantity = quantity;
		this.priceeach = priceEach;
		this.productname=productname;
	}
	
	public Cart(String productcode, BigDecimal priceEach,String productname)
	{
		this.productcode=productcode;
		this.quantity=1;
		this.priceeach=priceEach;
		this.productname=productname;
	}
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPriceEach() {
		return priceeach;
	}
	public void setPriceEach(BigDecimal priceEach) {
		this.priceeach = priceEach;
	}
	
	public BigDecimal itemTotal() {
		return getPriceEach().multiply(new BigDecimal (getQuantity()));
	}
	
}
