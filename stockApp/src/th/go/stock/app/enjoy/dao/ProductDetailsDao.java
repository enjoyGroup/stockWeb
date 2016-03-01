
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
import th.go.stock.app.enjoy.bean.ProductdetailBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm;
import th.go.stock.app.enjoy.model.Productdetail;
import th.go.stock.app.enjoy.model.ProductdetailPK;
import th.go.stock.app.enjoy.model.Productmaster;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ProductDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDetailsDao.class);
	
	public List<ProductmasterBean> searchByCriteria(	Session 				session, 
														ProductmasterBean 		productDetailsBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductmasterBean			bean					= null;
		List<ProductmasterBean> 	productDetailsList 		= new ArrayList<ProductmasterBean>();
		int							chkBoxSeq				= 0;
		
		try{	
			hql					= "select a.*, b.productTypeName, c.productGroupName "
								+ "	from productmaster a, productype b, productgroup c"
								+ "	where b.productTypeCode = a.productType"
									+ " and c.productTypeCode = a.productType"
									+ " and c.productGroupCode = a.productGroup";
			
			if(!productDetailsBean.getProductTypeName().equals("")){
				hql += " and b.productTypeName like ('" + productDetailsBean.getProductTypeName() + "%')";
			}
			if(!productDetailsBean.getProductGroupName().equals("***")){
				if(productDetailsBean.getProductGroupName().equals("")){
					hql += " and (c.productGroupName is null or c.productGroupName = '')";
				}else{
					hql += " and c.productGroupName like ('" + productDetailsBean.getProductGroupName() + "%')";
				}
			}
			if(!productDetailsBean.getProductName().equals("***")){
				if(productDetailsBean.getProductName().equals("")){
					hql += " and (a.productName is null or a.productName = '')";
				}else{
					hql += " and a.productName like ('" + productDetailsBean.getProductName() + "%')";
				}
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productType"		, new StringType());
			query.addScalar("productGroup"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("unitCode"			, new StringType());
//			query.addScalar("quantity"			, new StringType());
			query.addScalar("minQuan"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice1"		, new StringType());
			query.addScalar("salePrice2"		, new StringType());
			query.addScalar("salePrice3"		, new StringType());
			query.addScalar("salePrice4"		, new StringType());
			query.addScalar("salePrice5"		, new StringType());
//			query.addScalar("startDate"			, new StringType());
//			query.addScalar("expDate"			, new StringType());
//			query.addScalar("quantity"			, new StringType());
//			query.addScalar("productStatus"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ProductmasterBean();
				
				logger.info("productCode 			:: " + row[0]);
				logger.info("productType 			:: " + row[1]);
				logger.info("productGroup 			:: " + row[2]);
				logger.info("productName 			:: " + row[3]);
				logger.info("unitCode 				:: " + row[4]);
//				logger.info("quantity 				:: " + row[5]);
				logger.info("minQuan 				:: " + row[5]);
				logger.info("costPrice 				:: " + row[6]);
				logger.info("salePrice1 			:: " + row[7]);
				logger.info("salePrice2 			:: " + row[8]);
				logger.info("salePrice3 			:: " + row[9]);
				logger.info("salePrice4 			:: " + row[10]);
				logger.info("salePrice5 			:: " + row[11]);
//				logger.info("startDate 				:: " + row[8]);
//				logger.info("expDate 				:: " + row[9]);
//				logger.info("quantity 				:: " + row[10]);
//				logger.info("productStatus 			:: " + row[11]);
				logger.info("productTypeName 		:: " + row[12]);
				logger.info("productGroupName 		:: " + row[13]);
				
				bean.setProductCode				(EnjoyUtils.nullToStr(row[0]));
				bean.setProductTypeCode			(EnjoyUtils.nullToStr(row[1]));
				bean.setProductGroupCode		(EnjoyUtils.nullToStr(row[2]));
				bean.setProductName				(EnjoyUtils.nullToStr(row[3]));
				bean.setUnitCode				(EnjoyUtils.nullToStr(row[4]));
//				bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[5], 2));
				bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5], 2));
				bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6], 2));
				bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row[7], 2));
				bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
				bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row[9], 2));
				bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row[10], 2));
				bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row[11], 2));
