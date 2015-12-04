package th.go.stock.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.app.enjoy.utils.SendMail;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class UserDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsMaintananceServlet.class);
	
    private static final String FORM_NAME = "userDetailsMaintananceForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private CompanyDetailsDao			companyDetailsDao			= null;
    private UserDetailsMaintananceForm	form						= null;
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("[execute][Begin]");
		
         String pageAction = null; 
 		
 		try{
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (UserDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
             this.companyDetailsDao		= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getUserDetail")) this.form = new UserDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getUserDetail")){
 				this.onGetUserDetail(0);
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("checkDupUserId")){
 				this.checkDupUserId();
 			}else if(pageAction.equals("save")){
 				this.onSave();
 			}else if(pageAction.equals("resetPass")){
 				this.resetPass();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			this.form.setErrMsg(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{
			
			this.form.getUserDetailsBean().setUserStatus("1");
			this.setRefference();
			
			this.form.setTitlePage("เพิ่มผู้ใช้งานระบบ");
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			this.form.setStatusCombo(this.dao.getRefuserstatusCombo(session));
			this.form.setUserprivilegeList(this.dao.getUserprivilege(session));
			this.form.setCompanyCombo(this.companyDetailsDao.getCompanyCombo(session));
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[setRefference][End]");
		}
	}
	
	private void onGetUserDetail(int userUniqueId) throws EnjoyException{
		logger.info("[onGetUserDetail][Begin]");
		
		UserDetailsBean 	userdetailDb	= null;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			session.beginTransaction();
			
			if(userUniqueId==0){
				userUniqueId 				= EnjoyUtil.nullToStr(request.getParameter("userUniqueId")).equals("")?0:Integer.parseInt(request.getParameter("userUniqueId"));
			}
			
			logger.info("[onGetUserDetail] userUniqueId :: " + userUniqueId);
			
			userdetailDb				= this.dao.getUserdetail(session, userUniqueId);
			
			this.form.setTitlePage("แก้ไขผู้ใช้งานระบบ");
			this.form.setPageMode(UserDetailsMaintananceForm.EDIT);
			
			logger.info("[onGetUserDetail] userdetailDb :: " + userdetailDb);
			
			if(userdetailDb!=null){
				this.form.setUserDetailsBean(userdetailDb);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงข้อมูลผู้ใช้งาน");
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onGetUserDetail is error");
		}finally{
			session.close();
			
			this.setRefference();
			
			sessionFactory	= null;
			session			= null;
			logger.info("[onGetUserDetail][End]");
		}
		
	}
	
	private void checkDupUserId() throws EnjoyException{
		logger.info("[checkDupUserId][Begin]");
		
		String 				userId 			= null;
		int 				cou				= 0;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		JSONObject 			obj 			= null;
		String				pageMode		= null;
		int					userUniqueId	= 0;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			userUniqueId 				= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			
			logger.info("[checkDupUserId] userId 		:: " + userId);
			logger.info("[checkDupUserId] pageMode 		:: " + pageMode);
			logger.info("[checkDupUserId] userUniqueId 	:: " + userUniqueId);
			
			
			cou							= this.dao.checkDupUserId(session, userId, pageMode, userUniqueId);
			obj 						= new JSONObject();
			
			obj.put(STATUS, 		SUCCESS);
			obj.put("COU", 			cou);
			
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"checkDupUserId is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[checkDupUserId][End]");
		}
		
	}
	
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String				pageMode			= null;
		int					userUniqueId		= 0;
		String				tinCompany			= null;
		String				userName			= null;
		String				userSurname			= null;
		String 				userId 				= null;
		String				userEmail			= null;
		String				userStatus			= null;
		String				flagChangePassword 	= null;
		String				flagAlertStock 		= null;
		String				flagSalesman 		= null;
		String				commission 			= null;
		String				remark 				= null;
		String				userPrivilege		= null;
		String				pwd					= null;
		String				pwdEncypt			= null;
		String				userLevel			= null;
		SessionFactory 		sessionFactory		= null;
		Session 			session				= null;
		JSONObject 			obj 				= null;
		UserDetailsBean 	userDetailsBean		= null;
		SendMail			sendMail			= null;
		String				fullName			= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			tinCompany 					= EnjoyUtil.nullToStr(request.getParameter("tinCompany"));
			userName 					= EnjoyUtil.nullToStr(request.getParameter("userName"));
			userSurname 				= EnjoyUtil.nullToStr(request.getParameter("userSurname"));
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			userStatus 					= EnjoyUtil.nullToStr(request.getParameter("userStatus"));
			flagChangePassword 			= EnjoyUtil.chkBoxtoDb(request.getParameter("flagChangePassword"));
			flagAlertStock 				= EnjoyUtil.chkBoxtoDb(request.getParameter("flagAlertStock"));
			flagSalesman 				= EnjoyUtil.chkBoxtoDb(request.getParameter("flagSalesman"));
			commission 					= EnjoyUtil.chkBoxtoDb(request.getParameter("commission"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			userPrivilege 				= EnjoyUtil.nullToStr(request.getParameter("hidUserPrivilege"));
			userUniqueId 				= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			userLevel					= userPrivilege.indexOf("R01") > -1?"9":"1";
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			userDetailsBean				= new UserDetailsBean();
			sendMail					= new SendMail();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] tinCompany 			:: " + tinCompany);
			logger.info("[onSave] userName 				:: " + userName);
			logger.info("[onSave] userSurname 			:: " + userSurname);
			logger.info("[onSave] userId 				:: " + userId);
			logger.info("[onSave] userEmail 			:: " + userEmail);
			logger.info("[onSave] userStatus 			:: " + userStatus);
			logger.info("[onSave] flagChangePassword 	:: " + flagChangePassword);
			logger.info("[onSave] flagAlertStock 		:: " + flagAlertStock);
			logger.info("[onSave] flagSalesman 			:: " + flagSalesman);
			logger.info("[onSave] commission 			:: " + commission);
			logger.info("[onSave] remark 				:: " + remark);
			logger.info("[onSave] userPrivilege 		:: " + userPrivilege);
			logger.info("[onSave] userUniqueId 			:: " + userUniqueId);
			logger.info("[onSave] pwd 					:: " + pwd);
			logger.info("[onSave] pwdEncypt 			:: " + pwdEncypt);
			logger.info("[onSave] userLevel 			:: " + userLevel);
			
			userDetailsBean.setTinCompany(tinCompany);
			userDetailsBean.setUserName(userName);
			userDetailsBean.setUserSurname(userSurname);
			userDetailsBean.setUserId(userId);
			userDetailsBean.setUserEmail(userEmail);
			userDetailsBean.setUserStatus(userStatus);
			userDetailsBean.setFlagChangePassword(flagChangePassword);
			userDetailsBean.setFlagAlertStock(flagAlertStock);
			userDetailsBean.setFlagSalesman(flagSalesman);
			userDetailsBean.setCommission(commission);
			userDetailsBean.setRemark(remark);
			userDetailsBean.setUserPrivilege(userPrivilege);
			userDetailsBean.setUserUniqueId(userUniqueId);
			userDetailsBean.setUserLevel(userLevel);
			
			session.beginTransaction();
			
			if(pageMode.equals(UserDetailsMaintananceForm.NEW)){
				
				//Random new password (8 chars)
				pwd							= EnjoyUtil.genPassword(8);
				
				//Encypt password
				pwdEncypt					= EnjoyEncryptDecrypt.enCryption(userId, pwd);
				userDetailsBean.setPwd(pwdEncypt);
				
				this.dao.insertNewUser(session, userDetailsBean);
				
				/*Begin send new password to email*/
				fullName = userName + " " + userSurname;
				sendMail.sendMail(fullName, userId, pwd, userEmail);
				/*End send new password to email*/
				
			}else{
				this.dao.updateUserDetail(session, userDetailsBean);
			}
			
			
			
			session.getTransaction().commit();
			
			if(pageMode.equals(UserDetailsMaintananceForm.NEW)){
				session = sessionFactory.openSession();
				userUniqueId = this.dao.lastId(session);
			}
			
			logger.info("[onSave] After Save userUniqueId 			:: " + userUniqueId);
			
			obj.put(STATUS, 			SUCCESS);
			obj.put("userUniqueId", 	userUniqueId);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
//			this.onGetUserDetail(userUniqueId);
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onSave][End]");
		}
	}
	
	private void resetPass() throws EnjoyException{
		logger.info("[resetPass][Begin]");
		
		int					userUniqueId		= 0;
		String				userName			= null;
		String				userSurname			= null;
		String 				userId 				= null;
		String				userEmail			= null;
		String				pwd					= null;
		String				pwdEncypt			= null;
		SessionFactory 		sessionFactory		= null;
		Session 			session				= null;
		JSONObject 			obj 				= null;
		UserDetailsBean 	userDetailsBean		= null;
		SendMail			sendMail			= null;
		String				fullName			= null;
		
		try{
			userName 					= EnjoyUtil.nullToStr(request.getParameter("userName"));
			userSurname 				= EnjoyUtil.nullToStr(request.getParameter("userSurname"));
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			userUniqueId 				= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			userDetailsBean				= new UserDetailsBean();
			sendMail					= new SendMail();
			
			logger.info("[onSave] userId 				:: " + userId);
			logger.info("[onSave] userEmail 			:: " + userEmail);
			logger.info("[onSave] userUniqueId 			:: " + userUniqueId);
			
			//Random new password (8 chars)
			pwd							= EnjoyUtil.genPassword(8);
			
			//Encypt password
			pwdEncypt					= EnjoyEncryptDecrypt.enCryption(userId, pwd);
			userDetailsBean.setUserUniqueId(userUniqueId);
			userDetailsBean.setPwd(pwdEncypt);
			
			session.beginTransaction();
			
			this.dao.changePassword(session, userDetailsBean);
			
			/*Begin send new password to email*/
			fullName = userName + " " + userSurname;
			sendMail.sendMail(fullName, userId, pwd, userEmail);
			/*End send new password to email*/
			
			
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"resetPass is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[resetPass][End]");
		}
	}
	
	
	
	
	
	
	
	
	
}
