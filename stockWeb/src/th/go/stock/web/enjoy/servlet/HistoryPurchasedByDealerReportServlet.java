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
import th.go.stock.app.enjoy.bean.HistoryPurchasedByDealerReportBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.dao.HistoryPurchasedByDealerReportDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.HistoryPurchasedByDealerReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class HistoryPurchasedByDealerReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(HistoryPurchasedByDealerReportServlet.class);
	
    private static final String FORM_NAME = "historyPurchasedByDealerReportForm";
    
    private EnjoyUtil               			enjoyUtil                   = null;
    private HttpServletRequest          		request                     = null;
    private HttpServletResponse         		response                    = null;
    private HttpSession                 		session                     = null;
    private UserDetailsBean             		userBean                    = null;
    private HistoryPurchasedByDealerReportDao	dao							= null;
    private CompanyDetailsDao					companyDetailsDao			= null;
    private CompanyVendorDao					companyVendorDao			= null;
    private HistoryPurchasedByDealerReportForm	form						= null;
    
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
             this.form               		= (HistoryPurchasedByDealerReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new HistoryPurchasedByDealerReportDao();
             this.companyVendorDao			= new CompanyVendorDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new HistoryPurchasedByDealerReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/HistoryPurchasedByDealerReport.jsp");
 			}else if(pageAction.equals("showData")){
 				this.showData();
 			}else if(pageAction.equals("getVendorNameList")){
				this.getVendorNameList();
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
			this.form.setTitlePage("รายงานประวัติการซื้อตามผู้จำหน่าย");
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void showData() throws EnjoyException{
		logger.info("[showData][Begin]");
		
		JSONObject 									jsonObject 						= new JSONObject();
		JSONArray 									jSONArray 						= new JSONArray();
	    JSONObject 									objDetail 						= null;
	    HistoryPurchasedByDealerReportBean 			bean							= new HistoryPurchasedByDealerReportBean();
	    List<HistoryPurchasedByDealerReportBean> 	historyPurchasedByDealerList 	= null;
	    String										tin								= "";
	    String										reciveDateFrom					= "";
	    String										reciveDateTo					= "";
	    String										vendorName						= "";
	    CompanyDetailsBean 							companyDetailsBean				= new CompanyDetailsBean();
	    CompanyDetailsBean 							companyDetailsDb				= null;
		ViewPdfMainForm								viewPdfMainForm					= null;
		DataOutput 									output 							= null;
		ByteArrayOutputStream						buffer							= null;
		byte[] 										bytes							= null;

		try{
			tin 			= this.userBean.getTin();
			reciveDateFrom 	= EnjoyUtils.nullToStr(this.request.getParameter("reciveDateFrom"));
			reciveDateTo 	= EnjoyUtils.nullToStr(this.request.getParameter("reciveDateTo"));
			vendorName 		= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
			viewPdfMainForm	= new ViewPdfMainForm();
			
			jsonObject.put("tin"			,tin);
			jsonObject.put("reciveDateFrom"	,reciveDateFrom);
			jsonObject.put("reciveDateTo"	,reciveDateTo);
			jsonObject.put("vendorName"	,reciveDateTo);
			
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
			bean.setReciveDateFrom(reciveDateFrom);
			bean.setReciveDateTo(reciveDateTo);
			bean.setVendorName(vendorName);
			
			historyPurchasedByDealerList = dao.searchByCriteria(bean);
			
			if(historyPurchasedByDealerList!=null){
				for(HistoryPurchasedByDealerReportBean vo:historyPurchasedByDealerList){
					objDetail = new JSONObject();
					
					objDetail.put("vendorName"		,vo.getVendorName());
					objDetail.put("reciveNo"		,vo.getReciveNo());
					objDetail.put("reciveDate"		,vo.getReciveDate());
					objDetail.put("reciveTotal"	,vo.getReciveTotal());
					objDetail.put("reciveDiscount"	,vo.getReciveDiscount());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("historyPurchasedByDealerReportList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
			String pdfName = "HistoryPurchasedByDealerPdfForm";
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
	
	private void getVendorNameList(){
		logger.info("[getVendorNameList][Begin]");
	   
	   String			vendorName			= null;
	   List<ComboBean> 	list 				= null;
	   JSONArray 		jSONArray 			= null;
	   JSONObject 		objDetail 			= null;
	   String			tin 				= null;
	
	   try{
		   jSONArray 		= new JSONArray();
		   vendorName		= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
		   tin				= this.userBean.getTin();
		   
		   logger.info("[getVendorNameList] vendorName 				:: " + vendorName);
		   
		   list 		= this.companyVendorDao.vendorNameList(vendorName, tin);
		   
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
		   logger.info("[getVendorNameList][End]");
	   }
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.companyVendorDao.destroySession();
        this.companyDetailsDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.companyVendorDao.commit();
        this.companyDetailsDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.companyVendorDao.rollback();
        this.companyDetailsDao.rollback();
	}

	
	
	
	
	
}
