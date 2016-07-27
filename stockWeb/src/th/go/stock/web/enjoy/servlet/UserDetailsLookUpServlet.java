package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsLookUpForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class UserDetailsLookUpServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsLookUpServlet.class);
	
    private static final String FORM_NAME = "userDetailsLookUpForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private UserDetailsLookUpForm		form						= null;
    
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
             this.form               	= (UserDetailsLookUpForm) session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new UserDetailsLookUpForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/zoom/UserDetailsLookUpScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 		}catch(EnjoyException e){
 			e.printStackTrace();
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
			this.form.setLikeFlag("Y");
			this.initialCombo();	
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void initialCombo() throws EnjoyException{
		
		logger.info("[initialCombo][Begin]");
		
		try{
			this.setColumnList();
			this.setOrderByList();
			this.setSortByList();
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("initialCombo is error");
		}finally{
			logger.info("[initialCombo][End]");
		}
	}
	
	private void setColumnList() throws EnjoyException{
		
		logger.info("[setColumnList][Begin]");
		
		List<ComboBean>			columnList = null;
		
		try{
			
			columnList = new ArrayList<ComboBean>();
			
			columnList.add(new ComboBean(""		 		, "กรุณาระบุ"));
			columnList.add(new ComboBean("userEmail"	, "E-mail"));
			columnList.add(new ComboBean("userFullName"	, "ชื่อ-นามสกุล"));
			
			this.form.setColumnList(columnList);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setColumnList is error");
		}finally{
			logger.info("[setColumnList][End]");
		}
	}
	
	private void setOrderByList() throws EnjoyException{
		
		logger.info("[setOrderByList][Begin]");
		
		List<ComboBean>			orderByList = null;
		
		try{
			
			orderByList = new ArrayList<ComboBean>();
			
			orderByList.add(new ComboBean("userEmail"		, "E-mail"));
			orderByList.add(new ComboBean("userFullName"	, "ชื่อ-นามสกุล"));
			
			this.form.setOrderByList(orderByList);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setOrderByList is error");
		}finally{
			logger.info("[setOrderByList][End]");
		}
	}
	
	private void setSortByList() throws EnjoyException{
		
		logger.info("[setSortByList][Begin]");
		
		List<ComboBean>			sortByList = null;
		
		try{
			
			sortByList = new ArrayList<ComboBean>();
			
			sortByList.add(new ComboBean("ASC"	,"เรียงจากน้อยไปมาก"));
			sortByList.add(new ComboBean("DESC"	,"เรียงจากมากไปน้อย"));
			
			this.form.setSortByList(sortByList);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setSortByList is error");
		}finally{
			logger.info("[setSortByList][End]");
		}
	}
	
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<UserDetailsBean> 		list 				= new ArrayList<UserDetailsBean>();
        List<UserDetailsBean> 		listTemp 			= new ArrayList<UserDetailsBean>();
        HashMap						hashTable			= new HashMap();
        String						find				= null;
    	String						column				= null;
    	String						orderBy				= null;
    	String						sortBy				= null;
    	String						likeFlag			= null;
    	List<UserDetailsBean> 		dataList			= null;

		try{
			find						= EnjoyUtils.nullToStr(this.request.getParameter("find"));
			column						= EnjoyUtils.nullToStr(this.request.getParameter("column"));
			orderBy						= EnjoyUtils.nullToStr(this.request.getParameter("orderBy"));
			sortBy						= EnjoyUtils.nullToStr(this.request.getParameter("sortBy"));
			likeFlag					= EnjoyUtils.chkBoxtoDb(this.request.getParameter("likeFlag"));
			
			logger.info("[onSearch] find 	 	:: " + find);
			logger.info("[onSearch] column 	 	:: " + column);
			logger.info("[onSearch] orderBy 	:: " + orderBy);
			logger.info("[onSearch] sortBy 	 	:: " + sortBy);
			logger.info("[onSearch] likeFlag 	:: " + likeFlag);
			
			this.form.setFind		(find);
			this.form.setColumn		(column);
			this.form.setOrderBy	(orderBy);
			this.form.setSortBy		(sortBy);
			this.form.setLikeFlag	(likeFlag);
			
			if(userBean.getUserUniqueId()!=1){
				this.form.setTin		(userBean.getTin());
			}else{
				this.form.setTin		("");
			}
			dataList = this.dao.getUserDetailsLookUpList(this.form);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(UserDetailsBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<UserDetailsBean>();
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
				
			    listTemp = (List<UserDetailsBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
			    logger.info("[onSearchUserDetail] listTemp :: " + listTemp.size());
			    
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
			this.initialCombo();
			logger.info("[onSearch][End]");
		}
		
	}
	
	
	private void lp_getPage(){
		   logger.info("[lp_getPage][Begin]");
		   
		   int							pageNum				= 1;
		   List<UserDetailsBean> 		list 				= new ArrayList<UserDetailsBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   list = (List<UserDetailsBean>) this.form.getHashTable().get(pageNum);
			   this.form.setDataList(list);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }

	@Override
	public void destroySession() {
		this.dao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
	}
	
	
	
	
	
}
