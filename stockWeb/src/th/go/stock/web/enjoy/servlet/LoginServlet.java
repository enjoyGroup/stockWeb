package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
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

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response)throws Exception {
	   doProcess(request, response);
   }

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String 						userId 						= null;
        String 						passWord 					= null;
        HttpSession 				session 					= request.getSession(true);
        UserDetailsBean				userBean 					= null;
        UserDetailsDao	 			userDao 					= null;
        UserPrivilegeDao			userPrivilegeDao 			= null;
        EnjoyUtil           		enjoyUtil 					= null;
		JSONObject 					obj 						= null;
		RelationUserAndCompanyDao 	relationUserAndCompanyDao 	= null;
		int							countUserIncompany			= 0;
        				
        try{
			obj 						= new JSONObject();
			enjoyUtil 					= new EnjoyUtil(request, response);
        	userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
        	passWord 					= EnjoyUtil.nullToStr(request.getParameter("passWord"));
        	userDao						= new UserDetailsDao();
        	userPrivilegeDao 			= new UserPrivilegeDao();
			obj 						= new JSONObject();
			relationUserAndCompanyDao 	= new RelationUserAndCompanyDao();
			
        	this.checkExpiryDate();
        	
        	logger.info("[execute] userId 	:: " + userId);
        	logger.info("[execute] passWord 	:: " + passWord);
        	
        	userBean = userDao.userSelect(userId, passWord);
        	
        	if(userBean==null){
    			obj.put(STATUS, 		ERROR);
    			obj.put(ERR_MSG, 		"ไม่สามารถเข้าสู่ระบบได้ กรุณาตรวจสอบ user/password ใหม่อีกครั้ง");
        	}else{
        		//เช็คว่า user มีบริษัทสังกัดหรือยัง ยกเว้น user admin
        		if(userBean.getUserUniqueId()!=1){
        			countUserIncompany = relationUserAndCompanyDao.countForCheckLogin(userBean.getUserUniqueId());
            		
            		if(countUserIncompany > 0){
            			userBean.setUserPrivilegeList((ArrayList<UserPrivilegeBean>) userPrivilegeDao.userPrivilegeListSelect(userBean.getUserPrivilege()));
                		session.setAttribute("userBean", userBean);
            			obj.put(STATUS				, SUCCESS);
            			obj.put("flagChkCompany"	, userBean.getFlagChkCompany());
            			obj.put("FlagChange"		, userBean.getFlagChangePassword());
            		}else{
            			obj.put(STATUS, 		ERROR);
            			obj.put(ERR_MSG, 		"ไม่สามารถใช้งานได้ เนื่องจากยังไม่ได้ระบุว่าเป็นพนักงานของบริษัทในระบบ กรุณาติดต่อผู้ดูแลระบบ");
            		}
        		}else{
        			userBean.setUserPrivilegeList((ArrayList<UserPrivilegeBean>) userPrivilegeDao.userPrivilegeListSelect(userBean.getUserPrivilege()));
            		session.setAttribute("userBean", userBean);
            		
            		String sessionid    = request.getSession().getId();
                    String secure       = "";
                    if (request.isSecure()) {
                        secure = "; Secure"; 
                    }
                    response.setHeader( "Set-Cookie", "JSESSIONID=" + sessionid + ";HttpOnly" + secure );
            		
        			obj.put(STATUS				, SUCCESS);
        			obj.put("flagChkCompany"	, userBean.getFlagChkCompany());
        			obj.put("FlagChange"		, userBean.getFlagChangePassword());
        		}
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
    		userId 				= null;
            passWord 			= null;
            userBean 			= null;
            userDao 			= null;
            userPrivilegeDao 	= null;
            enjoyUtil 			= null;
    		obj 				= null;
        	logger.info("[EnjoyLoginSvc][execute][End]");
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
	
	
}