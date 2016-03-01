
package th.go.stock.app.enjoy.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Productquantity;
import th.go.stock.app.enjoy.model.ProductquantityPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ProductquantityDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductquantityDao.class);
	
	public void insertProductquantity(Session session, ProductquantityBean 		productquantityBean) throws EnjoyException{
		logger.info("[insertProductquantity][Begin]");
		
		ProductquantityPK 	id					= null;
		Productquantity 	productquantity 	= null;
		
		try{
			
			productquantity = new Productquantity();
			id				= new ProductquantityPK();
			
			id.setProductCode(productquantityBean.getProductCode());
			id.setTin(productquantityBean.getTin());
			
			productquantity.setId(id);
			productquantity.setQuantity(EnjoyUtils.parseBigDecimal(productquantityBean.getQuantity()));
			
			session.saveOrUpdate(productquantity);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductquantity");
		}finally{
			
//			product = null;
			logger.info("[insertProductquantity][End]");
		}
	}
	
	public void updateProductquantity(Session session, ProductquantityBean 		productquantityBean) throws EnjoyException{
		logger.info("[updateProductquantity][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Productquantity set quantity = :quantity"
										+ " where id.productCode = :productCode"
										+ "		and id.tin		 = :tin";
			
			query = session.createQuery(hql);
			query.setParameter("quantity"			, EnjoyUtils.parseBigDecimal(productquantityBean.getQuantity()));
			query.setParameter("productCode"		, productquantityBean.getProductCode());
			query.setParameter("tin"				, productquantityBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateProductquantity");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateProductquantity][End]");
		}
	}
	
	public String getProductquantity(ProductquantityBean 	productquantityBean) throws EnjoyException{
		logger.info("[getProductquantity][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<String>				list						= null;
		String						quantity					= null;
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		
		try{	
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql					= "select quantity "
								+ "	from productquantity"
								+ "	where productCode = '" + productquantityBean.getProductCode() + "'"
										+ " and tin = '" + productquantityBean.getTin() + "'";
			
			logger.info("[getProductquantity] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("quantity"		, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				quantity = EnjoyUtils.convertFloatToDisplay(list.get(0), 2);
			}
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductquantity");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getProductquantity][End]");
		}
		
		return quantity;
		
	}
}
