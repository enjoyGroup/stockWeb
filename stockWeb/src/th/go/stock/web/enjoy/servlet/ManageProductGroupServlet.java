package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ManageProductGroupBean;
import th.go.stock.app.enjoy.bean.ManageProductTypeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageProductGroupForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ManageProductGroupServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ManageProductGroupServlet.class);
	
    private static final String 	FORM_NAME 				= "manageProductGroupForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ManageProductGroupDao			dao							= null;
    private ManageProductTypeDao			productTypeDao				= null;
    private ManageProductGroupForm			form						= null;
    
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
             this.form               	= (ManageProductGroupForm)session.getAttribute(FORM_NAME);
             this.dao					= new ManageProductGroupDao();
             this.productTypeDao		= new ManageProductTypeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ManageProductGroupForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/ManageProductGroupScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getProductType")){
				this.getProductType();
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
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 				obj 					= null;
		ManageProductGroupBean	manageProductGroupBean	= null;
		String					newSeq					= null;
		String					productTypeCode			= null;
		
		try{
			
			manageProductGroupBean 	= new ManageProductGroupBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			productTypeCode 		= EnjoyUtil.nullToStr(request.getParameter("productTypeCode"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			logger.info("[newRecord] productTypeCode 	:: " + productTypeCode);
			
			manageProductGroupBean.setProductGroupStatus("A");
			manageProductGroupBean.setRowStatus(ManageProductGroupForm.NEW);
			manageProductGroupBean.setSeq(newSeq);
			manageProductGroupBean.setProductTypeCode(productTypeCode);
			
			this.form.getProductGroupList().add(manageProductGroupBean);
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
		String 							productGroupCode		= null;
		String 							productGroupName		= null;
		String 							seq						= null;
		List<ManageProductGroupBean> 	productGroupList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productGroupCode 		= EnjoyUtil.nullToStr(request.getParameter("productGroupCode"));
			productGroupName 		= EnjoyUtil.nullToStr(request.getParameter("productGroupName"));
			productGroupList			= this.form.getProductGroupList();
			
			logger.info("[updateRecord] seq 				:: " + seq);
			logger.info("[updateRecord] productGroupCode :: " + productGroupCode);
			logger.info("[updateRecord] productGroupName :: " + productGroupName);
			
			for(ManageProductGroupBean bean:productGroupList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductGroupCode(productGroupCode);
					bean.setProductGroupName(productGroupName);
					
					if(!bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
						bean.setRowStatus(ManageProductGroupForm.UPD);
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
		List<ManageProductGroupBean> 	productGroupList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productGroupList		= this.form.getProductGroupList();
			
			for(int i=0;i<productGroupList.size();i++){
				ManageProductGroupBean bean = productGroupList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
						bean.setProductGroupStatus("R");
						bean.setRowStatus(ManageProductGroupForm.DEL);
					}else{
						productGroupList.remove(i);
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
	   List<ManageProductGroupBean> productGroupList			= null;
	   ManageProductGroupBean		bean						= null;
	   ManageProductGroupBean		beanTemp					= null;
	   
	   try{
		   productGroupList		= this.form.getProductGroupList();
		   
		   for(int i=0;i<productGroupList.size();i++){
				bean = productGroupList.get(i);
				if(!bean.getRowStatus().equals(ManageProductGroupForm.DEL)){
					for(int j=(i+1);j<productGroupList.size();j++){
						beanTemp = productGroupList.get(j);
						
						if(!beanTemp.getRowStatus().equals(ManageProductGroupForm.DEL) && bean.getProductGroupCode().equals(beanTemp.getProductGroupCode())){
							throw new EnjoyException("รหัสหมู่สินค้าห้ามซ้ำ");
						}
						
						if(!beanTemp.getRowStatus().equals(ManageProductGroupForm.DEL) && bean.getProductGroupName().equals(beanTemp.getProductGroupName())){
							throw new EnjoyException("ชื่อหมู่สินค้าห้ามซ้ำ");
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
		List<ManageProductGroupBean> 	productGroupList		= null;
		ManageProductGroupBean 			bean					= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			productGroupList			= this.form.getProductGroupList();
			
			session.beginTransaction();
			
			for(int i=0;i<productGroupList.size();i++){
				bean = productGroupList.get(i);
				if(bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
					this.dao.insertProducGroup(session, bean);
				}else if(bean.getRowStatus().equals(ManageProductGroupForm.UPD) || bean.getRowStatus().equals(ManageProductGroupForm.DEL)){
					this.dao.updateProductgroup(session, bean);
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
	
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		ManageProductGroupBean 			manageProductGroupBean	= null;
		SessionFactory 					sessionFactory			= null;
		Session 						session					= null;
		List<ManageProductGroupBean> 	productGroupList 		= null;
		String							productTypeName			= null;
		String							productTypeCode			= null;
		JSONObject 						obj 					= null;
		String							seqTemp					= null;

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			obj 						= new JSONObject();
			productTypeName				= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
			productTypeCode				= this.productTypeDao.getProductTypeCode(productTypeName);
			
			logger.info("[onSearch] productTypeCode :: " + productTypeCode);
			logger.info("[onSearch] productTypeName :: " + productTypeName);
			
			if(productTypeCode==null || productTypeCode.equals("")){
				throw new EnjoyException("หมวดสินค้านี้ไม่มีในระบบกรุณาตรวจสอบ");
			}
			
			this.form.setProductTypeCode(productTypeCode);
			this.form.setProductTypeName(productTypeName);
			this.form.setChk(true);
			
			session.beginTransaction();
			
			manageProductGroupBean				= new ManageProductGroupBean();
			manageProductGroupBean.setProductTypeCode(productTypeCode);
			
			
			productGroupList	 		= this.dao.getProductGroupList(session, manageProductGroupBean);
			
			for(ManageProductGroupBean bean:productGroupList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setProductGroupList(productGroupList);
			
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
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
		}
		
	}
	
	private void getProductType(){
	   logger.info("[getProductType][Begin]");
	   
	   String							productTypeName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   jSONArray 				= new JSONArray();
		   
		   logger.info("[getProductType] productTypeName 			:: " + productTypeName);
		   
		   list 		= this.productTypeDao.productTypeNameList(productTypeName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductType] " + e.getMessage());
	   }finally{
		   logger.info("[getProductType][End]");
	   }
   }

}
