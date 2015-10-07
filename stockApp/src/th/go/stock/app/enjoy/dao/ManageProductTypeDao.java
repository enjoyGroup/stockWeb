
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
import th.go.stock.app.enjoy.bean.ManageProductTypeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Productype;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ManageProductTypeDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageProductTypeDao.class);
	
	public List<ManageProductTypeBean> getProductTypeList(	Session 					session) throws EnjoyException{
		logger.info("[getProductTypeList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		ManageProductTypeBean		bean						= null;
		List<ManageProductTypeBean> manageProductTypeBeanList 	= new ArrayList<ManageProductTypeBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select * "
								+ "	from productype"
								+ "	where productTypeStatus = 'A' order by productTypeCode asc";
			
			logger.info("[getProductTypeList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productTypeCode"		, new StringType());
			query.addScalar("productTypeName"		, new StringType());
			query.addScalar("productTypeStatus"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductTypeList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ManageProductTypeBean();
				
				logger.info("productTypeCode 		:: " + row[0]);
				logger.info("productTypeName 		:: " + row[1]);
				logger.info("productTypeStatus 		:: " + row[2]);
				logger.info("seq 					:: " + seq);
				
				bean.setProductTypeCode				(EnjoyUtils.nullToStr(row[0]));
				bean.setProductTypeName				(EnjoyUtils.nullToStr(row[1]));
				bean.setProductTypeStatus			(EnjoyUtils.nullToStr(row[2]));
				bean.setSeq							(EnjoyUtils.nullToStr(seq));
				
				manageProductTypeBeanList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getProductTypeList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getProductTypeList");
		}finally{
			hql						= null;
			logger.info("[getProductTypeList][End]");
		}
		
		return manageProductTypeBeanList;
		
	}
	
	public void insertProductype(Session session, ManageProductTypeBean manageProductTypeBean) throws EnjoyException{
		logger.info("[insertProductype][Begin]");
		
		Productype	productype						= null;
		
		try{
			
			productype = new Productype();
			
			productype.setProductTypeCode			(manageProductTypeBean.getProductTypeCode());
			productype.setProductTypeName			(manageProductTypeBean.getProductTypeName());
			productype.setProductTypeStatus			(manageProductTypeBean.getProductTypeStatus());
			
			session.saveOrUpdate(productype);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertProductype");
		}finally{
			
			productype = null;
			logger.info("[insertProductype][End]");
		}
	}
	
	public void updateProductype(Session session, ManageProductTypeBean manageProductTypeBean) throws EnjoyException{
		logger.info("[updateProductype][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Productype set productTypeName 		= :productTypeName"
											+ "	,productTypeStatus 			= :productTypeStatus"
										+ " where productTypeCode = :productTypeCode";
			
			query = session.createQuery(hql);
			query.setParameter("productTypeName"			, manageProductTypeBean.getProductTypeName());
			query.setParameter("productTypeStatus"			, manageProductTypeBean.getProductTypeStatus());
			query.setParameter("productTypeCode"			, manageProductTypeBean.getProductTypeCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateProductype");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateProductype][End]");
		}
	}
	
	public int checkDupProductTypeCode(Session session, String productTypeCode) throws EnjoyException{
		logger.info("[checkDupProductTypeCode][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou from productype where productTypeCode = '" + productTypeCode + "' and productTypeStatus = 'A'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupProductTypeCode] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupProductTypeCode][End]");
		}
		
		return result;
	}
	
	public int checkDupProductTypeName(Session session, String productTypeName) throws EnjoyException{
		logger.info("[checkDupProductTypeCode][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou from productype where productTypeName = '" + productTypeName + "' and productTypeStatus = 'A'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupProductTypeCode] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupProductTypeCode][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> productTypeNameList(String productTypeName){
		logger.info("[productTypeNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<Object[]>				list				= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			=  new ArrayList<ComboBean>();
			hql 				= " select productTypeCode, productTypeName from productype where productTypeName like ('"+productTypeName+"%') and productTypeStatus = 'A' order by productTypeName asc limit 10 ";
			
			logger.info("[productTypeNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("productTypeCode"			, new StringType());
			query.addScalar("productTypeName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductTypeList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("productTypeCode 		:: " + row[0]);
				logger.info("productTypeName 		:: " + row[1]);
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[productTypeNameList][End]");
		}
		
		return comboList;
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
