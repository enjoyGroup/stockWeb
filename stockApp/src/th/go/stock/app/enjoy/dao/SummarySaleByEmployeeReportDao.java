
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByEmployeeReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class SummarySaleByEmployeeReportDao extends DaoControl {
	
	public SummarySaleByEmployeeReportDao(){
		setLogger(EnjoyLogger.getLogger(SummarySaleByEmployeeReportDao.class));
		super.init();
	}
	
	public List<SummarySaleByEmployeeReportBean> searchByCriteria(SummarySaleByEmployeeReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String									hql						= null;
		SummarySaleByEmployeeReportBean			bean					= null;
		List<SummarySaleByEmployeeReportBean> 	list 					= new ArrayList<SummarySaleByEmployeeReportBean>();
		String 									invoiceDateFrom			= null;
		String 									invoiceDateTo			= null;
		HashMap<String, Object>					param					= new HashMap<String, Object>();
		List<String>							columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>			resultList				= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select a.invoiceCode"
									+ " , a. invoiceDate"
									+ " ,  CONCAT(b.cusName, ' ', b.cusSurname, ' (',b.branchName, ')') as cusName"
									+ " , a.invoiceTotal"
									+ " , a.saleCommission"
									+ " from invoicecashmaster a, customer b, userdetails c"
									+ " where a.cusCode = b.cusCode"
									+ "		and c.userUniqueId = a.saleUniqueId"
									+ "		and a.tin 			= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo	, '%Y%m%d')";
			
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			if(!criteria.getSaleName().equals("")){
				hql += " and CONCAT(c.userName, ' ', c.userSurname) = :saleName";
				param.put("saleName", criteria.getSaleName());
			}
			
			hql += " order by a.invoiceDate, a.invoiceCode";
			
			columnList.add("invoiceCode");
			columnList.add("invoiceDate");
			columnList.add("cusName");
			columnList.add("invoiceTotal");
			columnList.add("saleCommission");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByEmployeeReportBean();
				
				bean.setInvoiceCode		(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setInvoiceDate		(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setCusName			(EnjoyUtils.nullToStr(row.get("cusName")));
				bean.setInvoiceTotal	(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
				bean.setSaleCommission	(EnjoyUtils.convertFloatToDisplay(row.get("saleCommission"), 2));
				
				list.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql				= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return list;
		
	}
	
}
