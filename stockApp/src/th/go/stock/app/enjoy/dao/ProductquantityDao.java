
package th.go.stock.app.enjoy.dao;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Productquantity;
import th.go.stock.app.enjoy.model.ProductquantityPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ProductquantityDao extends DaoControl{
	
	public ProductquantityDao(){
		setLogger(EnjoyLogger.getLogger(ProductquantityDao.class));
		super.init();
	}
	
	public void insertProductquantity(ProductquantityBean 		productquantityBean) throws EnjoyException{
		getLogger().info("[insertProductquantity][Begin]");
		
		ProductquantityPK 	id					= null;
		Productquantity 	productquantity 	= null;
		
		try{
			
			productquantity = new Productquantity();
			id				= new ProductquantityPK();
			
			id.setProductCode(EnjoyUtils.parseInt(productquantityBean.getProductCode()));
			id.setTin(productquantityBean.getTin());
			
			productquantity.setId(id);
			productquantity.setQuantity(EnjoyUtils.parseBigDecimal(productquantityBean.getQuantity()));
			
			insertData(productquantity);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertProductquantity");
		}finally{
			
//			product = null;
			getLogger().info("[insertProductquantity][End]");
		}
	}
	
	public void updateProductquantity(ProductquantityBean productquantityBean) throws EnjoyException{
		getLogger().info("[updateProductquantity][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Productquantity set quantity = :quantity"
										+ " where id.productCode = :productCode"
										+ "		and id.tin		 = :tin";
			
			query = createQuery(hql);
			query.setParameter("quantity"			, EnjoyUtils.parseBigDecimal(productquantityBean.getQuantity()));
			query.setParameter("productCode"		, EnjoyUtils.parseInt(productquantityBean.getProductCode()));
			query.setParameter("tin"				, productquantityBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error updateProductquantity");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateProductquantity][End]");
		}
	}
	
	public String getProductquantity(ProductquantityBean 	productquantityBean) throws EnjoyException{
		getLogger().info("[getProductquantity][Begin]");
		
		String						hql						= null;
		String						quantity				= null;
		HashMap<String, Object>		param					= new HashMap<String, Object>();
		List<Object>				resultList				= null;
		
		try{	
			hql					= "select quantity "
								+ "	from productquantity"
								+ "	where productCode 	= :productCode"
								+ " 	and tin 		= :tin";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(productquantityBean.getProductCode()));
			param.put("tin"			, productquantityBean.getTin());

			resultList = getResult(hql, param, "quantity", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				quantity = EnjoyUtils.convertFloatToDisplay(resultList.get(0), 2);
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductquantity");
		}finally{
			hql				= null;
			getLogger().info("[getProductquantity][End]");
		}
		
		return quantity;
		
	}
}