//				bean.setStartDate				(EnjoyUtils.dateFormat(row[8], "yyyyMMdd", "dd/MM/yyyy"));
//				bean.setExpDate					(EnjoyUtils.dateFormat(row[9], "yyyyMMdd", "dd/MM/yyyy"));
//				bean.setProductStatus			(row[11]);
				bean.setProductTypeName			(EnjoyUtils.nullToStr(row[12]));
				bean.setProductGroupName		(EnjoyUtils.nullToStr(row[13]));
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

	
	public ProductmasterBean getProductDetail(	Session 				session, 
												ProductmasterBean 		productDetailsBean) throws EnjoyException{
		logger.info("[getProductDetail][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductmasterBean			bean					= null;
		
		try{		
			hql		= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
					+ "	from productmaster a, productype b, productgroup c, unittype d"
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
//			query.addScalar("quantity"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice1"		, new StringType());
			query.addScalar("salePrice2"		, new StringType());
			query.addScalar("salePrice3"		, new StringType());
			query.addScalar("salePrice4"		, new StringType());
			query.addScalar("salePrice5"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			query.addScalar("unitName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductDetail] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new ProductmasterBean();
					
					logger.info("productCode 			:: " + row[0]);
					logger.info("productType 			:: " + row[1]);
					logger.info("productGroup 			:: " + row[2]);
					logger.info("productName 			:: " + row[3]);
					logger.info("unitCode 				:: " + row[4]);
					logger.info("minQuan 				:: " + row[5]);
//					logger.info("quantity 				:: " + row[6]);
					logger.info("costPrice 				:: " + row[6]);
					logger.info("salePrice1 			:: " + row[7]);
					logger.info("salePrice2 			:: " + row[8]);
					logger.info("salePrice3 			:: " + row[9]);
					logger.info("salePrice4 			:: " + row[10]);
					logger.info("salePrice5 			:: " + row[11]);
					logger.info("productTypeName 		:: " + row[12]);
					logger.info("productGroupName 		:: " + row[13]);
					logger.info("unitName 				:: " + row[14]);
					
					bean.setProductCode				(EnjoyUtils.nullToStr(row[0]));
					bean.setProductTypeCode			(EnjoyUtils.nullToStr(row[1]));
					bean.setProductGroupCode		(EnjoyUtils.nullToStr(row[2]));
					bean.setProductName				(EnjoyUtils.nullToStr(row[3]));
					bean.setUnitCode				(EnjoyUtils.nullToStr(row[4]));
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5], 2));
//					bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[6], 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6], 2));
					bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row[7], 2));
					bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
					bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row[9], 2));
					bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row[10], 2));
					bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row[11], 2));
					bean.setProductTypeName			(EnjoyUtils.nullToStr(row[12]));
					bean.setProductGroupName		(EnjoyUtils.nullToStr(row[13]));
					bean.setUnitName				(EnjoyUtils.nullToStr(row[14]));
					
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
	
	public void insertProductmaster(Session session, ProductmasterBean 		productmasterBean) throws EnjoyException{
		logger.info("[insertProductmaster][Begin]");
		
		Productmaster	productmaster						= null;
		
		try{
			
			productmaster = new Productmaster();
			
			productmaster.setProductCode			(productmasterBean.getProductCode());
			productmaster.setProductType			(productmasterBean.getProductTypeCode());
			productmaster.setProductGroup			(productmasterBean.getProductGroupCode());
			productmaster.setProductName			(productmasterBean.getProductName());
			productmaster.setUnitCode				(EnjoyUtils.parseInt(productmasterBean.getUnitCode()));
//			productmaster.setQuantity				(EnjoyUtils.parseBigDecimal(productmasterBean.getQuantity()));
			productmaster.setMinQuan				(EnjoyUtils.parseBigDecimal(productmasterBean.getMinQuan()));
			productmaster.setCostPrice				(EnjoyUtils.parseBigDecimal(productmasterBean.getCostPrice()));
			productmaster.setSalePrice1				(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice1()));
			productmaster.setSalePrice2				(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice2()));
			productmaster.setSalePrice3				(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice3()));
			productmaster.setSalePrice4				(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice4()));
			productmaster.setSalePrice5				(EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice5()));
