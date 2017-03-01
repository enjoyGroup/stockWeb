package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.AdjustStockBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AdjustStockDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ProductQuanHistoryDao;
import th.go.stock.app.enjoy.dao.ProductquantityDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.AdjustStockForm;
import th.go.stock.app.enjoy.form.InvoiceCashMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class AdjustStockServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(AdjustStockServlet.class);
	
    private static final String 	FORM_NAME 				= "adjustStockForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private AdjustStockDao					dao							= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private AdjustStockForm					form						= null;
    private ProductquantityDao				productquantityDao			= null;
    private ProductQuanHistoryDao			productQuanHistoryDao		= null;
    
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
             this.form               		= (AdjustStockForm)session.getAttribute(FORM_NAME);
             this.dao						= new AdjustStockDao();
             this.productDetailsDao			= new ProductDetailsDao();
             this.productquantityDao		= new ProductquantityDao();
             this.productQuanHistoryDao		= new ProductQuanHistoryDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new AdjustStockForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/AdjustStockScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("getProductDetailByName")){
				this.getProductDetailByName();
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
	
	private void newRecord() throws EnjoyException{
		logger.info("[newRecord][Begin]");
		
		JSONObject 			obj 			= null;
		AdjustStockBean		adjustStockBean	= null;
		String				newSeq			= null;
		
		try{
			
			adjustStockBean 	= new AdjustStockBean();
			obj 				= new JSONObject();
			newSeq				= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			adjustStockBean.setRowStatus(InvoiceCashMaintananceForm.NEW);
			adjustStockBean.setSeq(newSeq);
			
			this.form.getAdjustStockList().add(adjustStockBean);
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
		
		JSONObject 				obj 			= null;
		String 					seq				= null;
		List<AdjustStockBean> 	adjustStockList	= null;
		
		try{
			
			obj 			= new JSONObject();
			seq 			= EnjoyUtil.nullToStr(request.getParameter("seq"));
			adjustStockList	= this.form.getAdjustStockList();
			
			for(int i=0;i<adjustStockList.size();i++){
				AdjustStockBean bean = adjustStockList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(AdjustStockForm.NEW)){
						adjustStockList.remove(i);
					}else{
						bean.setRowStatus(AdjustStockForm.DEL);
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
	
	private void updateRecord() throws EnjoyException{
		logger.info("[updateRecord][Begin]");
		
		JSONObject 				obj 			= null;
		String 					productCode		= null;
		String 					productName		= null;
		String 					unitName		= null;
		String 					quanOld			= null;
		String 					quanNew			= null;
		String 					quantity		= null;
		String 					remark			= null;
		String 					seq				= null;
		List<AdjustStockBean> 	adjustStockList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productCode 			= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			productName 			= EnjoyUtil.nullToStr(request.getParameter("productName"));
			unitName 				= EnjoyUtil.nullToStr(request.getParameter("unitName"));
			quanOld 				= EnjoyUtil.nullToStr(request.getParameter("quanOld"));
			quanNew 				= EnjoyUtil.nullToStr(request.getParameter("quanNew"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			remark 					= EnjoyUtil.nullToStr(request.getParameter("remark"));
			adjustStockList			= this.form.getAdjustStockList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] productCode 	:: " + productCode);
			logger.info("[updateRecord] productName 	:: " + productName);
			logger.info("[updateRecord] unitName 		:: " + unitName);
			logger.info("[updateRecord] quanOld 		:: " + quanOld);
			logger.info("[updateRecord] quanNew 		:: " + quanNew);
			logger.info("[updateRecord] quantity 		:: " + quantity);
			logger.info("[updateRecord] remark 			:: " + remark);
			
			for(AdjustStockBean bean:adjustStockList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductCode		(productCode);
					bean.setProductName		(productName);
					bean.setUnitName		(unitName);
					bean.setQuanOld			(quanOld);
					bean.setQuanNew			(quanNew);
					bean.setUnitName		(unitName);
					bean.setQuantity		(quantity);
					bean.setRemark			(remark);
					
					if(bean.getRowStatus().equals("")){
						bean.setRowStatus(AdjustStockForm.EDIT);
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
	
	private void getProductDetailByName(){
	   logger.info("[getProductDetailByName][Begin]");
	   
	   String							productName				= null;
	   JSONObject 						obj		 				= null;
	   ProductmasterBean				productmasterBean		= null;
	   String							tin						= null;
	   ProductquantityBean				productquantityBean		= null;
	   String							inventory				= "0.00";
	
	   try{
		   obj 				= new JSONObject();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   tin				= this.userBean.getTin();
		   
		   logger.info("[getProductDetailByName] productName 		:: " + productName);
		   logger.info("[getProductDetailByName] tin 				:: " + tin);
		   
		   productmasterBean 		= this.productDetailsDao.getProductDetailByName(productName, tin);
		   
		   if(productmasterBean!=null && !tin.equals("")){
			   
			   
			   obj.put("productCode"	,productmasterBean.getProductCode());
			   obj.put("productName"	,productmasterBean.getProductName());
			   
			   productquantityBean = new ProductquantityBean();
			   productquantityBean.setTin(tin);
			   productquantityBean.setProductCode(productmasterBean.getProductCode());
			   inventory = EnjoyUtils.convertFloatToDisplay(this.productquantityDao.getProductquantity(productquantityBean), 2);
			   
			   obj.put("quanOld"		,inventory);
			   obj.put("quantity"		,inventory);
			   obj.put("unitName"		,productmasterBean.getUnitName());
		   }else{
			   obj.put("productCode"	,"");
			   obj.put("productName"	,"");
			   obj.put("quanOld"		,inventory);
			   obj.put("quantity"		,inventory);
			   obj.put("unitName"		,"");
		   }
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getProductDetailByName is error");
		   e.printStackTrace();
		   logger.info("[getProductDetailByName] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getProductDetailByName][End]");
	   }
	}
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		JSONObject 					obj 						= null;
//		String						productCode					= null;
//		String						quanOld						= null;
//		String 						quanNew						= null;
//		String 						remark						= null;
//		String						quantity					= null;
		ProductquantityBean			productquantityBean			= null;
		String						tin							= null;
		String						quantityDb					= null;
		ProductQuanHistoryBean		productQuanHistoryBean		= null;
		ProductmasterBean 			productmasterBean			= null;
		ProductmasterBean 			productmasterBeanDb			= null;
		List<AdjustStockBean> 		adjustStockList				= null;
		int							hisCode						= 1;
		boolean						chkFlag						= true;
		
		try{
			obj 					= new JSONObject();
			adjustStockList			= this.form.getAdjustStockList();
			tin 					= this.userBean.getTin();
			
			for(AdjustStockBean bean:adjustStockList){
				productquantityBean = new ProductquantityBean();
				productquantityBean.setProductCode(bean.getProductCode());
				productquantityBean.setTin(tin);
				productquantityBean.setQuantity(bean.getQuantity());
				
				quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
				
				if(quantityDb==null){
					this.productquantityDao.insertProductquantity(productquantityBean);
				}else{
					this.productquantityDao.updateProductquantity(productquantityBean);
				}
				
				/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
				productQuanHistoryBean = new ProductQuanHistoryBean();
				productQuanHistoryBean.setFormRef("adjuststock");
				
				productmasterBean = new ProductmasterBean();
				productmasterBean.setProductCode(bean.getProductCode());
				productmasterBean.setTin(tin);
				productmasterBeanDb = productDetailsDao.getProductDetail(productmasterBean);
				if(productmasterBeanDb!=null){
					productQuanHistoryBean.setProductType(productmasterBeanDb.getProductTypeCode());
					productQuanHistoryBean.setProductGroup(productmasterBeanDb.getProductGroupCode());
					productQuanHistoryBean.setProductCode(productmasterBeanDb.getProductCode());
				}else{
					productQuanHistoryBean.setProductType("");
					productQuanHistoryBean.setProductGroup("");
					productQuanHistoryBean.setProductCode("");
				}
				productQuanHistoryBean.setTin(tin);
				
				if(EnjoyUtils.parseDouble(bean.getQuanNew()) > 0){
					productQuanHistoryBean.setQuantityPlus(bean.getQuanNew());
					productQuanHistoryBean.setQuantityMinus("0.00");
				}else{
					productQuanHistoryBean.setQuantityPlus("0.00");
					productQuanHistoryBean.setQuantityMinus(EnjoyUtil.nullToStr(Math.abs(EnjoyUtils.parseDouble(bean.getQuanNew()))));
				}
				productQuanHistoryBean.setQuantityTotal(String.valueOf(bean.getQuantity()));
				
				/*Begin หา hisCode*/
				if(chkFlag==true){
					hisCode		= productQuanHistoryDao.genId(tin);
					chkFlag  	= false;
				}else{
					hisCode++;
				}
				/*End หา hisCode*/
				productQuanHistoryDao.insert(productQuanHistoryBean, hisCode);
				/*End ส่วนประวัตเพิ่มลดสินค้า*/
				
				bean.setAdjustDate(EnjoyUtils.currDateThai());
				bean.setTin(tin);
				this.dao.insertAdjustHistory(bean);
				
			}
			
			this.commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			this.rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			this.rollback();
			logger.error(e);
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[onSave][End]");
		}
	}	

	private void getProductNameList(){
	   logger.info("[getProductNameList][Begin]");
	   
	   String				productName		= null;
	   String				tin				= null;
	   List<ComboBean> 		list 			= null;
	   JSONArray 			jSONArray 		= null;
	   JSONObject 			objDetail 		= null;
	
	   try{
		   jSONArray 		= new JSONArray();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   tin				= this.userBean.getTin();
		   
		   logger.info("[getProductNameList] productName :: " + productName);
		   logger.info("[getProductNameList] tin :: " + tin);
		   
		   list 		= this.productDetailsDao.productNameList(productName, null, null, tin, true);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductNameList][End]");
	   }
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.productDetailsDao.destroySession();
        this.productquantityDao.destroySession();
        this.productQuanHistoryDao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
        this.productDetailsDao.commit();
        this.productquantityDao.commit();
        this.productQuanHistoryDao.commit();
	}

	@Override
	public void rollback() {
		this.dao.rollback();
        this.productDetailsDao.rollback();
        this.productquantityDao.rollback();
        this.productQuanHistoryDao.rollback();
	}
	
}
