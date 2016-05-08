
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByMonthReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class SummarySaleByMonthReportDao extends DaoControl {
	
	public SummarySaleByMonthReportDao(){
		setLogger(EnjoyLogger.getLogger(SummarySaleByMonthReportDao.class));
		super.init();
	}
	
	public List<SummarySaleByMonthReportBean> searchByCriteria(SummarySaleByMonthReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String								hql						= null;
		SummarySaleByMonthReportBean		bean					= null;
		List<SummarySaleByMonthReportBean> 	list 					= new ArrayList<SummarySaleByMonthReportBean>();
		String 								invoiceDateFrom			= null;
		String 								invoiceDateTo			= null;
		HashMap<String, Object>				param					= new HashMap<String, Object>();
		List<String>						columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>		resultList				= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select CONCAT(b.cusName, ' ', b.cusSurname, ' (', b.branchName, ')') as cusName"
									+ " , d.productName"
									+ " , sum(c.quantity) as quantity"
									+ " , SUM(c.price) - SUM(c.discount) as price"
									+ " from invoicecashmaster a, customer b, invoicecashdetail c, productmaster d"
									+ " where a.cusCode = b.cusCode"
									+ "		and a.invoiceCode = c.invoiceCode"
									+ "		and c.productCode = d.productCode"
									+ "		and a.tin 			= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo		, '%Y%m%d')"
									+ " GROUP BY CONCAT(b.cusName, ' ', b.cusSurname, ' (', b.branchName, ')'),d.productName"
									+ " order by a.invoiceDate, a.invoiceCode";
			
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			columnList.add("cusName");
			columnList.add("productName");
			columnList.add("quantity");
			columnList.add("price");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByMonthReportBean();
				
				bean.setCusName		(EnjoyUtils.nullToStr(row.get("cusName")));
				bean.setProductName	(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setQuantity	(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				bean.setPrice		(EnjoyUtils.convertFloatToDisplay(row.get("price"), 2));
				
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
