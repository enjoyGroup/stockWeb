
package th.go.stock.app.enjoy.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.RefuserstatusBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.model.Refuserstatus;
import th.go.stock.app.enjoy.model.Userdetail;
import th.go.stock.app.enjoy.model.Userprivilege;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class UserDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsDao.class);
	
//	public static void main(String[] args) {
//		
//		SessionFactory 		sessionFactory	= null;
//		Session 			session			= null;
//		UserDetailsBean		userDetailsBean = null;
//		
//		try {
//			EnjoyLogger.initial(false);
//			sessionFactory 				= HibernateUtil.getSessionFactory();
//			session 					= sessionFactory.openSession();
//			userDetailsBean				= new UserDetailsBean();
//			
//			session.beginTransaction();
//			
//			updateUserDetail(session, userDetailsBean);
//			
//			session.getTransaction().commit();
//			
//			session.flush();
//			
//		} catch (EnjoyException e) {
//			// TODO Auto-generated catch block
//			session.getTransaction().rollback();
//			e.printStackTrace();
//		}finally{
//			session.close();
//		}
//	}
	
//	public UserDetailsBean userSelect(String userId, String pass){
//		logger.info("[UserDetailsDao][userSelect][Begin]");
//		
//		UserDetailsBean 	userDetailsBean = null;
//		SessionFactory 		sessionFactory	= null;
//		Session 			session			= null;
//		List<Userdetail> 	userdetailList	= null;
//		Userdetail 			userdetail		= null;
//		String				hql				= null;
//        String				passWord		= null;
//		int					maxRecord		= 0;
//		DateFormat 			dateFormat		= null;
//        Date 				date			= null;
//		
//		
//		try{
//		    passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
//logger.info("pass ==> " + passWord);
//		    sessionFactory 	= HibernateUtil.getSessionFactory();
//			session 		= sessionFactory.openSession();
//			hql				= "from Userdetail where userId = '" + userId + "'";
////			hql				= "from Userdetail where userId = '" + userId + "' and userPassword = '" + passWord + "'";
//			userdetailList 	= session.createQuery(hql).list();
//			maxRecord       = userdetailList.size();
//			dateFormat 		= new SimpleDateFormat("dd/MM/yyyy");
//		    date 	   		= new Date();
//			
//			for(int i=0;i<maxRecord;i++){
//				userdetail 		= userdetailList.get(i);
//				userDetailsBean	= new UserDetailsBean();
//				
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserUniqueId() 		:: " + userdetail.getUserUniqueId());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserId() 				:: " + userdetail.getUserId());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserName() 			:: " + userdetail.getUserName());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserSurname() 			:: " + userdetail.getUserSurname());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserPrivilege() 		:: " + userdetail.getUserPrivilege());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserLevel() 			:: " + userdetail.getUserLevel());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserStatus() 			:: " + userdetail.getUserStatus());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getFlagChangePassword() 	:: " + userdetail.getFlagChangePassword());
//				logger.debug("[UserDetailsDao][userSelect] userdetail.getUserEmail() 			:: " + userdetail.getUserEmail());
//				
//				userDetailsBean.setUserUniqueId			(userdetail.getUserUniqueId());
//				userDetailsBean.setUserId				(userdetail.getUserId());
//				userDetailsBean.setPwd					(userdetail.getUserPassword());
//				userDetailsBean.setUserName				(userdetail.getUserName());
//				userDetailsBean.setUserSurname			(userdetail.getUserSurname());
//				userDetailsBean.setUserPrivilege		(userdetail.getUserPrivilege());
//				userDetailsBean.setUserLevel			(userdetail.getUserLevel());
//				userDetailsBean.setUserStatus			(userdetail.getUserStatus());
//				userDetailsBean.setFlagChangePassword	(userdetail.getFlagChangePassword());
//				userDetailsBean.setCurrentDate			(dateFormat.format(date));
//				userDetailsBean.setUserEmail			(userdetail.getUserEmail());
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			session.close();
//			sessionFactory	= null;
//			session			= null;
//			userdetailList	= null;
//			userdetail		= null;
//			hql				= null;
//	        passWord		= null;
//			logger.info("[UserDetailsDao][userSelect][End]");
//		}
//		
//		return userDetailsBean;
//	}
	
	public UserDetailsBean userSelect(String userId, String pass){
		logger.info("[userSelect][Begin]");
		
		UserDetailsBean 	userDetailsBean = null;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		String				hql				= null;
        String				passWord		= null;
		DateFormat 			dateFormat		= null;
        Date 				date			= null;
        SQLQuery 			query 			= null;
        List<Object[]>		list			= null;
        Object[] 			row 			= null;
		
		try{
		    passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
		    sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
//			hql				= "select * from userdetails where userId = '" + userId + "'";
			hql				= "select * from userdetails where userId = '" + userId + "' and userPassword = '" + passWord + "'";
			query			= session.createSQLQuery(hql);
			dateFormat 		= new SimpleDateFormat("dd/MM/yyyy");
		    date 	   		= new Date();
		    
		    query.addScalar("userUniqueId"			, new StringType());
		    query.addScalar("userId"				, new StringType());
		    query.addScalar("userName"				, new StringType());
		    query.addScalar("userSurname"			, new StringType());
		    query.addScalar("userEmail"				, new StringType());
		    query.addScalar("userPrivilege"			, new StringType());
		    query.addScalar("userLevel"				, new StringType());
		    query.addScalar("userStatus"			, new StringType());
		    query.addScalar("flagChangePassword"	, new StringType());
		    
		    list		 	= query.list();
		    
		    logger.info("[userSelect] hql :: " + hql);
			logger.info("[userSelect] list :: " + list);
		    
		    if(list!=null && list.size() > 0){
				
		    	row 				= list.get(0);
				userDetailsBean 	= new UserDetailsBean();
				
				logger.info("[userSelect] userUniqueId 			:: " + row[0]);
				logger.info("[userSelect] userId 				:: " + row[1]);
				logger.info("[userSelect] userName 				:: " + row[2]);
				logger.info("[userSelect] userSurname 			:: " + row[3]);
				logger.info("[userSelect] userEmail 			:: " + row[4]);
				logger.info("[userSelect] userPrivilege 		:: " + row[5]);
				logger.info("[userSelect] userLevel 			:: " + row[6]);
				logger.info("[userSelect] userStatus 			:: " + row[7]);
				logger.info("[userSelect] flagChangePassword 	:: " + row[8]);
				
				userDetailsBean.setUserUniqueId			(Integer.parseInt(row[0].toString()));
				userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1].toString()));
				userDetailsBean.setPwd					(passWord);
				userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row[2].toString()));
				userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row[3].toString()));
				userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row[4].toString()));
				userDetailsBean.setUserPrivilege		(EnjoyUtils.nullToStr(row[5].toString()));
				userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row[6].toString()));
				userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[7].toString()));
				userDetailsBean.setFlagChangePassword	(EnjoyUtils.nullToStr(row[8].toString()));
				userDetailsBean.setCurrentDate			(dateFormat.format(date));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
	        passWord		= null;
			logger.info("[userSelect][End]");
		}
		
		return userDetailsBean;
	}
	
	/*ดึงสถานะมาอยู่ใน Combo*/
	public List<RefuserstatusBean> getRefuserstatusCombo(Session session) throws EnjoyException{
		logger.info("[getRefuserstatusCombo][Begin]");
		
		List<Refuserstatus> 		refuserstatusList		= null;
		String						hql						= null;
		RefuserstatusBean			bean					= null;
		List<RefuserstatusBean> 	refuserstatusBeanList	= null;
		
		try{
			
			hql						= "from Refuserstatus";
			refuserstatusList 		= session.createQuery(hql).list();
			refuserstatusBeanList	= new ArrayList<RefuserstatusBean>();
			
			for(Refuserstatus refuserstatus:refuserstatusList){
				bean = new RefuserstatusBean();
				
				bean.setUserStatusCode(String.valueOf(refuserstatus.getUserStatusCode()));
				bean.setUserStatusName(refuserstatus.getUserStatusName());
				
				refuserstatusBeanList.add(bean);
			}
			
			
		}catch(Exception e){
			logger.info("[getRefuserstatusCombo] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			hql						= null;
			logger.info("[getRefuserstatusCombo][End]");
		}
		
		return refuserstatusBeanList;
		
	}
	
	
	/*สิทธิในการใช้งานในระบบ*/
	public List<Userprivilege> getUserprivilege(Session session) throws EnjoyException{
		logger.info("[getUserprivilege][Begin]");
		
		List<Userprivilege> 	refuserstatusList		= null;
		String					hql						= null;
		
		try{
			
			hql					= "from Userprivilege";
			refuserstatusList 	= session.createQuery(hql).list();
			
		}catch(Exception e){
			logger.info("[getUserprivilege] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสิทธิในการใช้งานในระบบ");
		}finally{
			hql						= null;
			logger.info("[getUserprivilege][End]");
		}
		
		return refuserstatusList;
		
	}
	

	/*ดึง List ของผู้ใช้*/
	public List<UserDetailsBean> getListUserdetail(	Session 					session, 
													UserDetailsBean 			userdetailForm,
													Hashtable<String, String>	fUserprivilege) throws EnjoyException{
		logger.info("[getListUserdetail][Begin]");
		
		String					hql						= null;
		Userdetail				userdetail				= null;
		SQLQuery 				query 					= null;
		List<Object[]>			list					= null;
		UserDetailsBean			userDetailsBean			= null;
		Object[] 				row 					= null;
		List<UserDetailsBean> 	listUserDetailsBean 	= new ArrayList<UserDetailsBean>();
		HashMap					hashTable				= new HashMap();
		String[]				arrPrivilegeCode		= null;			
		String					privilegeName			= "";
		
		try{			
			hql					= "select a.userUniqueId"
										+ ", a.userId"
										+ ", a.userName"
										+ ", a.userSurname"
										+ ", a.userEmail"
										+ ", a.userPrivilege"
										+ ", a.userLevel"
										+ ", a.userStatus"
										+ ", a.flagChangePassword"
										+ ", b.userStatusName"
								+ "	from userdetails a, refuserstatus b "
								+ "	where a.userStatus = b.userStatusCode "
								+ " 	and a.userId <> 'admin'";
			
			if(!userdetailForm.getUserName().equals("")){
				hql += " and CONCAT(a.userName, ' ', a.userSurname) like ('" + userdetailForm.getUserName() + "%')";
			}
			if(!userdetailForm.getUserId().equals("")){
				hql += " and a.userId = '" + userdetailForm.getUserId() + "'";
			}
			if(!userdetailForm.getUserStatus().equals("")){
				hql += " and a.userStatus = '" + userdetailForm.getUserStatus() + "'";
			}

			query			= session.createSQLQuery(hql);			
			query.addScalar("userUniqueId"			, new IntegerType());
			query.addScalar("userId"				, new StringType());
			query.addScalar("userName"				, new StringType());
			query.addScalar("userSurname"			, new StringType());
			query.addScalar("userEmail"				, new StringType());
			query.addScalar("userPrivilege"			, new StringType());
			query.addScalar("userLevel"				, new StringType());
			query.addScalar("userStatus"			, new StringType());
			query.addScalar("flagChangePassword"	, new StringType());
			query.addScalar("userStatusName"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getListUserdetail] hql :: " + hql);
			logger.info("[getListUserdetail] list :: " + list);
			
			if(list!=null && list.size() > 0){
				logger.info("[getListUserdetail] list.size() :: " + list.size());
				
				for(int i=0;i<list.size();i++){
					row 				= list.get(i);
					userDetailsBean 	= new UserDetailsBean();
					privilegeName   	= "";
					
					logger.info("[getListUserdetail] userUniqueId 		:: " + row[0]);
					logger.info("[getListUserdetail] userId 			:: " + row[1]);
					logger.info("[getListUserdetail] userName 			:: " + row[2]);
					logger.info("[getListUserdetail] userSurname 		:: " + row[3]);
					logger.info("[getListUserdetail] userEmail 			:: " + row[4]);
					logger.info("[getListUserdetail] userPrivilege 		:: " + row[5]);
					logger.info("[getListUserdetail] userLevel 			:: " + row[6]);
					logger.info("[getListUserdetail] userStatus 		:: " + row[7]);
					logger.info("[getListUserdetail] flagChangePassword :: " + row[8]);
					logger.info("[getListUserdetail] userStatusName 	:: " + row[9]);
					
					arrPrivilegeCode	= EnjoyUtils.nullToStr(row[5].toString()).split("\\,");
					for(int j=0;j<arrPrivilegeCode.length;j++){
						if (! privilegeName.equals("")) privilegeName = privilegeName + "<br>";
						privilegeName   = privilegeName + "- " +fUserprivilege.get(arrPrivilegeCode[j]);
					}
					userDetailsBean.setUserUniqueId			(Integer.parseInt(row[0].toString()));
					userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1].toString()));
					userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row[2].toString()) + "  " + EnjoyUtils.nullToStr(row[3].toString()));
					userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row[3].toString()));
					userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row[4].toString()));
					userDetailsBean.setUserPrivilege		(privilegeName);
					userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row[6].toString()));
					userDetailsBean.setFlagChangePassword	(EnjoyUtils.nullToStr(row[8].toString()));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[9].toString()));
					
					listUserDetailsBean.add(userDetailsBean);
				}	
			}
			
		}catch(Exception e){
			logger.info("[getListUserdetail AAA] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			logger.info("[getListUserdetail][End]");
		}
		
		return listUserDetailsBean;
		
	}
	
	/*ดึงรายละเอียดของผู้ใช้*/
	public UserDetailsBean getUserdetail(Session session, int userUniqueId) throws EnjoyException{
		logger.info("[getUserdetail][Begin]");
		
		String				hql						= null;
		Userdetail			userdetail				= null;
		SQLQuery 			query 					= null;
		List<Object[]>		list					= null;
		UserDetailsBean		userDetailsBean			= null;
		Object[] 			row 					= null;
		
		try{
			
			hql					= "select userUniqueId"
										+ ", userId"
										+ ", userName"
										+ ", userSurname"
										+ ", userEmail"
										+ ", userPrivilege"
										+ ", userLevel"
										+ ", userStatus"
										+ ", flagChangePassword"
								+ "	from userdetails where userUniqueId = " + userUniqueId;
			query			= session.createSQLQuery(hql);
			
			query.addScalar("userUniqueId"			, new IntegerType());
			query.addScalar("userId"				, new StringType());
			query.addScalar("userName"				, new StringType());
			query.addScalar("userSurname"			, new StringType());
			query.addScalar("userEmail"				, new StringType());
			query.addScalar("userPrivilege"			, new StringType());
			query.addScalar("userLevel"				, new StringType());
			query.addScalar("userStatus"			, new StringType());
			query.addScalar("flagChangePassword"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getUserdetail] list :: " + list);
			
			if(list!=null && list.size() > 0){
				logger.info("[getUserdetail] list.size() :: " + list.size());
				
				row 				= list.get(0);
				userDetailsBean 	= new UserDetailsBean();
				
				logger.info("[getUserdetail] userUniqueId 		:: " + row[0]);
				logger.info("[getUserdetail] userId 			:: " + row[1]);
				logger.info("[getUserdetail] userName 			:: " + row[2]);
				logger.info("[getUserdetail] userSurname 		:: " + row[3]);
				logger.info("[getUserdetail] userEmail 			:: " + row[4]);
				logger.info("[getUserdetail] userPrivilege 		:: " + row[5]);
				logger.info("[getUserdetail] userLevel 			:: " + row[6]);
				logger.info("[getUserdetail] userStatus 		:: " + row[7]);
				logger.info("[getUserdetail] flagChangePassword :: " + row[8]);
				
				userDetailsBean.setUserUniqueId			(Integer.parseInt(row[0].toString()));
				userDetailsBean.setUserId				(row[1].toString());
				userDetailsBean.setUserName				(row[2].toString());
				userDetailsBean.setUserSurname			(row[3].toString());
				userDetailsBean.setUserEmail			(row[4].toString());
				userDetailsBean.setUserPrivilege		(row[5].toString());
				userDetailsBean.setUserLevel			(row[6].toString());
				userDetailsBean.setUserStatus			(row[7].toString());
				userDetailsBean.setFlagChangePassword	(row[8].toString());
			}
			
		}catch(Exception e){
			logger.info("[getUserdetail] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			logger.info("[getUserdetail][End]");
		}
		
		return userDetailsBean;
		
	}
	
	public void updateUserPassword(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[updateUserPassword][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		try{
			hql				= "update  Userdetail set userPassword = :userPassword"
										+ ",flagChangePassword = 'N'"
										+ " where userUniqueId = :userUniqueId";
			
			query = session.createQuery(hql);
			query.setParameter("userPassword"		, userDetailsBean.getPwd());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			result = query.executeUpdate();			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateUserPassword][End]");
		}
	}
	
	public int checkDupUserId(Session session, String userId, String pageMode, int userUniqueId) throws EnjoyException{
		logger.info("[checkDupUserId][Begin]");
		
		List<String> 					returnList 							= null;
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			returnList		= new ArrayList<String>();
			
			hql				= "Select count(*) cou from userdetails where userId = '" + userId + "'";
			
			logger.info("[checkDupUserId] pageMode :: " + pageMode);
			
			if(pageMode.equals(UserDetailsMaintananceForm.EDIT)){
				hql = hql + " and userUniqueId <> " + userUniqueId;
			}
			
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupUserId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupUserId][End]");
		}
		
		return result;
	}
	
	public void insertNewUser(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[insertNewUser][Begin]");
		
		Userdetail						userdetailDb						= null;
		
		try{
			
			userdetailDb = new Userdetail();
			
			userdetailDb.setUserId(userDetailsBean.getUserId());
			userdetailDb.setUserPassword(userDetailsBean.getPwd());
			userdetailDb.setUserName(userDetailsBean.getUserName());
			userdetailDb.setUserSurname(userDetailsBean.getUserSurname());
			userdetailDb.setUserEmail(userDetailsBean.getUserEmail());
			userdetailDb.setUserPrivilege(userDetailsBean.getUserPrivilege());
			userdetailDb.setUserLevel(userDetailsBean.getUserLevel());
			userdetailDb.setUserStatus(userDetailsBean.getUserStatus());
			userdetailDb.setFlagChangePassword(userDetailsBean.getFlagChangePassword());
			
			
			session.saveOrUpdate(userdetailDb);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล");
		}finally{
			
			userdetailDb = null;
			logger.info("[insertNewUser][End]");
		}
	}
	
	public void updateUserDetail(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[updateUserDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "update  Userdetail set userId 			= :userId"
												+ ", userName			= :userName"
												+ ", userSurname		= :userSurname"
												+ ", userEmail			= :userEmail"
												+ ", userPrivilege		= :userPrivilege"
												+ ", userLevel			= :userLevel"
												+ ", userStatus			= :userStatus"
												+ ", flagChangePassword = :flagChangePassword"
										+ " where userUniqueId = :userUniqueId";
			
			query = session.createQuery(hql);
			query.setParameter("userId"				, userDetailsBean.getUserId());
			query.setParameter("userName"			, userDetailsBean.getUserName());
			query.setParameter("userSurname"		, userDetailsBean.getUserSurname());
			query.setParameter("userEmail"			, userDetailsBean.getUserEmail());
			query.setParameter("userPrivilege"		, userDetailsBean.getUserPrivilege());
			query.setParameter("userLevel"			, userDetailsBean.getUserLevel());
			query.setParameter("userStatus"			, userDetailsBean.getUserStatus());
			query.setParameter("flagChangePassword"	, userDetailsBean.getFlagChangePassword());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			result = query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateUserDetail][End]");
		}
	}
	
	public int lastId(Session session) throws EnjoyException{
		logger.info("[lastId][Begin]");
		
		List<String> 					returnList 							= null;
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			returnList		= new ArrayList<String>();
			
			hql				= "Select max(userUniqueId) lastId from userdetails";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[lastId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[lastId][End]");
		}
		
		return result;
	}
	
	public void changePassword(Session session, UserDetailsBean userDetailsBean) throws EnjoyException{
		logger.info("[changePassword][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "update  Userdetail set userPassword 		= :userPassword"
										+ " where userUniqueId = :userUniqueId";
			
			query = session.createQuery(hql);
			query.setParameter("userPassword"		, userDetailsBean.getPwd());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			result = query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[changePassword][End]");
		}
	}
	
	
}
