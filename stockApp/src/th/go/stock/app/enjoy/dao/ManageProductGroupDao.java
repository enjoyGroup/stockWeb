
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
import th.go.stock.app.enjoy.bean.ManageProductGroupBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Productgroup;
import th.go.stock.app.enjoy.model.ProductgroupPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ManageProductGroupDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageProductGroupDao.class);
	
	public List<ManageProductGroupBean> getProductGroupList( Session 					session
															,ManageProductGroupBean 	manageProductGroupBean) throws EnjoyException{
		logger.info("[getProductGroupList][Begin]");
		
		String							hql							= null;
		SQLQuery 						query 						= null;
		List<Object[]>					list						= null;
		ManageProductGroupBean			bean						= null;
		List<ManageProductGroupBean> 	manageProductGroupBeanList 	= new ArrayList<ManageProductGroupBean>();
		int								seq							= 0;
		
		try{	
			hql					= "select * "
								+ "	from productgroup"
								+ "	where productTypeCode = '" + manageProductGroupBean.getProductTypeCode() + "' and productGroupStatus = 'A' order by productGroupCode asc";
			
			logger.info("[getProductGroupList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productTypeCode"		, new StringType());
			query.addScalar("productGroupCode"		, new StringType());
			query.addScalar("productGroupName"		, new StringType());
			query.addScalar("productGroupStatus"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductGroupList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ManageProductGroupBean();
				
				logger.info("productTypeCode 		:: " + row[0].toString());
				logger.info("productGroupCode 		:: " + row[1].toString());
				logger.info("productGroupName 		:: " + row[2].toString());
				logger.info("productGroupStatus 	:: " + row[3].toString());
				logger.info("seq 					:: " + seq);
				
				bean.setProductTypeCode				(row[0].toString());
				bean.setProductGroupCode			(row[1].toString());
				bean.setProductGroupName			(row[2].toString());
				bean.setProductGroupStatus			(row[3].toString());
				bean.setSeq							(String.valueOf(seq));
				
				manageProductGroupBeanList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getProductTypeList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductGroupList");
		}finally{
			hql						= null;
			logger.info("[getProductGroupList][End]");
		}
		
		return manageProductGroupBeanList;
		
	}
	
	public void insertProducGroup(Session session, ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		logger.info("[insertProducGroup][Begin]");
		
		Productgroup	productgroup	= null;
		ProductgroupPK	pk				= null;
		
		try{
			
			productgroup 	= new Productgroup();
			pk				= new ProductgroupPK();
			
			pk.setProductTypeCode(manageProductGroupBean.getProductTypeCode());
			pk.setProductGroupCode(manageProductGroupBean.getProductGroupCode());
			
			productgroup.setId(pk);
			productgroup.setProductGroupName(manageProductGroupBean.getProductGroupName());
			productgroup.setProductGroupStatus(manageProductGroupBean.getProductGroupStatus());
			
			session.saveOrUpdate(productgroup);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProducGroup");
		}finally{
			
			productgroup 	= null;
			pk 				= null;
			logger.info("[insertProducGroup][End]");
		}
	}
	
	public void updateProductgroup(Session session, ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		logger.info("[updateProductgroup][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Productgroup t set t.productGroupName 	= :productGroupName"
											+ "	,t.productGroupStatus 			= :productGroupStatus"
										+ " where t.id.productTypeCode 		= :productTypeCode"
											+ " and t.id.productGroupCode 	= :productGroupCode";
			
			query = session.createQuery(hql);
			query.setParameter("productGroupName"			, manageProductGroupBean.getProductGroupName());
			query.setParameter("productGroupStatus"			, manageProductGroupBean.getProductGroupStatus());
			query.setParameter("productTypeCode"			, manageProductGroupBean.getProductTypeCode());
			query.setParameter("productGroupCode"			, manageProductGroupBean.getProductGroupCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateProductgroup");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateProductgroup][End]");
		}
	}
	
	public int checkDupProductGroupCode(Session session, ManageProductGroupBean manageProductGroupBean) throws EnjoyException{
		logger.info("[checkDupProductGroupCode][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou "
							+ " from productgroup "
							+ " where productTypeCode 			= '" + manageProductGroupBean.getProductTypeCode() + "' "
									+ " and productGroupCode 	= '" + manageProductGroupBean.getProductGroupCode() + "'"
									+ " and productGroupStatus 	= 'A'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupProductGroupCode] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupProductGroupCode][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productGroupNameList(String productTypeName, String productGroupName, boolean flag){
		logger.info("[productGroupNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						productTypeCode		= null;
        List<Object[]>				listTemp			= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			=  new ArrayList<ComboBean>();
			
			/*Begin check ProductType section*/
			hql 				= " select productTypeCode from productype where productTypeName = '"+productTypeName+"' and productTypeStatus = 'A'";
			
			logger.info("[productGroupNameList] Check ProductType hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productTypeCode"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size()==1){
				productTypeCode = list.get(0);
			}
		    /*End check ProductType section*/
		    
			hql = "";
		    if(productTypeCode!=null){
		    	hql = " select productGroupCode, productGroupName"
		    			+ " from productgroup"
		    			+ " where productTypeCode = '"+productTypeCode+"'"
		    					+ " and productGroupName like ('"+productGroupName+"%') "
		    					+ " and productGroupStatus = 'A'"
		    			+ " order by productGroupName asc limit 10 ";
		    }else{
		    	if(flag==true){
		    		hql = " select productGroupCode, productGroupName"
			    			+ " from productgroup"
			    			+ " where"
			    					+ " productGroupName like ('"+productGroupName+"%') "
			    					+ " and productGroupStatus = 'A'"
			    			+ " order by productGroupName asc limit 10 ";
		    	}
		    }
		    
		    logger.info("[productGroupNameList] hql :: " + hql);
		    
		    if(!hql.equals("")){
		    	query			= session.createSQLQuery(hql);
				query.addScalar("productGroupCode"			, new StringType());
				query.addScalar("productGroupName"			, new StringType());
				
				listTemp = query.list();
				
				for(Object[] row:listTemp){
					comboBean 	= new ComboBean();
					
					logger.info("productGroupCode 		:: " + row[0].toString());
					logger.info("productGroupName 		:: " + row[1].toString());
					
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
	
	public String getProductGroupCode(String productTypeCode, String productGroupName){
		logger.info("[getProductGroupCode][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
		String						productGroupCode	= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select productGroupCode"
							+ "	from productgroup"
							+ " where productGroupStatus 		= 'A' "
								+ " and productGroupName 		= '" + productGroupName + "' "
										+ "and productTypeCode 	= '" + productTypeCode + "'";
			
			logger.info("[getProductGroupCode] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productGroupCode"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				productGroupCode = list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getProductGroupCode][End]");
		}
		
		return productGroupCode;
	}


	
}
