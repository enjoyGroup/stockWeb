package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import th.go.stock.app.enjoy.utils.HibernateUtil;
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
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (ProductDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new ProductDetailsDao();
             this.productTypeDao		= new ManageProductTypeDao();
             this.productGroupDao		= new ManageProductGroupDao();
             this.unitTypeDao			= new ManageUnitTypeDao();
 			
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
			this.setStatusCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setStatusCombo() throws EnjoyException{
		
		logger.info("[setStatusCombo][Begin]");
		
		List<ComboBean>			statusCombo = null;
		
		try{
			
			statusCombo = new ArrayList<ComboBean>();
			
			statusCombo.add(new ComboBean(""	, "กรุณาระบุ"));
			statusCombo.add(new ComboBean("A"	, "ใช้งานได้อยู่"));
			statusCombo.add(new ComboBean("C"	, "ยกเลิกการใช้งาน"));
			
			this.form.setStatusCombo(statusCombo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setStatusCombo is error");
		}finally{
			logger.info("[setStatusCombo][End]");
		}
	}
	
	private void getDetail(String productCode) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		ProductmasterBean 			productmasterBean		= null;
		ProductmasterBean 			productmasterBeanDb		= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		String						seqTemp					= null;
		List<ProductdetailBean> 	productdetailList		= null;
		ProductdetailBean 			productdetailBean		= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			productCode					= productCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("productCode")):productCode;
			
			session.beginTransaction();
			
			logger.info("[getDetail] productCode :: " + productCode);
			
			productmasterBean = new ProductmasterBean();
			productmasterBean.setProductCode(productCode);
			
			productmasterBeanDb				= this.dao.getProductDetail(session, productmasterBean);
			
			this.form.setTitlePage("แก้ไขรายละเอียดสินค้า");
			this.form.setPageMode(ProductDetailsMaintananceForm.EDIT);
			
			logger.info("[getDetail] productmasterBeanDb :: " + productmasterBeanDb);
			
			if(productmasterBeanDb!=null){
				productdetailBean = new ProductdetailBean();
				
				productdetailBean.setProductCode(productCode);
				productdetailList = this.dao.getProductdetailList(session, productdetailBean);
				
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
			session.close();
			
			this.setRefference();
			
			sessionFactory	= null;
			session			= null;
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
	   String 						productName					= null;
	   JSONObject 					obj 						= new JSONObject();
	   List<ProductdetailBean> 		productdetailList			= null;
	   ProductdetailBean			bean						= null;
	   ProductdetailBean			beanTemp					= null;
	   int							cou							= 0;
	   SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
	   
	   try{
		   sessionFactory 		= HibernateUtil.getSessionFactory();
		   session 				= sessionFactory.openSession();
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName		= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   unitName				= EnjoyUtils.nullToStr(this.request.getParameter("unitName"));
		   productCode			= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
		   productName			= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   productdetailList	= this.form.getProductdetailList();
		   
		   logger.info("[lp_validate] productTypeName 			:: " + productTypeName);
		   logger.info("[lp_validate] productGroupName 			:: " + productGroupName);
		   logger.info("[lp_validate] unitName 					:: " + unitName);
		   logger.info("[lp_validate] productCode 				:: " + productCode);
		   logger.info("[lp_validate] productName 				:: " + productName);
		   
		   if(this.form.getPageMode().equals(ProductDetailsMaintananceForm.NEW)){
			   cou = this.dao.checkDupProductCode(session, productCode);
			   if(cou > 0){
				   throw new EnjoyException("รหัสสินค้าซ้ำกันกรุณาระบุใหม่");
			   }
		   }
		   
		   cou = this.dao.checkDupProductName(session, productName, productCode, this.form.getPageMode());
		   if(cou > 0){
			   throw new EnjoyException("ชื่อสินค้าซ้ำกันกรุณาระบุใหม่");
		   }
		   
		   productTypeCode 		= this.productTypeDao.getProductTypeCode(productTypeName);
		   if(productTypeCode==null){
			   throw new EnjoyException("หมวดสินค้าไม่มีอยู่ในระบบ");
		   }
		   
		   productGroupCode = productGroupDao.getProductGroupCode(productTypeCode, productGroupName);
		   if(productGroupCode==null){
			   throw new EnjoyException("หมู่สินค้าไม่มีอยู่ในระบบ");
		   }
		   
		   unitCode = this.unitTypeDao.getUnitCode(unitName);
		   if(unitCode==null){
			   throw new EnjoyException("หน่วยสินค้าไม่มีอยู่ในระบบ");
		   }
		   
//		   for(int i=0;i<productdetailList.size();i++){
//				bean = productdetailList.get(i);
//				if(!bean.getRowStatus().equals(ProductDetailsMaintananceForm.DEL)){
//					
//					for(int j=(i+1);j<productdetailList.size();j++){
//						beanTemp = productdetailList.get(j);
//						if(!beanTemp.getRowStatus().equals(ProductDetailsMaintananceForm.DEL) && bean.getQuanDiscount().equals(beanTemp.getQuanDiscount())){
//							throw new EnjoyException("ปริมาณที่ซื้อซ้ำกันกรุณาระบุใหม่");
//						}
//					}
//				}
//			}
		   
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
		   session.flush();
		   session.clear();
		   session.close();
			
		   sessionFactory	= null;
		   session			= null;
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[lp_validate][End]");
	   }
   }
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String						pageMode				= null;
		String						productCode				= null;
		String						productName				= null;
		String						productTypeCode			= null;
		String						productGroupCode		= null;
		String						unitCode				= null;
		String						quantity				= null;
		String						minQuan					= null;
		String						costPrice				= null;
		String						salePrice1				= null;
		String						salePrice2				= null;
		String						salePrice3				= null;
		String						salePrice4				= null;
		String						salePrice5				= null;
//		String						startDate				= null;
//		String						expDate					= null;
//		String						productStatus			= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		JSONObject 					obj 					= null;
		ProductmasterBean  			productmasterBean		= null;
		List<ProductdetailBean> 	productdetailList		= null;
		ProductdetailBean			bean					= null;
		int							seqDb					= 1;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			productCode 				= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			productName 				= EnjoyUtil.nullToStr(request.getParameter("productName"));
			productTypeCode 			= EnjoyUtil.nullToStr(request.getParameter("productTypeCode"));
			productGroupCode 			= EnjoyUtil.nullToStr(request.getParameter("productGroupCode"));
			unitCode 					= EnjoyUtil.nullToStr(request.getParameter("unitCode"));
			quantity 					= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			minQuan 					= EnjoyUtil.nullToStr(request.getParameter("minQuan"));
			costPrice 					= EnjoyUtil.nullToStr(request.getParameter("costPrice"));
			salePrice1 					= EnjoyUtil.nullToStr(request.getParameter("salePrice1"));
			salePrice2 					= EnjoyUtil.nullToStr(request.getParameter("salePrice2"));
			salePrice3 					= EnjoyUtil.nullToStr(request.getParameter("salePrice3"));
			salePrice4 					= EnjoyUtil.nullToStr(request.getParameter("salePrice4"));
			salePrice5 					= EnjoyUtil.nullToStr(request.getParameter("salePrice5"));
//			startDate 					= EnjoyUtil.nullToStr(request.getParameter("startDate"));
//			expDate 					= EnjoyUtil.nullToStr(request.getParameter("expDate"));
//			productStatus 				= EnjoyUtil.nullToStr(request.getParameter("productStatus"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			productmasterBean			= new ProductmasterBean();
			productdetailList			= this.form.getProductdetailList();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] productCode 			:: " + productCode);
			logger.info("[onSave] productName 			:: " + productName);
			logger.info("[onSave] productTypeCode 		:: " + productTypeCode);
			logger.info("[onSave] productGroupCode 		:: " + productGroupCode);
			logger.info("[onSave] unitCode 				:: " + unitCode);
			logger.info("[onSave] quantity 				:: " + quantity);
			logger.info("[onSave] minQuan 				:: " + minQuan);
			logger.info("[onSave] costPrice 			:: " + costPrice);
			logger.info("[onSave] salePrice1 			:: " + salePrice1);
			logger.info("[onSave] salePrice2 			:: " + salePrice2);
			logger.info("[onSave] salePrice3 			:: " + salePrice3);
			logger.info("[onSave] salePrice4 			:: " + salePrice4);
			logger.info("[onSave] salePrice5 			:: " + salePrice5);
