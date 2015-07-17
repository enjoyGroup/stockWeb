package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
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
import th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.RelationUserAndCompanyForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class RelationUserAndCompanyServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RelationUserAndCompanyServlet.class);
	
    private static final String 	FORM_NAME 				= "relationUserAndCompanyForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private RelationUserAndCompanyDao		dao							= null;
    private CompanyDetailsDao				companyDetailsDao			= null;
    private UserDetailsDao					userDetailsDao				= null;
    private RelationUserAndCompanyForm		form						= null;
    
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
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (RelationUserAndCompanyForm)session.getAttribute(FORM_NAME);
             this.dao					= new RelationUserAndCompanyDao();
             this.companyDetailsDao		= new CompanyDetailsDao();
             this.userDetailsDao		= new UserDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new RelationUserAndCompanyForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/RelationUserAndCompanyScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getCompanyName")){
				this.getCompanyName();
			}else if(pageAction.equals("validate")){
				this.lp_validate();
			}else if(pageAction.equals("getUserFullName")){
				this.getUserFullName();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 					obj 						= null;
		RelationUserAndCompanyBean	relationUserAndCompanyBean	= null;
		String						newSeq						= null;
		String						tin							= null;
		String						userUniqueId				= null;
		String						userFullName				= null;
		String						userId						= null;
		String						userStatus					= null;
		String						userStatusName				= null;
		
		try{
			
			relationUserAndCompanyBean 	= new RelationUserAndCompanyBean();
			obj 						= new JSONObject();
			newSeq						= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin"));
			userUniqueId 				= EnjoyUtil.nullToStr(request.getParameter("userUniqueId"));
			userFullName 				= EnjoyUtil.nullToStr(request.getParameter("userFullName"));
			userId 						= EnjoyUtil.nullToStr(request.getParameter("userId"));
			userStatus 					= EnjoyUtil.nullToStr(request.getParameter("userStatus"));
			userStatusName 				= EnjoyUtil.nullToStr(request.getParameter("userStatusName"));
			
			logger.info("[newRecord] newSeq 		:: " + newSeq);
			logger.info("[newRecord] tin 			:: " + tin);
			logger.info("[newRecord] userUniqueId 	:: " + userUniqueId);
			logger.info("[newRecord] userFullName 	:: " + userFullName);
			logger.info("[newRecord] userId 		:: " + userId);
			logger.info("[newRecord] userStatus 	:: " + userStatus);
			logger.info("[newRecord] userStatusName :: " + userStatusName);
			
			relationUserAndCompanyBean.setRowStatus(RelationUserAndCompanyForm.NEW);
			relationUserAndCompanyBean.setSeq(newSeq);
			relationUserAndCompanyBean.setTin(tin);
			
			relationUserAndCompanyBean.setUserUniqueId(userUniqueId);
			relationUserAndCompanyBean.setUserFullName(userFullName);
			relationUserAndCompanyBean.setUserId(userId);
			relationUserAndCompanyBean.setUserStatus(userStatusName);
			relationUserAndCompanyBean.setUserStatusName(userStatusName);
			
			this.form.getRelationUserAndCompanyList().add(relationUserAndCompanyBean);
			this.form.setSeqTemp(newSeq);
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"newRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[newRecord][End]");
		}
	}
	
	private void deleteRecord() throws EnjoyException{
		logger.info("[deleteRecord][Begin]");
		
		JSONObject 							obj 							= null;
		String 								seq								= null;
		List<RelationUserAndCompanyBean> 	relationUserAndCompanyList		= null;
		
		try{
			
			obj 							= new JSONObject();
			seq 							= EnjoyUtil.nullToStr(request.getParameter("seq"));
			relationUserAndCompanyList		= this.form.getRelationUserAndCompanyList();
			
			for(int i=0;i<relationUserAndCompanyList.size();i++){
				RelationUserAndCompanyBean bean = relationUserAndCompanyList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(RelationUserAndCompanyForm.NEW)){
						bean.setRowStatus(RelationUserAndCompanyForm.DEL);
					}else{
						relationUserAndCompanyList.remove(i);
					}
					break;
				}
			}
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"deleteRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[deleteRecord][End]");
		}
	}
	
	private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   JSONObject 							obj 						= new JSONObject();
	   List<RelationUserAndCompanyBean> 	relationUserAndCompanyList	= null;
	   RelationUserAndCompanyBean			bean						= null;
	   RelationUserAndCompanyBean			beanTemp					= null;
	   String								notInUserUniqueId			= "";
	   int									cou							= 0;
	   
	   try{
		   relationUserAndCompanyList		= this.form.getRelationUserAndCompanyList();
		   
		   /*Begin Check ภายในบริษัทตัวเองว่ามี User ซ้ำมั้ย*/
		   for(int i=0;i<relationUserAndCompanyList.size();i++){
				bean = relationUserAndCompanyList.get(i);
				if(!bean.getRowStatus().equals(RelationUserAndCompanyForm.DEL)){
					for(int j=(i+1);j<relationUserAndCompanyList.size();j++){
						beanTemp = relationUserAndCompanyList.get(j);
						
						if(!beanTemp.getRowStatus().equals(RelationUserAndCompanyForm.DEL) && bean.getUserUniqueId().equals(beanTemp.getUserUniqueId())){
							throw new EnjoyException(bean.getUserFullName() + " มีบริษัทสังกัดแล้ว");
						}
					}
				}else{
					if(notInUserUniqueId.equals("")){
						notInUserUniqueId = bean.getUserUniqueId();
					}else{
						notInUserUniqueId += "," + bean.getUserUniqueId();
					}
				}
		   }
		   /*End Check ภายในบริษัทตัวเองว่ามี User ซ้ำมั้ย*/
		   
		   /*Begin Check ใน table ว่ามี User ซ้ำมั้ย*/
		   for(int i=0;i<relationUserAndCompanyList.size();i++){
			   bean = relationUserAndCompanyList.get(i);
			   if(!bean.getRowStatus().equals(RelationUserAndCompanyForm.DEL)){
				   cou = this.dao.checkDupUser(bean.getUserUniqueId(), notInUserUniqueId);
				   if(cou > 0){
					   throw new EnjoyException(bean.getUserFullName() + " มีบริษัทสังกัดแล้ว");
				   }
			   }
		   }
		   /*End Check ใน table ว่ามี User ซ้ำมั้ย*/
		   
		   obj.put(STATUS				, SUCCESS);
		   
	   }catch(EnjoyException e){
		   obj.put(STATUS, 				ERROR);
		   obj.put(ERR_MSG, 			e.getMessage());
	   }catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_MSG, 			"เกิดข้อผิดพลาดในการตรวจสอบข้อมูล");
			logger.info(e.getMessage());
			e.printStackTrace();
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[lp_validate][End]");
	   }
	}
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 						sessionFactory				= null;
		Session 							session						= null;
		JSONObject 							obj 						= null;
		List<RelationUserAndCompanyBean> 	relationUserAndCompanyList	= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			relationUserAndCompanyList	= this.form.getRelationUserAndCompanyList();
			
			session.beginTransaction();
			
			this.dao.deleteRelationUserAndCompany(session, this.form.getTin());
			
			for(RelationUserAndCompanyBean bean:relationUserAndCompanyList){
				if(!bean.getRowStatus().equals(RelationUserAndCompanyForm.DEL)){
					this.dao.insertRelationUserAndCompany(session, bean);
				}
			}
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onSave][End]");
		}
	}	
	
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		RelationUserAndCompanyBean 			relationUserAndCompanyBean		= null;
		SessionFactory 						sessionFactory					= null;
		Session 							session							= null;
		List<RelationUserAndCompanyBean> 	relationUserAndCompanyList		= null;
		String								tin								= null;
		String								companyName						= null;
		String								userFullName					= null;
		JSONObject 							obj 							= null;
		String								seqTemp							= null;

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			obj 						= new JSONObject();
			companyName					= EnjoyUtils.nullToStr(this.request.getParameter("companyName"));
			userFullName				= EnjoyUtils.nullToStr(this.request.getParameter("userFullName"));
			tin							= this.companyDetailsDao.getTin(companyName);
			
			logger.info("[onSearch] companyName 	:: " + companyName);
			logger.info("[onSearch] tin 			:: " + tin);
			logger.info("[onSearch] userFullName 	:: " + userFullName);
			
			if(tin==null || tin.equals("")){
				throw new EnjoyException("ระบุชื่อบริษัทผิดกรุณาตรวจสอบ");
			}
			
			this.form.setCompanyName(companyName);
			this.form.setTin(tin);
			this.form.setUserFullName(userFullName);
			this.form.setChk(true);
			
			session.beginTransaction();
			
			relationUserAndCompanyBean				= new RelationUserAndCompanyBean();
			relationUserAndCompanyBean.setTin(tin);
			relationUserAndCompanyBean.setUserFullName(userFullName);
			
			
			relationUserAndCompanyList	 		= this.dao.searchByCriteria(session, relationUserAndCompanyBean);
			
			for(RelationUserAndCompanyBean bean:relationUserAndCompanyList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setRelationUserAndCompanyList(relationUserAndCompanyList);
			
			if(seqTemp!=null){
				this.form.setSeqTemp(seqTemp);
			}
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSearch is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
		}
		
	}
	
	private void getCompanyName(){
	   logger.info("[getCompanyName][Begin]");
	   
	   String							companyName				= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   companyName			= EnjoyUtils.nullToStr(this.request.getParameter("companyName"));
		   jSONArray 			= new JSONArray();
		   
		   logger.info("[getCompanyName] companyName 			:: " + companyName);
		   
		   list 		= this.companyDetailsDao.companyNameList(companyName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getCompanyName] " + e.getMessage());
	   }finally{
		   logger.info("[getCompanyName][End]");
	   }
   }
	
	private void getUserFullName(){
	   logger.info("[getUserFullName][Begin]");
	   
	   String							userFullName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   userFullName			= EnjoyUtils.nullToStr(this.request.getParameter("userFullName"));
		   jSONArray 			= new JSONArray();
		   
		   logger.info("[getUserFullName] userFullName 			:: " + userFullName);
		   
		   list 		= this.userDetailsDao.userFullNameList(userFullName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getUserFullName] " + e.getMessage());
	   }finally{
		   logger.info("[getUserFullName][End]");
	   }
   }

}
