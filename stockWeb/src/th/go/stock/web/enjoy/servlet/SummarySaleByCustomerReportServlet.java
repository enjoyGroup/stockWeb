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
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.SummarySaleByCustomerReportBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.SummarySaleByCustomerReportDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.SummarySaleByCustomerReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class SummarySaleByCustomerReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SummarySaleByCustomerReportServlet.class);
	
    private static final String FORM_NAME = "summarySaleByCustomerReportForm";
    
    private EnjoyUtil               			enjoyUtil                   = null;
    private HttpServletRequest          		request                     = null;
    private HttpServletResponse         		response                    = null;
    private HttpSession                 		session                     = null;
    private UserDetailsBean             		userBean                    = null;
    private SummarySaleByCustomerReportDao		dao							= null;
    private CustomerDetailsDao					customerDetailsDao			= null;
    private ProductDetailsDao					productDetailsDao			= null;
    private SummarySaleByCustomerReportForm		form						= null;
    
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
             this.form               		= (SummarySaleByCustomerReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new SummarySaleByCustomerReportDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
             this.productDetailsDao			= new ProductDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SummarySaleByCustomerReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/SummarySaleByCustomerReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
 			}
// 			else if(pageAction.equals("getCusFullName")){
//				this.getCusFullName();
//			}
 			else if(pageAction.equals("getProductNameList")){
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
			this.form.setTitlePage("รายงานประวัติการซื้อของลูกค้า");
//			this.setRefference();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
//	private void setRefference() throws EnjoyException{
//		
//		logger.info("[setRefference][Begin]");
//		
//		try{
//			this.setCompanyCombo();
//		}catch(EnjoyException e){
//			throw new EnjoyException(e.getMessage());
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.info(e.getMessage());
//		}finally{
//			logger.info("[setRefference][End]");
//		}
//	}
	
	private void showData() throws EnjoyException{
		logger.info("[showData][Begin]");
		
		JSONObject 									jsonObject 						= new JSONObject();
		JSONArray 									jSONArray 						= new JSONArray();
	    JSONObject 									objDetail 						= null;
	    SummarySaleByCustomerReportBean 			bean							= new SummarySaleByCustomerReportBean();
	    List<SummarySaleByCustomerReportBean> 		resultList 						= null;
	    String										cusCode							= "";
	    String										invoiceDateFrom					= "";
	    String										invoiceDateTo					= "";
	    String										productName						= "";
	    CustomerDetailsBean 						customerDetailsBean				= new CustomerDetailsBean();
	    CustomerDetailsBean 						customerDetailsDb				= null;
		ViewPdfMainForm								viewPdfMainForm					= null;
		DataOutput 									output 							= null;
		ByteArrayOutputStream						buffer							= null;
		byte[] 										bytes							= null;

		try{
			cusCode 		= EnjoyUtils.nullToStr(this.request.getParameter("cusCode"));
			invoiceDateFrom = EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateFrom"));
			invoiceDateTo 	= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateTo"));
			productName	 	= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
			viewPdfMainForm	= new ViewPdfMainForm();
			
			jsonObject.put("cusCode"		,cusCode);
			jsonObject.put("invoiceDateFrom",invoiceDateFrom);
			jsonObject.put("invoiceDateTo"	,invoiceDateTo);
			jsonObject.put("productName"	,productName);
			
			/*Begin รายละเอียดรายงาน*/
			bean.setCusCode(cusCode);
			bean.setInvoiceDateFrom(invoiceDateFrom);
			bean.setInvoiceDateTo(invoiceDateTo);
			bean.setProductName(productName);
			
			resultList = dao.searchByCriteria(bean);
			
			if(resultList!=null){
				for(SummarySaleByCustomerReportBean vo:resultList){
					objDetail 	= new JSONObject();
					
					objDetail.put("productName"	,vo.getProductName());
					objDetail.put("invoiceDate"	,vo.getInvoiceDate());
					objDetail.put("quantity"	,vo.getQuantity());
					objDetail.put("price"		,vo.getPrice());
					objDetail.put("discount"	,vo.getDiscount());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			/*Begin รายละเอียดลูกค้า*/
			if(!"".equals(cusCode)){
				customerDetailsBean.setCusCode(cusCode);
				customerDetailsDb = customerDetailsDao.getCustomerDetail(customerDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("cusCode"		, customerDetailsDb.getCusCode());
				objDetail.put("fullName"	, customerDetailsDb.getFullName());
				objDetail.put("address"		, customerDetailsDb.getAddress());
				objDetail.put("tel"			, customerDetailsDb.getTel());
				objDetail.put("fax"			, customerDetailsDb.getFax());
				objDetail.put("email"		, customerDetailsDb.getEmail());
				objDetail.put("remark"		, customerDetailsDb.getRemark());
				
				jsonObject.put("customerDetails"	,objDetail);
			}
			/*ENd รายละเอียดลูกค้า*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
			String pdfName = "SummarySaleByCustomerPdfForm";
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
	
	private void getCusFullName(){
	   logger.info("[getCusFullName][Begin]");
	   
	   String							cusName				= null;
	   List<CustomerDetailsBean> 		list 				= null;
       JSONArray 						jSONArray 			= null;
       JSONObject 						objDetail 			= null;
       CustomerDetailsBean 				customerDetailsBean = new CustomerDetailsBean();
       
	   try{
		   cusName			= EnjoyUtils.nullToStr(this.request.getParameter("cusName"));
		   jSONArray 		= new JSONArray();
		   
		   logger.info("[getCusFullName] cusName 			:: " + cusName);
		   
		   customerDetailsBean.setFullName(cusName);
		   
		   list 		= this.customerDetailsDao.getCusFullName(customerDetailsBean);
		   
		   for(CustomerDetailsBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCusCode());
			   objDetail.put("value"		,bean.getFullName());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getCusFullName] " + e.getMessage());
	   }finally{
		   logger.info("[getCusFullName][End]");
	   }
	}	
	
	private void getProductNameList(){
	   logger.info("[getProductNameList][Begin]");
	   
	   String							productName				= null;
	   List<ComboBean> 					list 					= null;
	   JSONArray 						jSONArray 				= null;
	   JSONObject 						objDetail 				= null;
	
	   try{
		   jSONArray 				= new JSONArray();
		   productName				= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   
		   logger.info("[getProductNameList] productName 				:: " + productName);
		   
		   list 		= this.productDetailsDao.productNameList(productName, null, null, true);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.error(e);
	   }finally{
		   logger.info("[getProductNameList][End]");
	   }
	}

	
	
	
	
	
}
