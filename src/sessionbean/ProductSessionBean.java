package sessionbean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Product;
import domain.Productline;

/**
 * Session Bean implementation class ProductSessionBean
 */
@Stateless
@LocalBean
public class ProductSessionBean implements ProductSessionBeanLocal {
	@PersistenceContext(unitName = "OnlineOrderManagementSystem")
	EntityManager em;
    /**
     * Default constructor. 
     */
    public ProductSessionBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public int getNumberOfRows() {
		// TODO Auto-generated method stub
		Query q = null;
		q = em.createNativeQuery("SELECT COUNT(*) AS totalrow FROM classicmodels.products");
		BigInteger results = (BigInteger) q.getSingleResult();
		int i = results.intValue();
		return i;
	}

	@Override
	public List<Product> readProductAccordingToPage(int currentPage) throws EJBException {
		// TODO Auto-generated method stub
		List<Product> a = em.createNamedQuery("Product.findAll").getResultList();
		int start = currentPage * 10 - 10;
		List result = a.subList(Math.min(a.size(), start), Math.min(a.size(), start + 10));
		return result;
	}

	@Override
	public Product findProduct(String productCode) {
		// TODO Auto-generated method stub
		
		try {
			Query query = em.createNamedQuery("Product.findbyId");
			query.setParameter("productcode", productCode);
			return (Product) query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	@Override
	public void updateProduct(String[] s) {
		Query query = em.createQuery("update Product e set "
				+ "e.productname = :productname, "
				+ "e.productlineBean.productline = :productline, "
				+ "e.productscale = :productscale, "
				+ "e.productvendor = :productvendor, "
				+ "e.productdescription = :productdescription, "
				+ "e.quantityinstock = :quantityinstock, "
				+ "e.buyprice = :buyprice, "
				+ "e.msrp = :msrp "
				+ "where e.productcode =: productcode");
		query.setParameter("productname", s[1]);
		query.setParameter("productline", s[2]);
		query.setParameter("productscale", s[3]);
		query.setParameter("productvendor", s[4]);
		query.setParameter("productdescription", s[5]);
		query.setParameter("quantityinstock", Integer.valueOf(s[6]));
		query.setParameter("buyprice", new BigDecimal(s[7]));
		query.setParameter("msrp", new BigDecimal(s[8]));
		query.setParameter("productcode", s[0]);

		
		query.executeUpdate();
		
		
	}
	@Override
	public void deleteProduct(String productCode) {
		Query query = em.createQuery("Delete from Product p where p.productcode = :productcode");
		query.setParameter("productcode", productCode);
		query.executeUpdate();
	}
	
	@Override
	public void createProduct(String[] s) throws EJBException{
		/*
		 * Query query = em.createNamedQuery("Productline.findbyId");
		 * query.setParameter("productline", s[2]);
		 */
		Productline productline = em.find(Productline.class, s[2]);
		
		Product product = new Product();
		product.setProductcode(s[0]);
		product.setProductname(s[1]);
		product.setProductlineBean(productline);
		product.setProductscale(s[3]);
		product.setProductvendor(s[4]);
		product.setProductdescription(s[5]);
		product.setQuantityinstock(Integer.valueOf(s[6]));
		product.setBuyprice(new BigDecimal(s[7]));
		product.setMsrp(new BigDecimal(s[8]));
		
		em.persist(product);
		
	}
	
	@Override
	public List<String> getAllProductLine() {
		Query query = em.createQuery("select distinct p.productline from Productline p");
		List<String> resultList = query.getResultList();
		return resultList;
	}



}
