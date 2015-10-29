
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Refconstantcode;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class RefconstantcodeDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RefconstantcodeDao.class);
	
	
	public List<RefconstantcodeBean> searchByCriteria(	Session 				session, 
														RefconstantcodeBean 	refconstantcodeBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		RefconstantcodeBean			bean					= null;
		List<RefconstantcodeBean> 	refconstantcodeList 	= new ArrayList<RefconstantcodeBean>();
		
		try{	
			hql					= "select a.* "
								+ "	from refconstantcode a"
								+ "	where 1=1 ";
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("id"			, new StringType());
			query.addScalar("codeDisplay"	, new StringType());
			query.addScalar("codeNameTH"	, new StringType());
			query.addScalar("codeNameEN"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new RefconstantcodeBean();
				
				logger.info("id 				:: " + row[0]);
				logger.info("codeDisplay 		:: " + row[1]);
				logger.info("codeNameTH		 	:: " + row[2]);
				logger.info("codeNameEN 		:: " + row[3]);
				
				bean.setId					(EnjoyUtils.nullToStr(row[0]));
				bean.setCodeDisplay			(EnjoyUtils.nullToStr(row[1]));
				bean.setCodeNameTH			(EnjoyUtils.nullToStr(row[2]));
				bean.setCodeNameEN			(EnjoyUtils.nullToStr(row[3]));
				
				refconstantcodeList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return refconstantcodeList;
		
	}
	
	public void insertRefconstantcode(Session session, RefconstantcodeBean 		refconstantcodeBean) throws EnjoyException{
		logger.info("[insertRefconstantcode][Begin]");
		
		Refconstantcode	refconstantcode						= null;
		
		try{
			
			refconstantcode = new Refconstantcode();
			
			refconstantcode.setId(EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			refconstantcode.setCodeDisplay(refconstantcodeBean.getCodeDisplay());
			refconstantcode.setCodeNameTH(refconstantcodeBean.getCodeNameTH());
			refconstantcode.setCodeNameEN(refconstantcodeBean.getCodeNameEN());
			
			session.saveOrUpdate(refconstantcode);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertRefconstantcode");
		}finally{
			
			refconstantcode = null;
			logger.info("[insertRefconstantcode][End]");
		}
	}
	
	public void updateRefconstantcode(Session session, RefconstantcodeBean 		refconstantcodeBean) throws EnjoyException{
		logger.info("[updateRefconstantcode][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Refconstantcode set codeDisplay 	= :codeDisplay"
														+ ", codeNameTH	= :codeNameTH"
														+ ", codeNameEN	= :codeNameEN"
										+ " where id = :id";
			
			query = session.createQuery(hql);
			query.setParameter("codeDisplay"		, refconstantcodeBean.getCodeDisplay());
			query.setParameter("codeNameTH"			, refconstantcodeBean.getCodeNameTH());
			query.setParameter("codeNameEN"			, refconstantcodeBean.getCodeNameEN());
			query.setParameter("id"					, EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateRefconstantcode");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateRefconstantcode][End]");
		}
	}
	
	public void updateCodeDisplay(Session session, RefconstantcodeBean 		refconstantcodeBean) throws EnjoyException{
		logger.info("[updateCodeDisplay][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Refconstantcode set codeDisplay 	= :codeDisplay"
										+ " where id = :id";
			
			query = session.createQuery(hql);
			query.setParameter("codeDisplay"		, refconstantcodeBean.getCodeDisplay());
			query.setParameter("id"					, EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateCodeDisplay");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateCodeDisplay][End]");
		}
	}
	
}

