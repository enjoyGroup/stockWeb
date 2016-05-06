
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsLookUpForm;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.model.Userdetail;
import th.go.stock.app.enjoy.model.Userprivilege;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class UserDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsDao.class);
	
	public UserDetailsBean userSelect(String userId, String pass){
		logger.info("[userSelect][Begin]");
		
		UserDetailsBean 	userDetailsBean = null;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		String				hql				= null;
        String				passWord		= null;
        SQLQuery 			query 			= null;
        List<Object[]>		list			= null;
        Object[] 			row 			= null;
        String				flagChkCompany	= null;
		
		try{
		    passWord		= EnjoyEncryptDecrypt.enCryption(userId, pass);
		    sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
//			hql				= "select * from userdetails where userId = '" + userId + "'";
			hql				= "select * from userdetails where userId = '" + userId + "' and userPassword = '" + passWord + "'";
			query			= session.createSQLQuery(hql);
		    
		    query.addScalar("userUniqueId"			, new StringType());
		    query.addScalar("userId"				, new StringType());
		    query.addScalar("userName"				, new StringType());
		    query.addScalar("userSurname"			, new StringType());
		    query.addScalar("userEmail"				, new StringType());
		    query.addScalar("userPrivilege"			, new StringType());
		    query.addScalar("userLevel"				, new StringType());
		    query.addScalar("userStatus"			, new StringType());
		    query.addScalar("flagChangePassword"	, new StringType());
		    query.addScalar("flagAlertStock"		, new StringType());
		    query.addScalar("flagSalesman"			, new StringType());
		    query.addScalar("commission"			, new StringType());
		    query.addScalar("remark"				, new StringType());
		    
		    list		 	= query.list();
		    
		    logger.info("[userSelect] hql :: " + hql);
			logger.info("[userSelect] list :: " + list);
		    
		    if(list!=null && list.size() > 0){
				
		    	row 				= list.get(0);
				userDetailsBean 	= new UserDetailsBean();
				flagChkCompany		= this.flagChkCompany(session);
				
				logger.info("[userSelect] userUniqueId 			:: " + row[0]);
				logger.info("[userSelect] userId 				:: " + row[1]);
				logger.info("[userSelect] userName 				:: " + row[2]);
				logger.info("[userSelect] userSurname 			:: " + row[3]);
				logger.info("[userSelect] userEmail 			:: " + row[4]);
				logger.info("[userSelect] userPrivilege 		:: " + row[5]);
				logger.info("[userSelect] userLevel 			:: " + row[6]);
				logger.info("[userSelect] userStatus 			:: " + row[7]);
				logger.info("[userSelect] flagChangePassword 	:: " + row[8]);
				logger.info("[userSelect] flagAlertStock 		:: " + row[9]);
				logger.info("[userSelect] flagChkCompany 		:: " + flagChkCompany);
				logger.info("[userSelect] flagSalesman 			:: " + row[10]);
				logger.info("[userSelect] commission 			:: " + row[11]);
				logger.info("[userSelect] remark 				:: " + row[12]);
				
				userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row[0]));
				userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1]));
				userDetailsBean.setPwd					(passWord);
				userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row[2]));
				userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row[3]));
				
				userDetailsBean.setUserFullName(userDetailsBean.getUserName().concat(" ").concat(userDetailsBean.getUserSurname()));
				
				userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row[4]));
				userDetailsBean.setUserPrivilege		(EnjoyUtils.nullToStr(row[5]));
				userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row[6]));
				userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[7]));
				userDetailsBean.setFlagChangePassword	(EnjoyUtils.chkBoxtoDb(row[8]));
				userDetailsBean.setCurrentDate			(EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
				userDetailsBean.setFlagAlertStock		(EnjoyUtils.chkBoxtoDb(row[9]));
				userDetailsBean.setFlagChkCompany		(flagChkCompany);
				userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row[10]));
				userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row[11], 2));
				userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row[12]));
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
	public List<ComboBean> getRefuserstatusCombo() throws EnjoyException{
		logger.info("[getRefuserstatusCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			hql				= "select * from refuserstatus";
			query			= session.createSQLQuery(hql);
			query.addScalar("userStatusCode"		, new StringType());
			query.addScalar("userStatusName"		, new StringType());
			
			list		 	= query.list();
			
//			comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getRefuserstatusCombo] userStatusCode :: " + row[0]);
				logger.info("[getRefuserstatusCombo] userStatusName :: " + row[1]);
				
				comboBean.setCode(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			logger.info("[getRefuserstatusCombo] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getRefuserstatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	
	/*สิทธิในการใช้งานในระบบ*/
	public List<Userprivilege> getUserprivilege() throws EnjoyException{
		logger.info("[getUserprivilege][Begin]");
		
		List<Userprivilege> 	refuserstatusList		= null;
		String					hql						= null;
		SessionFactory 			sessionFactory			= null;
		Session 				session					= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql					= "from Userprivilege";
			refuserstatusList 	= session.createQuery(hql).list();
			
		}catch(Exception e){
			logger.info("[getUserprivilege] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสิทธิในการใช้งานในระบบ");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
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
		SQLQuery 				query 					= null;
		List<Object[]>			list					= null;
		UserDetailsBean			userDetailsBean			= null;
		Object[] 				row 					= null;
		List<UserDetailsBean> 	listUserDetailsBean 	= new ArrayList<UserDetailsBean>();
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
										+ ", a.flagAlertStock"
										+ ", a.flagSalesman"
										+ ", a.commission"
										+ ", a.remark"
										+ ", b.userStatusName"
								+ "	from userdetails a, refuserstatus b "
								+ "	where b.userStatusCode = a.userStatus "
								+ " 	and a.userId <> 'admin'";
			
			if(!userdetailForm.getUserName().equals("")){
				hql += " and CONCAT(a.userName, ' ', a.userSurname) like ('" + userdetailForm.getUserName() + "%')";
			}
			
			if(!userdetailForm.getUserId().equals("")){
				hql += " and a.userId like ('" + userdetailForm.getUserId() + "%')";
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
			query.addScalar("flagAlertStock"		, new StringType());
			query.addScalar("flagSalesman"			, new StringType());
			query.addScalar("commission"			, new StringType());
			query.addScalar("remark"				, new StringType());
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
					logger.info("[getListUserdetail] flagAlertStock 	:: " + row[9]);
					logger.info("[getListUserdetail] flagSalesman 		:: " + row[10]);
					logger.info("[getListUserdetail] commission 		:: " + row[11]);
					logger.info("[getListUserdetail] remark 			:: " + row[12]);
					logger.info("[getListUserdetail] userStatusName 	:: " + row[13]);
					
					arrPrivilegeCode	= EnjoyUtils.nullToStr(row[5]).split("\\,");
					for(int j=0;j<arrPrivilegeCode.length;j++){
						if (! privilegeName.equals("")) privilegeName = privilegeName + "<br>";
						privilegeName   = privilegeName + "- " +fUserprivilege.get(arrPrivilegeCode[j]);
					}
					userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row[0]));
					userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1]));
					userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row[2]) + "  " + EnjoyUtils.nullToStr(row[3]));
					userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row[4]));
					userDetailsBean.setUserPrivilege		(privilegeName);
					userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row[6]));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[7]));
					userDetailsBean.setFlagChangePassword	(EnjoyUtils.chkBoxtoDb(row[8]));
					userDetailsBean.setFlagAlertStock		(EnjoyUtils.chkBoxtoDb(row[9]));
					userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row[10]));
					userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row[11], 2));
					userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row[12]));
					userDetailsBean.setUserStatusName		(EnjoyUtils.nullToStr(row[13]));
					
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
										+ ", flagAlertStock"
										+ ", flagSalesman"
										+ ", commission"
										+ ", remark"
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
			query.addScalar("flagAlertStock"		, new StringType());
			query.addScalar("flagSalesman"			, new StringType());
			query.addScalar("commission"			, new StringType());
			query.addScalar("remark"				, new StringType());
			
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
				logger.info("[getUserdetail] flagAlertStock 	:: " + row[9]);
				logger.info("[getListUserdetail] flagSalesman 	:: " + row[10]);
				logger.info("[getListUserdetail] commission 	:: " + row[11]);
				logger.info("[getListUserdetail] remark 		:: " + row[12]);
				
				userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row[0]));
				userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1]));
				userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row[2]));
				userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row[3]));
				userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row[4]));
				userDetailsBean.setUserPrivilege		(EnjoyUtils.nullToStr(row[5]));
				userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row[6]));
				userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[7]));
				userDetailsBean.setFlagChangePassword	(EnjoyUtils.nullToStr(row[8]));
				userDetailsBean.setFlagAlertStock		(EnjoyUtils.nullToStr(row[9]));
				userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row[10]));
				userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row[11], 2));
				userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row[12]));
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
			userdetailDb.setFlagAlertStock(userDetailsBean.getFlagAlertStock());
			userdetailDb.setFlagSalesman(userDetailsBean.getFlagSalesman());
			userdetailDb.setCommission(EnjoyUtils.parseBigDecimal(userDetailsBean.getCommission()));
			userdetailDb.setRemark(userDetailsBean.getRemark());
			
			
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
												+ ", flagAlertStock 	= :flagAlertStock"
												+ ", flagSalesman 		= :flagSalesman"
												+ ", commission 		= :commission"
												+ ", remark 			= :remark"
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
			query.setParameter("flagAlertStock"		, userDetailsBean.getFlagAlertStock());
			query.setParameter("flagSalesman"		, userDetailsBean.getFlagSalesman());
			query.setParameter("commission"			, EnjoyUtils.parseBigDecimal(userDetailsBean.getCommission()));
			query.setParameter("remark"				, userDetailsBean.getRemark());
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
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			
			hql				= "Select max(userUniqueId) lastId from userdetails";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0)==null?0:list.get(0);
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
		
		
		try{
			hql				= "update  Userdetail set userPassword 		= :userPassword"
										+ " where userUniqueId = :userUniqueId";
			
			query = session.createQuery(hql);
			query.setParameter("userPassword"		, userDetailsBean.getPwd());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			query.executeUpdate();
			
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
	
	public List<ComboBean> userFullNameList(String userFullName){
		logger.info("[userFullNameList][Begin]");
		
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
			hql 				= " select userUniqueId, CONCAT(userName, ' ', userSurname) userFullName"
									+ " from userdetails"
									+ " where CONCAT(userName, ' ', userSurname) like ('"+userFullName+"%')"
											+ " and userStatus 	= 'A'"
											+ " and userId 		<> 'admin'"
									+ " order by CONCAT(userName, ' ', userSurname) asc limit 10 ";
			
			logger.info("[userFullNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("userUniqueId"			, new StringType());
			query.addScalar("userFullName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[userFullNameList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("userUniqueId 		:: " + row[0]);
				logger.info("userFullName 		:: " + row[1]);
				
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
			logger.info("[userFullNameList][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> getAlluserFullName(String userFullName){
		logger.info("[getAlluserFullName][Begin]");
		
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
			hql 				= " select userUniqueId, CONCAT(userName, ' ', userSurname) userFullName"
									+ " from userdetails"
									+ " where CONCAT(userName, ' ', userSurname) like ('"+userFullName+"%')"
									+ " order by CONCAT(userName, ' ', userSurname) asc limit 10 ";
			
			logger.info("[getAlluserFullName] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("userUniqueId"			, new StringType());
			query.addScalar("userFullName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getAlluserFullName] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("userUniqueId 		:: " + row[0]);
				logger.info("userFullName 		:: " + row[1]);
				
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
			logger.info("[getAlluserFullName][End]");
		}
		
		return comboList;
	}
	
	public List<UserDetailsBean> getUserDetailsLookUpList(	Session 					session, 
															UserDetailsLookUpForm 		form) throws EnjoyException{
		logger.info("[getUserDetailsLookUpList][Begin]");
		
		String					hql						= null;
		SQLQuery 				query 					= null;
		List<Object[]>			list					= null;
		UserDetailsBean 		userDetailsBean			= null;
		List<UserDetailsBean> 	listData 				= new ArrayList<UserDetailsBean>();
		String					find					= null;
		
		try{
			find				= form.getFind();
			hql					= "select * from (select a.*, CONCAT(a.userName, ' ', a.userSurname) userFullName, b.userStatusName"
													+ "	from userdetails a, refuserstatus b "
													+ "	where b.userStatusCode = a.userStatus"
													+ " 	and a.userStatus = 'A'"
													+ " 	and a.userId <> 'admin') t where 1=1";
			
			if(find!=null && !find.equals("")){
				hql += " and t." + form.getColumn();
				
				if(form.getLikeFlag().equals("Y")){
					hql += " like ('" + find + "%')";
				}else{
					hql += " = '" + find + "'";
				}
				
			}
			
			hql += " order by t." + form.getOrderBy() + " " + form.getSortBy();
			
			logger.info("[getUserDetailsLookUpList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("userUniqueId"			, new IntegerType());
			query.addScalar("userId"				, new StringType());
			query.addScalar("userFullName"			, new StringType());
			query.addScalar("userStatus"			, new StringType());
			query.addScalar("userStatusName"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getUserDetailsLookUpList] list :: " + list);
			
			if(list!=null){
				logger.info("[getUserDetailsLookUpList] list.size() :: " + list.size());
				
				for(Object[] row:list){
					userDetailsBean 		= new UserDetailsBean();
					
					logger.info("[getUserDetailsLookUpList] userUniqueId 	:: " + row[0]);
					logger.info("[getUserDetailsLookUpList] userId 			:: " + row[1]);
					logger.info("[getUserDetailsLookUpList] userFullName 	:: " + row[2]);
					logger.info("[getUserDetailsLookUpList] userStatus 		:: " + row[3]);
					logger.info("[getUserDetailsLookUpList] userStatusName 	:: " + row[4]);
					
					
					userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row[0]));
					userDetailsBean.setUserId				(EnjoyUtils.nullToStr(row[1]));
					userDetailsBean.setUserFullName			(EnjoyUtils.nullToStr(row[2]));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row[3]));
					userDetailsBean.setUserStatusName		(EnjoyUtils.nullToStr(row[4]));
					
					listData.add(userDetailsBean);
				}	
			}
			
		}catch(Exception e){
			logger.info("[getUserDetailsLookUpList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			logger.info("[getUserDetailsLookUpList][End]");
		}
		
		return listData;
		
	}
	
	public String flagChkCompany(Session session) throws EnjoyException{
		logger.info("[flagChkCompany][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		String							flagChkCompany						= "Y";
		
		try{
			
			hql				= "select count(*) cou FROM company";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
				if(result > 0)flagChkCompany	= "N";
			}
			
			logger.info("[flagChkCompany] flagChkCompany :: " + flagChkCompany);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[flagChkCompany][End]");
		}
		
		return flagChkCompany;
	}
	
	
}
