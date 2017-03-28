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
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ManageUnitTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ProductQuanHistoryDao;
import th.go.stock.app.enjoy.dao.ProductquantityDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ManageProductGroupForm;
import th.go.stock.app.enjoy.form.MultiManageProductForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.ExcelUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class MultiManageProductServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(MultiManageProductServlet.class);
	
    private static final String 	FORM_NAME 				= "multiManageProductForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ManageProductGroupDao			productGroupDao				= null;
    private ManageProductTypeDao			productTypeDao				= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private ManageUnitTypeDao				unitTypeDao					= null;
    private ProductquantityDao				productquantityDao			= null;
    private ProductQuanHistoryDao			productQuanHistoryDao		= null;
    private MultiManageProductForm			form						= null;
    
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
             this.form               		= (MultiManageProductForm)session.getAttribute(FORM_NAME);
             this.productGroupDao			= new ManageProductGroupDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productDetailsDao			= new ProductDetailsDao();
             this.unitTypeDao				= new ManageUnitTypeDao();
             this.productquantityDao		= new ProductquantityDao();
             this.productQuanHistoryDao		= new ProductQuanHistoryDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new MultiManageProductForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/MultiManageProductScn.jsp");
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
 			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
			}else if(pageAction.equals("getUnitNameList")){
				this.getUnitNameList();
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
		
		JSONObject 			obj 					= null;
		ProductmasterBean	productmasterBean		= new ProductmasterBean();
		String				newSeq					= null;
		String				productCodeDis			= null;
		String				productName				= null;
		String				minQuan					= null;
		String				costPrice				= null;
		String				salePrice1				= null;
		String				salePrice2				= null;
		String				salePrice3				= null;
		String				salePrice4				= null;
		String				salePrice5				= null;
		String				quantity				= null;
		
		try{
			
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			productCodeDis 			= EnjoyUtil.nullToStr(request.getParameter("productCodeDis"));
			productName 			= EnjoyUtil.nullToStr(request.getParameter("productName"));
			minQuan 				= EnjoyUtil.nullToStr(request.getParameter("minQuan"));
			costPrice 				= EnjoyUtil.nullToStr(request.getParameter("costPrice"));
			salePrice1 				= EnjoyUtil.nullToStr(request.getParameter("salePrice1"));
			salePrice2 				= EnjoyUtil.nullToStr(request.getParameter("salePrice2"));
			salePrice3 				= EnjoyUtil.nullToStr(request.getParameter("salePrice3"));
			salePrice4 				= EnjoyUtil.nullToStr(request.getParameter("salePrice4"));
			salePrice5 				= EnjoyUtil.nullToStr(request.getParameter("salePrice5"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			productmasterBean.setRowStatus(MultiManageProductForm.NEW);
			productmasterBean.setSeq(newSeq);
			productmasterBean.setProductCodeDis(productCodeDis);
			productmasterBean.setProductName(productName);
			productmasterBean.setMinQuan(minQuan);
			productmasterBean.setCostPrice(costPrice);
			productmasterBean.setSalePrice1(salePrice1);
			productmasterBean.setSalePrice2(salePrice2);
			productmasterBean.setSalePrice3(salePrice3);
			productmasterBean.setSalePrice4(salePrice4);
			productmasterBean.setSalePrice5(salePrice5);
			productmasterBean.setQuantity(quantity);
			
			this.form.getProductList().add(productmasterBean);
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
		String							productCodeDis			= null;
		String							productName				= null;
		String							minQuan					= null;
		String							costPrice				= null;
		String							salePrice1				= null;
		String							salePrice2				= null;
		String							salePrice3				= null;
		String							salePrice4				= null;
		String							salePrice5				= null;
		String							quantity				= null;
		String 							seq						= null;
		List<ProductmasterBean> 		productList				= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productCodeDis 			= EnjoyUtil.nullToStr(request.getParameter("productCodeDis"));
			productName 			= EnjoyUtil.nullToStr(request.getParameter("productName"));
			minQuan 				= EnjoyUtil.nullToStr(request.getParameter("minQuan"));
			costPrice 				= EnjoyUtil.nullToStr(request.getParameter("costPrice"));
			salePrice1 				= EnjoyUtil.nullToStr(request.getParameter("salePrice1"));
			salePrice2 				= EnjoyUtil.nullToStr(request.getParameter("salePrice2"));
			salePrice3 				= EnjoyUtil.nullToStr(request.getParameter("salePrice3"));
			salePrice4 				= EnjoyUtil.nullToStr(request.getParameter("salePrice4"));
			salePrice5 				= EnjoyUtil.nullToStr(request.getParameter("salePrice5"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			productList				= this.form.getProductList();
			
			logger.info("[updateRecord] seq 				:: " + seq);
			
			for(ProductmasterBean bean:productList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductCodeDis(productCodeDis);
					bean.setProductName(productName);
					bean.setMinQuan(minQuan);
					bean.setCostPrice(costPrice);
					bean.setSalePrice1(salePrice1);
					bean.setSalePrice2(salePrice2);
					bean.setSalePrice3(salePrice3);
					bean.setSalePrice4(salePrice4);
					bean.setSalePrice5(salePrice5);
					bean.setQuantity(quantity);
					
					if(!bean.getRowStatus().equals(MultiManageProductForm.NEW)){
						bean.setRowStatus(MultiManageProductForm.UPD);
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
		List<ProductmasterBean> 		productList				= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productList				= this.form.getProductList();
			
			for(int i=0;i<productList.size();i++){
				ProductmasterBean bean = productList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(MultiManageProductForm.NEW)){
						bean.setRowStatus(MultiManageProductForm.DEL);
					}else{
						productList.remove(i);
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
	   
	   JSONObject 					obj 					= new JSONObject();
	   List<ProductmasterBean> 		productList				= null;
	   int							cou						= 0;
	   String						tin						= null;
	   
	   try{
		   productList			= this.form.getProductList();
		   tin					= this.userBean.getTin();
		   
		   for(ProductmasterBean bean:productList){
			   if(bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
				   cou = this.productDetailsDao.checkDupProductCode(bean.getProductCodeDis(), tin, bean.getProductCode(), bean.getRowStatus());
				   if(cou > 0){
					   throw new EnjoyException("รหัสสินค้า " + bean.getProductCodeDis() + "มีอยู่ในระบบแล้วกรุณาระบุใหม่");
				   }
				   
				   cou = this.productDetailsDao.checkDupProductName(bean.getProductName(), bean.getProductCode(), tin, bean.getRowStatus());
				   if(cou > 0){
					   throw new EnjoyException("ชื่อสินค้า " + bean.getProductName() + "มีอยู่ในระบบแล้วกรุณาระบุใหม่");
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
		
		JSONObject 					obj 					= new JSONObject();
		List<ProductmasterBean> 	productList				= null;
		String						tin						= null;
		ProductquantityBean			productquantityBean		= null;
		String						quantityDb				= null;
		ProductQuanHistoryBean		productQuanHistoryBean	= null;
		ProductmasterBean			productmasterBean		= null;
		int							productCode				= 1;
		int							hisCode					= 1;
		boolean						chkFlag					= true;
		
		try{
			productList			= this.form.getProductList();
			tin 				= this.userBean.getTin();
			
			logger.info("[onSave] tin :: " + tin);
			
			for(ProductmasterBean bean:productList){
				if(bean.getRowStatus().equals(ManageProductGroupForm.NEW)){
					/*Begin หา productCode*/
					if(chkFlag==true){
						productCode = this.productDetailsDao.genId(tin);
						hisCode		= productQuanHistoryDao.genId(tin);
						chkFlag  	= false;
					}else{
						productCode++;
						hisCode++;
					}
					/*End หา productCode*/
					
					/*Begin รายการสินค้า*/
					bean.setTin				(tin);
					bean.setProductCode		(EnjoyUtils.nullToStr(productCode));
					bean.setProductTypeCode	(this.form.getProductTypeCode());
					bean.setProductGroupCode(this.form.getProductGroupCode());
					bean.setUnitCode		(this.form.getUnitCode());
					bean.setProductStatus	("A");
					this.productDetailsDao.insertProductmaster(bean);
					/*End รายการสินค้า*/
					
					/*Begin จำนวนสินค้า*/
					productquantityBean = new ProductquantityBean();
					productquantityBean.setProductCode(EnjoyUtils.nullToStr(productCode));
					productquantityBean.setTin(tin);
					productquantityBean.setQuantity(bean.getQuantity());
					
					quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
					
					if(quantityDb==null){
						this.productquantityDao.insertProductquantity(productquantityBean);
					}else{
						this.productquantityDao.updateProductquantity(productquantityBean);
					}
					/*End จำนวนสินค้า*/
					
					/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
					productQuanHistoryBean = new ProductQuanHistoryBean();
					productQuanHistoryBean.setFormRef("MultiManageProduct");
					
					productQuanHistoryBean.setProductType(bean.getProductTypeCode());
					productQuanHistoryBean.setProductGroup(bean.getProductGroupCode());
					productQuanHistoryBean.setProductCode(EnjoyUtils.nullToStr(productCode));
					
					productQuanHistoryBean.setTin(tin);
					
					if(EnjoyUtils.parseDouble(bean.getQuantity()) > 0){
						productQuanHistoryBean.setQuantityPlus(bean.getQuantity());
						productQuanHistoryBean.setQuantityMinus("0.00");
					}else{
						productQuanHistoryBean.setQuantityPlus("0.00");
						productQuanHistoryBean.setQuantityMinus(EnjoyUtil.nullToStr(Math.abs(EnjoyUtils.parseDouble(bean.getQuantity()))));
					}
					productQuanHistoryBean.setQuantityTotal(bean.getQuantity());
					
					productQuanHistoryDao.insert(productQuanHistoryBean, hisCode);
					/*End ส่วนประวัตเพิ่มลดสินค้า*/
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
		
		ProductmasterBean 				productDetailsBean		= null;
		List<ProductmasterBean> 		productList				= null;
		String							productTypeName			= null;
		String							productGroupName		= null;
		String							unitName				= null;
		String							productTypeCode			= null;
		String							productGroupCode		= null;
		String							unitCode				= null;
		JSONObject 						obj 					= new JSONObject();
		String							seqTemp					= null;
		String							tin 					= null;

		try{
			productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
			productGroupName		= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
			unitName				= EnjoyUtils.nullToStr(this.request.getParameter("unitName"));
			tin 					= this.userBean.getTin();
			
			productTypeCode 		= this.productTypeDao.getProductTypeCode(productTypeName, tin);
			if(productTypeCode==null){
				throw new EnjoyException("หมวดสินค้า"+productTypeName+"ไม่มีอยู่ในระบบ");
			}
			
			productGroupCode = productGroupDao.getProductGroupCode(productTypeCode, productGroupName, tin);
			if(productGroupCode==null){
				throw new EnjoyException("หมู่สินค้า"+productGroupName+"ไม่มีอยู่ในระบบ");
			}
			
			unitCode = this.unitTypeDao.getUnitCode(unitName, tin);
			if(unitCode==null){
				throw new EnjoyException("หน่วยสินค้า"+unitName+"ไม่มีอยู่ในระบบ");
			}
			
			this.form.setProductTypeCode(productTypeCode);
			this.form.setProductTypeName(productTypeName);
			this.form.setProductGroupCode(productGroupCode);
			this.form.setProductGroupName(productGroupName);
			this.form.setUnitCode(unitCode);
			this.form.setUnitName(unitName);
			
			this.form.setChk(true);
			
			productDetailsBean				= new ProductmasterBean();
			productDetailsBean.setTin(tin);
			productDetailsBean.setProductTypeName(productTypeName);
			productDetailsBean.setProductGroupName(productGroupName);
			productDetailsBean.setUnitName(unitName);
			
			productList	 		= this.productDetailsDao.getMultiManageProduct(productDetailsBean);
			
			for(ProductmasterBean bean:productList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setProductList(productList);
			
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
	
	private void getProductTypeNameList(){
	   logger.info("[getProductTypeNameList][Begin]");
	   
	   String			productTypeName		= null;
	   String			tin 				= null;
	   List<ComboBean> 	list 				= null;
       JSONArray 		jSONArray 			= null;
       JSONObject 		objDetail 			= null;
       
	   try{
		   productTypeName	= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   tin				= this.userBean.getTin();
		   jSONArray 		= new JSONArray();
		   
		   logger.info("[getProductTypeNameList] productTypeName 	:: " + productTypeName);
		   logger.info("[getProductTypeNameList] tin 				:: " + tin);
		   
		   
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
		   logger.info("[getProductTypeNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductTypeNameList][End]");
	   }
   }
	   
   private void getProductGroupNameList(){
	   logger.info("[getProductGroupNameList][Begin]");
	   
	   String			productTypeName			= null;
	   String			productGroupName		= null;
	   String			tin 					= null;
	   List<ComboBean> 	list 					= null;
       JSONArray 		jSONArray 				= null;
       JSONObject 		objDetail 				= null;
       
	   try{
		   jSONArray 			= new JSONArray();
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName		= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   tin					= this.userBean.getTin();
		   
		   
		   logger.info("[getProductGroupNameList] productTypeName 	:: " + productTypeName);
		   logger.info("[getProductGroupNameList] productGroupName 	:: " + productGroupName);
		   logger.info("[getProductGroupNameList] tin 				:: " + tin);
		   
		   list 		= this.productGroupDao.productGroupNameList(productTypeName, productGroupName, tin, false);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductGroupNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductGroupNameList][End]");
	   }
   }
	
	private void getUnitNameList(){
	   logger.info("[getUnitNameList][Begin]");
	   
	   String			unitName			= null;
	   String			tin 				= null;
	   List<ComboBean> 	list 				= null;
       JSONArray 		jSONArray 			= null;
       JSONObject 		objDetail 			= null;
      
	   try{
		   unitName			= EnjoyUtils.nullToStr(this.request.getParameter("unitName"));
		   tin				= this.userBean.getTin();
		   jSONArray 		= new JSONArray();
		   
		   logger.info("[getUnitNameList] unitName 	:: " + unitName);
		   logger.info("[getUnitNameList] tin 		:: " + tin);
		   
		   
		   list 		= this.unitTypeDao.unitNameList(unitName, tin);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getUnitNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getUnitNameList][End]");
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
		ProductmasterBean		productmasterBean		= null;
		String					productCodeDis			= null;
		String					productName				= null;
		String					minQuan					= null;
		String					costPrice				= null;
		String					salePrice1				= null;
		String					salePrice2				= null;
		String					salePrice3				= null;
		String					salePrice4				= null;
		String					salePrice5				= null;
		String					quantity				= null;
		boolean					del						= false;
		JSONObject 				obj 					= new JSONObject();
		JSONArray 				jSONArray 				= new JSONArray();
		JSONObject 				objDetail 				= null;
		int						seqTemp					= 0;
		List<ProductmasterBean> productList				= null;
		
		try{
			seqTemp 	= EnjoyUtil.parseInt(this.form.getSeqTemp());
			productList	= this.form.getProductList();
			
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
			    		
			    		if(numberOfSheets > 0){
//							for (int i = 0; i < numberOfSheets; i++) {
//								sheetName = wb.getSheetName(i);
//								sheet     = wb.getSheetAt(i);
			    			sheetName = wb.getSheetName(0);
							sheet     = wb.getSheetAt(0);
							rowArray  = ExcelUtil.getAllRows(sheet);
							
							logger.info("[lp_uploadFile] sheetName :: " + sheetName);
							
							for(int j=1;j<rowArray.length;j++){
								productmasterBean 	= new ProductmasterBean(rowArray[j]);
								objDetail 			= new JSONObject();
								productCodeDis		= productmasterBean.getColA().getValue();
								productName			= productmasterBean.getColB().getValue();
								minQuan				= productmasterBean.getColC().getValue();
								costPrice			= productmasterBean.getColD().getValue();
								salePrice1			= productmasterBean.getColE().getValue();
								salePrice2			= productmasterBean.getColF().getValue();
								salePrice3			= productmasterBean.getColG().getValue();
								salePrice4			= productmasterBean.getColH().getValue();
								salePrice5			= productmasterBean.getColI().getValue();
								quantity			= productmasterBean.getColJ().getValue();
								
								productmasterBean.setProductCodeDis(productCodeDis);
								productmasterBean.setProductName(productName);
								productmasterBean.setMinQuan(minQuan);
								productmasterBean.setCostPrice(costPrice);
								productmasterBean.setSalePrice1(salePrice1);
								productmasterBean.setSalePrice2(salePrice2);
								productmasterBean.setSalePrice3(salePrice3);
								productmasterBean.setSalePrice4(salePrice4);
								productmasterBean.setSalePrice5(salePrice5);
								productmasterBean.setQuantity(quantity);
								productmasterBean.setRowStatus(MultiManageProductForm.NEW);
								productmasterBean.setSeq(EnjoyUtil.nullToStr(++seqTemp));
								
								productList.add(productmasterBean);
								
								objDetail.put("productCodeDis"	, productCodeDis);
								objDetail.put("productName"		, productName);
								objDetail.put("minQuan"			, EnjoyUtils.convertFloatToDisplay(minQuan, 2));
								objDetail.put("costPrice"		, EnjoyUtils.convertFloatToDisplay(costPrice, 2));
								objDetail.put("salePrice1"		, EnjoyUtils.convertFloatToDisplay(salePrice1, 2));
								objDetail.put("salePrice2"		, EnjoyUtils.convertFloatToDisplay(salePrice2, 2));
								objDetail.put("salePrice3"		, EnjoyUtils.convertFloatToDisplay(salePrice3, 2));
								objDetail.put("salePrice4"		, EnjoyUtils.convertFloatToDisplay(salePrice4, 2));
								objDetail.put("salePrice5"		, EnjoyUtils.convertFloatToDisplay(salePrice5, 2));
								objDetail.put("quantity"		, EnjoyUtils.convertFloatToDisplay(quantity, 2));
								objDetail.put("seq"				, seqTemp);
								
								jSONArray.add(objDetail);
							}
//							}
			    		}
			    		
						del = uploadedFile.delete();
						logger.info("[lp_uploadFile] del :: " + del);
			    		
			         }
			    }
			}
			
			this.form.setSeqTemp(EnjoyUtil.nullToStr(seqTemp));
			
			obj.put(STATUS			, SUCCESS);
			obj.put("productList"	, jSONArray);
			obj.put("seqTemp"		, seqTemp);

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
        this.productTypeDao.destroySession();
        this.productGroupDao.destroySession();
        this.productDetailsDao.destroySession();
        this.unitTypeDao.destroySession();
        this.productquantityDao.destroySession();
        this.productQuanHistoryDao.destroySession();
	}

	@Override
	public void commit() {
        this.productTypeDao.commit();
        this.productGroupDao.commit();
        this.productDetailsDao.commit();
        this.unitTypeDao.commit();
        this.productquantityDao.commit();
        this.productQuanHistoryDao.commit();
	}

	@Override
	public void rollback() {
        this.productTypeDao.rollback();
        this.productGroupDao.rollback();
        this.productDetailsDao.rollback();
        this.unitTypeDao.rollback();
        this.productquantityDao.rollback();
        this.productQuanHistoryDao.rollback();
	}

}
