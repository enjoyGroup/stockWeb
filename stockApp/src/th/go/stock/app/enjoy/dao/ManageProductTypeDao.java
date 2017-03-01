
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ManageProductTypeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Productype;
import th.go.stock.app.enjoy.model.ProductypePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ManageProductTypeDao extends DaoControl{
	
	public ManageProductTypeDao(){
		setLogger(EnjoyLogger.getLogger(ManageProductTypeDao.class));
		super.init();
	}
	
	public List<ManageProductTypeBean> getProductTypeList(String tin) throws EnjoyException{
		getLogger().info("[getProductTypeList][Begin]");
		
		String							hql							= null;
		ManageProductTypeBean			bean						= null;
		List<ManageProductTypeBean> 	manageProductTypeBeanList 	= new ArrayList<ManageProductTypeBean>();
		int								seq							= 0;
		HashMap<String, Object>			param						= new HashMap<String, Object>();
		List<String>					columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList					= null;
		
		try{	
			hql					= "select * "
								+ "	from productype"
								+ "	where productTypeStatus = 'A'"
								+ "		and tin				= :tin"
								+ "	order by productTypeCodeDis asc";
			
			//Criteria
			param.put("tin"	, tin);

			//Column select
			columnList.add("productTypeCode");
			columnList.add("tin");
			columnList.add("productTypeCodeDis");
			columnList.add("productTypeName");
			columnList.add("productTypeStatus");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ManageProductTypeBean();
				
				bean.setProductTypeCode		(EnjoyUtils.nullToStr(row.get("productTypeCode")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setProductTypeCodeDis	(EnjoyUtils.nullToStr(row.get("productTypeCodeDis")));
				bean.setProductTypeName		(EnjoyUtils.nullToStr(row.get("productTypeName")));
				bean.setProductTypeStatus	(EnjoyUtils.nullToStr(row.get("productTypeStatus")));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				manageProductTypeBeanList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductTypeList");
		}finally{
			hql						= null;
			getLogger().info("[getProductTypeList][End]");
		}
		
		return manageProductTypeBeanList;
		
	}
	
	public void insertProductype(ManageProductTypeBean manageProductTypeBean) throws EnjoyException{
		getLogger().info("[insertProductype][Begin]");
		
		Productype		productype		= new Productype();
		ProductypePK 	id				= new ProductypePK();
		
		try{
			
			id.setProductTypeCode			(EnjoyUtils.parseInt(manageProductTypeBean.getProductTypeCode()));
			id.setTin						(manageProductTypeBean.getTin());
			
			productype.setId				(id);
			productype.setProductTypeCodeDis(manageProductTypeBean.getProductTypeCodeDis());
			productype.setProductTypeName	(manageProductTypeBean.getProductTypeName());
			productype.setProductTypeStatus	(manageProductTypeBean.getProductTypeStatus());
			
			insertData(productype);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertProductype");
		}finally{
			
			productype = null;
			getLogger().info("[insertProductype][End]");
		}
	}
	
	public void updateProductype(ManageProductTypeBean manageProductTypeBean) throws EnjoyException{
		getLogger().info("[updateProductype][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Productype set productTypeName 		= :productTypeName"
											+ "	,productTypeStatus 			= :productTypeStatus"
										+ " where productTypeCode 	= :productTypeCode"
										+ "		and	tin 			= :tin";
			
			query = createQuery(hql);
			query.setParameter("productTypeName"	, manageProductTypeBean.getProductTypeName());
			query.setParameter("productTypeStatus"	, manageProductTypeBean.getProductTypeStatus());
			query.setParameter("productTypeCode"	, EnjoyUtils.parseInt(manageProductTypeBean.getProductTypeCode()));
			query.setParameter("tin"				, manageProductTypeBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateProductype");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateProductype][End]");
		}
	}
	
//	public int checkDupProductTypeCode(String productTypeCodeDis, String tin) throws EnjoyException{
//		getLogger().info("[checkDupProductTypeCode][Begin]");
//		
//		String							hql						= null;
//		int 							result					= 0;
//		HashMap<String, Object>			param					= new HashMap<String, Object>();
//		List<Object>					resultList				= null;
//		
//		try{
//			hql		= "select count(*) cou "
//					+ "	from productype "
//					+ "	where productTypeCodeDis 	= :productTypeCodeDis"
//					+ "		and tin					= :tin"
//					+ "		and productTypeStatus 	= 'A'";
//			
//			//Criteria
//			param.put("productTypeCodeDis"	, productTypeCodeDis);
//			param.put("tin"					, tin);
//
//			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
//			
//			if(resultList!=null && resultList.size() > 0){
//				result = EnjoyUtils.parseInt(resultList.get(0));
//			}
//			
//			getLogger().info("[checkDupProductTypeCode] result 			:: " + result);
//			
//			
//			
//		}catch(Exception e){
//			getLogger().info(e.getMessage());
//			throw new EnjoyException(e.getMessage());
//		}finally{
//			hql									= null;
//			getLogger().info("[checkDupProductTypeCode][End]");
//		}
//		
//		return result;
//	}
	
	public int checkDupProductTypeName(String productTypeName, String tin) throws EnjoyException{
		getLogger().info("[checkDupProductTypeCode][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql		= "select count(*) cou "
					+ "	from productype "
					+ "	where productTypeName 		= :productTypeName"
					+ "		and tin					= :tin"
					+ "		and productTypeStatus 	= 'A'";
			
			//Criteria
			param.put("productTypeName"	, productTypeName);
			param.put("tin"				, tin);

			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupProductTypeCode] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			hql									= null;
			getLogger().info("[checkDupProductTypeCode][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productTypeNameList(String productTypeName, String tin){
		getLogger().info("[productTypeNameList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList	=  new ArrayList<ComboBean>();
			hql 		= "select productTypeCode, productTypeName "
						+ "	from productype "
						+ "	where productTypeName LIKE CONCAT(:productTypeName, '%')"
						+ "		and	tin					= :tin"
						+ "		and productTypeStatus 	= 'A' "
						+ "	order by productTypeName asc limit 10 ";
			
			//Criteria
			param.put("productTypeName"	, productTypeName);
			param.put("tin"				, tin);

			//Column select
			columnList.add("productTypeCode");
			columnList.add("productTypeName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("productTypeCode")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("productTypeName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
		}finally{
			getLogger().info("[productTypeNameList][End]");
		}
		
		return comboList;
	}
	
	public String getProductTypeCode(String productTypeName, String tin){
		getLogger().info("[getProductTypeCode][Begin]");
		
		String 							hql			 		= null;
        String							productTypeCode		= null;
        HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql 	= "select productTypeCode "
					+ "	from productype "
					+ "	where productTypeStatus = 'A' "
					+ "		and productTypeName = :productTypeName"
					+ "		and tin				= :tin";
			
			//Criteria
			param.put("productTypeName"	, productTypeName);
			param.put("tin"				, tin);

			resultList = getResult(hql, param, "productTypeCode", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				productTypeCode = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
		}finally{
			getLogger().info("[getProductTypeCode][End]");
		}
		
		return productTypeCode;
	}
	
	public int genId(String tin) throws EnjoyException{
		getLogger().info("[genId][Begin]");
		
		String							hql				= null;
		int 							result			= 1;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select (max(productTypeCode) + 1) newId"
					+ "	from productype "
					+ "	where tin		= :tin";
			
			//Criteria
			param.put("tin"		, tin);
			
			resultList = getResult(hql, param, "newId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0))==0?1:EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[genId] newId 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("genId error");
		}finally{
			hql									= null;
			getLogger().info("[genId][End]");
		}
		
		return result;
	}
	
}


