package sessionbean;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;

import domain.Product;

@Local
public interface ProductSessionBeanLocal {
	
	public int getNumberOfRows();
	public List<Product> readProductAccordingToPage(int currentPage) throws EJBException;
	public Product findProduct(String productCode);
	public void updateProduct(String[] s);
	public void deleteProduct(String productCode);
	public void createProduct(String[] s);
	public List<String> getAllProductLine();

}
