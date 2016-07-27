
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByProductReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class SummarySaleByProductReportDao extends DaoControl {
	
	public SummarySaleByProductReportDao(){
		setLogger(EnjoyLogger.getLogger(SummarySaleByProductReportDao.class));
		super.init();
	}
	
	public List<SummarySaleByProductReportBean> searchByCriteria(SummarySaleByProductReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String									hql						= null;
		SummarySaleByProductReportBean			bean					= null;
		List<SummarySaleByProductReportBean> 	list 					= new ArrayList<SummarySaleByProductReportBean>();
		String 									invoiceDateFrom			= null;
		String 									invoiceDateTo			= null;
		HashMap<String, Object>					param					= new HashMap<String, Object>();
		List<String>							columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>			resultList				= null;
		String									cusName					= null;
		
		try{	
			invoiceDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateFrom());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(criteria.getInvoiceDateTo());
			
			hql					= "select a.invoiceDate"
									+ " , CONCAT(b.cusName, ' ', b.cusSurname) as cusName"
									+ " , b.branchName"
									+ " , d.productName"
									+ " , c.quantity"
									+ " , c.price"
									+ " from invoicecashmaster a"
									+ "		inner join customer b on b.cusCode = a.cusCode and b.tin = a.tin"
									+ "		inner join invoicecashdetail c on c.invoiceCode = a.invoiceCode and c.tin = a.tin"
									+ "		inner join productmaster d on d.productCode = c.productCode and d.tin = c.tin"
									+ " where a.tin 			= :tin"
									+ " 	and a.invoiceDate >= STR_TO_DATE(:invoiceDateFrom	, '%Y%m%d')"
									+ " 	and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo	, '%Y%m%d')";
			
			param.put("tin"				, criteria.getTin());
			param.put("invoiceDateFrom"	, invoiceDateFrom);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			if(!criteria.getProductName().equals("")){
				hql += " and d.productName = :productName";
				param.put("productName", criteria.getProductName());
			}
			
			hql += " order by c. productCode, a. cusCode, a.invoiceDate";
			
			columnList.add("invoiceDate");
			columnList.add("cusName");
			columnList.add("branchName");
			columnList.add("productName");
			columnList.add("quantity");
			columnList.add("price");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new SummarySaleByProductReportBean();
				cusName	= EnjoyUtils.nullToStr(row.get("cusName"));
				if(!"".equals(EnjoyUtils.nullToStr(row.get("branchName")))){
					cusName += "(" + EnjoyUtils.nullToStr(row.get("branchName")) + ")";
				}
				
				bean.setInvoiceDate	(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
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
