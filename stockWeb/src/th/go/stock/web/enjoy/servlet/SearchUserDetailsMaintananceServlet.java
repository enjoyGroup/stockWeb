package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.model.Userprivilege;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class SearchUserDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SearchUserDetailsMaintananceServlet.class);
	
    private static final String FORM_NAME = "userDetailsMaintananceForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private UserDetailsMaintananceForm	form						= null;
    
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
             this.form               	= (UserDetailsMaintananceForm) session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getUserDetail")) this.form = new UserDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsSearchScn.jsp");
 			}else if(pageAction.equals("searchUserDetail")){
 				this.onSearchUserDetail();
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
			this.form.setTitlePage("เงื่อนไขค้นหาผู้ใช้งานระบบ");			
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
		
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			this.form.setRefuserstatusCombo(this.dao.getRefuserstatusCombo(session));
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[setRefference][End]");
		}
	}
	
	
	private void onSearchUserDetail() throws EnjoyException{
		logger.info("[onSearchUserDetail][Begin]");
		
		UserDetailsBean 			userdetailForm		= null;
		Userprivilege				userprivilege		= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		List<UserDetailsBean> 		listUserDetailsBean = null;
		List<Userprivilege> 		listUserprivilege   = null;
		Hashtable<String, String> 	fUserprivilege		= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<UserDetailsBean> 		list 				= new ArrayList<UserDetailsBean>();
        List<UserDetailsBean> 		listTemp 			= new ArrayList<UserDetailsBean>();
        HashMap						hashTable			= new HashMap();

		try{
			listUserDetailsBean 		= new ArrayList<UserDetailsBean>();
			listUserprivilege   		= new ArrayList<Userprivilege>();
			fUserprivilege				= new Hashtable<String, String>();

			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			userdetailForm				= new UserDetailsBean();
			
			userdetailForm.setUserName	(EnjoyUtils.nullToStr(this.request.getParameter("userName")));
			userdetailForm.setUserId	(EnjoyUtils.nullToStr(this.request.getParameter("userId")));
			userdetailForm.setUserStatus(EnjoyUtils.nullToStr(this.request.getParameter("userStatus")));
			
			this.form.setUserDetailsBean(userdetailForm);
			
			logger.info("[onSearchUserDetail] userdetailForm.getUserName() 	 :: " + userdetailForm.getUserName());
			logger.info("[onSearchUserDetail] userdetailForm.getUserId() 	 :: " + userdetailForm.getUserId());
			logger.info("[onSearchUserDetail] userdetailForm.getUserStatus() :: " + userdetailForm.getUserStatus());
			
			listUserprivilege 			= this.dao.getUserprivilege(session);
			for(int i=0;i<listUserprivilege.size();i++){
				userprivilege			= listUserprivilege.get(i);
				fUserprivilege.put(userprivilege.getPrivilegeCode() , userprivilege.getPrivilegeName());
			}	
			listUserDetailsBean	 		= this.dao.getListUserdetail(session, userdetailForm, fUserprivilege);

			//logger.info("[onSearchUserDetail] listUserDetailsBean.size :: " + listUserDetailsBean.size());
			logger.info("[onSearchUserDetail] listUserDetailsBean :: " + listUserDetailsBean.size());
			if(listUserDetailsBean.size() > 0){				
				//this.form.setUserDetailsBeanList(listUserDetailsBean);		
				
				hashTable.put(pageNum, list);
				for(UserDetailsBean bean:listUserDetailsBean){
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
				
				logger.info("[onSearchUserDetail] totalPage :: " + totalPage);
				
			    this.form.setTotalPage(totalPage);
			    this.form.setTotalRecord(EnjoyUtils.convertFloatToDisplay(String.valueOf(totalRs) , 0));
			    this.form.setHashTable(hashTable);
			    this.form.setPageNum(1);
				
			    listTemp = (List<UserDetailsBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
			    logger.info("[onSearchUserDetail] listTemp :: " + listTemp.size());
			    
			    this.form.setUserDetailsBeanList(listTemp);
				
				
			}else{
				this.form.setTotalPage(1);
			    this.form.setTotalRecord(EnjoyUtils.convertFloatToDisplay("0" , 0));
			    this.form.setHashTable(hashTable);
			    this.form.setPageNum(1);
				this.form.setUserDetailsBeanList(listUserDetailsBean);
				throw new EnjoyException("ไม่พบรายการตามเงื่อนไขที่ระบุ");
			}			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onSearchUserDetail is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.setRefference();
			logger.info("[onSearchUserDetail][End]");
		}
		
	}
	
	
	private void lp_getPage(){
		   logger.info("[lp_getPage][Begin]");
		   
		   int								pageNum				= 1;
		   List<UserDetailsBean> 			list 				= new ArrayList<UserDetailsBean>();
		   
		   try{
			   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
			   
			   this.form.setPageNum(pageNum);
			   
			   list = (List<UserDetailsBean>) this.form.getHashTable().get(pageNum);
			   this.form.setUserDetailsBeanList(list);
			   
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
			   logger.info("[lp_getPage][End]");
		   }
		   
	   }
	
	
	
	
	
}
