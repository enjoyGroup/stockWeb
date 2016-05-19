package th.go.stock.web.enjoy.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ProductQuanHistoryDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.StockMovingReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class StockMovingReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(StockMovingReportServlet.class);
	
    private static final String FORM_NAME = "stockMovingReportForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private ProductQuanHistoryDao		dao							= null;
    private CompanyDetailsDao			companyDetailsDao			= null;
    private RelationUserAndCompanyDao	relationUserAndCompanyDao	= null;
    private ManageProductTypeDao		productTypeDao				= null;
    private ManageProductGroupDao		productGroupDao				= null;
    private ProductDetailsDao			productDetailsDao			= null;
    private StockMovingReportForm		form						= null;
    
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
             this.form               		= (StockMovingReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new ProductQuanHistoryDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productGroupDao			= new ManageProductGroupDao();
             this.productDetailsDao			= new ProductDetailsDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new StockMovingReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/StockMovingReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
 			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 		}catch(EnjoyException e){
 			this.form.setErrMsg(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			dao.destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{		
			this.form.setTitlePage("รายงานเคลื่อนไหว Stock สินค้า");
			this.setRefference();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setCompanyCombo() throws EnjoyException{
		
		logger.info("[setCompanyCombo][Begin]");
		
		List<ComboBean>			companyCombo 		= null;
		
		try{
			
			companyCombo = this.relationUserAndCompanyDao.getCompanyList(this.userBean.getUserUniqueId());
			
			if(companyCombo.size() > 1){
				companyCombo.add(0, new ComboBean("", "กรุณาระบุ"));
			}
			
			this.form.setCompanyCombo(companyCombo);
		}
		catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setCompanyCombo is error");
		}finally{
			logger.info("[setCompanyCombo][End]");
		}
	}

	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		try{
			this.setCompanyCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void showData() throws EnjoyException{
		logger.info("[showData][Begin]");
		
		JSONObject 							jsonObject 					= new JSONObject();
		JSONArray 							jSONArray 					= new JSONArray();
	    JSONObject 							objDetail 					= null;
	    ProductQuanHistoryBean 				bean						= new ProductQuanHistoryBean();
	    List<ProductQuanHistoryBean> 		resultList 					= null;
	    String								tin							= "";
	    String								hisDateFrom					= "";
	    String								hisDateTo					= "";
	    String								productTypeName				= "";
	    String								productGroupName			= "";
	    String								productName					= "";
	    CompanyDetailsBean 					companyDetailsBean			= new CompanyDetailsBean();
	    CompanyDetailsBean 					companyDetailsDb			= null;
		ViewPdfMainForm						viewPdfMainForm				= null;
		DataOutput 							output 						= null;
		ByteArrayOutputStream				buffer						= null;
		byte[] 								bytes						= null;

		try{
			tin 				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
			hisDateFrom 		= EnjoyUtils.nullToStr(this.request.getParameter("hisDateFrom"));
			hisDateTo 			= EnjoyUtils.nullToStr(this.request.getParameter("hisDateTo"));
			productTypeName 	= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
			productGroupName 	= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
			productName 		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
			viewPdfMainForm		= new ViewPdfMainForm();
			
			jsonObject.put("tin"				,tin);
			jsonObject.put("hisDateFrom"		,hisDateFrom);
			jsonObject.put("hisDateTo"			,hisDateTo);
			jsonObject.put("productTypeName"	,productTypeName);
			jsonObject.put("productGroupName"	,productGroupName);
			jsonObject.put("productName"		,productName);
			
			/*Begin รายละเอียดบริษัท*/
			if(tin!=null && !"".equals(tin)){
				companyDetailsBean.setTin(tin);
				companyDetailsDb = companyDetailsDao.getCompanyDetail(companyDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("tin"			, companyDetailsDb.getTin());
				objDetail.put("companyName"	, companyDetailsDb.getCompanyName());
				objDetail.put("address"		, companyDetailsDb.getAddress());
				objDetail.put("tel"			, companyDetailsDb.getTel());
				objDetail.put("fax"			, companyDetailsDb.getFax());
				objDetail.put("email"		, companyDetailsDb.getEmail());
				objDetail.put("remark"		, companyDetailsDb.getRemark());
				
				jsonObject.put("companyDetails"	,objDetail);
			}
			/*ENd รายละเอียดบริษัท*/
			
			/*Begin รายละเอียดรายงาน*/
			bean.setTin(tin);
			bean.setHisDateFrom(hisDateFrom);
			bean.setHisDateTo(hisDateTo);
			bean.setProductTypeName(productTypeName);
			bean.setProductGroupName(productGroupName);
			bean.setProductName(productName);
			
			resultList = dao.searchByCriteria(bean);
			
			if(resultList!=null){
				for(ProductQuanHistoryBean vo:resultList){
					objDetail = new JSONObject();
					
					objDetail.put("formRef"			,vo.getFormRef());
					objDetail.put("hisDate"			,vo.getHisDate());
					objDetail.put("productName"		,vo.getProductName());
					objDetail.put("quantityPlus"	,vo.getQuantityPlus());
					objDetail.put("quantityMinus"	,vo.getQuantityMinus());
					objDetail.put("quantityTotal"	,vo.getQuantityTotal());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
   
			buffer = viewPdfMainForm.writeTicketPDF("StockMovingPdfForm", jsonObject);
	
			response.setContentType( "application/pdf" );
			output 	= new DataOutputStream( this.response.getOutputStream() );
			bytes 	= buffer.toByteArray();
	
			this.response.setContentLength(bytes.length);
			for( int i = 0; i < bytes.length; i++ ) { 
				output.writeByte( bytes[i] ); 
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.error(e);
			throw new EnjoyException("showData is error");
		}finally{
			logger.info("[showData][End]");
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
		   
		   list 		= this.productGroupDao.productGroupNameList(productTypeName, productGroupName, true);
		   
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
	   
	   String							productName				= null;
	   String							productTypeName			= null;
	   String							productGroupName		= null;
	   List<ComboBean> 					list 					= null;
	   JSONArray 						jSONArray 				= null;
	   JSONObject 						objDetail 				= null;
 
	   try{
		   jSONArray 				= new JSONArray();
		   productName				= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName			= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   
		   logger.info("[getProductNameList] productName 				:: " + productName);
		   logger.info("[getProductNameList] productTypeName 			:: " + productTypeName);
		   logger.info("[getProductNameList] productGroupName 			:: " + productGroupName);
		   
		   list 		= this.productDetailsDao.productNameList(productName, productTypeName, productGroupName, true);
		   
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
	
	
	
	
	
	
}
