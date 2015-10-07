
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.AdjustStockBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Adjusthistory;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class AdjustStockDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(AdjustStockDao.class);
	
	public List<AdjustStockBean> getAdjustHistoryList(	Session 			session, 
														AdjustStockBean 	adjustStockBean) throws EnjoyException{
		logger.info("[getAdjustHistoryList][Begin]");
		
		String								hql							= null;
		SQLQuery 							query 						= null;
		List<Object[]>						list						= null;
		AdjustStockBean						bean						= null;
		List<AdjustStockBean> 				adjustStockBeanList 		= new ArrayList<AdjustStockBean>();
		int									seq							= 0;
		
		try{	
			hql					= "select * from adjusthistory where productCode = '"+adjustStockBean.getProductCode()+"'";
			
			logger.info("[getAdjustHistoryList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("adjustNo"		, new StringType());
			query.addScalar("adjustDate"	, new StringType());
			query.addScalar("productCode"	, new StringType());
			query.addScalar("quanOld"		, new StringType());
			query.addScalar("quanNew"		, new StringType());
			query.addScalar("remark"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new AdjustStockBean();
				
				bean.setAdjustNo			(EnjoyUtils.nullToStr(row[0]));
				bean.setAdjustDate			(EnjoyUtils.dateFormat(row[1], "yyyyMMdd", "dd/MM/yyyy"));
				bean.setProductCode			(EnjoyUtils.nullToStr(row[2]));
				bean.setQuanOld				(EnjoyUtils.nullToStr(row[3]));
				bean.setQuanNew				(EnjoyUtils.nullToStr(row[4]));
				bean.setRemark				(EnjoyUtils.nullToStr(row[5]));
				
				adjustStockBeanList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			logger.info("[getAdjustHistoryList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getAdjustHistoryList");
		}finally{
			hql						= null;
			logger.info("[getAdjustHistoryList][End]");
		}
		
		return adjustStockBeanList;
		
	}
	
	
	public void insertAdjustHistory(Session session, AdjustStockBean adjustStockBean) throws EnjoyException{
		logger.info("[insertAdjustHistory][Begin]");
		
		Adjusthistory	adjusthistory	= null;
		
		try{
			
			adjusthistory = new Adjusthistory();
			
			adjusthistory.setAdjustDate			(adjustStockBean.getAdjustDate());
			adjusthistory.setProductCode		(adjustStockBean.getProductCode());
			adjusthistory.setQuanOld			(EnjoyUtils.parseBigDecimal(adjustStockBean.getQuanOld()));
			adjusthistory.setQuanNew			(EnjoyUtils.parseBigDecimal(adjustStockBean.getQuanNew()));
			adjusthistory.setRemark				(adjustStockBean.getRemark());
			
			session.saveOrUpdate(adjusthistory);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertAdjustHistory");
		}finally{
			adjusthistory = null;
			logger.info("[insertAdjustHistory][End]");
		}
	}
	
}
