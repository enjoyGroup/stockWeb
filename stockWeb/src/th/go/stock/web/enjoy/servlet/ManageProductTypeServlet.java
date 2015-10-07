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

import th.go.stock.app.enjoy.bean.ManageProductTypeBean;
import th.go.stock.app.enjoy.bean.ProductdetailBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageProductTypeForm;
import th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ManageProductTypeServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageProductTypeServlet.class);
	
    private static final String 	FORM_NAME 				= "manageProductTypeForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ManageProductTypeDao			dao							= null;
    private ManageProductTypeForm			form						= null;
    
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
             this.form               	= (ManageProductTypeForm)session.getAttribute(FORM_NAME);
             this.dao					= new ManageProductTypeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ManageProductTypeForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ManageProductTypeScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("validate")){
				this.lp_validate();
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
		List<ManageProductTypeBean> productTypeList			= null;
		String						seqTemp					= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			productTypeList				= this.dao.getProductTypeList(session);
			
			for(ManageProductTypeBean bean:productTypeList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setProductTypeList(productTypeList);
			
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
		ManageProductTypeBean	manageProductTypeBean	= null;
		String					newSeq					= null;
		
		try{
			
			manageProductTypeBean 	= new ManageProductTypeBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			manageProductTypeBean.setProductTypeStatus("A");
			manageProductTypeBean.setRowStatus(ManageProductTypeForm.NEW);
			manageProductTypeBean.setSeq(newSeq);
			
			this.form.getProductTypeList().add(manageProductTypeBean);
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
		String 							productTypeCode			= null;
		String 							productTypeName			= null;
		String 							seq						= null;
		List<ManageProductTypeBean> 	productTypeList			= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productTypeCode 		= EnjoyUtil.nullToStr(request.getParameter("productTypeCode"));
			productTypeName 		= EnjoyUtil.nullToStr(request.getParameter("productTypeName"));
			productTypeList			= this.form.getProductTypeList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] productTypeCode :: " + productTypeCode);
			logger.info("[updateRecord] productTypeName :: " + productTypeName);
			
			for(ManageProductTypeBean bean:productTypeList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductTypeCode(productTypeCode);
					bean.setProductTypeName(productTypeName);
					
					if(!bean.getRowStatus().equals(ManageProductTypeForm.NEW)){
						bean.setRowStatus(ManageProductTypeForm.UPD);
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
		List<ManageProductTypeBean> 	productTypeList			= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productTypeList			= this.form.getProductTypeList();
			
			for(int i=0;i<productTypeList.size();i++){
				ManageProductTypeBean bean = productTypeList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(ManageProductTypeForm.NEW)){
						bean.setProductTypeStatus("R");
						bean.setRowStatus(ManageProductTypeForm.DEL);
					}else{
						productTypeList.remove(i);
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
	
	private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   JSONObject 					obj 						= new JSONObject();
	   List<ManageProductTypeBean> 	productTypeList				= null;
	   ManageProductTypeBean		bean						= null;
	   ManageProductTypeBean		beanTemp					= null;
	   
	   try{
		   productTypeList	= this.form.getProductTypeList();
		   
		   for(int i=0;i<productTypeList.size();i++){
				bean = productTypeList.get(i);
				if(!bean.getRowStatus().equals(ManageProductTypeForm.DEL)){
					for(int j=(i+1);j<productTypeList.size();j++){
						beanTemp = productTypeList.get(j);
						
						if(!beanTemp.getRowStatus().equals(ManageProductTypeForm.DEL) && bean.getProductTypeCode().equals(beanTemp.getProductTypeCode())){
							throw new EnjoyException("รหัสหมวดสินค้าห้ามซ้ำ");
						}
						
						if(!beanTemp.getRowStatus().equals(ManageProductTypeForm.DEL) && bean.getProductTypeName().equals(beanTemp.getProductTypeName())){
							throw new EnjoyException("ชื่อหมวดสินค้าห้ามซ้ำ");
						}
						
					}
				}
		   }
		   
		   obj.put(STATUS				, SUCCESS);
		   
	   }catch(EnjoyException e){
		   obj.put(STATUS, 				ERROR);
		   obj.put(ERR_MSG, 			e.getMessage());
	   }catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			"เกิดข้อผิดพลาดในการตรวจสอบข้อมูล");
			logger.info(e.getMessage());
			e.printStackTrace();
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[lp_validate][End]");
	   }
	}

	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 					sessionFactory			= null;
		Session 						session					= null;
		JSONObject 						obj 					= null;
		List<ManageProductTypeBean> 	productTypeList			= null;
		ManageProductTypeBean 			bean					= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			productTypeList				= this.form.getProductTypeList();
			
			session.beginTransaction();
			
			for(int i=0;i<productTypeList.size();i++){
				bean = productTypeList.get(i);
				if(bean.getRowStatus().equals(ManageProductTypeForm.NEW)){
					this.dao.insertProductype(session, bean);
				}else if(bean.getRowStatus().equals(ManageProductTypeForm.UPD) || bean.getRowStatus().equals(ManageProductTypeForm.DEL)){
					this.dao.updateProductype(session, bean);
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
}
