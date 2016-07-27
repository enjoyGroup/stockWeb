package th.go.stock.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ChangePasswordForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyEncryptDecrypt;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ChangePasswordServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ChangePasswordServlet.class);
	
    private static final String FORM_NAME = "changePasswordForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private ChangePasswordForm			form						= null;
    
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
             this.userBean           	= (UserDetailsBean)    session.getAttribute("userBean");
             this.form               	= (ChangePasswordForm) session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ChangePasswordForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ChangePassScn.jsp");
 			}else if(pageAction.equals("save")){
 				this.saveUpdData();
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
		logger.info("[ChangePasswordServlet][onLoad][Begin]");
		
		try{			
			this.form.setOldUserPassword("");
			this.form.setNewUserPassword("");
			this.form.setConfirmUserPassword("");
//		}catch(EnjoyException e){
//			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[ChangePasswordServlet][onLoad][End]");
		}		
	}
	
	private void saveUpdData() throws Exception{
		logger.info("[ChangePasswordServlet][saveUpdRecord][Begin]");
		
		String				   oldUserPassword	    = null; 
		String				   newUserPassword	    = null; 
		UserDetailsBean 	   userDetailsBean  	= null;
		JSONObject 			   obj 			    	= null;
		
		try{	 		
			obj 				= new JSONObject();
			
			oldUserPassword		= EnjoyUtils.nullToStr(this.request.getParameter("oldUserPassword"));
			newUserPassword		= EnjoyUtils.nullToStr(this.request.getParameter("newUserPassword"));
			userDetailsBean		= this.userBean;
			
			oldUserPassword		= EnjoyEncryptDecrypt.enCryption(userDetailsBean.getUserEmail(), oldUserPassword); // เอารหัสผ่านเดิมเข้ารหัส และนำไปเทียบว่าเท่ากับค่าเดิมหรือไม่
			newUserPassword		= EnjoyEncryptDecrypt.enCryption(userDetailsBean.getUserEmail(), newUserPassword); 
			
			logger.info("[ChangePasswordServlet] oldUserPassword :: " + oldUserPassword);
			logger.info("[ChangePasswordServlet] userDetailsBean.getPwd() :: " + userDetailsBean.getPwd());

			if (!oldUserPassword.equals(userDetailsBean.getPwd())) {
				userDetailsBean.setErrMsg("รหัสผ่านเดิมไม่ถูกต้อง");
				throw new EnjoyException(userDetailsBean.getErrMsg());				
			}
			
			userDetailsBean.setPwd(newUserPassword);
			this.dao.updateUserPassword(userDetailsBean);
			
			obj.put(STATUS, 			SUCCESS);
			
			commit();
		}catch(EnjoyException e){
			rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			e.getMessage());
			e.printStackTrace();
		}catch(Exception e){
			rollback();
			obj.put("status", 			"ERROR");
			obj.put("errMsg", 			"เกิดข้อผิดพลาดในการเปลี่ยนรหัสผ่าน");
			e.printStackTrace();
		}finally{ 
			oldUserPassword	    = null; 
			newUserPassword	    = null; 
			userDetailsBean  	= null;
			
			this.enjoyUtil.writeMSG(obj.toString());

			logger.info("[ChangePasswordServlet][saveUpdRecord][End]");
		}
	}

	@Override
	public void destroySession() {
		dao.destroySession();
	}

	@Override
	public void commit() {
		dao.commit();
	}

	@Override
	public void rollback() {
		dao.rollback();
	}
}
