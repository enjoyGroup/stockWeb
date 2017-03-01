
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.AdjustStockBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.AdjustStockForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Adjusthistory;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class AdjustStockDao extends DaoControl {
	
	public AdjustStockDao(){
		setLogger(EnjoyLogger.getLogger(AdjustStockDao.class));
		super.init();
	}
	
	public List<AdjustStockBean> getAdjustHistoryList(AdjustStockBean 	adjustStockBean) throws EnjoyException{
		getLogger().info("[getAdjustHistoryList][Begin]");
		
		String								hql						= null;
		AdjustStockBean						bean					= null;
		List<AdjustStockBean> 				adjustStockBeanList 	= new ArrayList<AdjustStockBean>();
		HashMap<String, Object>				param					= new HashMap<String, Object>();
		List<String>						columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>		resultList				= null;
		
		try{	
			hql					= "select a.* "
								+ "		from("
								+ "			SELECT @rownum\\:=@rownum+1 AS rownum,t.*"
								+ "				from (SELECT @rownum\\:=0) r, adjusthistory t"
								+ "				where t.productCode = :productCode"
								+ "					and t.tin 		= :tin"
								+ "				order by t.adjustDate desc, adjustNo desc"
								+ "		) a LIMIT :limit, " + AdjustStockForm.ORDER_LIMIT;
			
			
			param.put("productCode"	, EnjoyUtils.parseInt(adjustStockBean.getProductCode()));
			param.put("tin"			, adjustStockBean.getTin());
			param.put("limit"		, adjustStockBean.getLastOrder());
			
			columnList.add("adjustNo");
			columnList.add("adjustDate");
			columnList.add("productCode");
			columnList.add("quanOld");
			columnList.add("quanNew");
			columnList.add("remark");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new AdjustStockBean();
				
				bean.setAdjustNo			(EnjoyUtils.nullToStr(row.get("adjustNo")));
				bean.setAdjustDate			(EnjoyUtils.dateToThaiDisplay(row.get("adjustDate")));
				bean.setProductCode			(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setQuanOld				(EnjoyUtils.convertFloatToDisplay(row.get("quanOld"), 2));
				bean.setQuanNew				(EnjoyUtils.convertFloatToDisplay(row.get("quanNew"), 2));
				bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				
				adjustStockBeanList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().info("[getAdjustHistoryList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getAdjustHistoryList");
		}finally{
			hql						= null;
			getLogger().info("[getAdjustHistoryList][End]");
		}
		
		return adjustStockBeanList;
		
	}
	
	public int checkLimitAdjustHistoryOrder(String productCode, String tin, int lastOrder) throws EnjoyException{
		getLogger().info("[checkLimitAdjustHistoryOrder][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql				= "select count(*) cou"
							+ "	from("
							+ "		SELECT @rownum\\:=@rownum+1 AS rownum,t.* "
							+ " 		from (SELECT @rownum\\:=0) r, adjusthistory t"
							+ " 		where t.productCode = :productCode"
							+ "					and t.tin 	= :tin"
							+ " 		order by t.adjustDate desc, adjustNo desc"
							+ " ) a where a.rownum > :lastOrder";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(productCode));
			param.put("tin"			, tin);
			param.put("lastOrder"	, lastOrder);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkLimitAdjustHistoryOrder] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			getLogger().info("[checkLimitAdjustHistoryOrder][End]");
		}
		
		return result;
	}
	
	
	public void insertAdjustHistory(AdjustStockBean adjustStockBean) throws EnjoyException{
		getLogger().info("[insertAdjustHistory][Begin]");
		
		Adjusthistory	adjusthistory	= null;
		
		try{
			
			adjusthistory = new Adjusthistory();
			
			adjusthistory.setAdjustDate			(adjustStockBean.getAdjustDate());
			adjusthistory.setProductCode		(EnjoyUtils.parseInt(adjustStockBean.getProductCode()));
			adjusthistory.setQuanOld			(EnjoyUtils.parseBigDecimal(adjustStockBean.getQuanOld()));
			adjusthistory.setQuanNew			(EnjoyUtils.parseBigDecimal(adjustStockBean.getQuanNew()));
			adjusthistory.setRemark				(adjustStockBean.getRemark());
			adjusthistory.setTin				(adjustStockBean.getTin());
			
			insertData(adjusthistory);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertAdjustHistory");
		}finally{
			adjusthistory = null;
			getLogger().info("[insertAdjustHistory][End]");
		}
	}
	
}