//			productmaster.setStartDate				(productDetailsBean.getStartDate());
//			productmaster.setExpDate				(productDetailsBean.getExpDate());
//			productmaster.setProductStatus			(productDetailsBean.getProductStatus());
			
			session.saveOrUpdate(productmaster);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductmaster");
		}finally{
			
			productmaster = null;
			logger.info("[insertProductmaster][End]");
		}
	}
	
	public void updateProductmaster(Session session, ProductmasterBean 		productmasterBean) throws EnjoyException{
		logger.info("[updateProductmaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Productmaster set productType 			= :productType"
												+ ", productGroup		= :productGroup"
												+ ", productName		= :productName"
												+ ", unitCode			= :unitCode"
//												+ ", quantity 			= :quantity"
												+ ", minQuan			= :minQuan"
												+ ", costPrice			= :costPrice"
												+ ", salePrice1 			= :salePrice1"
												+ ", salePrice2 			= :salePrice2"
												+ ", salePrice3 			= :salePrice3"
												+ ", salePrice4 			= :salePrice4"
												+ ", salePrice5 			= :salePrice5"
										+ " where productCode = :productCode";
			
			query = session.createQuery(hql);
			query.setParameter("productType"		, productmasterBean.getProductTypeCode());
			query.setParameter("productGroup"		, productmasterBean.getProductGroupCode());
			query.setParameter("productName"		, productmasterBean.getProductName());
			query.setParameter("unitCode"			, EnjoyUtils.parseInt(productmasterBean.getUnitCode()));
//			query.setParameter("quantity"			, EnjoyUtils.parseBigDecimal(productmasterBean.getQuantity()));
			query.setParameter("minQuan"			, EnjoyUtils.parseBigDecimal(productmasterBean.getMinQuan()));
			query.setParameter("costPrice"			, EnjoyUtils.parseBigDecimal(productmasterBean.getCostPrice()));
			query.setParameter("salePrice1"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice1()));
			query.setParameter("salePrice2"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice2()));
			query.setParameter("salePrice3"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice3()));
			query.setParameter("salePrice4"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice4()));
			query.setParameter("salePrice5"			, EnjoyUtils.parseBigDecimal(productmasterBean.getSalePrice5()));
			query.setParameter("productCode"		, productmasterBean.getProductCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateProductmaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateProductmaster][End]");
		}
	}
	
