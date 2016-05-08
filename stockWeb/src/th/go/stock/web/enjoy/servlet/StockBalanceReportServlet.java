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
import th.go.stock.app.enjoy.bean.StockBalanceReportBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.StockBalanceReportDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.StockBalanceReportForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class StockBalanceReportServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(StockBalanceReportServlet.class);
	
    private static final String FORM_NAME = "stockBalanceReportForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private StockBalanceReportDao		dao							= null;
    private CompanyDetailsDao			companyDetailsDao			= null;
    private RelationUserAndCompanyDao	relationUserAndCompanyDao	= null;
    private ManageProductTypeDao		productTypeDao				= null;
    private ManageProductGroupDao		productGroupDao				= null;
    private StockBalanceReportForm		form						= null;
    
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
             this.form               		= (StockBalanceReportForm) session.getAttribute(FORM_NAME);
             this.dao						= new StockBalanceReportDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.productTypeDao			= new ManageProductTypeDao();
             this.productGroupDao			= new ManageProductGroupDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new StockBalanceReportForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/StockBalanceReport.jsp");
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
 			dao.destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{		
			this.form.setTitlePage("รายงานสรุปยอดขายตามกลุ่มสินค้า");
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
		
		JSONObject 							jsonObject 						= new JSONObject();
		JSONArray 							jSONArray 						= new JSONArray();
	    JSONObject 							objDetail 						= null;
	    StockBalanceReportBean 				bean							= new StockBalanceReportBean();
	    List<StockBalanceReportBean> 		resultList 						= null;
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
			tin 			= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
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
				for(StockBalanceReportBean vo:resultList){
					objDetail = new JSONObject();
					
					objDetail.put("productTypeName"	,vo.getProductTypeName());
					objDetail.put("productGroupName",vo.getProductGroupName());
					objDetail.put("productName"		,vo.getProductName());
					objDetail.put("quantity"		,vo.getQuantity());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("resultList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
   
			buffer = viewPdfMainForm.writeTicketPDF("StockBalancePdfForm", jsonObject);
	
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
	
	
	
	
	
}
