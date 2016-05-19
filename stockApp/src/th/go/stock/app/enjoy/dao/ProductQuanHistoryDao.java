
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Productquanhistory;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ProductQuanHistoryDao extends DaoControl {
	
	public ProductQuanHistoryDao(){
		setLogger(EnjoyLogger.getLogger(ProductQuanHistoryDao.class));
		super.init();
	}
	
	public void insert(Session session, ProductQuanHistoryBean bean) throws EnjoyException{
		getLogger().info("[insert][Begin]");
		
		Productquanhistory 	productquanhistory 	= null;
		
		try{
			
			productquanhistory = new Productquanhistory();
			
			productquanhistory.setFormRef		(bean.getFormRef());
			productquanhistory.setHisDate		(EnjoyUtils.currDateThai());
			productquanhistory.setProductType	(bean.getProductType());
			productquanhistory.setProductGroup	(bean.getProductGroup());
			productquanhistory.setProductCode	(bean.getProductCode());
			productquanhistory.setTin			(bean.getTin());
			productquanhistory.setQuantityPlus	(EnjoyUtils.parseBigDecimal(bean.getQuantityPlus()));
			productquanhistory.setQuantityMinus	(EnjoyUtils.parseBigDecimal(bean.getQuantityMinus()));
			productquanhistory.setQuantityTotal	(EnjoyUtils.parseBigDecimal(bean.getQuantityTotal()));
			
			session.saveOrUpdate(productquanhistory);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insert");
		}finally{
			getLogger().info("[insert][End]");
		}
	}
	
	
	public List<ProductQuanHistoryBean> searchByCriteria(ProductQuanHistoryBean 	criteria) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql					= null;
		ProductQuanHistoryBean			bean				= null;
		List<ProductQuanHistoryBean> 	list 				= new ArrayList<ProductQuanHistoryBean>();
		String 							hisDateFrom			= null;
		String 							hisDateTo			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{	
			hisDateFrom 	= EnjoyUtils.dateThaiToDb(criteria.getHisDateFrom());
			hisDateTo		= EnjoyUtils.dateThaiToDb(criteria.getHisDateTo());
			
			hql					= "select  a.formRef, a.hisDate, b.productName, a.quantityPlus, a.quantityMinus, a.quantityTotal"
									+ " from productquanhistory a"
									+ " 	inner JOIN productmaster b on a.productCode = b. productCode"
									+ " 	inner JOIN productgroup c on a.productGroup = c.productGroupCode"
									+ " 	inner JOIN productype d on a.productType = d.productTypeCode"
									+ " where a.tin = :tin"
									+ " 	and a.hisDate >= STR_TO_DATE(:hisDateFrom	, '%Y%m%d')"
									+ " 	and a.hisDate <= STR_TO_DATE(:hisDateTo	, '%Y%m%d')";
			
			param.put("tin"			, criteria.getTin());
			param.put("hisDateFrom"	, hisDateFrom);
			param.put("hisDateTo"	, hisDateTo);
			
			if(!criteria.getProductName().equals("")){
				hql += " and b.productName = :productName";
				param.put("productName", criteria.getProductName());
			}
			
			if(!criteria.getProductGroupName().equals("")){
				hql += " and c.productGroupName = :productGroupName";
				param.put("productGroupName", criteria.getProductGroupName());
			}
			
			if(!criteria.getProductTypeName().equals("")){
				hql += " and d.productTypeName = :productTypeName";
				param.put("productTypeName", criteria.getProductTypeName());
			}
			
			hql += " order by a.productType, a.productGroup, a.productCode, a.hisDate";
			
			columnList.add("formRef");
			columnList.add("hisDate");
			columnList.add("productName");
			columnList.add("quantityPlus");
			columnList.add("quantityMinus");
			columnList.add("quantityTotal");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new ProductQuanHistoryBean();
				
				bean.setFormRef			(EnjoyUtils.nullToStr(row.get("formRef")));
				bean.setHisDate			(EnjoyUtils.dateToThaiDisplay(row.get("hisDate")));
				bean.setProductName		(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setQuantityPlus	(EnjoyUtils.convertFloatToDisplay(row.get("quantityPlus"), 2));
				bean.setQuantityMinus	(EnjoyUtils.convertFloatToDisplay(row.get("quantityMinus"), 2));
				bean.setQuantityTotal	(EnjoyUtils.convertFloatToDisplay(row.get("quantityTotal"), 2));
				
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
