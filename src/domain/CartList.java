package domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CartList {

	private List<Cart> cartList;
	private Date requiredDate;
	private Integer customernumber;
	
	public CartList() {
		
	}
	
	public List<Cart> addCartList(Cart cart){
		this.cartList.add(cart);
		return cartList;
	}
	
	public List<Cart> getCartList() {
		return cartList;
	}
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public Integer getCustomernumber() {
		return customernumber;
	}
	public void setCustomernumber(Integer customernumber) {
		this.customernumber = customernumber;
	}
	
	public int size() {
		return cartList.size();
	}
	
	public String getProductCode(int i) {
		return cartList.get(i).getProductcode();
	}
	
	public BigDecimal total() {
		BigDecimal total=BigDecimal.ZERO;
		for(Cart cart: cartList) {
			total=total.add(cart.itemTotal());
		}
		
		return total;
	}

}
