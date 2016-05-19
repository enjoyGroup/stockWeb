
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.AdjustStockBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.AdjustStockForm;
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
		
		try{	
			hql					= "select a.* "
								+ "		from("
								+ "			SELECT @rownum\\:=@rownum+1 AS rownum,t.*"
								+ "				from (SELECT @rownum\\:=0) r, adjusthistory t"
								+ "				where t.productCode = '"+adjustStockBean.getProductCode()+"'"
								+ "					and t.tin = '"+adjustStockBean.getTin()+"'"
								+ "				order by t.adjustDate desc, adjustNo desc"
								+ "		) a LIMIT " + adjustStockBean.getLastOrder() + ", " + AdjustStockForm.ORDER_LIMIT;
			
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
				bean.setAdjustDate			(EnjoyUtils.dateToThaiDisplay(row[1]));
				bean.setProductCode			(EnjoyUtils.nullToStr(row[2]));
				bean.setQuanOld				(EnjoyUtils.nullToStr(row[3]));
				bean.setQuanNew				(EnjoyUtils.nullToStr(row[4]));
				bean.setRemark				(EnjoyUtils.nullToStr(row[5]));
				
				adjustStockBeanList.add(bean);
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
	
	public int checkLimitAdjustHistoryOrder(Session session, String productCode, String tin, int lastOrder) throws EnjoyException{
		logger.info("[checkLimitAdjustHistoryOrder][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "select count(*) cou"
							+ "	from("
							+ "		SELECT @rownum\\:=@rownum+1 AS rownum,t.* "
							+ " 		from (SELECT @rownum\\:=0) r, adjusthistory t"
							+ " 		where t.productCode = '"+productCode+"'"
							+ "					and t.tin = '"+tin+"'"
							+ " 		order by t.adjustDate desc, adjustNo desc"
							+ " ) a where a.rownum > " + lastOrder;
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkLimitAdjustHistoryOrder] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkLimitAdjustHistoryOrder][End]");
		}
		
		return result;
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
			adjusthistory.setTin				(adjustStockBean.getTin());
			
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
