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
			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}else if(pageAction.equals("loadNextRangeOrder")){
				this.loadNextRangeOrder();
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
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		JSONObject 					obj 						= null;
		String						productCode					= null;
		String						quanOld						= null;
		String 						quanNew						= null;
		String 						remark						= null;
		String						quantity					= null;
		AdjustStockBean 			adjustStockBean				= null;
		ProductquantityBean			productquantityBean			= null;
		String						tin							= null;
		String						quantityDb					= null;
		ProductQuanHistoryBean		productQuanHistoryBean		= null;
		ProductmasterBean 			productmasterBean			= null;
		ProductmasterBean 			productmasterBeanDb			= null;
		
		try{
			obj 						= new JSONObject();
			productCode					= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
			quanOld						= EnjoyUtils.nullToStr(this.request.getParameter("quanOld"));
			quanNew						= EnjoyUtils.nullToStr(this.request.getParameter("quanNew"));
			remark						= EnjoyUtils.nullToStr(this.request.getParameter("remark"));
			quantity					= EnjoyUtils.nullToStr(this.request.getParameter("quantity"));
			tin 						= this.userBean.getTin();
			adjustStockBean				= this.form.getAdjustStockBean();
			
			productquantityBean = new ProductquantityBean();
			productquantityBean.setProductCode(productCode);
			productquantityBean.setTin(tin);
			productquantityBean.setQuantity(quantity);
			
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
			productmasterBean.setProductCode(productCode);
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
			
			if(EnjoyUtils.parseDouble(quanNew) > 0){
				productQuanHistoryBean.setQuantityPlus(quanNew);
				productQuanHistoryBean.setQuantityMinus("0.00");
			}else{
				productQuanHistoryBean.setQuantityPlus("0.00");
				productQuanHistoryBean.setQuantityMinus(EnjoyUtil.nullToStr(Math.abs(EnjoyUtils.parseDouble(quanNew))));
			}
			productQuanHistoryBean.setQuantityTotal(String.valueOf(quantity));
			
			productQuanHistoryDao.insert(productQuanHistoryBean);
			/*End ส่วนประวัตเพิ่มลดสินค้า*/
			
			adjustStockBean.setAdjustDate(EnjoyUtils.currDateThai());
			adjustStockBean.setProductCode(productCode);
			adjustStockBean.setQuanOld(quanOld);
			adjustStockBean.setQuanNew(quantity);
			adjustStockBean.setRemark(remark);
			adjustStockBean.setTin(tin);
			
			this.dao.insertAdjustHistory(adjustStockBean);
			
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
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		AdjustStockBean 				adjustStockBean					= null;
		List<AdjustStockBean> 			adjustHistoryListList			= null;
		String							productName						= null;
		ProductmasterBean				productmasterBean				= null;
		JSONObject 						obj 							= null;
		int								limitAdjustHistoryOrder			= 0;
		int								lastOrder						= 0;
		String							tin								= "";
		ProductquantityBean				productquantityBean				= null;
		String							inventory						= "";

		try{
			obj 						= new JSONObject();
			productName					= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
			tin							= this.userBean.getTin();
			productmasterBean			= this.productDetailsDao.getProductDetailByName(productName, tin);
			adjustStockBean				= new AdjustStockBean();
			
			if(productmasterBean==null){
				throw new EnjoyException("ระบุชื่อสินค้าผิดกรุณาตรวจสอบ");
			}
			
			adjustStockBean.setTin(tin);
			adjustStockBean.setProductCode(productmasterBean.getProductCode());
			adjustStockBean.setProductName(productmasterBean.getProductName());
			
			productquantityBean = new ProductquantityBean();
		    productquantityBean.setTin(tin);
		    productquantityBean.setProductCode(productmasterBean.getProductCode());
		    inventory = EnjoyUtils.convertFloatToDisplay(this.productquantityDao.getProductquantity(productquantityBean), 2);
			
			adjustStockBean.setQuanOld(inventory);
			adjustStockBean.setUnitName(productmasterBean.getUnitName());
			
			this.form.setChk(true);
			
			limitAdjustHistoryOrder = this.dao.checkLimitAdjustHistoryOrder(productmasterBean.getProductCode(), tin, adjustStockBean.getLastOrder());
			adjustHistoryListList	= this.dao.getAdjustHistoryList(adjustStockBean);
			
			if(limitAdjustHistoryOrder > AdjustStockForm.ORDER_LIMIT){
				lastOrder = EnjoyUtils.parseInt(adjustStockBean.getLastOrder()) + AdjustStockForm.ORDER_LIMIT;
				adjustStockBean.setLastOrder(lastOrder);
				this.form.setLimitAdjustHistoryFlag(true);
			}
			
			this.form.setAdjustHistoryListList(adjustHistoryListList);
			this.form.setAdjustStockBean(adjustStockBean);
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSearch is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSearch][End]");
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
	
	private void loadNextRangeOrder(){
		logger.info("[loadNextRangeOrder][Begin]");
		
		String 					lastOrder	 					= null;
		int						limitAdjustHistoryOrder			= 0;
		String					productCode						= null;
		JSONObject 				obj 							= null;
		JSONObject 				objDetail 						= null;
		JSONArray 				jSONArray 						= null;
		int						newLastOrder					= 0;
		List<AdjustStockBean> 	adjustHistoryList				= null;
		String					tin								= null;
		
		try{
			obj 				= new JSONObject();
			jSONArray 			= new JSONArray();
			lastOrder 			= EnjoyUtils.nullToStr(this.request.getParameter("lastOrder"));
			productCode 		= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
			tin 				= this.userBean.getTin();
			
			limitAdjustHistoryOrder 	= this.dao.checkLimitAdjustHistoryOrder(productCode, tin, EnjoyUtils.parseInt(lastOrder));
			adjustHistoryList			= this.dao.getAdjustHistoryList(this.form.getAdjustStockBean());
			
			for(AdjustStockBean bean:adjustHistoryList){
				objDetail = new JSONObject();
				
				objDetail.put("adjustDate"	, bean.getAdjustDate());
				objDetail.put("quanOld"		, bean.getQuanOld());
				objDetail.put("quanNew"		, bean.getQuanNew());
				objDetail.put("remark"		, bean.getRemark());
				
				jSONArray.add(objDetail);
			}
			
			if(limitAdjustHistoryOrder > AdjustStockForm.ORDER_LIMIT){
				newLastOrder = EnjoyUtils.parseInt(lastOrder) + AdjustStockForm.ORDER_LIMIT;
				this.form.getAdjustStockBean().setLastOrder(newLastOrder);
				this.form.setLimitAdjustHistoryFlag(true);
				
				obj.put("limitAdjustHistoryFlag"	, true);
			}else{
				this.form.setLimitAdjustHistoryFlag(false);
				
				obj.put("limitAdjustHistoryFlag"	, false);
			}
			
			obj.put("adjustHistoryList"	, jSONArray);
			obj.put("lastOrder"			, newLastOrder);
			obj.put(STATUS				, SUCCESS);
			
		}catch(Exception e){
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"loadNextRangeOrder is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[loadNextRangeOrder][End]");
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
