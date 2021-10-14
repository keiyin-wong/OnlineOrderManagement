package utilities;
import java.util.Comparator;

import domain.Orderdetail;

public class OrderDetailSortByLineNumber implements Comparator<Orderdetail> {

	@Override
	public int compare(Orderdetail o1, Orderdetail o2) {
		// TODO Auto-generated method stub
		return o1.getOrderlinenumber() - o2.getOrderlinenumber();
		
	}

}
