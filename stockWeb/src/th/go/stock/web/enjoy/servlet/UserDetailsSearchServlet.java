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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.UserDetailsSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class UserDetailsSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(UserDetailsSearchServlet.class);
	
    private static final String FORM_NAME = "userDetailsSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private UserDetailsDao				dao							= null;
    private UserDetailsSearchForm		form						= null;
    
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
             this.form               	= (UserDetailsSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getUserDetail")) this.form = new UserDetailsSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/UserDetailsSearchScn.jsp");
 			}else if(pageAction.equals("searchUserDetail")){
 				this.onSearchUserDetail();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}else if(pageAction.equals("userFullNameList")){
				this.userFullNameList();
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
		
		try{
			
			this.setStatusCombo();
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setStatusCombo() throws EnjoyException{
		
		List<ComboBean> 	comboList		= new ArrayList<ComboBean>();
		List<ComboBean> 	comboListDb		= null;
		
		try{
			comboListDb = this.dao.getRefuserstatusCombo();
			
			comboList.add(new ComboBean("", "ทั้งหมด"));
			for(ComboBean bean:comboListDb){
				comboList.add(new ComboBean(bean.getCode(), bean.getDesc()));
			}
			
			this.form.setStatusCombo(comboList);
			
		}catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setStatusCombo is error");
		}
	}	
	
	private void onSearchUserDetail() throws EnjoyException{
		logger.info("[onSearchUserDetail][Begin]");
		
		List<UserDetailsBean> 		listUserDetailsBean = null;
		List<UserPrivilegeBean> 	listUserprivilege   = null;
		Hashtable<String, String> 	fUserprivilege		= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<UserDetailsBean> 		list 				= new ArrayList<UserDetailsBean>();
        List<UserDetailsBean> 		listTemp 			= new ArrayList<UserDetailsBean>();
        HashMap						hashTable			= new HashMap();
        UserDetailsBean 			userDetailsBean 	= null;

		try{
			listUserDetailsBean 		= new ArrayList<UserDetailsBean>();
			fUserprivilege				= new Hashtable<String, String>();

			userDetailsBean				= new UserDetailsBean();
			
			userDetailsBean.setUserName		(EnjoyUtils.nullToStr(this.request.getParameter("userName")));
			userDetailsBean.setUserEmail	(EnjoyUtils.nullToStr(this.request.getParameter("userEmail")));
			userDetailsBean.setUserStatus	(EnjoyUtils.nullToStr(this.request.getParameter("userStatus")));
			userDetailsBean.setTin			(this.userBean.getTin());
			userDetailsBean.setUserUniqueId	(this.userBean.getUserUniqueId());
			
			this.form.setUserDetailsBean(userDetailsBean);
			
			logger.info("[onSearchUserDetail] userName 	 :: " + userDetailsBean.getUserName());
			logger.info("[onSearchUserDetail] userEmail  :: " + userDetailsBean.getUserEmail());
			logger.info("[onSearchUserDetail] userStatus :: " + userDetailsBean.getUserStatus());
			
			listUserprivilege 			= this.dao.getUserprivilege();
			for(UserPrivilegeBean userprivilege :listUserprivilege){
				fUserprivilege.put(userprivilege.getPrivilegeCode() , userprivilege.getPrivilegeName());
			}	
			listUserDetailsBean	 		= this.dao.getListUserdetail(userDetailsBean, fUserprivilege);

			logger.info("[onSearchUserDetail] listUserDetailsBean :: " + listUserDetailsBean.size());
			if(listUserDetailsBean.size() > 0){				
				
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
	
	private void userFullNameList(){
		   logger.info("[userFullNameList][Begin]");
		   
		   String							userName		= null;
		   List<ComboBean> 					list 			= null;
	       JSONArray 						jSONArray 		= null;
	       JSONObject 						objDetail 		= null;
	       
		   try{
			   userName			= EnjoyUtils.nullToStr(this.request.getParameter("userName"));
			   jSONArray 		= new JSONArray();
			   
			   logger.info("[userFullNameList] userName 			:: " + userName);
			   
			   
			   list 		= this.dao.userFullNameList(userName, userBean.getTin(), userBean.getUserUniqueId());
			   
			   for(ComboBean bean:list){
				   objDetail 		= new JSONObject();
				   
				   objDetail.put("id"			,bean.getCode());
				   objDetail.put("value"		,bean.getDesc());
				   
				   jSONArray.add(objDetail);
			   }
			   
			   this.enjoyUtil.writeMSG(jSONArray.toString());
			   
		   }catch(Exception e){
			   e.printStackTrace();
			   logger.info("[userFullNameList] " + e.getMessage());
		   }finally{
			   logger.info("[userFullNameList][End]");
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
