
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsLookUpForm;
import th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm;
import th.go.stock.app.enjoy.model.Product;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ProductDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDetailsDao.class);
	
	public List<ProductDetailsBean> searchByCriteria(	Session 				session, 
														ProductDetailsBean 		productDetailsBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductDetailsBean			bean					= null;
		List<ProductDetailsBean> 	productDetailsList 		= new ArrayList<ProductDetailsBean>();
		int							chkBoxSeq				= 0;
		
		try{	
			hql					= "select a.*, b.productTypeName, c.productGroupName "
								+ "	from product a, productype b, productgroup c"
								+ "	where b.productTypeCode = a.productType"
									+ " and c.productTypeCode = a.productType"
									+ " and c.productGroupCode = a.productGroup";
			
			if(!productDetailsBean.getProductTypeName().equals("")){
				hql += " and b.productTypeName like ('" + productDetailsBean.getProductTypeName() + "%')";
			}
			if(!productDetailsBean.getProductGroupName().equals("")){
				hql += " and c.productGroupName like ('" + productDetailsBean.getProductGroupName() + "%')";
			}
			if(!productDetailsBean.getProductName().equals("")){
				hql += " and a.productName like ('" + productDetailsBean.getProductName() + "%')";
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productType"		, new StringType());
			query.addScalar("productGroup"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("unitCode"			, new StringType());
			query.addScalar("minQuan"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("quantity"			, new StringType());
			query.addScalar("productStatus"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ProductDetailsBean();
				
				logger.info("productCode 			:: " + row[0].toString());
				logger.info("productType 			:: " + row[1].toString());
				logger.info("productGroup 			:: " + row[2].toString());
				logger.info("productName 			:: " + row[3].toString());
				logger.info("unitCode 				:: " + row[4].toString());
				logger.info("minQuan 				:: " + row[5].toString());
				logger.info("costPrice 				:: " + row[6].toString());
				logger.info("salePrice 				:: " + row[7].toString());
				logger.info("startDate 				:: " + row[8].toString());
				logger.info("expDate 				:: " + row[9].toString());
				logger.info("quantity 				:: " + row[10].toString());
				logger.info("productStatus 			:: " + row[11].toString());
				logger.info("productTypeName 		:: " + row[12].toString());
				logger.info("productGroupName 		:: " + row[13].toString());
				
				bean.setProductCode				(row[0].toString());
				bean.setProductTypeCode			(row[1].toString());
				bean.setProductGroupCode		(row[2].toString());
				bean.setProductName				(row[3].toString());
				bean.setUnitCode				(row[4].toString());
				bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5].toString(), 2));
				bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6].toString(), 2));
				bean.setSalePrice				(EnjoyUtils.convertFloatToDisplay(row[7].toString(), 2));
				bean.setStartDate				(EnjoyUtils.dateFormat(row[8].toString(), "yyyyMMdd", "dd/MM/yyyy"));
				bean.setExpDate					(EnjoyUtils.dateFormat(row[9].toString(), "yyyyMMdd", "dd/MM/yyyy"));
				bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[10].toString(), 2));
				bean.setProductStatus			(row[11].toString());
				bean.setProductTypeName			(row[12].toString());
				bean.setProductGroupName		(row[13].toString());
				bean.setChkBoxSeq				(String.valueOf(chkBoxSeq));
				
				productDetailsList.add(bean);
				chkBoxSeq++;
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return productDetailsList;
		
	}

	
	public ProductDetailsBean getProductDetail(	Session 				session, 
												ProductDetailsBean 		productDetailsBean) throws EnjoyException{
		logger.info("[getProductDetail][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductDetailsBean			bean					= null;
		
		try{		
			hql		= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
					+ "	from product a, productype b, productgroup c, unittype d"
					+ "	where b.productTypeCode 	= a.productType"
						+ " and c.productTypeCode 	= a.productType"
						+ " and c.productGroupCode 	= a.productGroup"
						+ " and d.unitCode 			= a.unitCode"
						+ " and a.productCode		= '" + productDetailsBean.getProductCode() + "'";
			
			logger.info("[getProductDetail] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productType"		, new StringType());
			query.addScalar("productGroup"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("unitCode"			, new StringType());
			query.addScalar("minQuan"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("quantity"			, new StringType());
			query.addScalar("productStatus"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			query.addScalar("unitName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductDetail] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new ProductDetailsBean();
					
					logger.info("productCode 			:: " + row[0].toString());
					logger.info("productType 			:: " + row[1].toString());
					logger.info("productGroup 			:: " + row[2].toString());
					logger.info("productName 			:: " + row[3].toString());
					logger.info("unitCode 				:: " + row[4].toString());
					logger.info("minQuan 				:: " + row[5].toString());
					logger.info("costPrice 				:: " + row[6].toString());
					logger.info("salePrice 				:: " + row[7].toString());
					logger.info("startDate 				:: " + row[8].toString());
					logger.info("expDate 				:: " + row[9].toString());
					logger.info("quantity 				:: " + row[10].toString());
					logger.info("productStatus 			:: " + row[11].toString());
					logger.info("productTypeName 		:: " + row[12].toString());
					logger.info("productGroupName 		:: " + row[13].toString());
					logger.info("unitName 				:: " + row[14].toString());
					
					bean.setProductCode				(row[0].toString());
					bean.setProductTypeCode			(row[1].toString());
					bean.setProductGroupCode		(row[2].toString());
					bean.setProductName				(row[3].toString());
					bean.setUnitCode				(row[4].toString());
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5].toString(), 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6].toString(), 2));
					bean.setSalePrice				(EnjoyUtils.convertFloatToDisplay(row[7].toString(), 2));
					bean.setStartDate				(EnjoyUtils.dateFormat(row[8].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setExpDate					(EnjoyUtils.dateFormat(row[9].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[10].toString(), 2));
					bean.setProductStatus			(row[11].toString());
					bean.setProductTypeName			(row[12].toString());
					bean.setProductGroupName		(row[13].toString());
					bean.setUnitName				(row[14].toString());
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getProductDetail] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductDetail");
		}finally{
			hql						= null;
			logger.info("[getProductDetail][End]");
		}
		
		return bean;
		
	}
	
	public void insertProductDetails(Session session, ProductDetailsBean 		productDetailsBean) throws EnjoyException{
		logger.info("[insertProductDetails][Begin]");
		
		Product	product						= null;
		
		try{
			
			product = new Product();
			
			product.setProductCode			(productDetailsBean.getProductCode());
			product.setProductType			(productDetailsBean.getProductTypeCode());
			product.setProductGroup			(productDetailsBean.getProductGroupCode());
			product.setProductName			(productDetailsBean.getProductName());
			product.setUnitCode				(EnjoyUtils.parseInt(productDetailsBean.getUnitCode()));
			product.setMinQuan				(EnjoyUtils.parseBigDecimal(productDetailsBean.getMinQuan()));
			product.setCostPrice			(EnjoyUtils.parseBigDecimal(productDetailsBean.getCostPrice()));
			product.setSalePrice			(EnjoyUtils.parseBigDecimal(productDetailsBean.getSalePrice()));
			product.setStartDate			(productDetailsBean.getStartDate());
			product.setExpDate				(productDetailsBean.getExpDate());
			product.setQuantity				(EnjoyUtils.parseBigDecimal(productDetailsBean.getQuantity()));
			product.setProductStatus		(productDetailsBean.getProductStatus());
			
			session.saveOrUpdate(product);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductDetails");
		}finally{
			
			product = null;
			logger.info("[insertProductDetails][End]");
		}
	}
	
	public void updateProductDetail(Session session, ProductDetailsBean 		productDetailsBean) throws EnjoyException{
		logger.info("[updateProductDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Product set productCode 			= :productCode"
												+ ", productType		= :productType"
												+ ", productGroup		= :productGroup"
												+ ", productName		= :productName"
												+ ", unitCode			= :unitCode"
												+ ", minQuan			= :minQuan"
												+ ", costPrice			= :costPrice"
												+ ", salePrice 			= :salePrice"
												+ ", startDate 			= :startDate"
												+ ", expDate 			= :expDate"
												+ ", quantity 			= :quantity"
												+ ", productStatus 		= :productStatus"
										+ " where productCode = :productCode";
			
			query = session.createQuery(hql);
			query.setParameter("productCode"		, productDetailsBean.getProductCode());
			query.setParameter("productType"		, productDetailsBean.getProductTypeCode());
			query.setParameter("productGroup"		, productDetailsBean.getProductGroupCode());
			query.setParameter("productName"		, productDetailsBean.getProductName());
			query.setParameter("unitCode"			, EnjoyUtils.parseInt(productDetailsBean.getUnitCode()));
			query.setParameter("minQuan"			, EnjoyUtils.parseBigDecimal(productDetailsBean.getMinQuan()));
			query.setParameter("costPrice"			, EnjoyUtils.parseBigDecimal(productDetailsBean.getCostPrice()));
			query.setParameter("salePrice"			, EnjoyUtils.parseBigDecimal(productDetailsBean.getSalePrice()));
			query.setParameter("startDate"			, productDetailsBean.getStartDate());
			query.setParameter("expDate"			, productDetailsBean.getExpDate());
			query.setParameter("quantity"			, EnjoyUtils.parseBigDecimal(productDetailsBean.getQuantity()));
			query.setParameter("productStatus"		, productDetailsBean.getProductStatus());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateProductDetail");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateProductDetail][End]");
		}
	}
	
	public int checkDupProductCode(Session session, String productCode) throws EnjoyException{
		logger.info("[checkDupProductCode][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou from product where productCode = '" + productCode + "' and productStatus = 'A'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupProductCode] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupProductCode][End]");
		}
		
		return result;
	}
	
	public int checkDupProductName(Session session, String productName, String productCode, String pageMode) throws EnjoyException{
		logger.info("[checkDupProductName][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql	= "Select count(*) cou from product where productName = '" + productName + "' and productStatus = 'A'";
			
			if(pageMode.equals(ProductDetailsMaintananceForm.EDIT)){
				hql += " and productCode <> '" + productCode + "'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupProductName] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupProductName][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productNameList(String productName, String productTypeName, String productGroupName, boolean flag){
		logger.info("[productNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						productTypeCode		= null;
        String						productGroupCode	= null;
        List<Object[]>				listTemp			= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			=  new ArrayList<ComboBean>();
			
			/*Begin check ProductType section*/
			hql 				= " select productTypeCode from productype where productTypeName = '"+productTypeName+"' and productTypeStatus = 'A'";
			
			logger.info("[productNameList] Check ProductType hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productTypeCode"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() == 1){
				productTypeCode = list.get(0);
			}
		    /*End check ProductType section*/
			
		    if(productTypeCode!=null){
		    	/*Begin check ProductGroup section*/
				hql 				= " select productGroupCode from productgroup where productTypeCode = '"+productTypeCode+"' and productGroupStatus = 'A'";
				
				logger.info("[productNameList] Check ProductType hql :: " + hql);
				
				query			= session.createSQLQuery(hql);
				query.addScalar("productGroupCode"			, new StringType());
				
				list		 	= query.list();
				
				if(list!=null && list.size() == 1){
					productGroupCode = list.get(0);
				}
			    /*End check ProductGroup section*/
		    }
		    
		    hql = "";
		    
		    if(productTypeCode!=null && productGroupCode!=null){
		    	hql = " select productCode, productName"
		    			+ " from product"
		    			+ " where productType 			= '" + productTypeCode + "'"
		    					+ " and productGroup 	= '" + productGroupCode + "'"
//		    					+ " and productStatus = 'A'"
		    			+ " order by productName asc limit 10";
		    }else{
		    	if(flag==true){
		    		hql = " select productCode, productName"
			    			+ " from product where 1=1";
		    		if(productTypeCode!=null) 	hql+= " and productType 	= '" + productTypeCode + "'";
		    		if(productGroupCode!=null) 	hql+= " and productGroup 	= '" + productGroupCode + "'";
		    		
		    		hql += " order by productName asc limit 10";
		    	}
		    }
		    
		    logger.info("[productGroupNameList] hql :: " + hql);
		    
	    	if(!hql.equals("")){
		    	query			= session.createSQLQuery(hql);
				query.addScalar("productCode"			, new StringType());
				query.addScalar("productName"			, new StringType());
				
				listTemp = query.list();
				
				for(Object[] row:listTemp){
					comboBean 	= new ComboBean();
					
					logger.info("productCode 		:: " + row[0].toString());
					logger.info("productName 		:: " + row[1].toString());
					
					comboBean.setCode				(row[0].toString());
					comboBean.setDesc				(row[1].toString());
					
					comboList.add(comboBean);
				}
	    	}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[productGroupNameList][End]");
		}
		
		return comboList;
	}
	
	public List<ProductDetailsBean> getProductDetailsLookUpList(	Session 					session, 
																	ProductDetailsLookUpForm 	form) throws EnjoyException{
		logger.info("[getProductDetailsLookUpList][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductDetailsBean 			bean					= null;
		List<ProductDetailsBean> 	listData 				= new ArrayList<ProductDetailsBean>();
		String						find					= null;
		
		try{
			find				= form.getFind();
			hql					= "select * from (select a.*, b.productTypeName, c.productGroupName "
													+ "	from product a, productype b, productgroup c"
													+ "	where b.productTypeCode = a.productType"
														+ " and c.productTypeCode = a.productType"
														+ " and c.productGroupCode = a.productGroup) t";
			
			if(find!=null && !find.equals("")){
				hql += " and t." + form.getColumn();
				
				if(form.getLikeFlag().equals("Y")){
					hql += " like ('" + find + "%')";
				}else{
					hql += " = '" + find + "'";
				}
				
			}
			
			hql += " order by t." + form.getOrderBy() + " " + form.getSortBy();
			
			logger.info("[getProductDetailsLookUpList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productType"		, new StringType());
			query.addScalar("productGroup"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("unitCode"			, new StringType());
			query.addScalar("minQuan"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("quantity"			, new StringType());
			query.addScalar("productStatus"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductDetailsLookUpList] list :: " + list);
			
			if(list!=null){
				logger.info("[getProductDetailsLookUpList] list.size() :: " + list.size());
				
				for(Object[] row:list){
					bean 		= new ProductDetailsBean();
					
					logger.info("productCode 			:: " + row[0].toString());
					logger.info("productType 			:: " + row[1].toString());
					logger.info("productGroup 			:: " + row[2].toString());
					logger.info("productName 			:: " + row[3].toString());
					logger.info("unitCode 				:: " + row[4].toString());
					logger.info("minQuan 				:: " + row[5].toString());
					logger.info("costPrice 				:: " + row[6].toString());
					logger.info("salePrice 				:: " + row[7].toString());
					logger.info("startDate 				:: " + row[8].toString());
					logger.info("expDate 				:: " + row[9].toString());
					logger.info("quantity 				:: " + row[10].toString());
					logger.info("productStatus 			:: " + row[11].toString());
					logger.info("productTypeName 		:: " + row[12].toString());
					logger.info("productGroupName 		:: " + row[13].toString());
					
					bean.setProductCode				(row[0].toString());
					bean.setProductTypeCode			(row[1].toString());
					bean.setProductGroupCode		(row[2].toString());
					bean.setProductName				(row[3].toString());
					bean.setUnitCode				(row[4].toString());
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5].toString(), 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6].toString(), 2));
					bean.setSalePrice				(EnjoyUtils.convertFloatToDisplay(row[7].toString(), 2));
					bean.setStartDate				(EnjoyUtils.dateFormat(row[8].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setExpDate					(EnjoyUtils.dateFormat(row[9].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[10].toString(), 2));
					bean.setProductStatus			(row[11].toString());
					bean.setProductTypeName			(row[12].toString());
					bean.setProductGroupName		(row[13].toString());
					
					listData.add(bean);
				}	
			}
			
		}catch(Exception e){
			logger.info("[getLookUpList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			logger.info("[getProductDetailsLookUpList][End]");
		}
		
		return listData;
		
	}
	
}
