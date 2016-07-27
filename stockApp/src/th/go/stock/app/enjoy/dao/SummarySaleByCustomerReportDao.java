
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByCustomerReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class SummarySaleByCustomerReportDao extends DaoControl {
	
	public SummarySaleByCustomerReportDao(){
		setLogger(EnjoyLogger.getLogger(SummarySaleByCustomerReportDao.class));
		super.init();
	}
	
	public List<SummarySaleByCustomerReportBean> searchByCriteria(SummarySaleByCustomerReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String									hql						= null;
		SummarySaleByCustomerReportBean			bean					= null;
		List<SummarySaleByCustomerReportBean> 	list 					= new ArrayList<SummarySaleByCustomerReportBean>();
		String 									invoiceDateFrom			= null;
		String 									invoiceDateTo			= null;
		HashMap<String, Object>					param					= new HashMap<String, Object>();
		List<String>							columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>			resultList				= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select d.productName, a.invoiceDate, c.quantity,  c.price, c.discount"
									+ " from invoicecashmaster a"
									+ " 	inner join customer b on b.cusCode 	= a.cusCode and b.tin	= a.tin"
									+ "		inner join invoicecashdetail c on c.invoiceCode 	= a.invoiceCode and c.tin	= a.tin"
									+ "		inner join productmaster d  on d.productCode 	= c.productCode and d.tin	= c.tin"
									+ "	where a.cusCode		= :cusCode"
									+ "		and a.tin		= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo	, '%Y%m%d')";
			
			param.put("cusCode"			, criteria.getCusCode());
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			if(!criteria.getProductName().equals("")){
				hql += " and d.productName = :productName";
				param.put("productName", criteria.getProductName());
			}
			
			hql += " order by a.invoiceDate, a.invoiceCode";
			
			columnList.add("productName");
			columnList.add("invoiceDate");
			columnList.add("quantity");
			columnList.add("price");
			columnList.add("discount");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByCustomerReportBean();
				
				bean.setProductName	(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setInvoiceDate	(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setQuantity	(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				bean.setPrice		(EnjoyUtils.convertFloatToDisplay(row.get("price"), 2));
				bean.setDiscount	(EnjoyUtils.convertFloatToDisplay(row.get("discount"), 2));
				
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
