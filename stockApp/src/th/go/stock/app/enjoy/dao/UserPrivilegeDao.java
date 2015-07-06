package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import th.go.stock.app.enjoy.bean.PagesDetailBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.model.Refpagedetail;
import th.go.stock.app.enjoy.model.Userprivilege;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class UserPrivilegeDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserPrivilegeDao.class);
	
	public List<UserPrivilegeBean> userPrivilegeListSelect(String privilegeCode){
		logger.info("[UserPrivilegeDao][userPrivilegeListSelect][Begin]");
		
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		List<UserPrivilegeBean> 	userPrivilegeBeanList	= null;
		UserPrivilegeBean 			userPrivilegeBean 		= null;
		List<Userprivilege> 		userPrivilegeList		= null;
		Userprivilege 				userprivilege			= null;
		String						hql						= null;
		String[]					arrPrivilegeCode		= null;				
		int							maxArrPrivilegeCode		= 0;
		int							maxRecord				= 0;
		try{
		    sessionFactory 		  = HibernateUtil.getSessionFactory();
			session 			  = sessionFactory.openSession();
			arrPrivilegeCode	  = privilegeCode.split("\\,");
			maxArrPrivilegeCode   = arrPrivilegeCode.length;
			userPrivilegeBeanList = new ArrayList<UserPrivilegeBean>();
			
			for(int i=0;i<maxArrPrivilegeCode;i++){			
				hql					= "from Userprivilege where privilegeCode = '" + arrPrivilegeCode[i] + "'";  // จะได้ 1 รายการเสมอ
				userPrivilegeList 	= session.createQuery(hql).list();			
				maxRecord      		= userPrivilegeList.size();
				
				for(int j=0;j<maxRecord;j++){
					userprivilege		= userPrivilegeList.get(j);
					userPrivilegeBean	= new UserPrivilegeBean();
					
					logger.debug("[UserPrivilegeDao][userPrivilegeListSelect] userprivilege.getPrivilegeCode() 		:: " + userprivilege.getPrivilegeCode());
					logger.debug("[UserPrivilegeDao][userPrivilegeListSelect] userprivilege.getPrivilegeName() 		:: " + userprivilege.getPrivilegeName());
					
					userPrivilegeBean.setPrivilegeCode(userprivilege.getPrivilegeCode());
					userPrivilegeBean.setPrivilegeName(userprivilege.getPrivilegeName());
					userPrivilegeBean.setPagesDetail((ArrayList<PagesDetailBean>) pagesDetailListSelect(userprivilege.getPagesCode()));
					userPrivilegeBeanList.add(userPrivilegeBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
//			userdetailList	= null;
//			userdetail		= null;
			hql				= null;
			logger.info("[UserPrivilegeDao][userPrivilegeListSelect][End]");
		}
		
		return userPrivilegeBeanList;
	}
	
	public List<PagesDetailBean> pagesDetailListSelect(String pageCodes){
		logger.info("[UserPrivilegeDao][pagesDetailListSelect][Begin]");
		
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		List<PagesDetailBean> 		pagesDetailBeanList		= null;
		PagesDetailBean 			pagesDetailBean 		= null;
		List<Refpagedetail> 		refpagedetailList		= null;
		Refpagedetail 				refpagedetail			= null;
		String						hql						= null;
		String[]					arrPageCodes			= null;				
		int							maxArrPageCodes			= 0;
		int							maxRecord				= 0;
		try{
		    sessionFactory 		  = HibernateUtil.getSessionFactory();
			session 			  = sessionFactory.openSession();
			arrPageCodes		  = pageCodes.split("\\,");
			maxArrPageCodes   	  = arrPageCodes.length;
			pagesDetailBeanList   = new ArrayList<PagesDetailBean>();
			
			for(int i=0;i<maxArrPageCodes;i++){			
				hql					= "from Refpagedetail where pageCodes = '" + arrPageCodes[i] + "'";  // จะได้ 1 รายการเสมอ
				refpagedetailList 	= session.createQuery(hql).list();			
				maxRecord      		= refpagedetailList.size();
				
				for(int j=0;j<maxRecord;j++){
					refpagedetail		= refpagedetailList.get(j);
					pagesDetailBean		= new PagesDetailBean();
					
					logger.debug("[UserPrivilegeDao][pagesDetailListSelect] refpagedetail.getPageCodes() 		:: " + refpagedetail.getPageCodes());
					logger.debug("[UserPrivilegeDao][pagesDetailListSelect] refpagedetail.getPageNames() 		:: " + refpagedetail.getPageNames());
					logger.debug("[UserPrivilegeDao][pagesDetailListSelect] refpagedetail.getPathPages() 		:: " + refpagedetail.getPathPages());
					
					pagesDetailBean.setPageCodes(refpagedetail.getPageCodes());
					pagesDetailBean.setPageNames(refpagedetail.getPageNames());
					pagesDetailBean.setPathPages(refpagedetail.getPathPages());
					pagesDetailBeanList.add(pagesDetailBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory			= null;
			session					= null;
			pagesDetailBean 		= null;
			refpagedetailList		= null;
			refpagedetail			= null;
			hql						= null;
			arrPageCodes			= null;				
			logger.info("[UserPrivilegeDao][pagesDetailListSelect][End]");
		}
		
		return pagesDetailBeanList;
	}
	
}