//	public void updateProductQuantity(Session session, ProductmasterBean 		productmasterBean) throws EnjoyException{
//		logger.info("[updateProductQuantity][Begin]");
//		
//		String							hql									= null;
//		Query 							query 								= null;
//		
//		try{
//			hql				= "update  Productmaster set quantity 			= :quantity"
//										+ " where productCode = :productCode";
//			
//			query = session.createQuery(hql);
//			
//			query.setParameter("quantity"			, EnjoyUtils.parseBigDecimal(productmasterBean.getQuantity()));
//			query.setParameter("productCode"		, productmasterBean.getProductCode());
//			
//			query.executeUpdate();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.info(e.getMessage());
//			throw new EnjoyException("Error updateProductQuantity");
//		}finally{
//			
//			hql									= null;
//			query 								= null;
//			logger.info("[updateProductQuantity][End]");
//		}
//	}
	
	public int checkDupProductCode(Session session, String productCode) throws EnjoyException{
		logger.info("[checkDupProductCode][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
//			hql				= "Select count(*) cou from product where productCode = '" + productCode + "' and productStatus = 'A'";
			hql				= "Select count(*) cou from productmaster where productCode = '" + productCode + "'";
			
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
//			hql	= "select count(*) cou from product where productName = '" + productName + "' and productStatus = 'A'";
			hql	= "select count(*) cou from productmaster where productName = '" + productName + "'";
			
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
			
			if(productTypeName!=null && productGroupName!=null){
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
			}
		    
		    hql = "";
		    
		    if(productTypeCode!=null && productGroupCode!=null){
		    	hql = " select productCode, productName"
		    			+ " from productmaster"
		    			+ " where productType 			= '" + productTypeCode + "'"
		    					+ " and productGroup 	= '" + productGroupCode + "'"
//		    					+ " and productStatus = 'A'"
		    			+ " and productName like('"+productName+"%')"
		    			+ " order by productName asc limit 10";
		    }else{
		    	if(flag==true){
		    		hql = " select productCode, productName"
			    			+ " from productmaster where 1=1";
		    		if(productTypeCode!=null) 	hql+= " and productType 	= '" + productTypeCode + "'";
		    		if(productGroupCode!=null) 	hql+= " and productGroup 	= '" + productGroupCode + "'";
		    		
		    		hql += " and productName like('"+productName+"%')";
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
					
					logger.info("productCode 		:: " + row[0]);
					logger.info("productName 		:: " + row[1]);
					
					comboBean.setCode				(EnjoyUtils.nullToStr(row[0]));
					comboBean.setDesc				(EnjoyUtils.nullToStr(row[1]));
					
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
	
//	public List<ProductmasterBean> getProductDetailsLookUpList(	Session 					session, 
//																	ProductDetailsLookUpForm 	form) throws EnjoyException{
//		logger.info("[getProductDetailsLookUpList][Begin]");
//		
//		String						hql						= null;
//		SQLQuery 					query 					= null;
//		List<Object[]>				list					= null;
//		ProductmasterBean 			bean					= null;
//		List<ProductmasterBean> 	listData 				= new ArrayList<ProductmasterBean>();
//		String						find					= null;
//		
//		try{
//			find				= form.getFind();
//			hql					= "select * from (select a.*, b.productTypeName, c.productGroupName "
//													+ "	from productmaster a, productype b, productgroup c"
//													+ "	where b.productTypeCode = a.productType"
//														+ " and c.productTypeCode = a.productType"
//														+ " and c.productGroupCode = a.productGroup) t";
//			
//			if(find!=null && !find.equals("")){
//				hql += " and t." + form.getColumn();
//				
//				if(form.getLikeFlag().equals("Y")){
//					hql += " like ('" + find + "%')";
//				}else{
//					hql += " = '" + find + "'";
//				}
//				
//			}
//			
//			hql += " order by t." + form.getOrderBy() + " " + form.getSortBy();
//			
//			logger.info("[getProductDetailsLookUpList] hql :: " + hql);
//
//			query			= session.createSQLQuery(hql);			
//			query.addScalar("productCode"		, new StringType());
//			query.addScalar("productType"		, new StringType());
//			query.addScalar("productGroup"		, new StringType());
//			query.addScalar("productName"		, new StringType());
//			query.addScalar("unitCode"			, new StringType());
//			query.addScalar("quantity"			, new StringType());
//			query.addScalar("minQuan"			, new StringType());
//			query.addScalar("costPrice"			, new StringType());
//			query.addScalar("salePrice1"		, new StringType());
//			query.addScalar("salePrice2"		, new StringType());
//			query.addScalar("salePrice3"		, new StringType());
//			query.addScalar("salePrice4"		, new StringType());
//			query.addScalar("salePrice5"		, new StringType());
////			query.addScalar("startDate"			, new StringType());
////			query.addScalar("expDate"			, new StringType());
////			query.addScalar("productStatus"		, new StringType());
//			query.addScalar("productTypeName"	, new StringType());
//			query.addScalar("productGroupName"	, new StringType());
//			
//			list		 	= query.list();
//			
//			logger.info("[getProductDetailsLookUpList] list :: " + list);
//			
//			if(list!=null){
//				logger.info("[getProductDetailsLookUpList] list.size() :: " + list.size());
//				
//				for(Object[] row:list){
//					bean 		= new ProductmasterBean();
//					
//					logger.info("productCode 			:: " + row[0]);
//					logger.info("productType 			:: " + row[1]);
//					logger.info("productGroup 			:: " + row[2]);
//					logger.info("productName 			:: " + row[3]);
//					logger.info("unitCode 				:: " + row[4]);
//					logger.info("quantity 				:: " + row[5]);
//					logger.info("minQuan 				:: " + row[6]);
//					logger.info("costPrice 				:: " + row[7]);
//					logger.info("salePrice1 			:: " + row[8]);
//					logger.info("salePrice2 			:: " + row[9]);
//					logger.info("salePrice3 			:: " + row[10]);
//					logger.info("salePrice4 			:: " + row[11]);
//					logger.info("salePrice5 			:: " + row[12]);
////					logger.info("startDate 				:: " + row[8]);
////					logger.info("expDate 				:: " + row[9]);
////					logger.info("productStatus 			:: " + row[11]);
//					logger.info("productTypeName 		:: " + row[13]);
//					logger.info("productGroupName 		:: " + row[14]);
//					
//					bean.setProductCode				(row[0]);
//					bean.setProductTypeCode			(row[1]);
//					bean.setProductGroupCode		(row[2]);
//					bean.setProductName				(row[3]);
//					bean.setUnitCode				(row[4]);
//					bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[5], 2));
//					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[6], 2));
//					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[7], 2));
//					bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
//					bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row[9], 2));
//					bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row[10], 2));
//					bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row[11], 2));
//					bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row[12], 2));
////					bean.setStartDate				(EnjoyUtils.dateFormat(row[8], "yyyyMMdd", "dd/MM/yyyy"));
////					bean.setExpDate					(EnjoyUtils.dateFormat(row[9], "yyyyMMdd", "dd/MM/yyyy"));
////					bean.setProductStatus			(row[11]);
//					bean.setProductTypeName			(row[13]);
//					bean.setProductGroupName		(row[14]);
//					
//					listData.add(bean);
//				}	
//			}
//			
//		}catch(Exception e){
//			logger.info("[getLookUpList] " + e.getMessage());
//			e.printStackTrace();
//			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
//		}finally{
//			hql						= null;
//			logger.info("[getProductDetailsLookUpList][End]");
//		}
//		
//		return listData;
//		
//	}
	
	
	public List<ProductdetailBean> getProductdetailList( Session 				session
														,ProductdetailBean 		productdetailBean) throws EnjoyException{
		logger.info("[getProductdetailList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		ProductdetailBean			bean						= null;
		List<ProductdetailBean> 	productdetailList 			= new ArrayList<ProductdetailBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select * "
								+ "	from productdetail"
								+ "	where productCode = '" + productdetailBean.getProductCode() + "' order by seq asc";
			
			logger.info("[getProductdetailList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("seq"				, new StringType());
			query.addScalar("quanDiscount"		, new StringType());
			query.addScalar("discountRate"		, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductdetailList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ProductdetailBean();
				
				logger.info("productCode 	:: " + row[0]);
				logger.info("seqDb 			:: " + row[1]);
				logger.info("quanDiscount 	:: " + row[2]);
				logger.info("discountRate 	:: " + row[3]);
				logger.info("startDate 		:: " + row[4]);
				logger.info("expDate 		:: " + row[5]);
				logger.info("seq 			:: " + seq);
				
				bean.setProductCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row[1]));
				bean.setQuanDiscount		(EnjoyUtils.convertFloatToDisplay(row[2], 2));
				bean.setDiscountRate		(EnjoyUtils.convertFloatToDisplay(row[3], 2));
				bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row[4]));
				bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row[5]));
				bean.setSeq					(String.valueOf(seq));
				
				productdetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getProductdetailList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductdetailList");
		}finally{
			hql						= null;
			logger.info("[getProductdetailList][End]");
		}
		
		return productdetailList;
		
	}
	
	public void insertProductdetail(Session session, ProductdetailBean 		productdetailBean) throws EnjoyException{
		logger.info("[insertProductdetail][Begin]");
		
		Productdetail			productdetail		= null;
		ProductdetailPK 		id 					= null;
		
		try{
			
			productdetail 	= new Productdetail();
			id 				= new ProductdetailPK();
			
			id.setProductCode(productdetailBean.getProductCode());
			id.setSeq(EnjoyUtils.parseInt(productdetailBean.getSeqDb()));
			
			productdetail.setId					(id);
			productdetail.setQuanDiscount		(EnjoyUtils.parseBigDecimal(productdetailBean.getQuanDiscount()));
			productdetail.setDiscountRate		(EnjoyUtils.parseBigDecimal(productdetailBean.getDiscountRate()));
			productdetail.setStartDate			(EnjoyUtils.dateToThaiDB(productdetailBean.getStartDate()));
			productdetail.setExpDate			(EnjoyUtils.dateToThaiDB(productdetailBean.getExpDate()));
			
			session.saveOrUpdate(productdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductdetail");
		}finally{
			id 				= null;
			productdetail 	= null;
			logger.info("[insertProductdetail][End]");
		}
	}
	
	public void deleteProductdetail(Session session, String productCode) throws EnjoyException{
		logger.info("[deleteProductdetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Productdetail t"
							+ " where t.id.productCode	 = '" + productCode + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteProductdetail][End]");
		}
	}

	
	public ProductmasterBean getProductDetailByName(String 	productName) throws EnjoyException{
		logger.info("[getProductDetailByName][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ProductmasterBean			bean					= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{		
			
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql		= "select a.*, b.productTypeName, c.productGroupName, d.unitName"
					+ "	from productmaster a, productype b, productgroup c, unittype d"
					+ "	where b.productTypeCode 	= a.productType"
						+ " and c.productTypeCode 	= a.productType"
						+ " and c.productGroupCode 	= a.productGroup"
						+ " and d.unitCode 			= a.unitCode"
						+ " and a.productName		= '" + productName + "'";
			
			logger.info("[getProductDetail] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productType"		, new StringType());
			query.addScalar("productGroup"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("unitCode"			, new StringType());
			query.addScalar("minQuan"			, new StringType());
//			query.addScalar("quantity"			, new StringType());
			query.addScalar("costPrice"			, new StringType());
			query.addScalar("salePrice1"		, new StringType());
			query.addScalar("salePrice2"		, new StringType());
			query.addScalar("salePrice3"		, new StringType());
			query.addScalar("salePrice4"		, new StringType());
			query.addScalar("salePrice5"		, new StringType());
			query.addScalar("productTypeName"	, new StringType());
			query.addScalar("productGroupName"	, new StringType());
			query.addScalar("unitName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductDetailByName] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new ProductmasterBean();
					
					logger.info("productCode 			:: " + row[0]);
					logger.info("productType 			:: " + row[1]);
					logger.info("productGroup 			:: " + row[2]);
					logger.info("productName 			:: " + row[3]);
					logger.info("unitCode 				:: " + row[4]);
					logger.info("minQuan 				:: " + row[5]);
//					logger.info("quantity 				:: " + row[6]);
					logger.info("costPrice 				:: " + row[6]);
					logger.info("salePrice1 			:: " + row[7]);
					logger.info("salePrice2 			:: " + row[8]);
					logger.info("salePrice3 			:: " + row[9]);
					logger.info("salePrice4 			:: " + row[10]);
					logger.info("salePrice5 			:: " + row[11]);
					logger.info("productTypeName 		:: " + row[12]);
					logger.info("productGroupName 		:: " + row[13]);
					logger.info("unitName 				:: " + row[14]);
					
					bean.setProductCode				(EnjoyUtils.nullToStr(row[0]));
					bean.setProductTypeCode			(EnjoyUtils.nullToStr(row[1]));
					bean.setProductGroupCode		(EnjoyUtils.nullToStr(row[2]));
					bean.setProductName				(EnjoyUtils.nullToStr(row[3]));
					bean.setUnitCode				(EnjoyUtils.nullToStr(row[4]));
					bean.setMinQuan					(EnjoyUtils.convertFloatToDisplay(row[5], 2));
//					bean.setQuantity				(EnjoyUtils.convertFloatToDisplay(row[6], 2));
					bean.setCostPrice				(EnjoyUtils.convertFloatToDisplay(row[6], 2));
					bean.setSalePrice1				(EnjoyUtils.convertFloatToDisplay(row[7], 2));
					bean.setSalePrice2				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
					bean.setSalePrice3				(EnjoyUtils.convertFloatToDisplay(row[9], 2));
					bean.setSalePrice4				(EnjoyUtils.convertFloatToDisplay(row[10], 2));
					bean.setSalePrice5				(EnjoyUtils.convertFloatToDisplay(row[11], 2));
					bean.setProductTypeName			(EnjoyUtils.nullToStr(row[12]));
					bean.setProductGroupName		(EnjoyUtils.nullToStr(row[13]));
					bean.setUnitName				(EnjoyUtils.nullToStr(row[14]));
					
				}	
			}
			
		}catch(Exception e){
			logger.info("[getProductDetailByName] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductDetailByName");
		}finally{
			hql					= null;
			session.close();
			sessionFactory		= null;
			session				= null;
			logger.info("[getProductDetailByName][End]");
		}
		
		return bean;
		
	}
	
	public String getQuanDiscount(String productCode, String quantity) throws EnjoyException{
		logger.info("[getQuanDiscount][Begin]");
		
		String			hql							= null;
		List<String>	list						= null;
		SQLQuery 		query 						= null;
		String 			discountRate				= "0.00";
		SessionFactory 	sessionFactory				= null;
		Session 		session						= null;
		
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql		= "select discountRate from productdetail"
					+ "		where productCode = '" + productCode + "'"
					+ "			and quanDiscount <= " + quantity
					+ "			and startDate <= '" + EnjoyUtils.currDateThai() + "'"
					+ "			and (expDate is null or expDate = '' or expDate >= '" + EnjoyUtils.currDateThai() + "')"
					+ "		order by quanDiscount ASC"
					+ "		LIMIT 1";
			
			logger.info("[getQuanDiscount] hql 			:: " + hql);
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("discountRate"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				discountRate = EnjoyUtils.convertFloatToDisplay(list.get(0), 2);
			}
			
			logger.info("[getQuanDiscount] discountRate 			:: " + discountRate);
			
			
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			throw new EnjoyException(e.getMessage());
		}finally{
			session.flush();
			session.clear();
			
			session.close();
			
			hql				= null;
			list			= null;
			query 			= null;
			sessionFactory	= null;
			session			= null;
			logger.info("[getQuanDiscount][End]");
		}
		
		return discountRate;
	}
	
