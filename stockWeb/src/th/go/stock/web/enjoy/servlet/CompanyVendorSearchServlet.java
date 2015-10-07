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

import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyVendorSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CompanyVendorSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyVendorSearchServlet.class);
	
    private static final String FORM_NAME = "companyVendorSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private CompanyVendorDao			dao							= null;
    private CompanyVendorSearchForm		form						= null;
    
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
             this.form               	= (CompanyVendorSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new CompanyVendorDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new CompanyVendorSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/CompanyVendorSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
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
			this.form.setTitlePage("ค้นหาบริษัทสั่งซื้อ");			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		CompanyVendorBean 			companyVendorBean	= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		List<CompanyVendorBean> 	dataList 			= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<CompanyVendorBean> 	list 				= new ArrayList<CompanyVendorBean>();
        List<CompanyVendorBean> 	listTemp 			= new ArrayList<CompanyVendorBean>();
        HashMap						hashTable			= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			companyVendorBean				= new CompanyVendorBean();
			
			companyVendorBean.setVendorName			(EnjoyUtils.nullToStr(this.request.getParameter("vendorName")));
			companyVendorBean.setBranchName			(EnjoyUtils.nullToStr(this.request.getParameter("branchName")));
			companyVendorBean.setTin				(EnjoyUtils.nullToStr(this.request.getParameter("tin")));
			
			this.form.setCompanyVendorBean(companyVendorBean);
			
			dataList	 		= this.dao.searchByCriteria(session, companyVendorBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(CompanyVendorBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<CompanyVendorBean>();
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
				
			    listTemp = (List<CompanyVendorBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
		   List<CompanyVendorBean> 			dataList 			= new ArrayList<CompanyVendorBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   dataList = (List<CompanyVendorBean>) this.form.getHashTable().get(pageNum);
			   this.form.setDataList(dataList);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }
	
	
	
	
	
}
