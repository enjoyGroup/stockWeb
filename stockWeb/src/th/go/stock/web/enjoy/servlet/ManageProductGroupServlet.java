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

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ManageProductGroupBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageProductGroupForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.ExcelUtil;
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
    private ProductDetailsDao				productDetailsDao			= null;
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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (ManageProductGroupForm)session.getAttribute(FORM_NAME);
             this.dao						= new ManageProductGroupDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productDetailsDao			= new ProductDetailsDao();
 			
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
		String 							productGroupCodeDis		= null;
		String 							productGroupName		= null;
		String 							seq						= null;
		List<ManageProductGroupBean> 	productGroupList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productGroupCode 		= EnjoyUtil.nullToStr(request.getParameter("productGroupCode"));
			productGroupCodeDis 	= EnjoyUtil.nullToStr(request.getParameter("productGroupCodeDis"));
			productGroupName 		= EnjoyUtil.nullToStr(request.getParameter("productGroupName"));
			productGroupList		= this.form.getProductGroupList();
			
			logger.info("[updateRecord] seq 				:: " + seq);
			logger.info("[updateRecord] productGroupCode 	:: " + productGroupCode);
			logger.info("[updateRecord] productGroupCodeDis :: " + productGroupCodeDis);
			logger.info("[updateRecord] productGroupName 	:: " + productGroupName);
			
			for(ManageProductGroupBean bean:productGroupList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductGroupCode	(productGroupCode);
					bean.setProductGroupCodeDis	(productGroupCodeDis);
					bean.setProductGroupName	(productGroupName);
					
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
						
						if(!beanTemp.getRowStatus().equals(ManageProductGroupForm.DEL) && bean.getProductGroupCodeDis().equals(beanTemp.getProductGroupCodeDis())){
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
		
		JSONObject 						obj 					= null;
		List<ManageProductGroupBean> 	productGroupList		= null;
		String							tin						= null;
		boolean							chkFlag					= true;
		int								productGroupCode		= 0;
		
		try{
			obj 					= new JSONObject();
			productGroupList		= this.form.getProductGroupList();
			tin 					= this.userBean.getTin();
			
			logger.info("[onSave] tin :: " + tin);
			
			for(ManageProductGroupBean bean:productGroupList){
				bean.setTin(tin);
				if(bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
					if(chkFlag==true){
						productGroupCode 	= this.dao.genId(tin, bean.getProductTypeCode());
						chkFlag  			= false;
					}else{
						productGroupCode++;
					}
					
					logger.info("[onSave] productGroupCode :: " + productGroupCode);
					
					bean.setProductGroupCode(EnjoyUtils.nullToStr(productGroupCode));
					this.dao.insertProducGroup(bean);
				}else if(bean.getRowStatus().equals(ManageProductGroupForm.UPD) || bean.getRowStatus().equals(ManageProductGroupForm.DEL)){
					this.dao.updateProductgroup(bean);
					
					if(bean.getRowStatus().equals(ManageProductGroupForm.DEL)){
						productDetailsDao.cancelProductmaster(tin, bean.getProductTypeCode(), bean.getProductGroupCode(), "");
					}
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
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		ManageProductGroupBean 			manageProductGroupBean	= null;
		List<ManageProductGroupBean> 	productGroupList 		= null;
		String							productTypeName			= null;
		String							productTypeCode			= null;
		JSONObject 						obj 					= null;
		String							seqTemp					= null;
		String							tin 					= null;

		try{
			obj 					= new JSONObject();
			productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
			tin 					= this.userBean.getTin();
			productTypeCode			= this.productTypeDao.getProductTypeCode(productTypeName, tin);
			
			logger.info("[onSearch] productTypeCode :: " + productTypeCode);
			logger.info("[onSearch] productTypeName :: " + productTypeName);
			
			if(productTypeCode==null || productTypeCode.equals("")){
				throw new EnjoyException("หมวดสินค้านี้ไม่มีในระบบกรุณาตรวจสอบ");
			}
			
			this.form.setProductTypeCode(productTypeCode);
			this.form.setProductTypeName(productTypeName);
			this.form.setChk(true);
			
			manageProductGroupBean				= new ManageProductGroupBean();
			manageProductGroupBean.setProductTypeCode(productTypeCode);
			manageProductGroupBean.setTin(tin);
			
			productGroupList	 		= this.dao.getProductGroupList(manageProductGroupBean);
			
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
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
		}
		
	}
	
	private void getProductType(){
	   logger.info("[getProductType][Begin]");
	   
	   String			productTypeName			= null;
	   List<ComboBean> 	list 					= null;
       JSONArray 		jSONArray 				= null;
       JSONObject 		objDetail 				= null;
       String			tin 					= null;
       
	   try{
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   tin 					= this.userBean.getTin();
		   jSONArray 			= new JSONArray();
		   
		   logger.info("[getProductType] productTypeName 	:: " + productTypeName);
		   logger.info("[getProductType] tin 				:: " + tin);
		   
		   list 		= this.productTypeDao.productTypeNameList(productTypeName, tin);
		   
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
		ManageProductGroupBean	manageProductGroupBean	= null;
		String					productGroupCodeDis		= null;
		String					productGroupName		= null;
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
							rowArray  = ExcelUtil.getAllRows(sheet);
							
							logger.info("[lp_uploadFile] sheetName :: " + sheetName);
							
							for(int j=1;j<rowArray.length;j++){
								manageProductGroupBean 	= new ManageProductGroupBean(rowArray[j]);
								objDetail 				= new JSONObject();
								productGroupCodeDis		= manageProductGroupBean.getColA().getValue();
								productGroupName		= manageProductGroupBean.getColB().getValue();
								
								logger.info("[lp_uploadFile] productGroupCodeDis :: " + productGroupCodeDis);
								logger.info("[lp_uploadFile] productGroupName :: " + productGroupName);
								
								objDetail.put("productGroupCodeDis"	, productGroupCodeDis);
								objDetail.put("productGroupName"	, productGroupName);
								
								jSONArray.add(objDetail);
							}
						}
			    		
						del = uploadedFile.delete();
						logger.info("[lp_uploadFile] del :: " + del);
			    		
			         }
			    }
			}
			
			obj.put(STATUS				, SUCCESS);
			obj.put("productGroupList"	, jSONArray);

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
        this.productTypeDao.destroySession();
        this.productDetailsDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.productTypeDao.commit();
        this.productDetailsDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.productTypeDao.rollback();
        this.productDetailsDao.rollback();
	}

}
