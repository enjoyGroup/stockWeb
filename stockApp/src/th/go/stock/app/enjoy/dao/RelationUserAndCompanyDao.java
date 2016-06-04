
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
import th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Relationuserncompany;
import th.go.stock.app.enjoy.model.RelationuserncompanyPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class RelationUserAndCompanyDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RelationUserAndCompanyDao.class);
	
	public List<RelationUserAndCompanyBean> searchByCriteria(	Session 					session, 
																RelationUserAndCompanyBean 	relationUserAndCompanyBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String								hql							= null;
		SQLQuery 							query 						= null;
		List<Object[]>						list						= null;
		RelationUserAndCompanyBean			bean						= null;
		List<RelationUserAndCompanyBean> 	relationUserAndCompanyList 	= new ArrayList<RelationUserAndCompanyBean>();
		int									seq							= 0;
		
		try{	
			hql					= "select a.*, b.userId, CONCAT(b.userName, ' ', b.userSurname) userFullName, b.userStatus, c.userStatusName"
								+ "	from relationuserncompany a, userdetails b, refuserstatus c"
								+ "	where b.userUniqueId 		= a.userUniqueId"
									+ " and c.userStatusCode	= b.userStatus"
									+ " and a.tin 		= '" + relationUserAndCompanyBean.getTin() + "'";
			
			if(!relationUserAndCompanyBean.getUserFullName().equals("")){
				hql += " and CONCAT(b.userName, ' ', b.userSurname) like ('" + relationUserAndCompanyBean.getUserFullName() + "%')";
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("userUniqueId"		, new StringType());
			query.addScalar("tin"				, new StringType());
			query.addScalar("userId"			, new StringType());
			query.addScalar("userFullName"		, new StringType());
			query.addScalar("userStatus"		, new StringType());
			query.addScalar("userStatusName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new RelationUserAndCompanyBean();
				
				bean.setUserUniqueId	(EnjoyUtils.nullToStr(row[0]));
				bean.setTin				(EnjoyUtils.nullToStr(row[1]));
				bean.setUserId			(EnjoyUtils.nullToStr(row[2]));
				bean.setUserFullName	(EnjoyUtils.nullToStr(row[3]));
				bean.setUserStatus		(EnjoyUtils.nullToStr(row[4]));
				bean.setUserStatusName	(EnjoyUtils.nullToStr(row[5]));
				bean.setSeq				(EnjoyUtils.nullToStr(seq));
				
				relationUserAndCompanyList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return relationUserAndCompanyList;
		
	}
	
	public int checkDupUser(String 	userUniqueId, String notInUserUniqueId) throws EnjoyException{
		logger.info("[checkDupUser][Begin]");
		
		String						hql					= null;
		List<Integer>			 	list				= null;
		SQLQuery 					query 				= null;
		int 						result				= 0;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			
			hql				= "select count(*) cou from relationuserncompany"
								+ " where userUniqueId 	= " + userUniqueId + " and not in (" + notInUserUniqueId + ")";
			
			logger.info("[checkDupUser] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupUser] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[checkDupUser][End]");
		}
		
		return result;
	}
	
	public void insertRelationUserAndCompany(Session session, RelationUserAndCompanyBean relationUserAndCompanyBean) throws EnjoyException{
		logger.info("[insertRelationUserAndCompany][Begin]");
		
		Relationuserncompany	relationuserncompany	= null;
		RelationuserncompanyPK	id						= null;
		
		try{
			id						= new RelationuserncompanyPK();
			relationuserncompany 	= new Relationuserncompany();
			
			id.setTin			(relationUserAndCompanyBean.getTin());
			id.setUserUniqueId	(EnjoyUtils.parseInt(relationUserAndCompanyBean.getUserUniqueId()));
			
			relationuserncompany.setId(id);
			
			session.saveOrUpdate(relationuserncompany);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertRelationUserAndCompany");
		}finally{
			relationuserncompany = null;
			logger.info("[insertRelationUserAndCompany][End]");
		}
	}
	
	public void deleteRelationUserAndCompany(Session session, String tin) throws EnjoyException{
		logger.info("[deleteRelationUserAndCompany][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			logger.info("[deleteRelationUserAndCompany] tin :: " + tin);
			
			hql				= "delete Relationuserncompany t"
							+ " where t.id.tin	 = '" + tin + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteRelationUserAndCompany][End]");
		}
	}
	
	public int countForCheckLogin(int userUniqueId) throws EnjoyException{
		logger.info("[countForCheckLogin][Begin]");
		
		String						hql					= null;
		List<Integer>			 	list				= null;
		SQLQuery 					query 				= null;
		int 						result				= 0;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			
			hql				= "select count(*) cou from relationuserncompany"
								+ " where userUniqueId 	= " + userUniqueId;
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[countForCheckLogin] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[countForCheckLogin][End]");
		}
		
		return result;
	}
	public List<ComboBean> getCompanyList(int userUniqueId) throws EnjoyException{
		logger.info("[getCompanyList][Begin]");
		
		String				hql						= null;
		List<Object[]>		list					= null;
		SQLQuery 			query 					= null;
		SessionFactory 		sessionFactory			= null;
		Session 			session					= null;
		List<ComboBean>		comboList				= new ArrayList<ComboBean>();
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql				= "select a.tin, b.companyName"
							+ "	from relationuserncompany a"
							+ "		INNER JOIN company b on a.tin = b.tin"
							+ "	where a.userUniqueId = " + userUniqueId;
			query			= session.createSQLQuery(hql);
			
			
			query.addScalar("tin"			, new StringType());
			query.addScalar("companyName"	, new StringType());
			
			list		 	= query.list();
			
			for(Object row[]:list){
				comboList.add(new ComboBean(EnjoyUtils.nullToStr(row[0]), EnjoyUtils.nullToStr(row[1])));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[getCompanyList][End]");
		}
		
		return comboList;
	}
	
}
