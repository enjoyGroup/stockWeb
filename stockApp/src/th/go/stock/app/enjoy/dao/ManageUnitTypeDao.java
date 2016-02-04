
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
import th.go.stock.app.enjoy.bean.ManageUnitTypeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Unittype;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ManageUnitTypeDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageUnitTypeDao.class);
	
	public List<ManageUnitTypeBean> getUnitTypeList(	Session 					session) throws EnjoyException{
		logger.info("[getUnitTypeList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		ManageUnitTypeBean			bean						= null;
		List<ManageUnitTypeBean> 	manageUnitTypeList 			= new ArrayList<ManageUnitTypeBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select * "
								+ "	from unittype"
								+ "	where unitStatus = 'A' order by unitCode asc";
			
			logger.info("[getUnitTypeList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("unitCode"		, new StringType());
			query.addScalar("unitName"		, new StringType());
			query.addScalar("unitStatus"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getUnitTypeList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ManageUnitTypeBean();
				
				logger.info("unitCode 		:: " + row[0]);
				logger.info("unitName 		:: " + row[1]);
				logger.info("unitStatus 	:: " + row[2]);
				logger.info("seq 			:: " + seq);
				
				bean.setUnitCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setUnitName			(EnjoyUtils.nullToStr(row[1]));
				bean.setUnitStatus			(EnjoyUtils.nullToStr(row[2]));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				manageUnitTypeList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getUnitTypeList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getUnitTypeList");
		}finally{
			hql						= null;
			logger.info("[getUnitTypeList][End]");
		}
		
		return manageUnitTypeList;
		
	}
	
	public void insertUnitType(Session session, ManageUnitTypeBean manageUnitTypeBean) throws EnjoyException{
		logger.info("[insertUnitType][Begin]");
		
		Unittype	unittype						= null;
		
		try{
			
			unittype = new Unittype();
			
			unittype.setUnitName			(manageUnitTypeBean.getUnitName());
			unittype.setUnitStatus			(manageUnitTypeBean.getUnitStatus());
			
			session.saveOrUpdate(unittype);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("Error insertUnitType");
		}finally{
			
			unittype = null;
			logger.info("[insertUnitType][End]");
		}
	}
	
	public void updateUnitType(Session session, ManageUnitTypeBean manageUnitTypeBean) throws EnjoyException{
		logger.info("[updateUnitType][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Unittype set unitName 		= :unitName"
											+ "	,unitStatus 		= :unitStatus"
										+ " where unitCode = :unitCode";
			
			query = session.createQuery(hql);
			query.setParameter("unitName"			, manageUnitTypeBean.getUnitName());
			query.setParameter("unitStatus"			, manageUnitTypeBean.getUnitStatus());
			query.setParameter("unitCode"			, Integer.parseInt(manageUnitTypeBean.getUnitCode()));
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateUnitType");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateUnitType][End]");
		}
	}
	
	public int checkDupUnitName(Session session, String unitName, String unitCode) throws EnjoyException{
		logger.info("[checkDupUnitName][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou from unittype where unitName = '" + unitName + "' and unitStatus = 'A'";
			
			if(unitCode!=null && !unitCode.equals("")){
				hql += " and unitCode <> '"+unitCode+"'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupUnitName] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupUnitName][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> unitNameList(String unitName){
		logger.info("[unitNameList][Begin]");
		
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
			comboList			= new ArrayList<ComboBean>();
			hql 				= "select unitCode, unitName from unittype where unitName like ('"+unitName+"%') and unitStatus = 'A' order by unitName asc limit 10 ";
			
			logger.info("[productTypeNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("unitCode"			, new StringType());
			query.addScalar("unitName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[unitNameList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("unitCode 		:: " + row[0]);
				logger.info("unitName 		:: " + row[1]);
				
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
			logger.info("[unitNameList][End]");
		}
		
		return comboList;
	}
	
	public String getUnitCode(String unitName){
		logger.info("[getUnitCode][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						unitCode			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select unitCode from unittype where unitStatus = 'A' and unitName = '" + unitName + "'";
			
			logger.info("[getUnitCode] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("unitCode"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				unitCode = list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getUnitCode][End]");
		}
		
		return unitCode;
	}

}
