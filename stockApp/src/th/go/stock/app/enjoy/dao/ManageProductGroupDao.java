
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

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
	
	public List<String> productTypeNameList(String productTypeName){
		logger.info("[productTypeNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 				= " select productTypeName from productype where productTypeName like ('"+productTypeName+"%') and productTypeStatus = 'A' order by productTypeName asc limit 10 ";
			
			logger.info("[productTypeNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productTypeName"			, new StringType());
			
			list		 	= query.list();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[productTypeNameList][End]");
		}
		
		return list;
	}
	
	public String getProductTypeCode(String productTypeName){
		logger.info("[getProductTypeCode][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						productTypeCode		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select productTypeCode from productype where productTypeStatus = 'A' and productTypeName = '" + productTypeName + "'";
			
			logger.info("[getProductTypeCode] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productTypeCode"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				productTypeCode = list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getProductTypeCode][End]");
		}
		
		return productTypeCode;
	}
	
}
