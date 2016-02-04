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
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyDetailsSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CompanyDetailsSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyDetailsSearchServlet.class);
	
    private static final String FORM_NAME = "companyDetailsSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private CompanyDetailsDao			dao							= null;
    private CompanyDetailsSearchForm	form						= null;
    
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
             this.form               	= (CompanyDetailsSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new CompanyDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new CompanyDetailsSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/CompanyDetailsSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}else if(pageAction.equals("getCompanyNameList")){
				this.getCompanyNameList();
			}else if(pageAction.equals("getTinList")){
				this.getTinList();
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
			this.form.setTitlePage("ค้นหารายละเอียดบริษัท");			
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
		
		try{
			setStatusCombo();
			
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
	
	private void setStatusCombo() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		List<ComboBean> 			comboListDb				= null;
		
		try{
			comboListDb = this.dao.getCompanystatusCombo();
			
			comboList.add(new ComboBean("", "ทั้งหมด"));
			
			for(ComboBean bean:comboListDb){
				comboList.add(new ComboBean(bean.getCode(), bean.getDesc()));
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
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		CompanyDetailsBean 			companyDetailsBean	= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		List<CompanyDetailsBean> 	dataList 			= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<CompanyDetailsBean> 	list 				= new ArrayList<CompanyDetailsBean>();
        List<CompanyDetailsBean> 	listTemp 			= new ArrayList<CompanyDetailsBean>();
        HashMap						hashTable			= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			companyDetailsBean				= new CompanyDetailsBean();
			
			companyDetailsBean.setTin			(EnjoyUtils.nullToStr(this.request.getParameter("tin")));
			companyDetailsBean.setCompanyName	(EnjoyUtils.nullToStr(this.request.getParameter("companyName")));
			companyDetailsBean.setCompanyStatus	(EnjoyUtils.nullToStr(this.request.getParameter("companyStatus")));
			
			this.form.setCompanyDetailsBean(companyDetailsBean);
			
			dataList	 		= this.dao.searchByCriteria(session, companyDetailsBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(CompanyDetailsBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<CompanyDetailsBean>();
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
				
			    listTemp = (List<CompanyDetailsBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
		   List<CompanyDetailsBean> 		dataList 				= new ArrayList<CompanyDetailsBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   dataList = (List<CompanyDetailsBean>) this.form.getHashTable().get(pageNum);
			   this.form.setDataList(dataList);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }
	
	private void getCompanyNameList(){
	   logger.info("[getCompanyNameList][Begin]");
	   
	   String							companyName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   companyName			= EnjoyUtils.nullToStr(this.request.getParameter("companyName"));
		   jSONArray 			= new JSONArray();
		   
		   logger.info("[getCompanyNameList] companyName 			:: " + companyName);
		   
		   
		   list 		= this.dao.companyNameListForAutoComplete(companyName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getCompanyNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getCompanyNameList][End]");
	   }
   }
	
	private void getTinList(){
		   logger.info("[getTinList][Begin]");
		   
		   String							tin				= null;
		   List<ComboBean> 					list 			= null;
	       JSONArray 						jSONArray 		= null;
	       JSONObject 						objDetail 		= null;
	       
		   try{
			   tin			= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
			   jSONArray 			= new JSONArray();
			   
			   logger.info("[getTinList] tin 			:: " + tin);
			   
			   
			   list 		= this.dao.tinListForAutoComplete(tin);
			   
			   for(ComboBean bean:list){
				   objDetail 		= new JSONObject();
				   
				   objDetail.put("id"			,bean.getCode());
				   objDetail.put("value"		,bean.getDesc());
				   
				   jSONArray.add(objDetail);
			   }
			   
			   this.enjoyUtil.writeMSG(jSONArray.toString());
			   
		   }catch(Exception e){
			   e.printStackTrace();
			   logger.info("[getTinList] " + e.getMessage());
		   }finally{
			   logger.info("[getTinList][End]");
		   }
	   }
		
	
	
	
}
