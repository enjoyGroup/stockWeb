package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.RefconstantcodeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.RefconstantcodeMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class RefconstantcodeMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RefconstantcodeMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "refconstantcodeMaintananceForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private RefconstantcodeMaintananceForm	form						= null;
    private RefconstantcodeDao				dao							= null;
    
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
             this.form               	= (RefconstantcodeMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new RefconstantcodeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new RefconstantcodeMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.getDetail();
 				request.setAttribute("target", Constants.PAGE_URL +"/RefconstantcodeMaintananceScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void getDetail() throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		List<RefconstantcodeBean> 		refconstantcodeList			= null;
		RefconstantcodeBean				refconstantcodeBean			= new RefconstantcodeBean();
		
		try{
			this.form.setTitlePage("ตั้งค่ารหัสเอกสาร");
			
			refconstantcodeBean.setTin(this.userBean.getTin());
			
			refconstantcodeBean.setTypeTB("1");
			refconstantcodeList		= this.dao.searchByCriteria(refconstantcodeBean);
			this.form.setSection1List(refconstantcodeList);
			
			refconstantcodeBean.setTypeTB("2");
			refconstantcodeList		= this.dao.searchByCriteria(refconstantcodeBean);
			this.form.setSection2List(refconstantcodeList);
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			logger.info("[getDetail][End]");
		}
		
	}
	   
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		JSONObject 					obj 					= null;
		List<RefconstantcodeBean> 	section1List			= null;
		List<RefconstantcodeBean> 	section2List			= null;
		String						sendMailFlg				= null;
		
		try{
			obj 					= new JSONObject();
			section1List			= this.form.getSection1List();
			section2List			= this.form.getSection2List();
			sendMailFlg				= EnjoyUtil.chkBoxtoDb(request.getParameter("sendMailFlg"));
			
			for(RefconstantcodeBean bean:section1List){
				if(bean.getRowStatus().equals(RefconstantcodeMaintananceForm.EDIT)){
					this.dao.updateCodeDisplay(bean);
				}
			}
			
			for(RefconstantcodeBean bean:section2List){
				bean.setCodeDisplay(sendMailFlg);
				this.dao.updateRefconstantcode(bean);
			}
			
			commit();
			
			obj.put(STATUS, 			SUCCESS);
			
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
	
	private void updateRecord() throws EnjoyException{
		logger.info("[updateRecord][Begin]");
		
		JSONObject 					obj 					= null;
		String 						id						= null;
		String 						codeDisplay				= null;
		String 						codeNameTH				= null;
		String 						codeNameEN				= null;
		String 						flagYear				= null;
		List<RefconstantcodeBean> 	refconstantcodeList		= null;
		
		try{
			
			obj 					= new JSONObject();
			id 						= EnjoyUtil.nullToStr(request.getParameter("id"));
			codeDisplay 			= EnjoyUtil.nullToStr(request.getParameter("codeDisplay"));
			codeNameTH 				= EnjoyUtil.nullToStr(request.getParameter("codeNameTH"));
			codeNameEN 				= EnjoyUtil.nullToStr(request.getParameter("codeNameEN"));
			flagYear 				= EnjoyUtil.chkBoxtoDb(request.getParameter("flagYear"));
			refconstantcodeList		= this.form.getSection1List();
			
			logger.info("[updateRecord] id 				:: " + id);
			logger.info("[updateRecord] codeDisplay 	:: " + codeDisplay);
			logger.info("[updateRecord] codeNameTH 		:: " + codeNameTH);
			logger.info("[updateRecord] codeNameEN 		:: " + codeNameEN);
			logger.info("[updateRecord] flagYear 		:: " + flagYear);
			
			for(RefconstantcodeBean bean:refconstantcodeList){
				if(bean.getId().equals(id) && !bean.getRowStatus().equals(RefconstantcodeMaintananceForm.DEL)){
					
					bean.setCodeDisplay	(codeDisplay);
					bean.setCodeNameTH	(codeNameTH);
					bean.setCodeNameEN	(codeNameEN);
					bean.setFlagYear	(flagYear);
					
					if(bean.getRowStatus().equals("")){
						bean.setRowStatus(RefconstantcodeMaintananceForm.EDIT);
					}
					
					break;
				}
			}
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"updateRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[updateRecord][End]");
		}
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
	}
	
}

