
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.AlertLowProductReportBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class AlertLowProductReportDao extends DaoControl {
	
	public AlertLowProductReportDao(){
		setLogger(EnjoyLogger.getLogger(AlertLowProductReportDao.class));
		super.init();
	}
	
	public List<AlertLowProductReportBean> searchByCriteria(AlertLowProductReportBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql					= null;
		AlertLowProductReportBean		bean				= null;
		List<AlertLowProductReportBean> list 				= new ArrayList<AlertLowProductReportBean>();
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{	
			
			hql					= "select t3. productTypeName, t4.productGroupName, t1.productname, t1.minQuan, t2.quantity"
									+ " from productmaster t1"
									+ "		INNER JOIN productquantity t2 on t1.productCode = t2.productCode and t1.minQuan >= t2.quantity and t1.tin = t2.tin"
									+ "		INNER JOIN productype t3 ON t1.productType = t3.productTypeCode and t1.tin = t3.tin and t3.productTypeStatus = 'A'"
									+ "		INNER JOIN productgroup t4 on t1.productType = t4.productTypeCode and t1.productGroup = t4.productGroupCode and t1.tin = t4.tin AND productGroupStatus = 'A'"
									+ " where t1.tin 			= :tin";
			
			param.put("tin"				, criteria.getTin());
			
			if(criteria.getProductTypeName()!=null && !criteria.getProductTypeName().equals("")){
				hql += " and t3.productTypeName = :productTypeName";
				param.put("productTypeName"		, criteria.getProductTypeName());
			}
			
			if(criteria.getProductGroupName()!=null && !criteria.getProductGroupName().equals("")){
				hql += " and t4.productGroupName = :productGroupName";
				param.put("productGroupName"	, criteria.getProductGroupName());
			}
			
			hql += " order by t1.productType, t1.productGroup, t1.productCode";
			
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			columnList.add("productName");
			columnList.add("minQuan");
			columnList.add("quantity");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new AlertLowProductReportBean();
				
				bean.setProductTypeName	(EnjoyUtils.nullToStr(row.get("productTypeName")));
				bean.setProductGroupName(EnjoyUtils.nullToStr(row.get("productGroupName")));
				bean.setProductName		(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setMinQuan			(EnjoyUtils.convertFloatToDisplay(row.get("minQuan"), 2));
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
