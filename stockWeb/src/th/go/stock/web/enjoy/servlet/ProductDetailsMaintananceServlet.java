package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductdetailBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ManageUnitTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ProductDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDetailsMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "productDetailsMaintananceForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ProductDetailsDao				dao							= null;
    private ProductDetailsMaintananceForm	form						= null;
    private ManageProductTypeDao			productTypeDao				= null;
    private ManageProductGroupDao			productGroupDao				= null;
    private ManageUnitTypeDao				unitTypeDao					= null;
    
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
             this.form               		= (ProductDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao						= new ProductDetailsDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productGroupDao			= new ManageProductGroupDao();
             this.unitTypeDao				= new ManageUnitTypeDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new ProductDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ProductDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/ProductDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("validate")){
				this.lp_validate();
			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
			}else if(pageAction.equals("getUnitNameList")){
				this.getUnitNameList();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("cancel")){
				this.cancel();
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
			
			this.setRefference();
			this.form.setTitlePage("เพิ่มรายละเอียดสินค้า");
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		try{
			this.setAvailPageFlagCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setAvailPageFlagCombo() throws EnjoyException{
		
		logger.info("[setAvailPageFlagCombo][Begin]");
		
		List<ComboBean>			availPageFlagCombo 	= null;
		JSONObject 				availPageFlagObj 	= null;
		JSONArray				jSONArray 			= new JSONArray();
		String[]				idArray 			= {"AL", "CA", "CR"};
		String[]				descArray 			= {"ทั้งหมด", "เงินสด", "เงินเชื่อ"};
		
		try{
			
			availPageFlagCombo = new ArrayList<ComboBean>();
			
			for(int i=0;i<idArray.length;i++){
				availPageFlagCombo.add(new ComboBean(idArray[i]	, descArray[i]));
				
				availPageFlagObj 	= new JSONObject();
				availPageFlagObj.put("id"	, idArray[i]);
				availPageFlagObj.put("desc"	, descArray[i]);
				jSONArray.add(availPageFlagObj);
			}
			
			logger.info("[setAvailPageFlagCombo] availPageFlagCombo :: " + jSONArray.toJSONString());
			
			form.setAvailPageFlagJsonString(jSONArray.toJSONString());
			
			this.form.setAvailPageFlagCombo(availPageFlagCombo);
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setAvailPageFlagCombo is error");
		}finally{
			logger.info("[setAvailPageFlagCombo][End]");
		}
	}
	
	private void getDetail(String productCode) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		ProductmasterBean 			productmasterBean		= null;
		ProductmasterBean 			productmasterBeanDb		= null;
		String						seqTemp					= null;
		List<ProductdetailBean> 	productdetailList		= null;
		ProductdetailBean 			productdetailBean		= null;
		String						tin						= null;
		
		try{
			productCode			= productCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("productCode")):productCode;
			tin					= this.userBean.getTin();
			
			logger.info("[getDetail] productCode 	:: " + productCode);
			logger.info("[getDetail] tin 			:: " + tin);
			
			productmasterBean = new ProductmasterBean();
			productmasterBean.setProductCode(productCode);
			productmasterBean.setTin(tin);
			
			productmasterBeanDb				= this.dao.getProductDetail(productmasterBean);
			
			this.form.setTitlePage("แก้ไขรายละเอียดสินค้า");
			this.form.setPageMode(ProductDetailsMaintananceForm.EDIT);
			
			logger.info("[getDetail] productmasterBeanDb :: " + productmasterBeanDb);
			
			if(productmasterBeanDb!=null){
				productdetailBean = new ProductdetailBean();
				
				productdetailBean.setProductCode(productCode);
				productdetailBean.setTin(tin);
				productdetailList = this.dao.getProductdetailList(productdetailBean);
				
				for(ProductdetailBean bean:productdetailList){
					seqTemp = bean.getSeq();
				}
				
				this.form.setProductdetailList(productdetailList);
				
				if(seqTemp!=null){
					this.form.setSeqTemp(seqTemp);
				}
				
				
				this.form.setProductmasterBean(productmasterBeanDb);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดสินค้า");
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			this.setRefference();
			
			logger.info("[getDetail][End]");
		}
		
	}
	   
   private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   String						productTypeCode				= null;
	   String						productGroupCode			= null;
	   String						unitCode					= null;
	   String						productTypeName				= null;
	   String						productGroupName			= null;
	   String						unitName					= null;
	   String 						productCode					= null;
	   String 						productCodeDis				= null;
	   String 						productName					= null;
	   String						tin							= null;
	   JSONObject 					obj 						= new JSONObject();
	   List<ProductdetailBean> 		productdetailList			= null;
	   ProductdetailBean			bean						= null;
	   ProductdetailBean			beanTemp					= null;
	   int							cou							= 0;
	   
	   try{
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName		= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   unitName				= EnjoyUtils.nullToStr(this.request.getParameter("unitName"));
		   productCode			= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
		   productCodeDis		= EnjoyUtils.nullToStr(this.request.getParameter("productCodeDis"));
		   productName			= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   tin					= this.userBean.getTin();
		   productdetailList	= this.form.getProductdetailList();
		   
		   logger.info("[lp_validate] productTypeName 	:: " + productTypeName);
		   logger.info("[lp_validate] productGroupName 	:: " + productGroupName);
		   logger.info("[lp_validate] unitName 			:: " + unitName);
		   logger.info("[lp_validate] productCode 		:: " + productCode);
		   logger.info("[lp_validate] productCodeDis 	:: " + productCodeDis);
		   logger.info("[lp_validate] productName 		:: " + productName);
		   logger.info("[lp_validate] tin 				:: " + tin);
		   
		   cou = this.dao.checkDupProductCode(productCodeDis, tin, productCode, this.form.getPageMode());
		   if(cou > 0){
			   throw new EnjoyException("รหัสสินค้าซ้ำกันกรุณาระบุใหม่");
		   }
		   
		   cou = this.dao.checkDupProductName(productName, productCode, tin, this.form.getPageMode());
		   if(cou > 0){
			   throw new EnjoyException("ชื่อสินค้าซ้ำกันกรุณาระบุใหม่");
		   }
		   
		   productTypeCode 		= this.productTypeDao.getProductTypeCode(productTypeName, tin);
		   if(productTypeCode==null){
			   throw new EnjoyException("หมวดสินค้าไม่มีอยู่ในระบบ");
		   }
		   
		   productGroupCode = productGroupDao.getProductGroupCode(productTypeCode, productGroupName, tin);
		   if(productGroupCode==null){
			   throw new EnjoyException("หมู่สินค้าไม่มีอยู่ในระบบ");
		   }
		   
		   unitCode = this.unitTypeDao.getUnitCode(unitName, tin);
		   if(unitCode==null){
			   throw new EnjoyException("หน่วยสินค้าไม่มีอยู่ในระบบ");
		   }
		   
		   obj.put(STATUS				, SUCCESS);
		   obj.put("productTypeCode"	, productTypeCode);
		   obj.put("productGroupCode"	, productGroupCode);
		   obj.put("unitCode"			, unitCode);
		   
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
		
		String						productCode				= null;
		String						productCodeDis			= null;
		String						productName				= null;
		String						productTypeCode			= null;
		String						productGroupCode		= null;
		String						unitCode				= null;
		String						minQuan					= null;
		String						costPrice				= null;
		String						salePrice1				= null;
		String						salePrice2				= null;
		String						salePrice3				= null;
		String						salePrice4				= null;
		String						salePrice5				= null;
		String						tin						= null;
		JSONObject 					obj 					= null;
		ProductmasterBean  			productmasterBean		= null;
		List<ProductdetailBean> 	productdetailList		= null;
		ProductdetailBean			bean					= null;
		int							seqDb					= 1;
		
		try{
			productCode 				= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			productCodeDis 				= EnjoyUtil.nullToStr(request.getParameter("productCodeDis"));
			productName 				= EnjoyUtil.nullToStr(request.getParameter("productName"));
			productTypeCode 			= EnjoyUtil.nullToStr(request.getParameter("productTypeCode"));
			productGroupCode 			= EnjoyUtil.nullToStr(request.getParameter("productGroupCode"));
			unitCode 					= EnjoyUtil.nullToStr(request.getParameter("unitCode"));
			minQuan 					= EnjoyUtil.nullToStr(request.getParameter("minQuan"));
			costPrice 					= EnjoyUtil.nullToStr(request.getParameter("costPrice"));
			salePrice1 					= EnjoyUtil.nullToStr(request.getParameter("salePrice1"));
			salePrice2 					= EnjoyUtil.nullToStr(request.getParameter("salePrice2"));
			salePrice3 					= EnjoyUtil.nullToStr(request.getParameter("salePrice3"));
			salePrice4 					= EnjoyUtil.nullToStr(request.getParameter("salePrice4"));
			salePrice5 					= EnjoyUtil.nullToStr(request.getParameter("salePrice5"));
			tin							= this.userBean.getTin();
			obj 						= new JSONObject();
			productmasterBean			= new ProductmasterBean();
			productdetailList			= this.form.getProductdetailList();
			
			logger.info("[onSave] productCode 			:: " + productCode);
			logger.info("[onSave] productCodeDis 		:: " + productCodeDis);
			logger.info("[onSave] productName 			:: " + productName);
			logger.info("[onSave] productTypeCode 		:: " + productTypeCode);
			logger.info("[onSave] productGroupCode 		:: " + productGroupCode);
			logger.info("[onSave] unitCode 				:: " + unitCode);
			logger.info("[onSave] minQuan 				:: " + minQuan);
			logger.info("[onSave] costPrice 			:: " + costPrice);
			logger.info("[onSave] salePrice1 			:: " + salePrice1);
			logger.info("[onSave] salePrice2 			:: " + salePrice2);
			logger.info("[onSave] salePrice3 			:: " + salePrice3);
			logger.info("[onSave] salePrice4 			:: " + salePrice4);
			logger.info("[onSave] salePrice5 			:: " + salePrice5);
			logger.info("[onSave] tin 					:: " + tin);
			
//			productmasterBean.setProductCode(productCode);
			productmasterBean.setTin(tin);
			productmasterBean.setProductCodeDis(productCodeDis);
			productmasterBean.setProductName(productName);
			productmasterBean.setProductTypeCode(productTypeCode);
			productmasterBean.setProductGroupCode(productGroupCode);
			productmasterBean.setUnitCode(unitCode);
			productmasterBean.setMinQuan(minQuan);
			productmasterBean.setCostPrice(costPrice);
			productmasterBean.setSalePrice1(salePrice1);
			productmasterBean.setSalePrice2(salePrice2);
			productmasterBean.setSalePrice3(salePrice3);
			productmasterBean.setSalePrice4(salePrice4);
			productmasterBean.setSalePrice5(salePrice5);
			productmasterBean.setProductStatus("A");
			
			if("".equals(productCode)){
				productCode = EnjoyUtil.nullToStr(this.dao.genId(tin));
				productmasterBean.setProductCode(productCode);
				
				this.dao.insertProductmaster(productmasterBean);
			}else{
				productmasterBean.setProductCode(productCode);
				this.dao.updateProductmaster(productmasterBean);
			}
			
			this.dao.deleteProductdetail(productCode, tin);
			
			for(int i=0;i<productdetailList.size();i++){
				bean = productdetailList.get(i);
				if(!bean.getRowStatus().equals(ProductDetailsMaintananceForm.DEL)){
					bean.setProductCode(productCode);
					bean.setTin(tin);
					bean.setSeqDb(String.valueOf(seqDb));
					this.dao.insertProductdetail(bean);
					seqDb++;
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
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 				obj 					= null;
		ProductdetailBean		productdetailBean		= null;
		String					newSeq					= null;
		
		try{
			
			productdetailBean 		= new ProductdetailBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			productdetailBean.setRowStatus(ProductDetailsMaintananceForm.NEW);
			productdetailBean.setSeq(newSeq);
			productdetailBean.setAvailPageFlag("AL");
			
			this.form.getProductdetailList().add(productdetailBean);
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
		String 							quanDiscount			= null;
		String 							discountRate			= null;
		String 							seq						= null;
		List<ProductdetailBean> 		productdetailList		= null;
		String 							startDate				= null;
		String 							expDate					= null;
		String 							availPageFlag			= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			quanDiscount 			= EnjoyUtil.nullToStr(request.getParameter("quanDiscount"));
			discountRate 			= EnjoyUtil.nullToStr(request.getParameter("discountRate"));
			startDate 				= EnjoyUtils.nullToStr(request.getParameter("startDate"));
			expDate 				= EnjoyUtils.nullToStr(request.getParameter("expDate"));
			availPageFlag 			= EnjoyUtils.nullToStr(request.getParameter("availPageFlag"));
			productdetailList		= this.form.getProductdetailList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] quanDiscount 	:: " + quanDiscount);
			logger.info("[updateRecord] discountRate 	:: " + discountRate);
			logger.info("[updateRecord] startDate 		:: " + startDate);
			logger.info("[updateRecord] expDate 		:: " + expDate);
			logger.info("[updateRecord] availPageFlag 	:: " + availPageFlag);
			
			for(ProductdetailBean bean:productdetailList){
				if(bean.getSeq().equals(seq)){
					
					bean.setQuanDiscount	(quanDiscount);
					bean.setDiscountRate	(discountRate);
					bean.setStartDate		(startDate);
					bean.setExpDate			(expDate);
					bean.setAvailPageFlag	(availPageFlag);
					
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
		List<ProductdetailBean> 		productdetailList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productdetailList		= this.form.getProductdetailList();
			
			for(int i=0;i<productdetailList.size();i++){
				ProductdetailBean bean = productdetailList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(ProductDetailsMaintananceForm.NEW)){
						productdetailList.remove(i);
					}else{
						bean.setRowStatus(ProductDetailsMaintananceForm.DEL);
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
	
	private void cancel() throws EnjoyException{
		logger.info("[cancel][Begin]");
		
		JSONObject 		obj 			= null;
		String 			productCode		= null;
		String			tin 			= null;
		
		try{
			
			obj 			= new JSONObject();
			productCode 	= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			tin				= this.userBean.getTin();
			
			logger.info("[cancel] productCode :: " + productCode);
			
			dao.cancelProductmaster(tin, "", "", productCode);
			
			commit();
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"cancel is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[cancel][End]");
		}
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.productTypeDao.destroySession();
        this.productGroupDao.destroySession();
        this.unitTypeDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.productTypeDao.commit();
        this.productGroupDao.commit();
        this.unitTypeDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.productTypeDao.rollback();
        this.productGroupDao.rollback();
        this.unitTypeDao.rollback();
	}
	
	
}
