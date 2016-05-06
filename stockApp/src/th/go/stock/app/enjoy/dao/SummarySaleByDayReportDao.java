
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByDayReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class SummarySaleByDayReportDao extends DaoControl {
	
	public SummarySaleByDayReportDao(){
		setLogger(EnjoyLogger.getLogger(SummarySaleByDayReportDao.class));
		super.init();
	}
	
	public List<SummarySaleByDayReportBean> searchByCriteria(SummarySaleByDayReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String								hql						= null;
		SummarySaleByDayReportBean			bean					= null;
		List<SummarySaleByDayReportBean> 	list 					= new ArrayList<SummarySaleByDayReportBean>();
		String 								invoiceDateFrom			= null;
		String 								invoiceDateTo			= null;
		HashMap<String, Object>				param					= new HashMap<String, Object>();
		List<String>						columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>		resultList				= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select a.invoiceCode"
									+ " , CONCAT(b.cusName, ' ', b.cusSurname, ' (', b.branchName, ')') as cusName"
									+ " , a.invoiceDate"
									+ " , a.invoiceTotal"
									+ " , a.remark"
									+ " from invoicecashmaster a, customer b"
									+ " where a.cusCode = b.cusCode"
									+ "		and a.tin 			= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo		, '%Y%m%d')";
			
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			hql += " order by a.invoiceDate, a.invoiceCode";
			
			columnList.add("invoiceCode");
			columnList.add("cusName");
			columnList.add("invoiceDate");
			columnList.add("invoiceTotal");
			columnList.add("remark");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByDayReportBean();
				
				bean.setInvoiceCode	(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setCusName		(EnjoyUtils.nullToStr(row.get("cusName")));
				bean.setInvoiceDate	(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setInvoiceTotal(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
				bean.setRemark		(EnjoyUtils.nullToStr(row.get("remark")));
				
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