//	public String getInventory(String productCode) throws EnjoyException{
//		logger.info("[getInventory][Begin]");
//		
//		String			hql							= null;
//		List<String>	list						= null;
//		SQLQuery 		query 						= null;
//		String 			inventory					= "0.00";
//		SessionFactory 	sessionFactory				= null;
//		Session 		session						= null;
//		
//		
//		try{
//			sessionFactory 				= HibernateUtil.getSessionFactory();
//			session 					= sessionFactory.openSession();
//			
//			hql		= "select quantity from productmaster where productCode = '" + productCode + "'";
//			
//			query			= session.createSQLQuery(hql);
//			
//			query.addScalar("quantity"			, new StringType());
//			
//			list		 	= query.list();
//			
//			if(list!=null && list.size() > 0){
//				inventory = EnjoyUtils.convertFloatToDisplay(list.get(0), 2);
//			}
//			
//			logger.info("[getInventory] inventory 			:: " + inventory);
//			
//			
//			
//		}catch(Exception e){
//			logger.error(e);
//			throw new EnjoyException(e.getMessage());
//		}finally{
//			session.flush();
//			session.clear();
//			session.close();
//			
//			hql				= null;
//			list			= null;
//			query 			= null;
//			sessionFactory	= null;
//			session			= null;
//			logger.info("[getInventory][End]");
//		}
//		
//		return inventory;
//	}
	
}
