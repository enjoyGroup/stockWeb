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

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CustomerDetailsSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CustomerDetailsSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CustomerDetailsSearchServlet.class);
	
    private static final String FORM_NAME = "customerDetailsSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private CustomerDetailsDao			dao							= null;
    private CustomerDetailsSearchForm	form						= null;
    
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
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean) session.getAttribute("userBean");
             this.form               	= (CustomerDetailsSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new CustomerDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new CustomerDetailsSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/CustomerDetailsSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch(null);
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
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
			this.setRefference();			
			this.form.setTitlePage("ค้นหารายละเอียดลูกค้า");			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		List<ComboBean> 	comboList		= null;
		ComboBean			comboBean		= null;
		
		try{
			
			comboList = this.dao.getStatusCombo();
			
			if(comboList!=null && comboList.size() > 0){
				comboBean = comboList.get(0);
				comboBean.setDesc("ทุกสถานะ");
			}
			
			this.form.setStatusCombo(comboList);
			
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	
	private void onSearch(CustomerDetailsBean 	customerDetailsBean) throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		List<CustomerDetailsBean> 	dataList 			= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<CustomerDetailsBean> 	list 				= new ArrayList<CustomerDetailsBean>();
        List<CustomerDetailsBean> 	listTemp 			= new ArrayList<CustomerDetailsBean>();
        HashMap						hashTable			= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			if(customerDetailsBean==null){
				customerDetailsBean				= new CustomerDetailsBean();
				
				customerDetailsBean.setCusCode			(EnjoyUtils.nullToStr(this.request.getParameter("cusCode")));
				customerDetailsBean.setFullName			(EnjoyUtils.nullToStr(this.request.getParameter("fullName")));
				customerDetailsBean.setCusStatus		(EnjoyUtils.nullToStr(this.request.getParameter("cusStatus")));
				customerDetailsBean.setIdNumber			(EnjoyUtils.nullToStr(this.request.getParameter("idNumber")));
			}
			
			this.form.setCustomerDetailsBean(customerDetailsBean);
			
			dataList	 		= this.dao.searchByCriteria(session, customerDetailsBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(CustomerDetailsBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<CustomerDetailsBean>();
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
				
			    listTemp = (List<CustomerDetailsBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
			
			this.setRefference();
			logger.info("[onSearch][End]");
		}
		
	}
	
	private void lp_getPage(){
	   logger.info("[lp_getPage][Begin]");
	   
	   int								pageNum				= 1;
	   List<CustomerDetailsBean> 		dataList 			= new ArrayList<CustomerDetailsBean>();
	   
	   try{
		   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
		   
		   this.form.setPageNum(pageNum);
		   
		   dataList = (List<CustomerDetailsBean>) this.form.getHashTable().get(pageNum);
		   this.form.setDataList(dataList);
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
		   logger.info("[lp_getPage][End]");
	   }
	   
   }

	
	
	
	
}
