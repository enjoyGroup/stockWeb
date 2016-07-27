package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ManageUnitTypeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageUnitTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageUnitTypeForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (ManageUnitTypeForm)session.getAttribute(FORM_NAME);
             this.dao						= new ManageUnitTypeDao();
 			
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
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{
			onSearch();
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		JSONObject 					obj 					= null;
		List<ManageUnitTypeBean> 	unitTypeList			= null;
		String						seqTemp					= null;
		String 						tin 					= null;

		try{
			obj 	= new JSONObject();
			tin 	= this.userBean.getTin();
			
			unitTypeList				= this.dao.getUnitTypeList(tin);
			
			for(ManageUnitTypeBean bean:unitTypeList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setUnitTypeList(unitTypeList);
			
			if(seqTemp!=null){
				this.form.setSeqTemp(seqTemp);
			}
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSearch is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
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
		
		JSONObject 						obj 					= null;
		List<ManageUnitTypeBean> 		unitTypeList			= null;
		String							tin						= null;
		int								unitCode				= 1;
		boolean							chkFlag					= true;
		
		try{
			obj 				= new JSONObject();
			unitTypeList		= this.form.getUnitTypeList();
			tin 				= this.userBean.getTin();
			
			for(ManageUnitTypeBean bean:unitTypeList){
				bean.setTin(tin);
				if(bean.getRowStatus().equals(ManageUnitTypeForm.NEW)){
					if(chkFlag==true){
						unitCode = this.dao.genId(tin);
						chkFlag  = false;
					}else{
						unitCode++;
					}
					bean.setUnitCode(EnjoyUtils.nullToStr(unitCode));
					this.dao.insertUnitType(bean);
				}else if(bean.getRowStatus().equals(ManageUnitTypeForm.UPD) || bean.getRowStatus().equals(ManageUnitTypeForm.DEL)){
					this.dao.updateUnitType(bean);
				}
			}
			
			commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.error(e);
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSave][End]");
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
