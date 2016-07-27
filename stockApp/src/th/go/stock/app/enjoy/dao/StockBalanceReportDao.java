
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.StockBalanceReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class StockBalanceReportDao extends DaoControl {
	
	public StockBalanceReportDao(){
		setLogger(EnjoyLogger.getLogger(StockBalanceReportDao.class));
		super.init();
	}
	
	public List<StockBalanceReportBean> searchByCriteria(StockBalanceReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		StockBalanceReportBean			bean					= null;
		List<StockBalanceReportBean> 	list 					= new ArrayList<StockBalanceReportBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			
			hql					= "select c.productTypeName, d.productGroupName, a.productName, b.quantity "
									+ " from productmaster a"
									+ "		inner join productquantity b on b.productCode = a.productCode and b.tin = a.tin"
									+ "		inner join productype c on c.productTypeCode = a.productType and c.tin = a.tin"
									+ "		inner join productgroup d on d.productTypeCode = a.productType and d.productGroupCode = a.productGroup and d.tin and a.tin "
									+ " where a.tin 			= :tin";
			
			param.put("tin"				, criteria.getTin());
			
			if(criteria.getProductTypeName()!=null && !criteria.getProductTypeName().equals("")){
				hql += " and c.productTypeName = :productTypeName";
				param.put("productTypeName"		, criteria.getProductTypeName());
			}
			
			if(criteria.getProductGroupName()!=null && !criteria.getProductGroupName().equals("")){
				hql += " and d.productGroupName = :productGroupName";
				param.put("productGroupName"	, criteria.getProductGroupName());
			}
			
			hql += " order by a.productType, a.productGroup, a.productCode";
			
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			columnList.add("productName");
			columnList.add("quantity");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new StockBalanceReportBean();
				
				bean.setProductTypeName	(EnjoyUtils.nullToStr(row.get("productTypeName")));
				bean.setProductGroupName(EnjoyUtils.nullToStr(row.get("productGroupName")));
				bean.setProductName		(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setQuantity		(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				
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
