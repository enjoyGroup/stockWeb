package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.InvoiceCreditDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UpdateInvoiceCreditSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class UpdateInvoiceCreditSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UpdateInvoiceCreditSearchServlet.class);
	
    private static final String FORM_NAME = "updateInvoiceCreditSearchForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private InvoiceCreditDao				invoiceCreditDao			= null;
    private CustomerDetailsDao				customerDetailsDao			= null;
    private RelationUserAndCompanyDao		relationUserAndCompanyDao	= null;
    private UpdateInvoiceCreditSearchForm	form						= null;
    
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
             this.form               		= (UpdateInvoiceCreditSearchForm) session.getAttribute(FORM_NAME);
             this.invoiceCreditDao			= new InvoiceCreditDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new UpdateInvoiceCreditSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/UpdateInvoiceCreditSearchScn.jsp");
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
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{		
			this.form.setTitlePage("ปรับปรุงงบการขายเงินเชื่อ");
			this.setRefference();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
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
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		InvoiceCreditMasterBean 		invoiceCreditMasterBean	= null;
		SessionFactory 					sessionFactory			= null;
		Session 						session					= null;
		List<InvoiceCreditMasterBean> 	dataList 				= null;
		int								cou						= 0;
		int								pageNum					= 1;
        int								totalPage				= 0;
        int								totalRs					= 0;
        List<InvoiceCreditMasterBean> 	list 					= new ArrayList<InvoiceCreditMasterBean>();
        List<InvoiceCreditMasterBean> 	listTemp 				= new ArrayList<InvoiceCreditMasterBean>();
        HashMap						  	hashTable				= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			invoiceCreditMasterBean		= new InvoiceCreditMasterBean();
			
			invoiceCreditMasterBean.setInvoiceCode		(EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode")));
			invoiceCreditMasterBean.setTin				(EnjoyUtils.nullToStr(this.request.getParameter("tin")));
			invoiceCreditMasterBean.setInvoiceDateForm	(EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateForm")));
			invoiceCreditMasterBean.setInvoiceDateTo	(EnjoyUtils.nullToStr(this.request.getParameter("invoiceDateTo")));
			invoiceCreditMasterBean.setCusFullName		(EnjoyUtils.nullToStr(this.request.getParameter("cusFullName")));
			
			this.form.setInvoiceCreditMasterBean(invoiceCreditMasterBean);
			
			dataList	 		= this.invoiceCreditDao.searchByCriteriaForCredit(session, invoiceCreditMasterBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(InvoiceCreditMasterBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<InvoiceCreditMasterBean>();
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
				
			    listTemp = (List<InvoiceCreditMasterBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
			session.close();
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onSearch][End]");
		}
		
	}
	
	private void lp_getPage(){
		   logger.info("[lp_getPage][Begin]");
		   
		   int								pageNum				= 1;
		   List<InvoiceCreditMasterBean> 	dataList 			= new ArrayList<InvoiceCreditMasterBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   dataList = (List<InvoiceCreditMasterBean>) this.form.getHashTable().get(pageNum);
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
	   List<CustomerDetailsBean> 	list 				= null;
       JSONArray 					jSONArray 			= null;
       JSONObject 					objDetail 			= null;
       CustomerDetailsBean 			customerDetailsBean	= null;
       
	   try{
		   cusFullName			= EnjoyUtils.nullToStr(this.request.getParameter("cusFullName"));
		   jSONArray 			= new JSONArray();
		   customerDetailsBean	= new CustomerDetailsBean();
		   
		   logger.info("[getCusFullName] cusFullName 			:: " + cusFullName);
		   
		   customerDetailsBean.setFullName(cusFullName);
		   
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
	
	
	
	
	
}
