package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.RefconstantcodeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.RefconstantcodeMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;
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
 			logger.info("[execute][End]");
 		}
	}
	
	private void getDetail() throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		SessionFactory 					sessionFactory				= null;
		Session 						session						= null;
		RefconstantcodeBean 			refconstantcodeBean			= null;
		List<RefconstantcodeBean> 		refconstantcodeList			= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			this.form.setTitlePage("ตั้งค่ารหัสเอกสาร");
			
			session.beginTransaction();
			
			refconstantcodeBean 	= new RefconstantcodeBean();
			refconstantcodeList		= this.dao.searchByCriteria(session, refconstantcodeBean);
			
			this.form.setRefconstantcodeList(refconstantcodeList);
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			logger.info("[getDetail][End]");
		}
		
	}
	   
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		JSONObject 					obj 						= null;
		List<RefconstantcodeBean> 	refconstantcodeList			= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			refconstantcodeList			= this.form.getRefconstantcodeList();
			
			session.beginTransaction();
			
			for(RefconstantcodeBean bean:refconstantcodeList){
				if(bean.getRowStatus().equals(RefconstantcodeMaintananceForm.EDIT)){
					this.dao.updateCodeDisplay(session, bean);
				}
			}
			
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
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
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
			refconstantcodeList		= this.form.getRefconstantcodeList();
			
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
	
}