//			logger.info("[onSave] startDate 			:: " + startDate);
//			logger.info("[onSave] expDate 				:: " + expDate);
//			logger.info("[onSave] productStatus 		:: " + productStatus);
			
			productmasterBean.setProductCode(productCode);
			productmasterBean.setProductName(productName);
			productmasterBean.setProductTypeCode(productTypeCode);
			productmasterBean.setProductGroupCode(productGroupCode);
			productmasterBean.setUnitCode(unitCode);
			productmasterBean.setQuantity(quantity);
			productmasterBean.setMinQuan(minQuan);
			productmasterBean.setCostPrice(costPrice);
			productmasterBean.setSalePrice1(salePrice1);
			productmasterBean.setSalePrice2(salePrice2);
			productmasterBean.setSalePrice3(salePrice3);
			productmasterBean.setSalePrice4(salePrice4);
			productmasterBean.setSalePrice5(salePrice5);
//			productDetailsBean.setStartDate(EnjoyUtils.dateFormat(startDate, "dd/MM/yyyy", "yyyyMMdd"));
//			productDetailsBean.setExpDate(EnjoyUtils.dateFormat(expDate, "dd/MM/yyyy", "yyyyMMdd"));
//			productDetailsBean.setProductStatus(productStatus);
			
			session.beginTransaction();
			
			if(pageMode.equals(ProductDetailsMaintananceForm.NEW)){
				this.dao.insertProductmaster(session, productmasterBean);
			}else{
				this.dao.updateProductmaster(session, productmasterBean);
			}
			
			this.dao.deleteProductdetail(session, productCode);
			
			for(int i=0;i<productdetailList.size();i++){
				bean = productdetailList.get(i);
				if(!bean.getRowStatus().equals(ProductDetailsMaintananceForm.DEL)){
					bean.setProductCode(productCode);
					bean.setSeqDb(String.valueOf(seqDb));
					this.dao.insertProductdetail(session, bean);
					seqDb++;
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
	
	private void getProductTypeNameList(){
	   logger.info("[getProductTypeNameList][Begin]");
	   
	   String							productTypeName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   jSONArray 				= new JSONArray();
		   
		   logger.info("[getProductTypeNameList] productTypeName 			:: " + productTypeName);
		   
		   
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
		   logger.info("[getProductTypeNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductTypeNameList][End]");
	   }
   }
	   
   private void getProductGroupNameList(){
	   logger.info("[getProductGroupNameList][Begin]");
	   
	   String							productTypeName			= null;
	   String							productGroupName		= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   jSONArray 				= new JSONArray();
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName			= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   
		   
		   logger.info("[getProductGroupNameList] productTypeName 			:: " + productTypeName);
		   logger.info("[getProductGroupNameList] productGroupName 			:: " + productGroupName);
		   
		   list 		= this.productGroupDao.productGroupNameList(productTypeName, productGroupName, false);
		   
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
	   
	   String							unitName			= null;
	   List<ComboBean> 					list 				= null;
       JSONArray 						jSONArray 			= null;
       JSONObject 						objDetail 			= null;
      
	   try{
		   unitName			= EnjoyUtils.nullToStr(this.request.getParameter("unitName"));
		   jSONArray 				= new JSONArray();
		   
		   logger.info("[getUnitNameList] unitName 			:: " + unitName);
		   
		   
		   list 		= this.unitTypeDao.unitNameList(unitName);
		   
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
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			quanDiscount 			= EnjoyUtil.nullToStr(request.getParameter("quanDiscount"));
			discountRate 			= EnjoyUtil.nullToStr(request.getParameter("discountRate"));
			startDate 				= EnjoyUtils.nullToStr(request.getParameter("startDate"));
			expDate 				= EnjoyUtils.nullToStr(request.getParameter("expDate"));
			productdetailList		= this.form.getProductdetailList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] quanDiscount 	:: " + quanDiscount);
			logger.info("[updateRecord] discountRate 	:: " + discountRate);
			logger.info("[updateRecord] startDate 		:: " + startDate);
			logger.info("[updateRecord] expDate 		:: " + expDate);
			
			for(ProductdetailBean bean:productdetailList){
				if(bean.getSeq().equals(seq)){
					
					bean.setQuanDiscount	(quanDiscount);
					bean.setDiscountRate	(discountRate);
					bean.setStartDate		(startDate);
					bean.setExpDate			(expDate);
					
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
	
	
}
