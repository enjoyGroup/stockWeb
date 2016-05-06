
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.HistoryPurchasedByProductReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class HistoryPurchasedByProductReportDao extends DaoControl {
	
	public HistoryPurchasedByProductReportDao(){
		setLogger(EnjoyLogger.getLogger(HistoryPurchasedByProductReportDao.class));
		super.init();
	}
	
	public List<HistoryPurchasedByProductReportBean> searchByCriteria(HistoryPurchasedByProductReportBean 	historyPurchased) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String										hql						= null;
		HistoryPurchasedByProductReportBean			bean					= null;
		List<HistoryPurchasedByProductReportBean> 	historyPurchasedList 	= new ArrayList<HistoryPurchasedByProductReportBean>();
		String 										reciveDateFrom			= null;
		String 										reciveDateTo			= null;
		HashMap<String, Object>						param					= new HashMap<String, Object>();
		List<String>								columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>				resultList				= null;
		
		try{	
			reciveDateFrom 		= EnjoyUtils.dateThaiToDb(historyPurchased.getReciveDateFrom());
			reciveDateTo		= EnjoyUtils.dateThaiToDb(historyPurchased.getReciveDateTo());
			
			hql					= "select d.productName"
									+ " , CONCAT(b.vendorName, '(', b.branchName, ')') as vendorName"
									+ " , a.reciveNo"
									+ " , a.reciveDate"
									+ " , c.costPrice"
									+ " , c.discountRate"
									+ " from reciveordermaster a, companyvendor b, reciveordedetail c, productmaster d"
									+ " where a.vendorCode 		= b.vendorCode"
									+ "		and a.reciveNo 		= c.reciveNo"
									+ "		and c.productCode 	= d.productCode"
									+ "		and a.tin 			= :tin"
									+ " 	and a.reciveDate >= STR_TO_DATE(:reciveDateFrom	, '%Y%m%d')"
									+ " 	and a.reciveDate <= STR_TO_DATE(:reciveDateTo	, '%Y%m%d')";
			
			param.put("tin"				, historyPurchased.getTin());
			param.put("reciveDateFrom"	, reciveDateFrom);
			param.put("reciveDateTo"	, reciveDateTo);
			
			if(!historyPurchased.getProductName().equals("")){
				hql += " and d.productName = :productName";
				param.put("productName", historyPurchased.getProductName());
			}
			
			hql += " order by c. productCode, a. vendorCode, a.reciveDate";
			
			columnList.add("productName");
			columnList.add("vendorName");
			columnList.add("reciveNo");
			columnList.add("reciveDate");
			columnList.add("costPrice");
			columnList.add("discountRate");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new HistoryPurchasedByProductReportBean();
				
				bean.setProductName		(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setVendorName		(EnjoyUtils.nullToStr(row.get("vendorName")));
				bean.setReciveNo		(EnjoyUtils.nullToStr(row.get("reciveNo")));
				bean.setReciveDate		(EnjoyUtils.dateToThaiDisplay(row.get("reciveDate")));
				bean.setCostPrice		(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
				bean.setDiscountRate	(EnjoyUtils.convertFloatToDisplay(row.get("discountRate"), 2));
				
				historyPurchasedList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql				= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return historyPurchasedList;
		
	}
	
}
