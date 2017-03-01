package th.go.stock.web.enjoy.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ProductDetailsSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDetailsSearchServlet.class);
	
    private static final String FORM_NAME = "productDetailsSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private ProductDetailsDao			dao							= null;
    private ManageProductTypeDao		productTypeDao				= null;
    private ManageProductGroupDao		productGroupDao				= null;
    private ProductDetailsSearchForm	form						= null;
    
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
             this.userBean           		= (UserDetailsBean) session.getAttribute("userBean");
             this.form               		= (ProductDetailsSearchForm) session.getAttribute(FORM_NAME);
             this.dao						= new ProductDetailsDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productGroupDao			= new ManageProductGroupDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ProductDetailsSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/ProductDetailsSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}else if(pageAction.equals("setForPrint")){
				this.setForPrint();
			}else if(pageAction.equals("checkForPrint")){
				this.checkForPrint();
			}else if(pageAction.equals("print")){
				this.print();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 		}catch(EnjoyException e){
 			this.form.setErrMsg(e.getMessage());
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
			this.form.setTitlePage("ค้นหารายละเอียดสินค้า");			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		ProductmasterBean 			productmasterBean	= null;
		List<ProductmasterBean> 	dataList 			= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<ProductmasterBean> 	list 				= new ArrayList<ProductmasterBean>();
        List<ProductmasterBean> 	listTemp 			= new ArrayList<ProductmasterBean>();
        HashMap						hashTable			= new HashMap();

		try{
			productmasterBean				= new ProductmasterBean();
			
			productmasterBean.setProductTypeName	(EnjoyUtils.nullToStr(this.request.getParameter("productTypeName")));
			productmasterBean.setProductGroupName	(EnjoyUtils.nullToStr(this.request.getParameter("productGroupName")));
			productmasterBean.setProductName		(EnjoyUtils.nullToStr(this.request.getParameter("productName")));
			productmasterBean.setTin				(this.userBean.getTin());
			
			this.form.setProductmasterBean	(productmasterBean);
			this.form.setRadPrint			(EnjoyUtils.nullToStr(this.request.getParameter("radPrint")));
			
			dataList	 		= this.dao.searchByCriteria(productmasterBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(ProductmasterBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<ProductmasterBean>();
			    		pageNum++;
			    	}
					
					list.add(bean);
					hashTable.put(pageNum, list);
			    	cou++;
			    	totalRs++;	
					
				}
				
				totalPage = hashTable.size();
				
				logger.info("[onSearch] totalPage :: " + totalPage);
				
			    this.form.setTotalPage(totalPage);
			    this.form.setTotalRecord(EnjoyUtils.convertFloatToDisplay(String.valueOf(totalRs) , 0));
			    this.form.setHashTable(hashTable);
			    this.form.setPageNum(1);
				
			    listTemp = (List<ProductmasterBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
			    logger.info("[onSearch] listTemp :: " + listTemp.size());
			    
			    this.form.setDataList(listTemp);
				
				
			}else{
				this.form.setTotalPage(1);
			    this.form.setTotalRecord(EnjoyUtils.convertFloatToDisplay("0" , 0));
			    this.form.setHashTable(hashTable);
			    this.form.setPageNum(1);
				this.form.setDataList(dataList);
				throw new EnjoyException("ไม่พบรายการตามเงื่อนไขที่ระบุ");
			}			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onSearch is error");
		}finally{
			logger.info("[onSearch][End]");
		}
		
	}
	
	
	private void lp_getPage(){
	   logger.info("[lp_getPage][Begin]");
	   
	   int								pageNum				= 1;
	   List<ProductmasterBean> 			dataList 			= new ArrayList<ProductmasterBean>();
	   
	   try{
		   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
		   
		   this.form.setPageNum(pageNum);
		   
		   dataList = (List<ProductmasterBean>) this.form.getHashTable().get(pageNum);
		   this.form.setDataList(dataList);
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
		   logger.info("[lp_getPage][End]");
	   }
	   
   }
	
	private void getProductTypeNameList(){
	   logger.info("[getProductTypeNameList][Begin]");
	   
	   String			productTypeName			= null;
	   List<ComboBean> 	list 					= null;
       JSONArray 		jSONArray 				= null;
       JSONObject 		objDetail 				= null;
       String			tin 					= null;
       
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
	   List<ComboBean> 	list 					= null;
	   JSONArray 		jSONArray 				= null;
	   JSONObject 		objDetail 				= null;
	   String			tin 					= null;
    
	   try{
		   jSONArray 			= new JSONArray();
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName		= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   tin					= this.userBean.getTin();
		   
		   
		   logger.info("[getProductGroupNameList] productTypeName 	:: " + productTypeName);
		   logger.info("[getProductGroupNameList] productGroupName 	:: " + productGroupName);
		   logger.info("[getProductGroupNameList] tin 				:: " + tin);
		   
		   list 		= this.productGroupDao.productGroupNameList(productTypeName, productGroupName, tin, true);
		   
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
	   
	private void getProductNameList(){
	   logger.info("[getProductNameList][Begin]");
	   
	   String			productName				= null;
	   String			productTypeName			= null;
	   String			productGroupName		= null;
	   List<ComboBean> 	list 					= null;
	   JSONArray 		jSONArray 				= null;
	   JSONObject 		objDetail 				= null;
	   String			tin 					= null;
 
	   try{
		   jSONArray 				= new JSONArray();
		   productName				= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName			= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   tin						= this.userBean.getTin();
		   
		   logger.info("[getProductNameList] productName 		:: " + productName);
		   logger.info("[getProductNameList] productTypeName 	:: " + productTypeName);
		   logger.info("[getProductNameList] productGroupName 	:: " + productGroupName);
		   
		   list 		= this.dao.productNameList(productName, productTypeName, productGroupName, tin, true);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductNameList][End]");
	   }
	}
	
	private void setForPrint(){
		logger.info("[setForPrint][Begin]");
		
		JSONObject 						obj 				= null;
		String 							chkBoxSeq			= null;
		String 							chkBox				= null;
		int								pageNum				= 1;
		List<ProductmasterBean> 		dataList 			= new ArrayList<ProductmasterBean>();
		
		try{
			
			obj 					= new JSONObject();
			chkBoxSeq 				= EnjoyUtil.nullToStr(request.getParameter("chkBoxSeq"));
			pageNum 				= EnjoyUtil.parseInt(request.getParameter("pageNum"));
			chkBox 					= EnjoyUtil.chkBoxtoDb(request.getParameter("chkBox"));
			
			logger.info("[setForPrint] chkBoxSeq 	:: " + chkBoxSeq);
			logger.info("[setForPrint] pageNum 		:: " + pageNum);
			logger.info("[setForPrint] chkBox 		:: " + chkBox);
			
			dataList 				= (List<ProductmasterBean>) this.form.getHashTable().get(pageNum);
			
			for(ProductmasterBean bean:dataList){
				if(bean.getChkBoxSeq().equals(chkBoxSeq)){
					bean.setChkBox(chkBox);
					break;
				}
			}
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"setForPrint is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[setForPrint][End]");
		}
	}
			   
	private void checkForPrint(){
	   logger.info("[checkForPrint][Begin]");
	   
	   JSONObject 					obj 				= null;
	   List<ProductmasterBean> 		dataList			= null;
	   HashMap						hashTable			= null;
	   int							checkRecord			= 0;
 
	   try{
		   obj 				= new JSONObject();
		   hashTable		= this.form.getHashTable();
		   
		   obj.put(STATUS, 			SUCCESS);
		   
		   for (Object key : hashTable.keySet()) {
			   dataList = (List<ProductmasterBean>) hashTable.get(key);
			   
			   for(ProductmasterBean bean:dataList){
				   if(bean.getChkBox().equals("Y")){
					   checkRecord++;
					   break;
				   }
			   }
		   }
		   
		   obj.put("checkRecord" , checkRecord);
		   
	   }catch(Exception e){
		   obj.put(STATUS	, ERROR);
		   obj.put(ERR_MSG	, "checkForPrint is error");
		   e.printStackTrace();
		   logger.info("[checkForPrint] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[checkForPrint][End]");
	   }
	}
	
	private void print(){
		   logger.info("[print][Begin]");
		   
		   JSONObject 					obj 				= null;
		   JSONArray 					detailJSONArray 	= null;
		   JSONObject 					objDetail 			= null;
		   List<ProductmasterBean> 		dataList			= null;
		   HashMap						hashTable			= null;
		   DataOutput 					output 				= null;
		   ByteArrayOutputStream		buffer				= null;
		   byte[] 						bytes				= null;
		   ViewPdfMainForm				viewPdfMainForm		= null;
	 
		   try{
			   obj 				= new JSONObject();
			   detailJSONArray 	= new JSONArray();
			   hashTable		= this.form.getHashTable();
			   viewPdfMainForm	= new ViewPdfMainForm();
			   
			   obj.put(STATUS, 			SUCCESS);
			   
			   for (Object key : hashTable.keySet()) {
				   dataList = (List<ProductmasterBean>) hashTable.get(key);
				   
				   for(ProductmasterBean bean:dataList){
					   if(bean.getChkBox().equals("Y")){
						   objDetail 		= new JSONObject();
						   objDetail.put("productCodeDis"		,bean.getProductCodeDis());
						   objDetail.put("productName"			,bean.getProductName());
						   
						   detailJSONArray.add(objDetail);
					   }
				   }
			   }
			   
			   obj.put("printType"	, EnjoyUtils.nullToStr(this.request.getParameter("printType")));
			   obj.put("detailList" , detailJSONArray);
			   
			   logger.info("[print] obj.toString() :: " + obj.toString());
			   String pdfName = "ProductBarcodePdfForm";
			   buffer = viewPdfMainForm.writeTicketPDFForBarCode(pdfName, obj, "รหัสสินค้า");
				
			   response.setContentType( "application/pdf" );
			   response.setHeader("Content-Disposition", "filename=".concat(String.valueOf(pdfName+".pdf")));
			   output 	= new DataOutputStream( this.response.getOutputStream() );
			   bytes 	= buffer.toByteArray();
				
			   this.response.setContentLength(bytes.length);
			   for( int i = 0; i < bytes.length; i++ ) { 
				   output.writeByte( bytes[i] ); 
			   }
			   
		   }catch(Exception e){
			   e.printStackTrace();
			   logger.info("[print] " + e.getMessage());
		   }finally{
			   logger.info("[print][End]");
		   }
		}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.productTypeDao.destroySession();
        this.productGroupDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.productTypeDao.commit();
        this.productGroupDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.productTypeDao.rollback();
        this.productGroupDao.rollback();
	}

	
	
	
}
