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

import th.go.stock.app.enjoy.bean.ReciveOrderMasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ReciveStockDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ReciveStockSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ReciveStockSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ReciveStockSearchServlet.class);
	
    private static final String FORM_NAME = "reciveStockSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private ReciveStockDao				dao							= null;
    private ReciveStockSearchForm		form						= null;
    
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
             this.form               	= (ReciveStockSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new ReciveStockDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ReciveStockSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/ReciveStockSearchScn.jsp");
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
			this.form.setTitlePage("ค้นหาตามใบสั่งของ");			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		ReciveOrderMasterBean 		reciveOrderMasterBean	= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		List<ReciveOrderMasterBean> dataList 				= null;
		int							cou						= 0;
		int							pageNum					= 1;
        int							totalPage				= 0;
        int							totalRs					= 0;
        List<ReciveOrderMasterBean> list 					= new ArrayList<ReciveOrderMasterBean>();
        List<ReciveOrderMasterBean> listTemp 				= new ArrayList<ReciveOrderMasterBean>();
        HashMap						hashTable				= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			reciveOrderMasterBean				= new ReciveOrderMasterBean();
			
			reciveOrderMasterBean.setReciveNo			(EnjoyUtils.nullToStr(this.request.getParameter("reciveNo")));
			reciveOrderMasterBean.setReciveDateFrom		(EnjoyUtils.nullToStr(this.request.getParameter("reciveDateFrom")));
			reciveOrderMasterBean.setReciveDateTo		(EnjoyUtils.nullToStr(this.request.getParameter("reciveDateTo")));
			
			this.form.setReciveOrderMasterBean(reciveOrderMasterBean);
			
			dataList	 		= this.dao.searchByCriteria(session, reciveOrderMasterBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(ReciveOrderMasterBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<ReciveOrderMasterBean>();
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
				
			    listTemp = (List<ReciveOrderMasterBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
		   List<ReciveOrderMasterBean> 		dataList 			= new ArrayList<ReciveOrderMasterBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   dataList = (List<ReciveOrderMasterBean>) this.form.getHashTable().get(pageNum);
			   this.form.setDataList(dataList);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }
	
	
	
	
	
}
