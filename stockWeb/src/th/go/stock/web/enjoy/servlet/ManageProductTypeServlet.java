package th.go.stock.web.enjoy.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ManageProductTypeBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageProductTypeForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.ExcelUtil;
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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (ManageProductTypeForm)session.getAttribute(FORM_NAME);
             this.dao						= new ManageProductTypeDao();
 			
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
			}else if(pageAction.equals("uploadFile")){
				this.lp_uploadFile();
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
		List<ManageProductTypeBean> productTypeList			= null;
		String						seqTemp					= null;
		String 						tin 					= null;

		try{
			obj 	= new JSONObject();
			tin 	= this.userBean.getTin();
			
			productTypeList				= this.dao.getProductTypeList(tin);
			
			for(ManageProductTypeBean bean:productTypeList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setProductTypeList(productTypeList);
			
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
		
		JSONObject 						obj 					= null;
		List<ManageProductTypeBean> 	productTypeList			= null;
		String							tin						= null;
		
		try{
			obj 				= new JSONObject();
			productTypeList		= this.form.getProductTypeList();
			tin 				= this.userBean.getTin();
			
			for(ManageProductTypeBean bean:productTypeList){
				bean.setTin(tin);
				if(bean.getRowStatus().equals(ManageProductTypeForm.NEW)){
					this.dao.insertProductype(bean);
				}else if(bean.getRowStatus().equals(ManageProductTypeForm.UPD) || bean.getRowStatus().equals(ManageProductTypeForm.DEL)){
					this.dao.updateProductype(bean);
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
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSave][End]");
		}
	}
	
	private void lp_uploadFile() throws Exception{
		logger.info("[lp_uploadFile][Begin]");
		
		boolean                 isMultipart             = ServletFileUpload.isMultipartContent(this.request);
		List                    items                   = null;
		Iterator                iterator                = null;
		String                  fileName                = null;
        File                    uploadedFile            = null;
        long 					curr 					= System.currentTimeMillis();
		String[]				extentArr				= null;
		String					extent					= null;
        Workbook 				wb						= null;
		int 					numberOfSheets;
		Sheet 					sheet					= null;
		String 					sheetName 				= "";
		Row[]   				rowArray  				= null;
		ManageProductTypeBean	manageProductTypeBean	= null;
		String					productTypeCode			= null;
		String					productTypeName			= null;
		boolean					del						= false;
		JSONObject 				obj 					= new JSONObject();
		JSONArray 				jSONArray 				= new JSONArray();
		JSONObject 				objDetail 				= null;
		
		try{
			if (isMultipart) {
				items                   = (List) this.request.getAttribute(Constants.LIST_FILE);
                iterator                = items.iterator();
                logger.info("[lp_uploadFile] items :: " + items.size());
			    
			    while (iterator.hasNext()) {
			    	FileItem item = (FileItem) iterator.next();
			    	
			    	logger.info("[lp_uploadFile] item.isFormField() :: " + item.isFormField());
			    	
			    	if (!item.isFormField()) {
			    		fileName 			= new File(item.getName()).getName();
			    		extentArr			= fileName.split("\\.");
			    		extent				= extentArr[(extentArr.length - 1)];
			    		fileName			= String.valueOf(curr) + "." + extent;
			    		
			    		logger.info("[lp_uploadFile] fileName :: " + fileName);
	
			    		uploadedFile = new File(fileName);
			    		item.write(uploadedFile);
			    		
			    		wb             = ExcelUtil.getWorkbook(uploadedFile);
			    		numberOfSheets = wb.getNumberOfSheets();
			    		
			    		logger.info("[lp_uploadFile] numberOfSheets :: " + numberOfSheets);
			    		
						for (int i = 0; i < numberOfSheets; i++) {
							sheetName = wb.getSheetName(i);
							sheet     = wb.getSheetAt(i);
//							startRow  = sheet.getFirstRowNum();
//							endRow    = sheet.getPhysicalNumberOfRows();
							rowArray  = ExcelUtil.getAllRows(sheet);
							
							logger.info("[lp_uploadFile] sheetName :: " + sheetName);
							
							for(int j=1;j<rowArray.length;j++){
								manageProductTypeBean 	= new ManageProductTypeBean(rowArray[j]);
								objDetail 				= new JSONObject();
								productTypeCode			= manageProductTypeBean.getColA().getValue();
								productTypeName			= manageProductTypeBean.getColB().getValue();
								
								logger.info("[lp_uploadFile] productTypeCode :: " + productTypeCode);
								logger.info("[lp_uploadFile] productTypeName :: " + productTypeName);
								
								objDetail.put("productTypeCode", productTypeCode);
								objDetail.put("productTypeName", productTypeName);
								
								jSONArray.add(objDetail);
							}
						}
			    		
						del = uploadedFile.delete();
						logger.info("[lp_uploadFile] del :: " + del);
			    		
			         }
			    }
			}
			
			obj.put(STATUS				, SUCCESS);
			obj.put("productTypeList"	, jSONArray);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"uploadFile is error");
			throw e;
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[lp_uploadFile][End]");
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
