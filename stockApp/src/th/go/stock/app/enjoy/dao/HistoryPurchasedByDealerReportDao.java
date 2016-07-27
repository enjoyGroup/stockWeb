
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.HistoryPurchasedByDealerReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class HistoryPurchasedByDealerReportDao extends DaoControl {
	
	public HistoryPurchasedByDealerReportDao(){
		setLogger(EnjoyLogger.getLogger(HistoryPurchasedByDealerReportDao.class));
		super.init();
	}
	
	public List<HistoryPurchasedByDealerReportBean> searchByCriteria(HistoryPurchasedByDealerReportBean 	historyPurchased) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String										hql						= null;
		HistoryPurchasedByDealerReportBean			bean					= null;
		List<HistoryPurchasedByDealerReportBean> 	historyPurchasedList 	= new ArrayList<HistoryPurchasedByDealerReportBean>();
		String 										reciveDateFrom			= null;
		String 										reciveDateTo			= null;
		HashMap<String, Object>						param					= new HashMap<String, Object>();
		List<String>								columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>				resultList				= null;
		
		try{	
			reciveDateFrom 		= EnjoyUtils.dateThaiToDb(historyPurchased.getReciveDateFrom());
			reciveDateTo		= EnjoyUtils.dateThaiToDb(historyPurchased.getReciveDateTo());
			
			hql					= "select CONCAT(b.vendorName, '(', b.branchName, ')') as vendorName , a.reciveNo, a.reciveDate, a.reciveTotal, a.reciveDiscount"
									+ " from reciveordermaster a, companyvendor b"
									+ " where a.vendorCode = b.vendorCode"
									+ "		and a.tin = b.tinCompany"
									+ "		and a.tin = :tin"
									+ " 	and a.reciveDate >= STR_TO_DATE(:reciveDateFrom	, '%Y%m%d')"
									+ " 	and a.reciveDate <= STR_TO_DATE(:reciveDateTo	, '%Y%m%d')";
			
			param.put("tin"				, historyPurchased.getTin());
			param.put("reciveDateFrom"	, reciveDateFrom);
			param.put("reciveDateTo"	, reciveDateTo);
			
			if(!historyPurchased.getVendorName().equals("")){
				hql += " and b.vendorName = :vendorName";
				param.put("vendorName", historyPurchased.getVendorName());
			}
			
			hql += " order by a.reciveDate, a.vendorCode";
			
			columnList.add("vendorName");
			columnList.add("reciveNo");
			columnList.add("reciveDate");
			columnList.add("reciveTotal");
			columnList.add("reciveDiscount");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new HistoryPurchasedByDealerReportBean();
				
				bean.setVendorName		(EnjoyUtils.nullToStr(row.get("vendorName")));
				bean.setReciveNo		(EnjoyUtils.nullToStr(row.get("reciveNo")));
				bean.setReciveDate		(EnjoyUtils.dateToThaiDisplay(row.get("reciveDate")));
				bean.setReciveTotal		(EnjoyUtils.convertFloatToDisplay(row.get("reciveTotal"), 2));
				bean.setReciveDiscount	(EnjoyUtils.convertFloatToDisplay(row.get("reciveDiscount"), 2));
				
				historyPurchasedList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql				= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return historyPurchasedList;
		
	}
	
}
