package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import th.go.stock.app.enjoy.bean.RelationGroupCustomerBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.RelationGroupCustomerDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.RelationGroupCustomerForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class RelationGroupCustomerServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RelationGroupCustomerServlet.class);
	
    private static final String 	FORM_NAME 				= "relationGroupCustomerForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private RelationGroupCustomerDao		dao							= null;
    private RelationGroupCustomerForm		form						= null;
    
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
             this.form               	= (RelationGroupCustomerForm)session.getAttribute(FORM_NAME);
             this.dao					= new RelationGroupCustomerDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new RelationGroupCustomerForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/RelationGroupCustomerScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
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
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		try{
			this.setGroupSalePriceCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setGroupSalePriceCombo() throws EnjoyException{
		
		logger.info("[setGroupSalePriceCombo][Begin]");
		
		List<ComboBean>			combo 			= null;
		JSONObject 				obj 			= null;
		JSONObject 				objDetail 		= null;
		JSONArray 				jsonArray 		= null;
		
		try{
			
			combo 				= new ArrayList<ComboBean>();
			obj 				= new JSONObject();
			jsonArray	 		= new JSONArray();
			
			combo.add(new ComboBean(""	, "กรุณาระบุ"));
			
			objDetail = new JSONObject();
			objDetail.put("code", "");
			objDetail.put("desc", "กรุณาระบุ");
			jsonArray.add(objDetail);
			
			for(int i=1;i<=5;i++){
				
				combo.add(new ComboBean(EnjoyUtils.nullToStr(i)	, "ใช้ราคาที่ " + i));
				
				objDetail = new JSONObject();
				objDetail.put("code", EnjoyUtils.nullToStr(i));
				objDetail.put("desc", "ใช้ราคาที่ " + i);
				jsonArray.add(objDetail);	
			}
			
			obj.put("jsonGroupSalePriceCombo", jsonArray);
			
			this.form.setGroupSalePriceCombo(combo);
			this.form.setJsonGroupSalePriceCombo(obj.toString());
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setGroupSalePriceCombo is error");
		}finally{
			logger.info("[setGroupSalePriceCombo][End]");
		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		SessionFactory 						sessionFactory					= null;
		Session 							session							= null;
		String								seqTemp							= null;
		RelationGroupCustomerBean			relationGroupCustomerBean		= null;
		List<RelationGroupCustomerBean> 	relationGroupCustomerList		= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			session.beginTransaction();
			
			this.form.setTitlePage("กำหนดกลุ่มลูกค้า");
			
			relationGroupCustomerBean = new RelationGroupCustomerBean();
			relationGroupCustomerList = this.dao.searchByCriteria(session, relationGroupCustomerBean);
			
			this.form.setRelationGroupCustomerList(relationGroupCustomerList);
			
			seqTemp = EnjoyUtil.nullToStr(this.dao.getlastId(session));
			this.form.setSeqTemp(seqTemp);
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			session.close();
			
			this.setRefference();
			
			sessionFactory	= null;
			session			= null;
			logger.info("[onLoad][End]");
		}
		
	}
	   
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 						sessionFactory				= null;
		Session 							session						= null;
		JSONObject 							obj 						= null;
		List<RelationGroupCustomerBean> 	relationGroupCustomerList	= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			relationGroupCustomerList	= this.form.getRelationGroupCustomerList();
			
			session.beginTransaction();
			
			for(RelationGroupCustomerBean bean:relationGroupCustomerList){
				if(!bean.getRowStatus().equals("")){
					if(bean.getRowStatus().equals(RelationGroupCustomerForm.NEW)){
						this.dao.insertRelationGroupCustomer(session, bean);
					}else if(bean.getRowStatus().equals(RelationGroupCustomerForm.EDIT)){
						this.dao.updateRelationGroupCustomer(session, bean);
					}else if(bean.getRowStatus().equals(RelationGroupCustomerForm.DEL)){
						this.dao.rejectRelationGroupCustomer(session, bean);
					}
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
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 					obj 						= null;
		RelationGroupCustomerBean	relationGroupCustomerBean	= null;
		String						newSeq						= null;
		
		try{
			
			relationGroupCustomerBean 	= new RelationGroupCustomerBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			relationGroupCustomerBean.setRowStatus(RelationGroupCustomerForm.NEW);
			relationGroupCustomerBean.setSeq(newSeq);
			
			this.form.getRelationGroupCustomerList().add(relationGroupCustomerBean);
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
	
	private void updateRecord() throws EnjoyException{
		logger.info("[updateRecord][Begin]");
		
		JSONObject 							obj 						= null;
		String 								cusGroupName				= null;
		String 								groupSalePrice				= null;
		String 								seq							= null;
		List<RelationGroupCustomerBean> 	relationGroupCustomerList	= null;
		
		try{
			
			obj 						= new JSONObject();
			seq 						= EnjoyUtil.nullToStr(request.getParameter("seq"));
			cusGroupName 				= EnjoyUtil.nullToStr(request.getParameter("cusGroupName"));
			groupSalePrice 				= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
			relationGroupCustomerList	= this.form.getRelationGroupCustomerList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] cusGroupName 	:: " + cusGroupName);
			logger.info("[updateRecord] groupSalePrice 	:: " + groupSalePrice);
			
			for(RelationGroupCustomerBean bean:relationGroupCustomerList){
				if(bean.getSeq().equals(seq) && !bean.getRowStatus().equals(RelationGroupCustomerForm.DEL)){
					
					bean.setCusGroupName	(cusGroupName);
					bean.setGroupSalePrice	(groupSalePrice);
					
					if(bean.getRowStatus().equals("")){
						bean.setRowStatus(RelationGroupCustomerForm.EDIT);
					}
					
					break;
				}
			}
			
			obj.put(STATUS, 		SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"updateRecord is error");
		}finally{
			
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[updateRecord][End]");
		}
	}
	
	private void deleteRecord() throws EnjoyException{
		logger.info("[deleteRecord][Begin]");
		
		JSONObject 						obj 						= null;
		String 							seq							= null;
		List<RelationGroupCustomerBean> relationGroupCustomerList	= null;
		
		try{
			
			obj 						= new JSONObject();
			seq 						= EnjoyUtil.nullToStr(request.getParameter("seq"));
			relationGroupCustomerList	= this.form.getRelationGroupCustomerList();
			
			for(int i=0;i<relationGroupCustomerList.size();i++){
				RelationGroupCustomerBean bean = relationGroupCustomerList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(RelationGroupCustomerForm.NEW)){
						relationGroupCustomerList.remove(i);
					}else{
						bean.setRowStatus(RelationGroupCustomerForm.DEL);
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
}

