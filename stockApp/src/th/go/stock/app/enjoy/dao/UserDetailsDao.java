
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsLookUpForm;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Userdetail;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class UserDetailsDao extends DaoControl{
	
	public UserDetailsDao(){
		setLogger(EnjoyLogger.getLogger(UserDetailsDao.class));
		super.init();
	}
	
	public UserDetailsBean userSelect(String userEmail, String pass){
		getLogger().info("[userSelect][Begin]");
		
		UserDetailsBean 				userDetailsBean 	= null;
		String							hql					= null;
        String							passWord			= null;
        HashMap<String, Object> 		row 				= null;
        String							flagChkCompany		= null;
        HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
		    passWord		= EnjoyEncryptDecrypt.enCryption(userEmail, pass);
			hql				= "select * from userdetails where userEmail = :userEmail and userPassword = :passWord";
			
			//Criteria
			param.put("userEmail"	, userEmail);
			param.put("passWord"	, passWord);
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userEmail");
			columnList.add("userName");
			columnList.add("userSurname");
			columnList.add("userPrivilege");
			columnList.add("userLevel");
			columnList.add("userStatus");
			columnList.add("flagChangePassword");
			columnList.add("flagAlertStock");
			columnList.add("flagSalesman");
			columnList.add("commission");
			columnList.add("remark");
		    
			resultList = getResult(hql, param, columnList);
		    
		    if(resultList!=null && resultList.size() > 0){
				
		    	row 				= resultList.get(0);
				userDetailsBean 	= new UserDetailsBean();
				flagChkCompany		= this.flagChkCompany();
				
				userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row.get("userUniqueId")));
				userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row.get("userEmail")));
				userDetailsBean.setPwd					(passWord);
				userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row.get("userName")));
				userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row.get("userSurname")));
				
				userDetailsBean.setUserFullName(userDetailsBean.getUserName().concat(" ").concat(userDetailsBean.getUserSurname()));
				
				userDetailsBean.setUserPrivilege		(EnjoyUtils.nullToStr(row.get("userPrivilege")));
				userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row.get("userLevel")));
				userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row.get("userStatus")));
				userDetailsBean.setFlagChangePassword	(EnjoyUtils.chkBoxtoDb(row.get("flagChangePassword")));
				userDetailsBean.setCurrentDate			(EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
				userDetailsBean.setFlagAlertStock		(EnjoyUtils.chkBoxtoDb(row.get("flagAlertStock")));
				userDetailsBean.setFlagChkCompany		(flagChkCompany);
				userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row.get("flagSalesman")));
				userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row.get("commission"), 2));
				userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row.get("remark")));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			hql				= null;
	        passWord		= null;
			getLogger().info("[userSelect][End]");
		}
		
		return userDetailsBean;
	}
	
	/*ดึงสถานะมาอยู่ใน Combo*/
	public List<ComboBean> getRefuserstatusCombo() throws EnjoyException{
		getLogger().info("[getRefuserstatusCombo][Begin]");
		
		String							hql					= null;
		ComboBean						comboBean			= null;
		List<ComboBean> 				comboList			= new ArrayList<ComboBean>();
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			hql				= "select * from refuserstatus";
			
			//Column select
			columnList.add("userStatusCode");
			columnList.add("userStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean();
				
				comboBean.setCode(EnjoyUtils.nullToStr(row.get("userStatusCode")));
				comboBean.setDesc(EnjoyUtils.nullToStr(row.get("userStatusName")));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			getLogger().info("[getRefuserstatusCombo] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			hql				= null;
			getLogger().info("[getRefuserstatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	
	/*สิทธิในการใช้งานในระบบ*/
	public List<UserPrivilegeBean> getUserprivilege() throws EnjoyException{
		getLogger().info("[getUserprivilege][Begin]");
		
		List<UserPrivilegeBean> 		refuserstatusList		= new ArrayList<UserPrivilegeBean>();
		String							hql						= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		UserPrivilegeBean				userPrivilegeBean		= null;
		
		try{
			hql					= "select * from userprivilege where flagDispaly = 'Y'";
			
			//Column select
			columnList.add("privilegeCode");
			columnList.add("privilegeName");
			columnList.add("pagesCode");
			
			resultList = getResult(hql, param, columnList);
			for(HashMap<String, Object> row:resultList){
				userPrivilegeBean = new UserPrivilegeBean();
				
				userPrivilegeBean.setPrivilegeCode(EnjoyUtils.nullToStr(row.get("privilegeCode")));
				userPrivilegeBean.setPrivilegeName(EnjoyUtils.nullToStr(row.get("privilegeName")));
				userPrivilegeBean.setPagesCode(EnjoyUtils.nullToStr(row.get("pagesCode")));
				
				refuserstatusList.add(userPrivilegeBean);
			}
			
		}catch(Exception e){
			getLogger().info("[getUserprivilege] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสิทธิในการใช้งานในระบบ");
		}finally{
			hql				= null;
			getLogger().info("[getUserprivilege][End]");
		}
		
		return refuserstatusList;
		
	}
	

	/*ดึง List ของผู้ใช้*/
	public List<UserDetailsBean> getListUserdetail(	UserDetailsBean 			userdetailForm,
													Hashtable<String, String>	fUserprivilege) throws EnjoyException{
		getLogger().info("[getListUserdetail][Begin]");
		
		String							hql						= null;
		UserDetailsBean					userDetailsBean			= null;
		List<UserDetailsBean> 			listUserDetailsBean 	= new ArrayList<UserDetailsBean>();
		String[]						arrPrivilegeCode		= null;			
		String							privilegeName			= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			
			if(userdetailForm.getUserUniqueId()==1){
				hql		= "select a.*"
						+ ", b.userStatusName"
						+ "	from userdetails a"
						+ "		inner join refuserstatus b on b.userStatusCode = a.userStatus"
						+ "	where a.userUniqueId <> 1";
			}else{
				hql		= "select a.*"
						+ ", b.userStatusName"
						+ "	from userdetails a"
						+ "		inner join refuserstatus b on b.userStatusCode = a.userStatus"
						+ "		inner join relationuserncompany c on c.userUniqueId = a.userUniqueId and c.tin = :tin"
						+ "	where a.userUniqueId <> 1";
				
				param.put("tin"	, userdetailForm.getTin());
			}
			
			if(!userdetailForm.getUserName().equals("")){
				hql += " and CONCAT(a.userName, ' ', a.userSurname) LIKE CONCAT(:userName, '%')";
				param.put("userName"	, userdetailForm.getUserName());
			}
			
			if(!userdetailForm.getUserEmail().equals("")){
				hql += " and a.userEmail LIKE CONCAT(:userEmail, '%')";
				param.put("userEmail"	, userdetailForm.getUserEmail());
			}
			
			if(!userdetailForm.getUserStatus().equals("")){
				hql += " and a.userStatus = :userStatus";
				param.put("userStatus"	, userdetailForm.getUserStatus());
			}
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userEmail");
			columnList.add("userName");
			columnList.add("userSurname");
			columnList.add("userPrivilege");
			columnList.add("userLevel");
			columnList.add("userStatus");
			columnList.add("flagChangePassword");
			columnList.add("flagAlertStock");
			columnList.add("flagSalesman");
			columnList.add("commission");
			columnList.add("remark");
			columnList.add("userStatusName");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				userDetailsBean 	= new UserDetailsBean();
				privilegeName   	= "";
				
				arrPrivilegeCode	= EnjoyUtils.nullToStr(row.get("userPrivilege")).split("\\,");
				for(int j=0;j<arrPrivilegeCode.length;j++){
					if (! privilegeName.equals("")) privilegeName = privilegeName + "<br>";
					privilegeName   = privilegeName + "- " +fUserprivilege.get(arrPrivilegeCode[j]);
				}
				userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row.get("userUniqueId")));
				userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row.get("userEmail")));
				userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row.get("userName")) + "  " + EnjoyUtils.nullToStr(row.get("userSurname")));
				userDetailsBean.setUserPrivilege		(privilegeName);
				userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row.get("userLevel")));
				userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row.get("userStatus")));
				userDetailsBean.setFlagChangePassword	(EnjoyUtils.chkBoxtoDb(row.get("flagChangePassword")));
				userDetailsBean.setFlagAlertStock		(EnjoyUtils.chkBoxtoDb(row.get("flagAlertStock")));
				userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row.get("flagSalesman")));
				userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row.get("commission"), 2));
				userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row.get("remark")));
				userDetailsBean.setUserStatusName		(EnjoyUtils.nullToStr(row.get("userStatusName")));
				
				listUserDetailsBean.add(userDetailsBean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			getLogger().info("[getListUserdetail][End]");
		}
		
		return listUserDetailsBean;
		
	}
	
	/*ดึงรายละเอียดของผู้ใช้*/
	public UserDetailsBean getUserdetail(int userUniqueId) throws EnjoyException{
		getLogger().info("[getUserdetail][Begin]");
		
		String							hql					= null;
		UserDetailsBean					userDetailsBean		= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			
			hql					= "select * from userdetails where userUniqueId = :userUniqueId";
			
			//Criteria
			param.put("userUniqueId"	, userUniqueId);
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userEmail");
			columnList.add("userPassword");
			columnList.add("userName");
			columnList.add("userSurname");
			columnList.add("userPrivilege");
			columnList.add("userLevel");
			columnList.add("userStatus");
			columnList.add("flagChangePassword");
			columnList.add("flagAlertStock");
			columnList.add("flagSalesman");
			columnList.add("commission");
			columnList.add("remark");
			
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList!=null && resultList.size() == 1){
				for(HashMap<String, Object>	row:resultList){
					userDetailsBean 	= new UserDetailsBean();
					
					userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row.get("userUniqueId")));
					userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row.get("userEmail")));
					userDetailsBean.setPwd					(EnjoyUtils.nullToStr(row.get("userPassword")));
					userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row.get("userName")));
					userDetailsBean.setUserSurname			(EnjoyUtils.nullToStr(row.get("userSurname")));
					userDetailsBean.setUserPrivilege		(EnjoyUtils.nullToStr(row.get("userPrivilege")));
					userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row.get("userLevel")));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row.get("userStatus")));
					userDetailsBean.setFlagChangePassword	(EnjoyUtils.nullToStr(row.get("flagChangePassword")));
					userDetailsBean.setFlagAlertStock		(EnjoyUtils.nullToStr(row.get("flagAlertStock")));
					userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row.get("flagSalesman")));
					userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row.get("commission"), 2));
					userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row.get("remark")));
				}
			}
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			getLogger().info("[getUserdetail][End]");
		}
		
		return userDetailsBean;
		
	}
	
	public void updateUserPassword(UserDetailsBean userDetailsBean) throws EnjoyException{
		getLogger().info("[updateUserPassword][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Userdetail set userPassword = :userPassword"
										+ ",flagChangePassword = 'N'"
										+ " where userUniqueId = :userUniqueId";
			
			query = createQuery(hql);
			query.setParameter("userPassword"		, userDetailsBean.getPwd());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			query.executeUpdate();			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateUserPassword][End]");
		}
	}
	
	public int checkDupUserEmail(String userEmail, String pageMode, int userUniqueId) throws EnjoyException{
		getLogger().info("[checkDupUserId][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql		= "select count(userEmail) cou from userdetails where userEmail = :userEmail and userUniqueId <> 1";
			
			//Criteria
			param.put("userEmail"	, userEmail);
			
			getLogger().info("[checkDupUserId] pageMode :: " + pageMode);
			
			if(pageMode.equals(UserDetailsMaintananceForm.EDIT)){
				hql = hql + " and userUniqueId <> :userUniqueId";
				param.put("userUniqueId"	, userUniqueId);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupUserId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			getLogger().info("[checkDupUserId][End]");
		}
		
		return result;
	}
	
	public void insertNewUser(UserDetailsBean userDetailsBean) throws EnjoyException{
		getLogger().info("[insertNewUser][Begin]");
		
		Userdetail						userdetailDb						= null;
		
		try{
			
			userdetailDb = new Userdetail();
			
			userdetailDb.setUserEmail(userDetailsBean.getUserEmail());
			userdetailDb.setUserPassword(userDetailsBean.getPwd());
			userdetailDb.setUserName(userDetailsBean.getUserName());
			userdetailDb.setUserSurname(userDetailsBean.getUserSurname());
			userdetailDb.setUserPrivilege(userDetailsBean.getUserPrivilege());
			userdetailDb.setUserLevel(userDetailsBean.getUserLevel());
			userdetailDb.setUserStatus(userDetailsBean.getUserStatus());
			userdetailDb.setFlagChangePassword(userDetailsBean.getFlagChangePassword());
			userdetailDb.setFlagAlertStock(userDetailsBean.getFlagAlertStock());
			userdetailDb.setFlagSalesman(userDetailsBean.getFlagSalesman());
			userdetailDb.setCommission(EnjoyUtils.parseBigDecimal(userDetailsBean.getCommission()));
			userdetailDb.setRemark(userDetailsBean.getRemark());
			
			insertData(userdetailDb);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการบันทึกข้อมูล");
		}finally{
			
			userdetailDb = null;
			getLogger().info("[insertNewUser][End]");
		}
	}
	
	public void updateUserDetail(UserDetailsBean userDetailsBean) throws EnjoyException{
		getLogger().info("[updateUserDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Userdetail set userEmail 		= :userEmail"
												+ ", userName			= :userName"
												+ ", userSurname		= :userSurname"
												+ ", userPrivilege		= :userPrivilege"
												+ ", userLevel			= :userLevel"
												+ ", userStatus			= :userStatus"
												+ ", flagChangePassword = :flagChangePassword"
												+ ", flagAlertStock 	= :flagAlertStock"
												+ ", flagSalesman 		= :flagSalesman"
												+ ", commission 		= :commission"
												+ ", remark 			= :remark"
										+ " where userUniqueId = :userUniqueId";
			
			query = createQuery(hql);
			query.setParameter("userEmail"			, userDetailsBean.getUserEmail());
			query.setParameter("userName"			, userDetailsBean.getUserName());
			query.setParameter("userSurname"		, userDetailsBean.getUserSurname());
			query.setParameter("userPrivilege"		, userDetailsBean.getUserPrivilege());
			query.setParameter("userLevel"			, userDetailsBean.getUserLevel());
			query.setParameter("userStatus"			, userDetailsBean.getUserStatus());
			query.setParameter("flagChangePassword"	, userDetailsBean.getFlagChangePassword());
			query.setParameter("flagAlertStock"		, userDetailsBean.getFlagAlertStock());
			query.setParameter("flagSalesman"		, userDetailsBean.getFlagSalesman());
			query.setParameter("commission"			, EnjoyUtils.parseBigDecimal(userDetailsBean.getCommission()));
			query.setParameter("remark"				, userDetailsBean.getRemark());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateUserDetail][End]");
		}
	}
	
	public int lastId() throws EnjoyException{
		getLogger().info("[lastId][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			
			hql				= "select max(userUniqueId) lastId from userdetails";
			
			resultList = getResult(hql, param, "lastId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[lastId] result 			:: " + result);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			getLogger().info("[lastId][End]");
		}
		
		return result;
	}
	
	public void changePassword(UserDetailsBean userDetailsBean) throws EnjoyException{
		getLogger().info("[changePassword][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		
		try{
			hql				= "update  Userdetail set userPassword 		= :userPassword"
										+ " where userUniqueId = :userUniqueId";
			
			query = createQuery(hql);
			query.setParameter("userPassword"		, userDetailsBean.getPwd());
			query.setParameter("userUniqueId"		, userDetailsBean.getUserUniqueId());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการอัพเดทข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[changePassword][End]");
		}
	}
	
	public List<ComboBean> userFullNameList(String userFullName, String tin, int userUniqueId){
		getLogger().info("[userFullNameList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList			=  new ArrayList<ComboBean>();
			
			if(userUniqueId==1){
				hql 	= "select t.userUniqueId, CONCAT(t.userName, ' ', t.userSurname) userFullName"
						+ " from userdetails t"
						+ " where CONCAT(t.userName, ' ', t.userSurname) :userFullName"
						+ " 	and t.userStatus 	= 'A'"
						+ " 	and t.userUniqueId 	<> 1"
						+ " order by CONCAT(t.userName, ' ', t.userSurname) asc limit 10 ";
			}else{
				hql 	= "select t.userUniqueId, CONCAT(t.userName, ' ', t.userSurname) userFullName"
						+ " from userdetails t"
						+ "		inner join relationuserncompany a on a.userUniqueId = t.userUniqueId and a.tin = :tin"
						+ " where CONCAT(t.userName, ' ', t.userSurname) LIKE CONCAT(:userFullName, '%')"
						+ " 	and t.userStatus 	= 'A'"
						+ " 	and t.userUniqueId 	<> 1"
						+ " order by CONCAT(t.userName, ' ', t.userSurname) asc limit 10 ";
				
				param.put("tin"				, tin);
			}
			
			param.put("userFullName"	, userFullName);
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userFullName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("userUniqueId")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("userFullName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[userFullNameList][End]");
		}
		
		return comboList;
	}
	
	public List<UserDetailsBean> getUserDetailsLookUpList(UserDetailsLookUpForm form) throws EnjoyException{
		getLogger().info("[getUserDetailsLookUpList][Begin]");
		
		String							hql					= null;
		UserDetailsBean 				userDetailsBean		= null;
		List<UserDetailsBean> 			listData 			= new ArrayList<UserDetailsBean>();
		String							find				= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			find				= form.getFind();
			
			if("".equals(form.getTin())){
				hql		= "select t.* from (select a.*, CONCAT(a.userName, ' ', a.userSurname) userFullName, b.userStatusName"
						+ "	from userdetails a"
						+ "		inner join refuserstatus b on b.userStatusCode = a.userStatus"
						+ "	where a.userStatus = 'A'"
						+ " 	and a.userUniqueId <> 1) t where 1=1";
			}else{
				hql		= "select t.* from (select a.*, CONCAT(a.userName, ' ', a.userSurname) userFullName, b.userStatusName"
						+ "	from userdetails a"
						+ "		inner join refuserstatus b on b.userStatusCode = a.userStatus"
						+ "		inner join relationuserncompany c on c.userUniqueId = a.userUniqueId and c.tin = :tin"
						+ "	where a.userStatus = 'A'"
						+ " 	and a.userUniqueId <> 1) t where 1=1";
				
				param.put("tin"	, form.getTin());
			}
			
			if(find!=null && !find.equals("")){
				hql += " and t." + form.getColumn();
				
				if(form.getLikeFlag().equals("Y")){
					hql += " LIKE CONCAT(:find, '%')";
				}else{
					hql += " = :find";
				}
				param.put("find"	, find);
			}
			
			hql += " order by t." + form.getOrderBy() + " " + form.getSortBy();
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userEmail");
			columnList.add("userFullName");
			columnList.add("userStatus");
			columnList.add("userStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList!=null){
				
				for(HashMap<String, Object> row:resultList){
					userDetailsBean 		= new UserDetailsBean();
					
					userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row.get("userUniqueId")));
					userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row.get("userEmail")));
					userDetailsBean.setUserFullName			(EnjoyUtils.nullToStr(row.get("userFullName")));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row.get("userStatus")));
					userDetailsBean.setUserStatusName		(EnjoyUtils.nullToStr(row.get("userStatusName")));
					
					listData.add(userDetailsBean);
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			getLogger().info("[getUserDetailsLookUpList][End]");
		}
		
		return listData;
		
	}
	
	public String flagChkCompany() throws EnjoyException{
		getLogger().info("[flagChkCompany][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		String							flagChkCompany		= "Y";
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			
			hql				= "select count(*) cou FROM company where tin <> '9999999999999' and companyStatus = 'A'";
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
				if(result > 0)flagChkCompany	= "N";
			}
			
			getLogger().info("[flagChkCompany] flagChkCompany :: " + flagChkCompany);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql	= null;
			getLogger().info("[flagChkCompany][End]");
		}
		
		return flagChkCompany;
	}
	/*ดึง List ของผู้ใช้*/
	public UserDetailsBean getUserdetailByTin(String 	fullName ,String tin, Hashtable<String, String>	fUserprivilege) throws EnjoyException{
		getLogger().info("[getUserdetailByTin][Begin]");
		
		String							hql						= null;
		UserDetailsBean					userDetailsBean			= null;
		String[]						arrPrivilegeCode		= null;			
		String							privilegeName			= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{			
			hql					= "select a.*"
								+ "		, b.userStatusName"
								+ "	from userdetails a"
								+ "		inner join refuserstatus b on b.userStatusCode = a.userStatus"
								+ "		inner join relationuserncompany c on c.userUniqueId = a.userUniqueId and c.tin = :tin"
								+ "	where a.userUniqueId <> 1"
								+ "		and a.userStatus = 'A'"
								+ "		and CONCAT(a.userName, ' ', a.userSurname) = :fullName";
			
			param.put("tin"			, tin);
			param.put("fullName"	, fullName);
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("userEmail");
			columnList.add("userName");
			columnList.add("userSurname");
			columnList.add("userPrivilege");
			columnList.add("userLevel");
			columnList.add("userStatus");
			columnList.add("flagChangePassword");
			columnList.add("flagAlertStock");
			columnList.add("flagSalesman");
			columnList.add("commission");
			columnList.add("remark");
			columnList.add("userStatusName");

			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					userDetailsBean 	= new UserDetailsBean();
					privilegeName   	= "";
					
					arrPrivilegeCode	= EnjoyUtils.nullToStr(row.get("userPrivilege")).split("\\,");
					for(int j=0;j<arrPrivilegeCode.length;j++){
						if (! privilegeName.equals("")) privilegeName = privilegeName + "<br>";
						privilegeName   = privilegeName + "- " +fUserprivilege.get(arrPrivilegeCode[j]);
					}
					userDetailsBean.setUserUniqueId			(EnjoyUtils.parseInt(row.get("userUniqueId")));
					userDetailsBean.setUserEmail			(EnjoyUtils.nullToStr(row.get("userEmail")));
					userDetailsBean.setUserName				(EnjoyUtils.nullToStr(row.get("userName")) + "  " + EnjoyUtils.nullToStr(row.get("userSurname")));
					userDetailsBean.setUserPrivilege		(privilegeName);
					userDetailsBean.setUserLevel			(EnjoyUtils.nullToStr(row.get("userLevel")));
					userDetailsBean.setUserStatus			(EnjoyUtils.nullToStr(row.get("userStatus")));
					userDetailsBean.setFlagChangePassword	(EnjoyUtils.chkBoxtoDb(row.get("flagChangePassword")));
					userDetailsBean.setFlagAlertStock		(EnjoyUtils.chkBoxtoDb(row.get("flagAlertStock")));
					userDetailsBean.setFlagSalesman			(EnjoyUtils.chkBoxtoDb(row.get("flagSalesman")));
					userDetailsBean.setCommission			(EnjoyUtils.convertFloatToDisplay(row.get("commission"), 2));
					userDetailsBean.setRemark			    (EnjoyUtils.nullToStr(row.get("remark")));
					userDetailsBean.setUserStatusName		(EnjoyUtils.nullToStr(row.get("userStatusName")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดผู้ใช้");
		}finally{
			hql						= null;
			getLogger().info("[getUserdetailByTin][End]");
		}
		
		return userDetailsBean;
		
	}
	
	
}
