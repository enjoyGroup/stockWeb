
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ProductDiscountBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Productdiscount;
import th.go.stock.app.enjoy.model.ProductdiscountPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ProductDiscountDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDiscountDao.class);
	
	public List<ProductDiscountBean> getProductDiscountList( Session 					session
															,ProductDiscountBean 		productDiscountBean) throws EnjoyException{
		logger.info("[getProductDiscountList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		ProductDiscountBean			bean						= null;
		List<ProductDiscountBean> 	productDiscountList 	= new ArrayList<ProductDiscountBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select * "
								+ "	from productdiscount"
								+ "	where productCode = '" + productDiscountBean.getProductCode() + "' order by seq asc";
			
			logger.info("[getProductDiscountList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("seq"				, new StringType());
			query.addScalar("quanDiscount"		, new StringType());
			query.addScalar("discount"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductDiscountList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ProductDiscountBean();
				
				logger.info("productCode 	:: " + row[0].toString());
				logger.info("seqDb 			:: " + row[1].toString());
				logger.info("quanDiscount 	:: " + row[2].toString());
				logger.info("discount 		:: " + row[3].toString());
				logger.info("seq 			:: " + seq);
				
				bean.setProductCode			(row[0].toString());
				bean.setSeqDb				(row[1].toString());
				bean.setQuanDiscount		(row[2].toString());
				bean.setDiscount			(row[3].toString());
				bean.setSeq					(String.valueOf(seq));
				
				productDiscountList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getProductDiscountList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductDiscountList");
		}finally{
			hql						= null;
			logger.info("[getProductDiscountList][End]");
		}
		
		return productDiscountList;
		
	}
	
	public void insertProductdiscount(Session session, ProductDiscountBean 		productDiscountBean) throws EnjoyException{
		logger.info("[insertProductdiscount][Begin]");
		
		Productdiscount			productdiscount		= null;
		ProductdiscountPK 		id 					= null;
		
		try{
			
			productdiscount = new Productdiscount();
			id 				= new ProductdiscountPK();
			
			id.setProductCode(productDiscountBean.getProductCode());
			id.setSeq(EnjoyUtils.parseInt(productDiscountBean.getSeqDb()));
			
			productdiscount.setId(id);
			productdiscount.setQuanDiscount(EnjoyUtils.parseBigDecimal(productDiscountBean.getQuanDiscount()));
			productdiscount.setDiscount(EnjoyUtils.parseBigDecimal(productDiscountBean.getDiscount()));
			
			session.saveOrUpdate(productdiscount);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductdiscount");
		}finally{
			
			id 				= null;
			productdiscount = null;
			logger.info("[insertProductdiscount][End]");
		}
	}
	
	public void deleteProductdiscount(Session session, String productCode) throws EnjoyException{
		logger.info("[deleteProductdiscount][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Productdiscount t"
							+ " where t.id.productCode	 = '" + productCode + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteProductdiscount][End]");
		}
	}

	
}
