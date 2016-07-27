package th.go.stock.web.enjoy.servlet;

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
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.InvoiceCashDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.InvoiceCashSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class InvoiceCashSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(InvoiceCashSearchServlet.class);
	
    private static final String FORM_NAME = "invoiceCashSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private InvoiceCashDao				dao							= null;
    private CustomerDetailsDao			customerDetailsDao			= null;
    private InvoiceCashSearchForm		form						= null;
    
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
             this.form               		= (InvoiceCashSearchForm) session.getAttribute(FORM_NAME);
             this.dao						= new InvoiceCashDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new InvoiceCashSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/InvoiceCashSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}else if(pageAction.equals("getCusFullName")){
				this.getCusFullName();
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
			this.form.setTitlePage("ค้นหารายละเอียดการขายเงินสด");
			this.setRefference();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setInvoiceStatusCombo() throws EnjoyException{
		
		logger.info("[setInvoiceStatusCombo][Begin]");
		
		List<ComboBean>			combo 			= null;
		
		try{
			
			combo 				= new ArrayList<ComboBean>();
			
			combo.add(new ComboBean(""	, "ทุกสถานะ"));
			combo.add(new ComboBean("A"	, "ใช้งานอยู่"));
			combo.add(new ComboBean("C"	, "ยกเลิกการใช้งาน"));
			//combo.add(new ComboBean("W"	, "รอสร้างใบ Invoice"));
			
			
			this.form.setInvoiceStatusCombo(combo);
		}
		catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}finally{
			logger.info("[setInvoiceStatusCombo][End]");
		}
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		try{
			this.setInvoiceStatusCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		InvoiceCashMasterBean 		invoiceCashMasterBean	= null;
		List<InvoiceCashMasterBean> dataList 				= null;
		int							cou						= 0;
		int							pageNum					= 1;
        int							totalPage				= 0;
        int							totalRs					= 0;
        List<InvoiceCashMasterBean> list 					= new ArrayList<InvoiceCashMasterBean>();
        List<InvoiceCashMasterBean> listTemp 				= new ArrayList<InvoiceCashMasterBean>();
        HashMap						hashTable				= new HashMap();

		try{
			invoiceCashMasterBean				= new InvoiceCashMasterBean();
			
			invoiceCashMasterBean.setTin				(this.userBean.getTin());
			invoiceCashMasterBean.setInvoiceCode		(EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode")));
			invoiceCashMasterBean.setInvoiceDateForm	(EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateForm")));
			invoiceCashMasterBean.setInvoiceDateTo		(EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateTo")));
			invoiceCashMasterBean.setCusFullName		(EnjoyUtils.nullToStr(this.request.getParameter("cusFullName")));
			invoiceCashMasterBean.setInvoiceStatus		(EnjoyUtils.nullToStr(this.request.getParameter("invoiceStatus")));
			
			this.form.setInvoiceCashMasterBean(invoiceCashMasterBean);
			
			dataList	 		= this.dao.searchByCriteria(invoiceCashMasterBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(InvoiceCashMasterBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<InvoiceCashMasterBean>();
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
				
			    listTemp = (List<InvoiceCashMasterBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
		   List<InvoiceCashMasterBean> 		dataList 			= new ArrayList<InvoiceCashMasterBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   dataList = (List<InvoiceCashMasterBean>) this.form.getHashTable().get(pageNum);
			   this.form.setDataList(dataList);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }
	
	private void getCusFullName(){
	   logger.info("[getCusFullName][Begin]");
	   
	   String						cusFullName			= null;
	   String						tin					= null;
	   List<CustomerDetailsBean> 	list 				= null;
       JSONArray 					jSONArray 			= null;
       JSONObject 					objDetail 			= null;
       CustomerDetailsBean 			customerDetailsBean	= null;
       
	   try{
		   cusFullName			= EnjoyUtils.nullToStr(this.request.getParameter("cusFullName"));
		   tin					= this.userBean.getTin();
		   jSONArray 			= new JSONArray();
		   customerDetailsBean	= new CustomerDetailsBean();
		   
		   logger.info("[getCusFullName] cusFullName 	:: " + cusFullName);
		   logger.info("[getCusFullName] tin 			:: " + tin);
		   
		   customerDetailsBean.setFullName(cusFullName);
		   customerDetailsBean.setTin(tin);
		   
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
		   logger.error(e);
	   }finally{
		   logger.info("[getCusFullName][End]");
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
