package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (RelationGroupCustomerForm)session.getAttribute(FORM_NAME);
             this.dao						= new RelationGroupCustomerDao();
 			
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
			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			destroySession();
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
		
		try{
			this.form.setTitlePage("กำหนดกลุ่มลูกค้า");
			this.setRefference();
			this.onSearch();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		JSONObject 							obj 							= null;
		String								seqTemp							= null;
		RelationGroupCustomerBean			relationGroupCustomerBean		= null;
		List<RelationGroupCustomerBean> 	relationGroupCustomerList		= null;
		String 								tin								= null;

		try{
			obj 	= new JSONObject();
			tin 	= this.userBean.getTin();
			
			relationGroupCustomerBean = new RelationGroupCustomerBean();
			relationGroupCustomerBean.setTin(tin);
			relationGroupCustomerList = this.dao.searchByCriteria(relationGroupCustomerBean);
			
			this.form.setRelationGroupCustomerList(relationGroupCustomerList);
			
			seqTemp = EnjoyUtil.nullToStr(this.dao.getlastId(tin));
			this.form.setSeqTemp(seqTemp);
			
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
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
		}
		
	}
	   
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		JSONObject 							obj 						= null;
		List<RelationGroupCustomerBean> 	relationGroupCustomerList	= null;
		int									cusGroupCode				= 1;
		String								tin							= null;
		boolean								chkFlag						= true;
		
		try{
			obj 						= new JSONObject();
			relationGroupCustomerList	= this.form.getRelationGroupCustomerList();
			tin 						= this.userBean.getTin();
			
			for(RelationGroupCustomerBean bean:relationGroupCustomerList){
				
				logger.info("[onSave] cusGroupName 		:: " + bean.getCusGroupName());
				logger.info("[onSave] RowStatus 		:: " + bean.getRowStatus());
				logger.info("[onSave] groupSalePrice 	:: " + bean.getGroupSalePrice());
				logger.info("[onSave] cusGroupCode 		:: " + bean.getCusGroupCode());
				
				bean.setTin(tin);
				
				if(!bean.getRowStatus().equals("")){
					if(bean.getRowStatus().equals(RelationGroupCustomerForm.NEW)){
						if(chkFlag==true){
							cusGroupCode = this.dao.genId(tin);
							chkFlag  = false;
						}else{
							cusGroupCode++;
						}
						bean.setCusGroupCode(EnjoyUtils.nullToStr(cusGroupCode));
						this.dao.insertRelationGroupCustomer(bean);
					}else if(bean.getRowStatus().equals(RelationGroupCustomerForm.EDIT)){
						this.dao.updateRelationGroupCustomer(bean);
					}else if(bean.getRowStatus().equals(RelationGroupCustomerForm.DEL)){
						this.dao.rejectRelationGroupCustomer(bean);
					}
				}
			}
			
			commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			logger.error(e);
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.error(e);
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
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

