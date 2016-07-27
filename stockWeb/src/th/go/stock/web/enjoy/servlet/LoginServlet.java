package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.dao.UserPrivilegeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

 public class LoginServlet extends EnjoyStandardSvc {
	 
   static final long serialVersionUID = 1L;
   private static final EnjoyLogger logger = EnjoyLogger.getLogger(LoginServlet.class);
   
   private EnjoyUtil               		enjoyUtil                   = null;
   private HttpServletRequest          	request                     = null;
   private HttpServletResponse         	response                    = null;
   private HttpSession                 	session                     = null;
   private UserDetailsDao	 			userDao 					= null;
   private UserPrivilegeDao				userPrivilegeDao 			= null;
   private RelationUserAndCompanyDao 	relationUserAndCompanyDao 	= null;
   private CompanyDetailsDao			companyDetailsDao			= null;

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response)throws Exception {
	   doProcess(request, response);
   }

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		logger.info("[doProcess][begin]");
		
		String pageAction = null;
        				
        try{
        	 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userDao					= new UserDetailsDao();
             this.userPrivilegeDao			= new UserPrivilegeDao();
             this.relationUserAndCompanyDao	= new RelationUserAndCompanyDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
             
             logger.info("[doProcess] pageAction : " + pageAction );
             
             if(pageAction.equals("login")){
 				this.login();
 			 }else if(pageAction.equals("setTinForTinUser")){
  				this.setTinForTinUser();
  			 }
             
        }catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			destroySession();
 			logger.info("[doProcess][End]");
 		}
	}
	
	private void login() throws EnjoyException{
		logger.info("[login][Begin]");
		
		String 						userEmail 					= null;
        String 						user_pwd 					= null;
        UserDetailsBean				userBean 					= null;
		JSONObject 					obj 						= null;
		int							countUserIncompany			= 0;
		List<ComboBean> 			companyList					= null;
		JSONObject 					companyObj 					= null;
		JSONArray					companyObjList 				= null;
		
		try{
			obj 						= new JSONObject();
			userEmail 					= EnjoyUtil.nullToStr(request.getParameter("userEmail"));
			user_pwd 					= EnjoyUtil.nullToStr(request.getParameter("user_pwd"));
			obj 						= new JSONObject();
			companyObjList				= new JSONArray();
			
        	this.checkExpiryDate();
        	
        	logger.info("[login] userEmail 	:: " + userEmail);
        	logger.info("[login] user_pwd 	:: " + user_pwd);
        	
        	userBean = userDao.userSelect(userEmail, user_pwd);
        	
        	if(userBean==null){
    			obj.put(STATUS, 		ERROR);
    			obj.put(ERR_MSG, 		"ไม่สามารถเข้าสู่ระบบได้ กรุณาตรวจสอบ E-mail/password ใหม่อีกครั้ง");
        	}else{
        		//เช็คว่า user มีบริษัทสังกัดหรือยัง ยกเว้น user admin
        		if(userBean.getUserUniqueId()!=1){
        			companyList 		= relationUserAndCompanyDao.getCompanyList(userBean.getUserUniqueId());
        			countUserIncompany 	= companyList.size();
        			
            		if(countUserIncompany > 0){
            			userBean.setFlagChkCompany("N");
            			
            			obj.put(STATUS				, SUCCESS);
            			obj.put("flagChkCompany"	, userBean.getFlagChkCompany());
            			obj.put("FlagChange"		, userBean.getFlagChangePassword());
            			obj.put("countUserIncompany", countUserIncompany);
            			
            			if(countUserIncompany==1){
            				userBean.setTin(companyList.get(0).getCode());
            				userBean.setCompanyName(companyList.get(0).getDesc());
            			}else{
            				for(ComboBean bean:companyList){
            					companyObj = new JSONObject();
            					
            					companyObj.put("code"	, bean.getCode());
            					companyObj.put("desc"	, bean.getDesc());
            					
            					companyObjList.add(companyObj);
            				}
            				
            				obj.put("companyObjList", companyObjList);
            			}
            			
            		}else{
            			obj.put(STATUS, 		ERROR);
            			obj.put(ERR_MSG, 		"ไม่สามารถใช้งานได้ เนื่องจากยังไม่ได้ระบุว่าเป็นพนักงานของบริษัทในระบบ กรุณาติดต่อผู้ดูแลระบบ");
            		}
        		}else{
        			//Admin enjoy สังกัดได้ บ เดียวเท่านั้น
        			ComboBean comboBean 		= relationUserAndCompanyDao.getCompanyForAdminEnjoy(userBean.getUserUniqueId());
        			userBean.setTin(comboBean.getCode());
    				userBean.setCompanyName(comboBean.getDesc());
        			
        			obj.put(STATUS				, SUCCESS);
        			obj.put("flagChkCompany"	, userBean.getFlagChkCompany());
        			obj.put("FlagChange"		, userBean.getFlagChangePassword());
        			obj.put("countUserIncompany", 1);
        		}
        		
        		userBean.setUserPrivilegeList((ArrayList<UserPrivilegeBean>) userPrivilegeDao.userPrivilegeListSelect(userBean.getUserPrivilege()));
        		this.session.setAttribute("userBean", userBean);
        		
        		String sessionid    = request.getSession().getId();
                String secure       = "";
                if (request.isSecure()) {
                    secure = "; Secure"; 
                }
                response.setHeader( "Set-Cookie", "JSESSIONID=" + sessionid + ";HttpOnly" + secure );
        	} 
		}catch(EnjoyException e){
        	e.printStackTrace();
        	logger.error(e);
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error(e);
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดระหว่าง Login");
        }finally{
        	enjoyUtil.writeMSG(obj.toString());
        	userEmail 			= null;
        	user_pwd 			= null;
            userBean 			= null;
    		obj 				= null;
    		
        	logger.info("[login][End]");
        }
	}
	
	private void setTinForTinUser() throws EnjoyException{
		logger.info("[setTinForTinUser][Begin]");
		
		JSONObject 			obj 		= null;
		UserDetailsBean     userBean    = null;
		String				tin 		= null;
		CompanyDetailsBean	bean		= new CompanyDetailsBean();
		CompanyDetailsBean	beanDb		= null;
		
		try{
			obj 		= new JSONObject();
			tin 		= EnjoyUtil.nullToStr(request.getParameter("tin"));
			userBean    = (UserDetailsBean)session.getAttribute("userBean");
			
			logger.info("[setTinForTinUser] tin :: " + tin);
			
			bean.setTin(tin);
			beanDb = this.companyDetailsDao.getCompanyDetail(bean);
			
			userBean.setTin(tin);
			userBean.setCompanyName(beanDb.getCompanyName());
			
			obj.put(STATUS				, SUCCESS);
			
		}catch(Exception e){
        	e.printStackTrace();
        	logger.error(e);
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"เกิดข้อผิดพลาดระหว่างการเลือกบริษัทที่สังกัด");
        }finally{
        	enjoyUtil.writeMSG(obj.toString());
    		
        	logger.info("[setTinForTinUser][End]");
        }
	}
	
	private void checkExpiryDate() throws EnjoyException, Exception{
		Date	  			currDate			= new Date();
		Date	  			expDate				= null;
		SimpleDateFormat    dt              	= null;
		Calendar 			currDateC 			= Calendar.getInstance(Locale.US);
		
		try{
			dt      	= new SimpleDateFormat("yyyyMMdd",Locale.US); 
			expDate     = dt.parse("20150130"); 
			
			currDateC.setTime(currDate);
			
//			if(currDateC.getTime().after(expDate)){
			if("1".equals("2")){
				logger.info("[checkExpiryDate] Expiry");
				throw new EnjoyException("เกิดข้อผิดพลาดในการเข้าสู่ระบบ");
			}
            
		}catch(EnjoyException e){
			throw e;
		}catch(Exception e){
			throw e;
		}
	}

	@Override
	public void destroySession() {
		this.userDao.destroySession();
        this.userPrivilegeDao.destroySession();
        this.relationUserAndCompanyDao.destroySession();
        this.companyDetailsDao.destroySession();
	}

	@Override
	public void commit() {
		this.userDao.commit();
        this.userPrivilegeDao.commit();
        this.relationUserAndCompanyDao.commit();
        this.companyDetailsDao.commit();
	}

	@Override
	public void rollback() {
		this.userDao.rollback();
        this.userPrivilegeDao.rollback();
        this.relationUserAndCompanyDao.rollback();
        this.companyDetailsDao.rollback();
	}
	
	
}