package th.go.stock.web.enjoy.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.RefconstantcodeBean;
import th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.RefconstantcodeDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
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
    private RelationUserAndCompanyDao	relationUserAndCompanyDao	= null;
    private RefconstantcodeDao			refconstantcodeDao			= null;
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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (UserDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao						= new UserDetailsDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.refconstantcodeDao		= new RefconstantcodeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getUserDetail")) this.form = new UserDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getUserDetail")){
 				this.onGetUserDetail(0);
 				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("checkDupUserEmail")){
 				this.checkDupUserEmail();
 			}else if(pageAction.equals("save")){
 				this.onSave();
 			}else if(pageAction.equals("resetPass")){
 				this.resetPass();
 			}else if(pageAction.equals("genPdf")){
 				this.genPdf();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			this.form.setErrMsg(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		RefconstantcodeBean refconstantcodeBean = null;
		
		try{
			refconstantcodeBean = refconstantcodeDao.getDetail(Constants.SEND_MAIL_ID, this.userBean.getTin());
			
			this.form.setSendMailFlag(refconstantcodeBean.getCodeDisplay());
			this.form.getUserDetailsBean().setUserStatus("A");
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
		
		try{
			this.setStatusCombo();
			this.form.setUserprivilegeList(this.dao.getUserprivilege());
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setStatusCombo() throws EnjoyException{
		
		List<ComboBean> 	comboList		= new ArrayList<ComboBean>();
		List<ComboBean> 	comboListDb		= null;
		
		try{
			comboListDb = this.dao.getRefuserstatusCombo();
			
			for(ComboBean bean:comboListDb){
				comboList.add(new ComboBean(bean.getCode(), bean.getDesc()));
			}
			
			this.form.setStatusCombo(comboList);
			
		}catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setStatusCombo is error");
		}
	}
	
	private void onGetUserDetail(int userUniqueId) throws EnjoyException{
		logger.info("[onGetUserDetail][Begin]");
		
		UserDetailsBean 	userdetailDb		= null;
		RefconstantcodeBean refconstantcodeBean = null;
		
		try{
			if(userUniqueId==0){
				userUniqueId 				= EnjoyUtil.nullToStr(request.getParameter("userUniqueId")).equals("")?0:Integer.parseInt(request.getParameter("userUniqueId"));
			}
			
			logger.info("[onGetUserDetail] userUniqueId :: " + userUniqueId);
			
			userdetailDb		= this.dao.getUserdetail(userUniqueId);
			refconstantcodeBean = refconstantcodeDao.getDetail(Constants.SEND_MAIL_ID, this.userBean.getTin());
			
			this.form.setSendMailFlag(refconstantcodeBean.getCodeDisplay());
			
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
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("onGetUserDetail is error");
		}finally{
			this.setRefference();
			logger.info("[onGetUserDetail][End]");
		}
		
	}
	
	private void checkDupUserEmail() throws EnjoyException{
		logger.info("[checkDupUserEmail][Begin]");
		
		String 				userEmail 		= null;
		int 				cou				= 0;
		JSONObject 			obj 			= null;
		String				pageMode		= null;
		int					userUniqueId	= 0;
		
		try{
			userEmail 			= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			pageMode 			= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			userUniqueId 		= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			
			logger.info("[checkDupUserEmail] userEmail 	:: " + userEmail);
			logger.info("[checkDupUserEmail] pageMode 		:: " + pageMode);
			logger.info("[checkDupUserEmail] userUniqueId 	:: " + userUniqueId);
			
			
			cou							= this.dao.checkDupUserEmail(userEmail, pageMode, userUniqueId);
			obj 						= new JSONObject();
			
			obj.put(STATUS, 		SUCCESS);
			obj.put("COU", 			cou);
			
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"checkDupUserEmail is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[checkDupUserEmail][End]");
		}
		
	}
	
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String						pageMode					= null;
		int							userUniqueId				= 0;
		String						userName					= null;
		String						userSurname					= null;
		String						userEmail					= null;
		String						userStatus					= null;
		String						flagChangePassword 			= null;
		String						flagAlertStock 				= null;
		String						flagSalesman 				= null;
		String						commission 					= null;
		String						remark 						= null;
		String						userPrivilege				= null;
		String						pwd							= null;
		String						pwdEncypt					= null;
		String						userLevel					= null;
		JSONObject 					obj 						= null;
		UserDetailsBean 			userDetailsBean				= null;
		SendMail					sendMail					= null;
		String						fullName					= null;
		RelationUserAndCompanyBean 	relationUserAndCompanyBean 	= null;
		String						sendMailFlag				= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			userName 					= EnjoyUtil.nullToStr(request.getParameter("userName"));
			userSurname 				= EnjoyUtil.nullToStr(request.getParameter("userSurname"));
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			userStatus 					= EnjoyUtil.nullToStr(request.getParameter("userStatus"));
			flagChangePassword 			= EnjoyUtil.chkBoxtoDb(request.getParameter("flagChangePassword"));
			flagAlertStock 				= EnjoyUtil.chkBoxtoDb(request.getParameter("flagAlertStock"));
			flagSalesman 				= EnjoyUtil.chkBoxtoDb(request.getParameter("flagSalesman"));
			commission 					= EnjoyUtil.chkBoxtoDb(request.getParameter("commission"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			userPrivilege 				= EnjoyUtil.nullToStr(request.getParameter("hidUserPrivilege"));
			userUniqueId 				= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			sendMailFlag 				= EnjoyUtil.nullToStr(request.getParameter("sendMailFlag"));
			userLevel					= userPrivilege.indexOf("R01") > -1?"9":"1";
			obj 						= new JSONObject();
			userDetailsBean				= new UserDetailsBean();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] userName 				:: " + userName);
			logger.info("[onSave] userSurname 			:: " + userSurname);
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
			logger.info("[onSave] sendMailFlag 			:: " + sendMailFlag);
			
			userDetailsBean.setUserName(userName);
			userDetailsBean.setUserSurname(userSurname);
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
			
			if(pageMode.equals(UserDetailsMaintananceForm.NEW)){
				
				//Random new password (8 chars)
				pwd							= EnjoyUtil.genPassword(8);
				
				//Encrypt password
				pwdEncypt					= EnjoyEncryptDecrypt.enCryption(userEmail, pwd);
				userDetailsBean.setPwd(pwdEncypt);
				
				this.dao.insertNewUser(userDetailsBean);
				
				userUniqueId = this.dao.lastId();
				
				//ไม่ใช่ user admin enjoy ให้ insert ลง table relationuserncompany ด้วย
				if(this.userBean.getUserUniqueId()!=1){
					relationUserAndCompanyBean = new RelationUserAndCompanyBean();
					relationUserAndCompanyBean.setUserUniqueId(String.valueOf(userUniqueId));
					relationUserAndCompanyBean.setTin(this.userBean.getTin());
					
					relationUserAndCompanyDao.insertRelationUserAndCompany(relationUserAndCompanyBean);
				}
				
				/*Begin send new password to email*/
				fullName = userName + " " + userSurname;
				if("Y".equals(sendMailFlag)){
					sendMail = new SendMail();
					sendMail.sendMail(fullName, userEmail, pwd, userEmail);
				}
				/*End send new password to email*/
				
			}else{
				this.dao.updateUserDetail(userDetailsBean);
			}
			
			commit();
			
			logger.info("[onSave] After Save userUniqueId 			:: " + userUniqueId);
			
			obj.put(STATUS			,SUCCESS);
			obj.put("userUniqueId"	,userUniqueId);
			obj.put("fullName"		,fullName);
			obj.put("userEmail"		,userEmail);
			obj.put("pwd"			,pwd);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSave][End]");
		}
	}
	
	private void resetPass() throws EnjoyException{
		logger.info("[resetPass][Begin]");
		
		int					userUniqueId		= 0;
		String				userName			= null;
		String				userSurname			= null;
		String				userEmail			= null;
		String				pwd					= null;
		String				pwdEncypt			= null;
		JSONObject 			obj 				= null;
		UserDetailsBean 	userDetailsBean		= null;
		SendMail			sendMail			= null;
		String				fullName			= null;
		String				sendMailFlag		= null;
		
		try{
			userName 					= EnjoyUtil.nullToStr(request.getParameter("userName"));
			userSurname 				= EnjoyUtil.nullToStr(request.getParameter("userSurname"));
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			userUniqueId 				= EnjoyUtil.parseInt(request.getParameter("userUniqueId"));
			sendMailFlag 				= EnjoyUtil.nullToStr(request.getParameter("sendMailFlag"));
			obj 						= new JSONObject();
			userDetailsBean				= new UserDetailsBean();
			
			logger.info("[onSave] userEmail 			:: " + userEmail);
			logger.info("[onSave] userUniqueId 			:: " + userUniqueId);
			logger.info("[onSave] sendMailFlag 			:: " + sendMailFlag);
			
			//Random new password (8 chars)
			pwd							= EnjoyUtil.genPassword(8);
			
			//Encypt password
			pwdEncypt					= EnjoyEncryptDecrypt.enCryption(userEmail, pwd);
			userDetailsBean.setUserUniqueId(userUniqueId);
			userDetailsBean.setPwd(pwdEncypt);
			
			this.dao.changePassword(userDetailsBean);
			
			/*Begin send new password to email*/
			fullName = userName + " " + userSurname;
			
			if("Y".equals(sendMailFlag)){
				sendMail = new SendMail();
				sendMail.sendMail(fullName, userEmail, pwd, userEmail);
			}
			/*End send new password to email*/
			
			commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("userUniqueId"	,userUniqueId);
			obj.put("fullName"		,fullName);
			obj.put("userEmail"		,userEmail);
			obj.put("pwd"			,pwd);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"resetPass is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[resetPass][End]");
		}
	}
	
	private void genPdf(){
		logger.info("[genPdf][Begin]");
		
		JSONObject 				obj 						= new JSONObject();
		ViewPdfMainForm			viewPdfMainForm				= new ViewPdfMainForm();
		DataOutput 				output 						= null;
		ByteArrayOutputStream	buffer						= null;
		byte[] 					bytes						= null;
		String 					fullName 					= null;
		String 					userEmail 					= null;
		String 					pwd 						= null;
		
		try{
			fullName 		= EnjoyUtil.nullToStr(request.getParameter("fullName"));
			userEmail 			= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			pwd 			= EnjoyUtil.nullToStr(request.getParameter("pwd"));
			
			obj.put("fullName"	,fullName);
			obj.put("userEmail"	,userEmail);
			obj.put("pwd"		,pwd);
			
			String pdfName = "UserDetailPdfForm";
			buffer = viewPdfMainForm.writeTicketPDF(pdfName, obj, this.form.getTitlePage());
			
			response.setContentType( "application/pdf" );
			response.setHeader("Content-Disposition", "filename=".concat(String.valueOf(pdfName+".pdf")));
			output 	= new DataOutputStream( this.response.getOutputStream() );
			bytes 	= buffer.toByteArray();
	
			this.response.setContentLength(bytes.length);
			for( int i = 0; i < bytes.length; i++ ) { 
				output.writeByte( bytes[i] ); 
			}
			
		}catch(Exception e){
			
		}finally{
			logger.info("[genPdf][End]");
		}
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.companyDetailsDao.destroySession();
        this.relationUserAndCompanyDao.destroySession();
        this.refconstantcodeDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.companyDetailsDao.commit();
        this.relationUserAndCompanyDao.commit();
        this.refconstantcodeDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.companyDetailsDao.rollback();
        this.relationUserAndCompanyDao.rollback();
        this.refconstantcodeDao.rollback();
	}
	
	
	
	
	
	
	
	
	
}
