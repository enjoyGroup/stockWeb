
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductdetailBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Productdetail;
import th.go.stock.app.enjoy.model.ProductdetailPK;
import th.go.stock.app.enjoy.model.Productmaster;
import th.go.stock.app.enjoy.model.ProductmasterPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ProductDetailsDao extends DaoControl{
	
	public ProductDetailsDao(){
		setLogger(EnjoyLogger.getLogger(ProductDetailsDao.class));
		super.init();
	}
	
	public List<ProductmasterBean> searchByCriteria(ProductmasterBean productDetailsBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		ProductmasterBean				bean					= null;
		List<ProductmasterBean> 		productDetailsList 		= new ArrayList<ProductmasterBean>();
		int								chkBoxSeq				= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select a.*, b.productTypeName, c.productGroupName "
								+ "	from productmaster a"
								+ "		inner join productype b on b.productTypeCode = a.productType and b.tin = a.tin"
								+ "		inner join productgroup c on c.productTypeCode 	= a.productType and c.productGroupCode = a.productGroup and c.tin = a.tin"
								+ "	where a.tin	= :tin";
			
			//Criteria
			param.put("tin"	, productDetailsBean.getTin());
			
			if(!productDetailsBean.getProductTypeName().equals("")){
				hql += " and b.productTypeName LIKE CONCAT(:productTypeName, '%')";
				param.put("productTypeName"	, productDetailsBean.getProductTypeName());
			}
			if(!productDetailsBean.getProductGroupName().equals("")){
				hql += " and c.productGroupName LIKE CONCAT(:productGroupName, '%')";
				param.put("productGroupName"	, productDetailsBean.getProductGroupName());
			}
			if(!productDetailsBean.getProductName().equals("")){
				hql += " and a.productName LIKE CONCAT(:productName, '%')";
				param.put("productName"	, productDetailsBean.getProductName());
			}
			
			//Column select
			columnList.add("productCode");
			columnList.add("tin");
			columnList.add("productType");
			columnList.add("productGroup");
			columnList.add("productName");
			columnList.add("unitCode");
			columnList.add("minQuan");
			columnList.add("costPrice");
			columnList.add("salePrice1");
			columnList.add("salePrice2");
			columnList.add("salePrice3");
			columnList.add("salePrice4");
			columnList.add("salePrice5");
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ProductmasterBean();
				
				bean.setProductCode				(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setProductTypeCode			(EnjoyUtils.nullToStr(row.get("productType")));
				bean.setProductGroupCode		(EnjoyUtils.nullToStr(row.get("productGroup")));
				bean.setProductName				(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setUnitCode				(EnjoyUtils.nullToStr(row.get("unitCode")));
				bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row.get("minQuan"), 2));
				bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
				bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice1"), 2));
				bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice2"), 2));
				bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice3"), 2));
				bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice4"), 2));
				bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice5"), 2));
				bean.setProductTypeName			(EnjoyUtils.nullToStr(row.get("productTypeName")));
				bean.setProductGroupName		(EnjoyUtils.nullToStr(row.get("productGroupName")));
				bean.setChkBoxSeq				(String.valueOf(chkBoxSeq));
				
				productDetailsList.add(bean);
				chkBoxSeq++;
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return productDetailsList;
		
	}
	
	public ProductmasterBean getProductDetail(ProductmasterBean 		productDetailsBean) throws EnjoyException{
		getLogger().info("[getProductDetail][Begin]");
		
		String							hql						= null;
		ProductmasterBean				bean					= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{		
			hql		= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
					+ "	from productmaster a, productype b, productgroup c, unittype d"
					+ "	where b.productTypeCode 	= a.productType"
					+ "		and b.tin				= a.tin"
					+ " 	and c.productTypeCode 	= a.productType"
					+ " 	and c.productGroupCode 	= a.productGroup"
					+ "		and c.tin				= a.tin"
					+ " 	and d.unitCode 			= a.unitCode"
					+ "		and d.tin				= a.tin"
					+ " 	and a.productCode		= :productCode"
					+ "		and a.tin				= :tin";
			
			//Criteria
			param.put("productCode"	, productDetailsBean.getProductCode());
			param.put("tin"			, productDetailsBean.getTin());
			
			//Column select
			columnList.add("productCode");
			columnList.add("tin");
			columnList.add("productType");
			columnList.add("productGroup");
			columnList.add("productName");
			columnList.add("unitCode");
			columnList.add("minQuan");
			columnList.add("costPrice");
			columnList.add("salePrice1");
			columnList.add("salePrice2");
			columnList.add("salePrice3");
			columnList.add("salePrice4");
			columnList.add("salePrice5");
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			columnList.add("unitName");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new ProductmasterBean();
					
					bean.setProductCode				(EnjoyUtils.nullToStr(row.get("productCode")));
					bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setProductTypeCode			(EnjoyUtils.nullToStr(row.get("productType")));
					bean.setProductGroupCode		(EnjoyUtils.nullToStr(row.get("productGroup")));
					bean.setProductName				(EnjoyUtils.nullToStr(row.get("productName")));
					bean.setUnitCode				(EnjoyUtils.nullToStr(row.get("unitCode")));
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row.get("minQuan"), 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
					bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice1"), 2));
					bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice2"), 2));
					bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice3"), 2));
					bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice4"), 2));
					bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice5"), 2));
					bean.setProductTypeName			(EnjoyUtils.nullToStr(row.get("productTypeName")));
					bean.setProductGroupName		(EnjoyUtils.nullToStr(row.get("productGroupName")));
					bean.setUnitName				(EnjoyUtils.nullToStr(row.get("unitName")));
					
				}	
			}
			
			
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductDetail");
		}finally{
			hql						= null;
			getLogger().info("[getProductDetail][End]");
		}
		
		return bean;
		
	}
	
	public void insertProductmaster(ProductmasterBean productmasterBean) throws EnjoyException{
		getLogger().info("[insertProductmaster][Begin]");
		
		Productmaster	productmaster	= new Productmaster();
		ProductmasterPK id				= new ProductmasterPK();
		
		try{
			
			id.setProductCode	(productmasterBean.getProductCode());
			id.setTin			(productmasterBean.getTin());
			
			productmaster.setId				(id);
			productmaster.setProductType	(productmasterBean.getProductTypeCode());
			productmaster.setProductGroup	(productmasterBean.getProductGroupCode());
			productmaster.setProductName	(productmasterBean.getProductName());
			productmaster.setUnitCode		(EnjoyUtils.parseInt(productmasterBean.getUnitCode()));
			productmaster.setMinQuan		(EnjoyUtils.parseBigDecimal(productmasterBean.getMinQuan()));
			productmaster.setCostPrice		(EnjoyUtils.parseBigDecimal(productmasterBean.getCostPrice()));
			productmaster.setSalePrice1		(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice1()));
			productmaster.setSalePrice2		(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice2()));
			productmaster.setSalePrice3		(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice3()));
			productmaster.setSalePrice4		(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice4()));
			productmaster.setSalePrice5		(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice5()));
			
			insertData(productmaster);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertProductmaster");
		}finally{
			
			productmaster = null;
			getLogger().info("[insertProductmaster][End]");
		}
	}
	
	public void updateProductmaster(ProductmasterBean productmasterBean) throws EnjoyException{
		getLogger().info("[updateProductmaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Productmaster t set t.productType 	= :productType"
												+ ", t.productGroup			= :productGroup"
												+ ", t.productName			= :productName"
												+ ", t.unitCode				= :unitCode"
												+ ", t.minQuan				= :minQuan"
												+ ", t.costPrice			= :costPrice"
												+ ", t.salePrice1 			= :salePrice1"
												+ ", t.salePrice2 			= :salePrice2"
												+ ", t.salePrice3 			= :salePrice3"
												+ ", t.salePrice4 			= :salePrice4"
												+ ", t.salePrice5 			= :salePrice5"
										+ " where t.id.productCode 	= :productCode"
										+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
			query.setParameter("productType"		, productmasterBean.getProductTypeCode());
			query.setParameter("productGroup"		, productmasterBean.getProductGroupCode());
			query.setParameter("productName"		, productmasterBean.getProductName());
			query.setParameter("unitCode"			, EnjoyUtils.parseInt(productmasterBean.getUnitCode()));
			query.setParameter("minQuan"			, EnjoyUtils.parseBigDecimal(productmasterBean.getMinQuan()));
			query.setParameter("costPrice"			, EnjoyUtils.parseBigDecimal(productmasterBean.getCostPrice()));
			query.setParameter("salePrice1"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice1()));
			query.setParameter("salePrice2"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice2()));
			query.setParameter("salePrice3"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice3()));
			query.setParameter("salePrice4"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice4()));
			query.setParameter("salePrice5"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice5()));
			query.setParameter("productCode"		, productmasterBean.getProductCode());
			query.setParameter("tin"				, productmasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateProductmaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateProductmaster][End]");
		}
	}
	
	public int checkDupProductCode(String productCode, String tin) throws EnjoyException{
		getLogger().info("[checkDupProductCode][Begin]");
		
		String							hql				= null;
		int 							result			= 0;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select count(*) cou "
					+ "	from productmaster "
					+ "	where productCode 	= :productCode"
					+ "		and tin 		= :tin";
			
			//Criteria
			param.put("productCode"	, productCode);
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupProductCode] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("checkDupProductCode error");
		}finally{
			hql									= null;
			getLogger().info("[checkDupProductCode][End]");
		}
		
		return result;
	}
	
	public int checkDupProductName(String productName, String productCode, String tin, String pageMode) throws EnjoyException{
		getLogger().info("[checkDupProductName][Begin]");
		
		String							hql				= null;
		int 							result			= 0;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql	= "select count(*) cou "
				+ "	from productmaster "
				+ "	where productName 	= :productName"
				+ "		and tin 		= :tin";
			
			//Criteria
			param.put("productName"	, productName);
			param.put("tin"			, tin);
			
			if(pageMode.equals(ProductDetailsMaintananceForm.EDIT)){
				hql += " and productCode <> :productCode";
				param.put("productCode"	, productCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupProductName] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("checkDupProductName error");
		}finally{
			hql									= null;
			getLogger().info("[checkDupProductName][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productNameList(String productName, String productTypeName, String productGroupName, String tin, boolean flag) throws EnjoyException{
		getLogger().info("[productNameList][Begin]");
		
		String 							hql			 		= null;
        String							productTypeCode		= null;
        String							productGroupCode	= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		List<Object>					listObj				= null;
		
		try{
			comboList			=  new ArrayList<ComboBean>();
			
			if(productTypeName!=null && productGroupName!=null){
				/*Begin check ProductType section*/
				hql = "select productTypeCode "
					+ "	from productype "
					+ "	where productTypeName 		= :productTypeName"
					+ "		and tin					= :tin"
					+ "		and productTypeStatus 	= 'A'";
				
				//Criteria
				param.put("productTypeName"	, productTypeName);
				param.put("tin"				, tin);
				
				listObj = getResult(hql, param, "productTypeCode", Constants.STRING_TYPE);
				
				if(listObj!=null && listObj.size() == 1){
					productTypeCode = EnjoyUtils.nullToStr(listObj.get(0));
				}
			    /*End check ProductType section*/
				
				/*Begin check ProductGroup section*/
			    if(productTypeCode!=null){
			    	param		= new HashMap<String, Object>();
			    	
					hql 	= "select productGroupCode "
							+ "	from productgroup "
							+ "	where productTypeCode 		= :productTypeCode"
							+ "		and tin					= :tin"
							+ "		and productGroupStatus 	= 'A'";
					
					//Criteria
					param.put("productTypeCode"	, productTypeCode);
					param.put("tin"				, tin);
					
					listObj = getResult(hql, param, "productGroupCode", Constants.STRING_TYPE);
					
					if(listObj!=null && listObj.size() == 1){
						productGroupCode = EnjoyUtils.nullToStr(listObj.get(0));
					}
			    }
			    /*End check ProductGroup section*/
			}
		    
		    hql 		= "";
		    param		= new HashMap<String, Object>();
		    
		    getLogger().info("[productNameList] productTypeCode  :: " + productTypeCode);
		    getLogger().info("[productNameList] productGroupCode :: " + productGroupCode);
			
		    if(productTypeCode!=null && productGroupCode!=null){
		    	hql = " select productCode, productName"
		    			+ " from productmaster"
		    			+ " where productType 		= :productTypeCode"
		    			+ " 	and productGroup 	= :productGroupCode"
		    			+ " 	and tin 			= :tin"
		    			+ " 	and productName LIKE CONCAT(:productName, '%')"
		    			+ " order by productName asc limit 10";
		    	
		    	//Criteria
				param.put("productTypeCode"	, productTypeCode);
				param.put("productGroupCode", productGroupCode);
				param.put("tin"				, tin);
				param.put("productName"		, productName);
				
		    }else{
		    	if(flag==true){
		    		hql = "select productCode, productName"
			    		+ " from productmaster where tin = :tin";
		    		
		    		param.put("tin"				, tin);
		    		
		    		if(productTypeCode!=null){
		    			hql+= " and productType 	= :productTypeCode";
		    			param.put("productTypeCode"	, productTypeCode);
		    		}
		    		if(productGroupCode!=null){
		    			hql+= " and productGroup 	= :productGroupCode";
		    			param.put("productGroupCode", productGroupCode);
		    		}
		    		
		    		hql += " and productName LIKE CONCAT(:productName, '%')";
		    		hql += " order by productName asc limit 10";
		    		
		    		param.put("productName"		, productName);
		    		
		    	}
		    }
		    
	    	if(!hql.equals("")){
	    		//Column select
				columnList.add("productCode");
				columnList.add("productName");
				
				resultList = getResult(hql, param, columnList);
				
				for(HashMap<String, Object> row:resultList){
					comboBean 	= new ComboBean();
					
					comboBean.setCode	(EnjoyUtils.nullToStr(row.get("productCode")));
					comboBean.setDesc	(EnjoyUtils.nullToStr(row.get("productName")));
					
					comboList.add(comboBean);
				}
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("productNameList error");
		}finally{
			getLogger().info("[productNameList][End]");
		}
		
		return comboList;
	}
	
	public List<ProductdetailBean> getProductdetailList(ProductdetailBean productdetailBean) throws EnjoyException{
		getLogger().info("[getProductdetailList][Begin]");
		
		String							hql					= null;
		ProductdetailBean				bean				= null;
		List<ProductdetailBean> 		productdetailList 	= new ArrayList<ProductdetailBean>();
		int								seq					= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{	
			hql					= "select * "
								+ "	from productdetail"
								+ "	where productCode 	= :productCode"
								+ "		and tin 		= :tin"
								+ "	order by seq asc";
			
			//Criteria
			param.put("productCode"	, productdetailBean.getProductCode());
			param.put("tin"			, productdetailBean.getTin());
			
			//Column select
			columnList.add("productCode");
			columnList.add("seq");
			columnList.add("quanDiscount");
			columnList.add("discountRate");
			columnList.add("startDate");
			columnList.add("expDate");
			
			resultList = getResult(hql, param, columnList);

			for(HashMap<String, Object> row:resultList){
				bean 	= new ProductdetailBean();
				
				bean.setProductCode			(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row.get("seq")));
				bean.setQuanDiscount		(EnjoyUtils.convertFloatToDisplay(row.get("quanDiscount"), 2));
				bean.setDiscountRate		(EnjoyUtils.convertFloatToDisplay(row.get("discountRate"), 2));
				bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row.get("startDate")));
				bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row.get("expDate")));
				bean.setSeq					(String.valueOf(seq));
				
				productdetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductdetailList");
		}finally{
			hql						= null;
			getLogger().info("[getProductdetailList][End]");
		}
		
		return productdetailList;
		
	}
	
	public void insertProductdetail(ProductdetailBean productdetailBean) throws EnjoyException{
		getLogger().info("[insertProductdetail][Begin]");
		
		Productdetail			productdetail		= new Productdetail();
		ProductdetailPK 		id 					= new ProductdetailPK();
		
		try{
			id.setProductCode	(productdetailBean.getProductCode());
			id.setTin			(productdetailBean.getTin());
			id.setSeq			(EnjoyUtils.parseInt(productdetailBean.getSeqDb()));
			
			productdetail.setId					(id);
			productdetail.setQuanDiscount		(EnjoyUtils.parseBigDecimal(productdetailBean.getQuanDiscount()));
			productdetail.setDiscountRate		(EnjoyUtils.parseBigDecimal(productdetailBean.getDiscountRate()));
			productdetail.setStartDate			(EnjoyUtils.dateToThaiDB(productdetailBean.getStartDate()));
			productdetail.setExpDate			(EnjoyUtils.dateToThaiDB(productdetailBean.getExpDate()));
			
			insertData(productdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertProductdetail");
		}finally{
			id 				= null;
			productdetail 	= null;
			getLogger().info("[insertProductdetail][End]");
		}
	}
	
	public void deleteProductdetail(String productCode, String tin) throws EnjoyException{
		getLogger().info("[deleteProductdetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Productdetail t"
							+ " where t.id.productCode	= :productCode"
							+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
			query.setParameter("productCode", productCode);
			query.setParameter("tin"		, tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteProductdetail][End]");
		}
	}

	
	public ProductmasterBean getProductDetailByName(String 	productName, String tin) throws EnjoyException{
		getLogger().info("[getProductDetailByName][Begin]");
		
		String							hql					= null;
		ProductmasterBean				bean				= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{		
			hql		= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
					+ "	from productmaster a"
					+ "		inner join productype b ON b.productTypeCode = a.productType and b.tin	= a.tin"
					+ "		inner join productgroup c on c.productTypeCode = a.productType and c.productGroupCode = a.productGroup and c.tin	= a.tin"
					+ " 	inner JOIN unittype d on d.unitCode = a.unitCode and d.tin= a.tin "
					+ " where a.productName		= :productName"
					+ "		and a.tin			= :tin";
			
			//Criteria
			param.put("productName"	, productName);
			param.put("tin"			, tin);
			
			//Column select
			columnList.add("productCode");
			columnList.add("productType");
			columnList.add("productGroup");
			columnList.add("productName");
			columnList.add("unitCode");
			columnList.add("minQuan");
			columnList.add("costPrice");
			columnList.add("salePrice1");
			columnList.add("salePrice2");
			columnList.add("salePrice3");
			columnList.add("salePrice4");
			columnList.add("salePrice5");
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			columnList.add("unitName");
			
			resultList = getResult(hql, param, columnList);

			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new ProductmasterBean();
					
					bean.setProductCode				(EnjoyUtils.nullToStr(row.get("productCode")));
					bean.setProductTypeCode			(EnjoyUtils.nullToStr(row.get("productType")));
					bean.setProductGroupCode		(EnjoyUtils.nullToStr(row.get("productGroup")));
					bean.setProductName				(EnjoyUtils.nullToStr(row.get("productName")));
					bean.setUnitCode				(EnjoyUtils.nullToStr(row.get("unitCode")));
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row.get("minQuan"), 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
					bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice1"), 2));
					bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice2"), 2));
					bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice3"), 2));
					bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice4"), 2));
					bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice5"), 2));
					bean.setProductTypeName			(EnjoyUtils.nullToStr(row.get("productTypeName")));
					bean.setProductGroupName		(EnjoyUtils.nullToStr(row.get("productGroupName")));
					bean.setUnitName				(EnjoyUtils.nullToStr(row.get("unitName")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getProductDetailByName");
		}finally{
			hql					= null;
			getLogger().info("[getProductDetailByName][End]");
		}
		
		return bean;
		
	}
	
	public String getQuanDiscount(String productCode, String quantity, String invoiceDate, String tin) throws EnjoyException{
		getLogger().info("[getQuanDiscount][Begin]");
		
		String							hql					= null;
		String 							discountRate		= "0.00";
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			invoiceDate			= invoiceDate.equals("")?EnjoyUtils.currDateThai():EnjoyUtils.dateThaiToDb(invoiceDate);
			quantity			= quantity.equals("")?"0.00":quantity;
			
			hql		= "select discountRate from productdetail"
					+ "		where productCode 		= :productCode"
					+ "			and quanDiscount 	<= :quantity"
					+ "			and startDate 		<= :startDate"
					+ "			and (expDate is null or expDate = '' or expDate >= :expDate)"
					+ "			and tin				= :tin"
					+ "		order by quanDiscount ASC, startDate DESC"
					+ "		LIMIT 1";
			
			//Criteria
			param.put("productCode"	, productCode);
			param.put("quantity"	, quantity);
			param.put("startDate"	, invoiceDate);
			param.put("expDate"		, invoiceDate);
			param.put("tin"			, tin);

			resultList = getResult(hql, param, "discountRate", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				discountRate = EnjoyUtils.convertFloatToDisplay(resultList.get(0), 2);
			}
			
			getLogger().info("[getQuanDiscount] discountRate 			:: " + discountRate);
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[getQuanDiscount][End]");
		}
		
		return discountRate;
	}
	
	
	public List<ProductmasterBean> getMultiManageProduct(ProductmasterBean productDetailsBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		ProductmasterBean				bean					= null;
		List<ProductmasterBean> 		productDetailsList 		= new ArrayList<ProductmasterBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
								+ "	from productmaster a"
								+ "		inner join productype b on b.productTypeCode = a.productType and b.tin = a.tin"
								+ "		inner join productgroup c on c.productTypeCode 	= a.productType and c.productGroupCode = a.productGroup and c.tin = a.tin"
								+ "		inner join unittype d on d.unitCode = a.unitCode and d.tin = a.tin"
								+ "	where a.tin					= :tin"
								+ "		and b.productTypeName 	= :productTypeName"
								+ "		and c.productGroupName 	= :productGroupName"
								+ "		and d.unitName			= :unitName";
			
			//Criteria
			param.put("tin"				, productDetailsBean.getTin());
			param.put("productTypeName"	, productDetailsBean.getProductTypeName());
			param.put("productGroupName", productDetailsBean.getProductGroupName());
			param.put("unitName"		, productDetailsBean.getUnitName());
			
			//Column select
			columnList.add("productCode");
			columnList.add("tin");
			columnList.add("productType");
			columnList.add("productGroup");
			columnList.add("productName");
			columnList.add("unitCode");
			columnList.add("minQuan");
			columnList.add("costPrice");
			columnList.add("salePrice1");
			columnList.add("salePrice2");
			columnList.add("salePrice3");
			columnList.add("salePrice4");
			columnList.add("salePrice5");
			columnList.add("productTypeName");
			columnList.add("productGroupName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ProductmasterBean();
				
				bean.setProductCode				(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setProductTypeCode			(EnjoyUtils.nullToStr(row.get("productType")));
				bean.setProductGroupCode		(EnjoyUtils.nullToStr(row.get("productGroup")));
				bean.setProductName				(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setUnitCode				(EnjoyUtils.nullToStr(row.get("unitCode")));
				bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row.get("minQuan"), 2));
				bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
				bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice1"), 2));
				bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice2"), 2));
				bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice3"), 2));
				bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice4"), 2));
				bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row.get("salePrice5"), 2));
				bean.setProductTypeName			(EnjoyUtils.nullToStr(row.get("productTypeName")));
				bean.setProductGroupName		(EnjoyUtils.nullToStr(row.get("productGroupName")));
				
				productDetailsList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return productDetailsList;
		
	}

}
