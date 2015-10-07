
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComparePriceBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Compareprice;
import th.go.stock.app.enjoy.model.ComparepricePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ComparePriceDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ComparePriceDao.class);
	
	public List<ComparePriceBean> searchByCriteria(	Session 			session, 
													ComparePriceBean 	comparePriceBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String								hql							= null;
		SQLQuery 							query 						= null;
		List<Object[]>						list						= null;
		ComparePriceBean					bean						= null;
		List<ComparePriceBean> 				comparePriceList 			= new ArrayList<ComparePriceBean>();
		int									seq							= 0;
		
		try{	
			hql					= "select a.productCode, b.productName, a.seq, a.vendorCode, c.vendorName, c.branchName, a.quantity, a.price"
									+ "	from compareprice a, productmaster b, companyvendor c"
									+ "	where b.productCode = a.productCode"
										+ " AND c.vendorCode = a.vendorCode"
										+ " AND a.productCode = '" + comparePriceBean.getProductCode() + "'";
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("vendorCode"		, new StringType());
			query.addScalar("vendorName"		, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("seq"				, new StringType());
			query.addScalar("quantity"			, new StringType());
			query.addScalar("price"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ComparePriceBean();
				
				bean.setProductCode	(EnjoyUtils.nullToStr(row[0]));
				bean.setProductName	(EnjoyUtils.nullToStr(row[1]));
				bean.setVendorCode	(EnjoyUtils.nullToStr(row[2]));
				bean.setVendorName	(EnjoyUtils.nullToStr(row[3]));
				bean.setBranchName	(EnjoyUtils.nullToStr(row[4]));
				bean.setSeqDb		(EnjoyUtils.nullToStr(row[5]));
				bean.setQuantity	(EnjoyUtils.convertFloatToDisplay(row[6], 2));
				bean.setPrice		(EnjoyUtils.convertFloatToDisplay(row[7], 2));
				bean.setSeq			(EnjoyUtils.nullToStr(seq));
				
				comparePriceList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return comparePriceList;
		
	}
	
	public void insertCompareprice(Session session, ComparePriceBean comparePriceBean) throws EnjoyException{
		logger.info("[insertCompareprice][Begin]");
		
		Compareprice	compareprice	= null;
		ComparepricePK	pk				= null;
		
		try{
			
			compareprice 	= new Compareprice();
			pk				= new ComparepricePK();
			
			pk.setProductCode(comparePriceBean.getProductCode());
			pk.setSeq(EnjoyUtils.parseInt(comparePriceBean.getSeq()));
			
			compareprice.setId(pk);
			compareprice.setVendorCode(EnjoyUtils.parseInt(comparePriceBean.getVendorCode()));
			compareprice.setQuantity(EnjoyUtils.parseBigDecimal(comparePriceBean.getQuantity()));
			compareprice.setPrice(EnjoyUtils.parseBigDecimal(comparePriceBean.getPrice()));
			
			session.saveOrUpdate(compareprice);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertCompareprice");
		}finally{
			compareprice = null;
			pk			 = null;
			logger.info("[insertCompareprice][End]");
		}
	}
	
	public void deleteCompareprice(Session session, String productCode) throws EnjoyException{
		logger.info("[deleteCompareprice][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Compareprice t"
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
			logger.info("[deleteCompareprice][End]");
		}
	}
	
}
