
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ManageProductGroupBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Productgroup;
import th.go.stock.app.enjoy.model.ProductgroupPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ManageProductGroupDao extends DaoControl{
	
	public ManageProductGroupDao(){
		setLogger(EnjoyLogger.getLogger(ManageProductGroupDao.class));
		super.init();
	}
	
	public List<ManageProductGroupBean> getProductGroupList(ManageProductGroupBean 	manageProductGroupBean) throws EnjoyException{
		getLogger().info("[getProductGroupList][Begin]");
		
		String							hql							= null;
		ManageProductGroupBean			bean						= null;
		List<ManageProductGroupBean> 	manageProductGroupBeanList 	= new ArrayList<ManageProductGroupBean>();
		int								seq							= 0;
		HashMap<String, Object>			param						= new HashMap<String, Object>();
		List<String>					columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList					= null;
		
		try{	
			hql					= "select * "
								+ "	from productgroup"
								+ "	where productTypeCode 		= :productTypeCode"
								+ "		and tin					= :tin"
								+ "		and productGroupStatus 	= 'A' "
								+ "	order by productGroupCode asc";
			
			//Criteria
			param.put("productTypeCode"	, manageProductGroupBean.getProductTypeCode());
			param.put("tin"				, manageProductGroupBean.getTin());

			//Column select
			columnList.add("productTypeCode");
			columnList.add("productGroupCode");
			columnList.add("tin");
			columnList.add("productGroupName");
			columnList.add("productGroupStatus");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ManageProductGroupBean();
				
				bean.setProductTypeCode		(EnjoyUtils.nullToStr(row.get("productTypeCode")));
				bean.setProductGroupCode	(EnjoyUtils.nullToStr(row.get("productGroupCode")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setProductGroupName	(EnjoyUtils.nullToStr(row.get("productGroupName")));
				bean.setProductGroupStatus	(EnjoyUtils.nullToStr(row.get("productGroupStatus")));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				manageProductGroupBeanList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductGroupList");
		}finally{
			hql						= null;
			getLogger().info("[getProductGroupList][End]");
		}
		
		return manageProductGroupBeanList;
		
	}
	
	public void insertProducGroup(ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		getLogger().info("[insertProducGroup][Begin]");
		
		Productgroup	productgroup	= null;
		ProductgroupPK	pk				= null;
		
		try{
			
			productgroup 	= new Productgroup();
			pk				= new ProductgroupPK();
			
			pk.setProductTypeCode	(manageProductGroupBean.getProductTypeCode());
			pk.setProductGroupCode	(manageProductGroupBean.getProductGroupCode());
			pk.setTin				(manageProductGroupBean.getTin());
			
			productgroup.setId(pk);
			productgroup.setProductGroupName(manageProductGroupBean.getProductGroupName());
			productgroup.setProductGroupStatus(manageProductGroupBean.getProductGroupStatus());
			
			insertData(productgroup);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertProducGroup");
		}finally{
			
			productgroup 	= null;
			pk 				= null;
			getLogger().info("[insertProducGroup][End]");
		}
	}
	
	public void updateProductgroup(ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		getLogger().info("[updateProductgroup][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Productgroup t set "
							+ "		t.productGroupName 		= :productGroupName"
							+ "		,t.productGroupStatus 	= :productGroupStatus"
							+ " where t.id.productTypeCode 		= :productTypeCode"
							+ " 	and t.id.productGroupCode 	= :productGroupCode"
							+ "		and t.id.tin				= :tin";
			
			query = createQuery(hql);
			query.setParameter("productGroupName"	, manageProductGroupBean.getProductGroupName());
			query.setParameter("productGroupStatus"	, manageProductGroupBean.getProductGroupStatus());
			query.setParameter("productTypeCode"	, manageProductGroupBean.getProductTypeCode());
			query.setParameter("productGroupCode"	, manageProductGroupBean.getProductGroupCode());
			query.setParameter("tin"				, manageProductGroupBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateProductgroup");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateProductgroup][End]");
		}
	}
	
	public int checkDupProductGroupCode(ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		getLogger().info("[checkDupProductGroupCode][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql				= "Select count(*) cou "
							+ " from productgroup "
							+ " where productTypeCode 			= :productTypeCode"
									+ " and productGroupCode 	= :productGroupCode"
									+ "	and tin					= :tin"
									+ " and productGroupStatus 	= 'A'";
			
			//Criteria
			param.put("productTypeCode"		, manageProductGroupBean.getProductTypeCode());
			param.put("productGroupCode"	, manageProductGroupBean.getProductGroupCode());
			param.put("tin"					, manageProductGroupBean.getTin());
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupProductGroupCode] result 			:: " + result);
			
			
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql		= null;
			getLogger().info("[checkDupProductGroupCode][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productGroupNameList(String productTypeName, String productGroupName, String tin, boolean flag){
		getLogger().info("[productGroupNameList][Begin]");
		
		String 							hql			 		= null;
        String							productTypeCode		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		List<Object>					listObj				= null;
		
		try{
			comboList			=  new ArrayList<ComboBean>();
			
			/*Begin check ProductType section*/
			hql 				= "select productTypeCode "
								+ "	from productype "
								+ "	where productTypeName 		= :productTypeName"
								+ "		and tin					= :tin"
								+ "		and productTypeStatus 	= 'A'";
			
			//Criteria
			param.put("productTypeName"	, productTypeName);
			param.put("tin"				, tin);
			
			listObj = getResult(hql, param, "productTypeCode", Constants.STRING_TYPE);
			
			if(listObj!=null && listObj.size()==1){
				productTypeCode = EnjoyUtils.nullToStr(listObj.get(0));
			}
		    /*End check ProductType section*/
		    
			hql 		= "";
			param		= new HashMap<String, Object>();
			
		    if(productTypeCode!=null){
		    	hql = " select productGroupCode, productGroupName"
		    			+ " from productgroup"
		    			+ " where productTypeCode 			= :productTypeCode"
		    					+ " and productGroupName LIKE CONCAT(:productGroupName, '%')"
		    					+ "	and tin					= :tin"
		    					+ " and productGroupStatus 	= 'A'"
		    			+ " order by productGroupName asc limit 10 ";
		    	
		    	param.put("productTypeCode"		, productTypeCode);
		    	param.put("productGroupName"	, productGroupName);
		    	param.put("tin"					, tin);
		    	
		    }else{
		    	if(flag==true){
		    		hql = " select productGroupCode, productGroupName"
			    			+ " from productgroup"
			    			+ " where productGroupName LIKE CONCAT(:productGroupName, '%')"
			    			+ " 	and tin 				= :tin"
			    			+ " 	and productGroupStatus 	= 'A'"
			    			+ " order by productGroupName asc limit 10 ";
		    		
		    		param.put("productGroupName"	, productGroupName);
		    		param.put("tin"					, tin);
		    	}
		    }
		    
		    getLogger().info("[productGroupNameList] hql :: " + hql);
		    
		    if(!hql.equals("")){
		    	//Column select
				columnList.add("productGroupCode");
				columnList.add("productGroupName");
		    	
				resultList = getResult(hql, param, columnList);
				
				for(HashMap<String, Object> row:resultList){
					comboBean 	= new ComboBean();
					
					comboBean.setCode				(EnjoyUtils.nullToStr(row.get("productGroupCode")));
					comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("productGroupName")));
					
					comboList.add(comboBean);
				}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
		}finally{
			getLogger().info("[productGroupNameList][End]");
		}
		
		return comboList;
	}
	
	public String getProductGroupCode(String productTypeCode, String productGroupName, String tin){
		getLogger().info("[getProductGroupCode][Begin]");
		
		String 							hql			 		= null;
		String							productGroupCode	= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql 		= " select productGroupCode"
							+ "	from productgroup"
							+ " where productGroupStatus 	= 'A' "
							+ " 	and productGroupName 	= :productGroupName"
							+ "		and productTypeCode 	= :productTypeCode"
							+ "		and tin					= :tin";
			
			//Criteria
			param.put("productGroupName"	, productGroupName);
			param.put("productTypeCode"		, productTypeCode);
			param.put("tin"					, tin);
			
			resultList = getResult(hql, param, "productGroupCode", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				productGroupCode = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[getProductGroupCode][End]");
		}
		
		return productGroupCode;
	}


	
}
