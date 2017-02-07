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

import th.go.stock.app.enjoy.bean.AlertLowProductReportBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AlertLowProductReportDao;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.AlertLowProductReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class AlertLowProductReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(AlertLowProductReportServlet.class);
	
    private static final String FORM_NAME = "alertLowProductReportForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private AlertLowProductReportDao	dao							= null;
    private CompanyDetailsDao			companyDetailsDao			= null;
    private ManageProductTypeDao		productTypeDao				= null;
    private ManageProductGroupDao		productGroupDao				= null;
    private AlertLowProductReportForm	form						= null;
    
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
             this.form               		= (AlertLowProductReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new AlertLowProductReportDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productGroupDao			= new ManageProductGroupDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new AlertLowProductReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/AlertLowProductReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
 			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
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
			this.form.setTitlePage("รายงานแจ้งเตือน Stock สินค้าใกล้หมด");
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void showData() throws EnjoyException{
		logger.info("[showData][Begin]");
		
		JSONObject 							jsonObject 						= new JSONObject();
		JSONArray 							jSONArray 						= new JSONArray();
	    JSONObject 							objDetail 						= null;
	    AlertLowProductReportBean 			bean							= new AlertLowProductReportBean();
	    List<AlertLowProductReportBean> 	resultList 						= null;
	    String								tin								= "";
	    String								productTypeName					= "";
	    String								productGroupName				= "";
	    CompanyDetailsBean 					companyDetailsBean				= new CompanyDetailsBean();
	    CompanyDetailsBean 					companyDetailsDb				= null;
		ViewPdfMainForm						viewPdfMainForm					= null;
		DataOutput 							output 							= null;
		ByteArrayOutputStream				buffer							= null;
		byte[] 								bytes							= null;

		try{
			tin 			= this.userBean.getTin();
			productTypeName = EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
			productGroupName= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
			viewPdfMainForm	= new ViewPdfMainForm();
			
			jsonObject.put("tin"				,tin);
			jsonObject.put("productTypeName"	,productTypeName);
			jsonObject.put("productGroupName"	,productGroupName);
			
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
			bean.setProductTypeName(productTypeName);
			bean.setProductGroupName(productGroupName);
			
			resultList = dao.searchByCriteria(bean);
			
			if(resultList!=null){
				for(AlertLowProductReportBean vo:resultList){
					objDetail = new JSONObject();
					
					objDetail.put("productTypeName"	,vo.getProductTypeName());
					objDetail.put("productGroupName",vo.getProductGroupName());
					objDetail.put("productName"		,vo.getProductName());
					objDetail.put("minQuan"			,vo.getMinQuan());
					objDetail.put("quantity"		,vo.getQuantity());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
			String pdfName = "AlertLowProductPdfForm";
			buffer = viewPdfMainForm.writeTicketPDF(pdfName, jsonObject, this.form.getTitlePage());
	
			response.setContentType( "application/pdf" );
			response.setHeader("Content-Disposition", "filename=".concat(String.valueOf(pdfName+".pdf")));
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
	   
	   String				productTypeName			= null;
	   List<ComboBean> 		list 					= null;
       JSONArray 			jSONArray 				= null;
       JSONObject 			objDetail 				= null;
       String				tin 					= null;
       
	   try{
		   productTypeName		= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   tin					= this.userBean.getTin();
		   jSONArray 			= new JSONArray();
		   
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
	   
	   String				productTypeName			= null;
	   String				productGroupName		= null;
	   String				tin 					= null;
	   List<ComboBean> 		list 					= null;
       JSONArray 			jSONArray 				= null;
       JSONObject 			objDetail 				= null;
       
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

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.productTypeDao.destroySession();
        this.productGroupDao.destroySession();
        this.companyDetailsDao.destroySession();
	}
	
	@Override
	public void commit() {
		this.dao.commit();
        this.productTypeDao.commit();
        this.productGroupDao.commit();
        this.companyDetailsDao.commit();
	}
	
	@Override
	public void rollback() {
		this.dao.rollback();
        this.productTypeDao.rollback();
        this.productGroupDao.rollback();
        this.companyDetailsDao.rollback();
	}
	
	
	
	
	
}
