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
import th.go.stock.app.enjoy.bean.SummarySaleByDayReportBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.SummarySaleByDayReportDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.SummarySaleByDayReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class SummarySaleByDayReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SummarySaleByDayReportServlet.class);
	
    private static final String FORM_NAME = "summarySaleByDayReportForm";
    
    private EnjoyUtil               			enjoyUtil                   = null;
    private HttpServletRequest          		request                     = null;
    private HttpServletResponse         		response                    = null;
    private HttpSession                 		session                     = null;
    private UserDetailsBean             		userBean                    = null;
    private SummarySaleByDayReportDao			dao							= null;
    private CompanyDetailsDao					companyDetailsDao			= null;
    private RelationUserAndCompanyDao			relationUserAndCompanyDao	= null;
    private SummarySaleByDayReportForm			form						= null;
    
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
             this.form               		= (SummarySaleByDayReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new SummarySaleByDayReportDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new SummarySaleByDayReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/SummarySaleByDayReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
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
			this.form.setTitlePage("รายงานสรุปยอดขายประจำวัน");
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
	    SummarySaleByDayReportBean 					bean							= new SummarySaleByDayReportBean();
	    List<SummarySaleByDayReportBean> 			resultList 						= null;
	    String										tin								= "";
	    String										invoiceDateFrom					= "";
	    String										invoiceDateTo					= "";
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
			viewPdfMainForm	= new ViewPdfMainForm();
			
			jsonObject.put("tin"				,tin);
			jsonObject.put("invoiceDateFrom"	,invoiceDateFrom);
			jsonObject.put("invoiceDateTo"		,invoiceDateTo);
			
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
			
			resultList = dao.searchByCriteria(bean);
			
			if(resultList!=null){
				for(SummarySaleByDayReportBean vo:resultList){
					objDetail = new JSONObject();
					
					objDetail.put("invoiceCode"		,vo.getInvoiceCode());
					objDetail.put("cusName"			,vo.getCusName());
					objDetail.put("invoiceDate"		,vo.getInvoiceDate());
					objDetail.put("invoiceTotal"	,vo.getInvoiceTotal());
					objDetail.put("remark"			,vo.getRemark());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
   
			buffer = viewPdfMainForm.writeTicketPDF("SummarySaleByDayPdfForm", jsonObject);
	
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
	
	
}
