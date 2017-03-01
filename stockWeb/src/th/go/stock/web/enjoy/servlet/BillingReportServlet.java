package th.go.stock.web.enjoy.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
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
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.InvoiceCreditDao;
import th.go.stock.app.enjoy.dao.SummarySaleByCustomerReportDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.BillingReportForm;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class BillingReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(BillingReportServlet.class);
	
    private static final String FORM_NAME = "billingReportForm";
    
    private EnjoyUtil               			enjoyUtil                   = null;
    private HttpServletRequest          		request                     = null;
    private HttpServletResponse         		response                    = null;
    private HttpSession                 		session                     = null;
    private UserDetailsBean             		userBean                    = null;
    private SummarySaleByCustomerReportDao		dao							= null;
    private CustomerDetailsDao					customerDetailsDao			= null;
    private BillingReportForm					form						= null;
    
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
             this.form               		= (BillingReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new SummarySaleByCustomerReportDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new BillingReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/BillingReport.jsp");
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
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{		
			this.form.setTitlePage("ใบวางบิล");
			
			setInvoiceTypeCombo();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setInvoiceTypeCombo() throws EnjoyException{
		
		logger.info("[setInvoiceTypeCombo][Begin]");
		
		List<ComboBean>			combo 			= null;
		
		try{
			
			combo 				= new ArrayList<ComboBean>();
			
			combo.add(new ComboBean(""	, "ทั้งหมด"));
			combo.add(new ComboBean("V"	, "Vat"));
			combo.add(new ComboBean("N"	, "ไม่มี Vat"));
			
			this.form.setInvoiceTypeCombo(combo);
		}
		catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setInvoiceTypeCombo is error");
		}finally{
			logger.info("[setInvoiceTypeCombo][End]");
		}
	}
	
	private void showData() throws EnjoyException{
		logger.info("[showData][Begin]");
		
		JSONObject 						jsonObject 				= new JSONObject();
		JSONArray 						jSONArray 				= new JSONArray();
	    JSONObject 						objDetail 				= null;
	    CompanyDetailsBean 				companyDetailsBean		= new CompanyDetailsBean();
	    CompanyDetailsBean 				companyDetailsDb		= null;
	    CustomerDetailsBean 			customerDetailsBean		= new CustomerDetailsBean();
	    CustomerDetailsBean 			customerDetailsDb		= null;
	    InvoiceCreditMasterBean 		invoiceCreditMasterBean = new InvoiceCreditMasterBean();
	    InvoiceCreditMasterBean 		invoiceCreditMasterSum 	= null;
	    String							cusCode					= null;
	    String							tin						= null;
	    String							invoiceCode				= null;
	    String							invoiceDateFrom			= null;
	    String							invoiceDateTo			= null;
	    String							invoiceType				= null;
	    String							includeVat				= null;
	    List<InvoiceCreditMasterBean> 	invoiceCreditMasterList = null;
	    InvoiceCreditDao				invoiceCreditDao		= new InvoiceCreditDao();
	    CustomerDetailsDao				customerDetailsDao		= new CustomerDetailsDao();
	    CompanyDetailsDao				companyDetailsDao		= new CompanyDetailsDao();
	    String							vatDis					= null;
	    ViewPdfMainForm					viewPdfMainForm			= null;
		DataOutput 						output 					= null;
		ByteArrayOutputStream			buffer					= null;
		byte[] 							bytes					= null;
		double							sumvat					= 0;
		String							sumvatStr				= "";

		try{
			cusCode 		= EnjoyUtils.nullToStr(this.request.getParameter("cusCode"));
			invoiceCode		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode"));
			invoiceType 	= EnjoyUtils.nullToStr(this.request.getParameter("invoiceType"));
			invoiceDateFrom = EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateFrom"));
			invoiceDateTo 	= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateTo"));
			includeVat	 	= EnjoyUtils.nullToStr(this.request.getParameter("includeVat"));
			tin	 			= this.userBean.getTin();
			vatDis	 		= ConfigFile.getVat() + "%";
			
			viewPdfMainForm	= new ViewPdfMainForm();
			
			/*Begin รายการใบวางบิล*/
			invoiceCreditMasterBean.setCusCode(cusCode);
			invoiceCreditMasterBean.setInvoiceCode(invoiceCode);
			invoiceCreditMasterBean.setInvoiceType(invoiceType);
			invoiceCreditMasterBean.setInvoiceDateForm(invoiceDateFrom);
			invoiceCreditMasterBean.setInvoiceDateTo(invoiceDateTo);
			invoiceCreditMasterBean.setTin(tin);
			invoiceCreditMasterList = invoiceCreditDao.searchForBillingReport(invoiceCreditMasterBean);
			
			for(InvoiceCreditMasterBean vo:invoiceCreditMasterList){
				objDetail = new JSONObject();
				objDetail.put("invoiceCode"		, vo.getInvoiceCode());
				objDetail.put("invoiceDate"		, vo.getInvoiceDate());
				objDetail.put("invoicePrice"	, vo.getInvoicePrice());
				objDetail.put("invoicediscount"	, vo.getInvoicediscount());
				objDetail.put("invoiceDeposit"	, vo.getInvoiceDeposit());
				objDetail.put("invoiceVat"		, vo.getInvoiceVat());
				objDetail.put("invoiceTotal"	, vo.getInvoiceTotal());
				
				jSONArray.add(objDetail);
			}
			jsonObject.put("invoiceCreditMasterList"	,jSONArray);
			/*End รายการใบวางบิล*/
			
			/*Begin รายละเอียดใบวางบิล*/
			objDetail = new JSONObject();
			invoiceCreditMasterSum = invoiceCreditDao.sumTotalForBillingReport(invoiceCreditMasterBean);
			objDetail.put("sumInvoicePrice"		,invoiceCreditMasterSum.getInvoicePrice());
			objDetail.put("sumInvoicediscount"	,invoiceCreditMasterSum.getInvoicediscount());
			objDetail.put("sumInvoiceDeposit"	,invoiceCreditMasterSum.getInvoiceDeposit());
			
			logger.info("[showData] includeVat :: " + includeVat);
			if("Y".equals(includeVat)){
				sumvat = ((EnjoyUtils.parseDouble(ConfigFile.getVat()) + 100) * EnjoyUtils.parseDouble(invoiceCreditMasterSum.getInvoicePrice()))/100;
				logger.info("[showData] sumvat :: " + sumvat);
				sumvatStr = EnjoyUtils.convertFloatToDisplay(String.valueOf(sumvat), 2);
			}else{
				sumvatStr = invoiceCreditMasterSum.getInvoiceVat();
			}
			
			logger.info("[showData] sumvatStr :: " + sumvatStr);
			
			objDetail.put("sumInvoiceVat"		,sumvatStr);
			objDetail.put("sumInvoiceTotal"		,invoiceCreditMasterSum.getInvoiceTotal());
			objDetail.put("totalRecord"			,String.valueOf(invoiceCreditMasterList.size()));
			objDetail.put("bullingDate"			,EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
			objDetail.put("tin"					,tin);
			objDetail.put("vatDis"				,vatDis);
			
			jsonObject.put("billingDetail"	,objDetail);
			/*End รายละเอียดใบวางบิล*/
			
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
			
			/*Begin รายละเอียดลูกค้า*/
			if(cusCode!=null && !"".equals(cusCode)){
				customerDetailsBean.setCusCode(cusCode);
				customerDetailsBean.setTin(tin);
				customerDetailsDb = customerDetailsDao.getCustomerDetail(customerDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("cusCode"		, customerDetailsDb.getCusCode());
				objDetail.put("cusName"		, customerDetailsDb.getCusName());
				objDetail.put("cusSurname"	, customerDetailsDb.getCusSurname());
				objDetail.put("branchName"	, customerDetailsDb.getBranchName());
				objDetail.put("sex"			, customerDetailsDb.getSex());
				objDetail.put("idType"		, customerDetailsDb.getIdType());
				objDetail.put("idNumber"	, customerDetailsDb.getIdNumber());
				objDetail.put("birthDate"	, customerDetailsDb.getBirthDate());
				objDetail.put("religion"	, customerDetailsDb.getReligion());
				objDetail.put("job"			, customerDetailsDb.getJob());
				objDetail.put("address"		, customerDetailsDb.getAddress());
				objDetail.put("tel"			, customerDetailsDb.getTel());
				objDetail.put("fax"			, customerDetailsDb.getFax());
				objDetail.put("email"		, customerDetailsDb.getEmail());
				
				jsonObject.put("customerDetails",	objDetail);
			}
			/*End รายละเอียดลูกค้า*/
			
			this.form.setInvoiceCreditMasterBean(invoiceCreditMasterBean);
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
			String pdfName = "BillingPdfForm";
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
	
	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.customerDetailsDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.customerDetailsDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.customerDetailsDao.rollback();
	}

	
	
	
	
	
}

