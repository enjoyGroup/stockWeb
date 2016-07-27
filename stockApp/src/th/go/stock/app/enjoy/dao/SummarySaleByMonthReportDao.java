
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
		String								cusName					= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select CONCAT(b.cusName, ' ', b.cusSurname) as cusName"
									+ " , b.branchName"
									+ " , d.productName"
									+ " , sum(c.quantity) as quantity"
									+ " , SUM(c.price) - SUM(c.discount) as price"
									+ " from invoicecashmaster a"
									+ "		inner join customer b on b.cusCode = a.cusCode and b.tin = a.tin"
									+ "		inner join invoicecashdetail c on c.invoiceCode = a.invoiceCode and c.tin = a.tin"
									+ "		inner join productmaster d on d.productCode = c.productCode and d.tin = c.tin"
									+ " where a.tin 			= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo		, '%Y%m%d')"
									+ " GROUP BY CONCAT(b.cusName, ' ', b.cusSurname, ' (', b.branchName, ')'),d.productName"
									+ " order by a.invoiceDate, a.invoiceCode";
			
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			columnList.add("cusName");
			columnList.add("branchName");
			columnList.add("productName");
			columnList.add("quantity");
			columnList.add("price");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByMonthReportBean();
				cusName	= EnjoyUtils.nullToStr(row.get("cusName"));
				if(!"".equals(EnjoyUtils.nullToStr(row.get("branchName")))){
					cusName += "(" + EnjoyUtils.nullToStr(row.get("branchName")) + ")";
				}
				
				bean.setCusName		(cusName);
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
