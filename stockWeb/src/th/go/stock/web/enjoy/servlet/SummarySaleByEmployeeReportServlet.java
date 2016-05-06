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
import th.go.stock.app.enjoy.bean.SummarySaleByEmployeeReportBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.SummarySaleByEmployeeReportDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.SummarySaleByEmployeeReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class SummarySaleByEmployeeReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SummarySaleByEmployeeReportServlet.class);
	
    private static final String FORM_NAME = "summarySaleByEmployeeReportForm";
    
    private EnjoyUtil               			enjoyUtil                   = null;
    private HttpServletRequest          		request                     = null;
    private HttpServletResponse         		response                    = null;
    private HttpSession                 		session                     = null;
    private UserDetailsBean             		userBean                    = null;
    private SummarySaleByEmployeeReportDao		dao							= null;
    private CompanyDetailsDao					companyDetailsDao			= null;
    private RelationUserAndCompanyDao			relationUserAndCompanyDao	= null;
    private UserDetailsDao						userDetailsDao				= null;
    private SummarySaleByEmployeeReportForm		form						= null;
    
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
             this.form               		= (SummarySaleByEmployeeReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new SummarySaleByEmployeeReportDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.userDetailsDao			= new UserDetailsDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SummarySaleByEmployeeReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/SummarySaleByEmployeeReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
 			}else if(pageAction.equals("getSaleName")){
				this.getSaleName();
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
			this.form.setTitlePage("รายงานยอดขายของพนักงาน");
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
		
		JSONObject 									jsonObject 						= new JSONObject();
		JSONArray 									jSONArray 						= new JSONArray();
	    JSONObject 									objDetail 						= null;
	    SummarySaleByEmployeeReportBean 			bean							= new SummarySaleByEmployeeReportBean();
	    List<SummarySaleByEmployeeReportBean> 		resultList 						= null;
	    String										tin								= "";
	    String										invoiceDateFrom					= "";
	    String										invoiceDateTo					= "";
	    String										saleName						= "";
	    CompanyDetailsBean 							companyDetailsBean				= new CompanyDetailsBean();
	    CompanyDetailsBean 							companyDetailsDb				= null;
		ViewPdfMainForm								viewPdfMainForm					= null;
		DataOutput 									output 							= null;
		ByteArrayOutputStream						buffer							= null;
		byte[] 										bytes							= null;

		try{
			tin 			= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
			invoiceDateFrom = EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateFrom"));
			invoiceDateTo 	= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateTo"));
			saleName	 	= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
			viewPdfMainForm	= new ViewPdfMainForm();
			
			jsonObject.put("tin"			,tin);
			jsonObject.put("invoiceDateFrom",invoiceDateFrom);
			jsonObject.put("invoiceDateTo"	,invoiceDateTo);
			jsonObject.put("saleName"		,saleName);
			
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
			bean.setInvoiceDateFrom(invoiceDateFrom);
			bean.setInvoiceDateTo(invoiceDateTo);
			bean.setSaleName(saleName);
			
			resultList = dao.searchByCriteria(bean);
			
			if(resultList!=null){
				for(SummarySaleByEmployeeReportBean vo:resultList){
					objDetail = new JSONObject();
					
					objDetail.put("invoiceCode"		,vo.getInvoiceCode());
					objDetail.put("invoiceDate"		,vo.getInvoiceDate());
					objDetail.put("cusName"			,vo.getCusName());
					objDetail.put("invoiceTotal"	,vo.getInvoiceTotal());
					objDetail.put("saleCommission"	,vo.getSaleCommission());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
   
			buffer = viewPdfMainForm.writeTicketPDF("SummarySaleByEmployeePdfForm", jsonObject);
	
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
	
	private void getSaleName(){
	   logger.info("[getSaleName][Begin]");
	   
	   String							saleName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   saleName			= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
		   jSONArray 		= new JSONArray();
		   
		   logger.info("[getSaleName] saleName 			:: " + saleName);
		   
		   list 		= this.userDetailsDao.userFullNameList(saleName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getSaleName] " + e.getMessage());
	   }finally{
		   logger.info("[getSaleName][End]");
	   }
	}	

	
	
	
	
	
}
