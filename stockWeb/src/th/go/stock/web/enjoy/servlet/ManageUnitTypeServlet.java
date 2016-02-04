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

import th.go.stock.app.enjoy.bean.ManageUnitTypeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageUnitTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageUnitTypeForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ManageUnitTypeServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageUnitTypeServlet.class);
	
    private static final String 	FORM_NAME 				= "manageUnitTypeForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ManageUnitTypeDao				dao							= null;
    private ManageUnitTypeForm				form						= null;
    
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
             this.form               	= (ManageUnitTypeForm)session.getAttribute(FORM_NAME);
             this.dao					= new ManageUnitTypeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ManageUnitTypeForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ManageUnitTypeScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
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
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		List<ManageUnitTypeBean> 	unitTypeList			= null;
		String						seqTemp					= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			unitTypeList				= this.dao.getUnitTypeList(session);
			
			for(ManageUnitTypeBean bean:unitTypeList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setUnitTypeList(unitTypeList);
			
			if(seqTemp!=null){
				this.form.setSeqTemp(seqTemp);
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 				obj 					= null;
		ManageUnitTypeBean		manageUnitTypeBean		= null;
		String					newSeq					= null;
		
		try{
			
			manageUnitTypeBean 		= new ManageUnitTypeBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			manageUnitTypeBean.setUnitStatus("A");
			manageUnitTypeBean.setRowStatus(ManageUnitTypeForm.NEW);
			manageUnitTypeBean.setSeq(newSeq);
			
			this.form.getUnitTypeList().add(manageUnitTypeBean);
			this.form.setSeqTemp(newSeq);
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"newRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[newRecord][End]");
		}
	}
	
	private void updateRecord() throws EnjoyException{
		logger.info("[updateRecord][Begin]");
		
		JSONObject 						obj 					= null;
		String 							unitName				= null;
		String 							seq						= null;
		List<ManageUnitTypeBean> 		unitTypeList			= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			unitName 				= EnjoyUtil.nullToStr(request.getParameter("unitName"));
			unitTypeList			= this.form.getUnitTypeList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] unitName 		:: " + unitName);
			
			for(ManageUnitTypeBean bean:unitTypeList){
				if(bean.getSeq().equals(seq)){
					
					bean.setUnitName(unitName);
					
					if(!bean.getRowStatus().equals(ManageUnitTypeForm.NEW)){
						bean.setRowStatus(ManageUnitTypeForm.UPD);
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
	
	private void deleteRecord() throws EnjoyException{
		logger.info("[deleteRecord][Begin]");
		
		JSONObject 						obj 					= null;
		String 							seq						= null;
		List<ManageUnitTypeBean> 		unitTypeList			= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			unitTypeList			= this.form.getUnitTypeList();
			
			for(int i=0;i<unitTypeList.size();i++){
				ManageUnitTypeBean bean = unitTypeList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(ManageUnitTypeForm.NEW)){
						bean.setUnitStatus("R");
						bean.setRowStatus(ManageUnitTypeForm.DEL);
					}else{
						unitTypeList.remove(i);
					}
					break;
				}
			}
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"deleteRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[deleteRecord][End]");
		}
	}
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 					sessionFactory			= null;
		Session 						session					= null;
		JSONObject 						obj 					= null;
		List<ManageUnitTypeBean> 		unitTypeList			= null;
		int								chk						= 0;
		ManageUnitTypeBean 				bean					= null;
		ManageUnitTypeBean 				beanTemp				= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			unitTypeList				= this.form.getUnitTypeList();
			
			session.beginTransaction();
			
			for(int i=0;i<unitTypeList.size();i++){
				bean = unitTypeList.get(i);
				if(bean.getRowStatus().equals(ManageUnitTypeForm.NEW)){
					
//					for(int j=(i+1);j<unitTypeList.size();j++){
//						beanTemp = unitTypeList.get(j);
//						if(beanTemp.getRowStatus().equals(ManageUnitTypeForm.NEW) && bean.getUnitName().equals(beanTemp.getUnitName())){
//							throw new EnjoyException("ชื่อหน่วยสินค้าห้ามซ้ำ");
//						}
//					}
//					
//					chk = this.dao.checkDupUnitName(session, bean.getUnitName(), "");
//					
//					logger.info("[onSave] " + bean.getUnitName() + " chk :: " + chk);
//					
//					if(chk > 0){
//						throw new EnjoyException("ชื่อหน่วยสินค้าห้ามซ้ำ");
//					}
					
					this.dao.insertUnitType(session, bean);
				}else if(bean.getRowStatus().equals(ManageUnitTypeForm.UPD) || bean.getRowStatus().equals(ManageUnitTypeForm.DEL)){
					
//					if(bean.getRowStatus().equals(ManageUnitTypeForm.UPD)){
//						chk = this.dao.checkDupUnitName(session, bean.getUnitName(), bean.getUnitCode());
//						
//						logger.info("[onSave] UPD " + bean.getUnitName() + " chk :: " + chk);
//						
//						if(chk > 0){
//							throw new EnjoyException("ชื่อหน่วยสินค้าห้ามซ้ำ");
//						}
//					}
					
					this.dao.updateUnitType(session, bean);
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
			logger.error(e);
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
}
