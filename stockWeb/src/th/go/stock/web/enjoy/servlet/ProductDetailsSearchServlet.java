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
import th.go.stock.app.enjoy.bean.ProductDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.ManageProductGroupDao;
import th.go.stock.app.enjoy.dao.ManageProductTypeDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ProductDetailsSearchForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ProductDetailsSearchServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ProductDetailsSearchServlet.class);
	
    private static final String FORM_NAME = "productDetailsSearchForm";
    
    private EnjoyUtil               	enjoyUtil                   = null;
    private HttpServletRequest          request                     = null;
    private HttpServletResponse         response                    = null;
    private HttpSession                 session                     = null;
    private UserDetailsBean             userBean                    = null;
    private ProductDetailsDao			dao							= null;
    private ManageProductTypeDao		productTypeDao				= null;
    private ManageProductGroupDao		productGroupDao				= null;
    private ProductDetailsSearchForm	form						= null;
    
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
             this.form               	= (ProductDetailsSearchForm) session.getAttribute(FORM_NAME);
             this.dao					= new ProductDetailsDao();
             this.productTypeDao		= new ManageProductTypeDao();
             this.productGroupDao		= new ManageProductGroupDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ProductDetailsSearchForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
				request.setAttribute("target", Constants.PAGE_URL +"/ProductDetailsSearchScn.jsp");
 			}else if(pageAction.equals("search")){
 				this.onSearch();
 			}else if(pageAction.equals("getPage")){
				this.lp_getPage();
			}else if(pageAction.equals("getProductTypeNameList")){
				this.getProductTypeNameList();
			}else if(pageAction.equals("getProductGroupNameList")){
				this.getProductGroupNameList();
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
			this.form.setTitlePage("เงื่อนไขค้นหารายละเอียดสินค้า");			
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
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setStatusCombo() throws EnjoyException{
		
		logger.info("[setStatusCombo][Begin]");
		
		List<ComboBean>			statusCombo = null;
		
		try{
			
			statusCombo = new ArrayList<ComboBean>();
			
			statusCombo.add(new ComboBean(""	, "กรุณาระบุ"));
			statusCombo.add(new ComboBean("A"	, "ใช้งานได้อยู่"));
			statusCombo.add(new ComboBean("C"	, "ยกเลิกการใช้งาน"));
			
			this.form.setStatusCombo(statusCombo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setStatusCombo is error");
		}finally{
			logger.info("[setStatusCombo][End]");
		}
	}
		
	
	private void onSearch() throws EnjoyException{
		logger.info("[onSearch][Begin]");
		
		ProductDetailsBean 			productDetailsBean	= null;
		SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		List<ProductDetailsBean> 	dataList 			= null;
		int							cou					= 0;
		int							pageNum				= 1;
        int							totalPage			= 0;
        int							totalRs				= 0;
        List<ProductDetailsBean> 	list 				= new ArrayList<ProductDetailsBean>();
        List<ProductDetailsBean> 	listTemp 			= new ArrayList<ProductDetailsBean>();
        HashMap						hashTable			= new HashMap();

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			session.beginTransaction();
			
			productDetailsBean				= new ProductDetailsBean();
			
			productDetailsBean.setProductTypeName		(EnjoyUtils.nullToStr(this.request.getParameter("productTypeName")));
			productDetailsBean.setProductGroupName		(EnjoyUtils.nullToStr(this.request.getParameter("productGroupName")));
			productDetailsBean.setProductName			(EnjoyUtils.nullToStr(this.request.getParameter("productName")));
			productDetailsBean.setProductStatus			(EnjoyUtils.nullToStr(this.request.getParameter("productStatus")));
			
			this.form.setProductDetailsBean(productDetailsBean);
			
			dataList	 		= this.dao.searchByCriteria(session, productDetailsBean);
			
			if(dataList.size() > 0){				
				
				hashTable.put(pageNum, list);
				for(ProductDetailsBean bean:dataList){
					if(cou==10){
			    		cou 		= 0;
			    		list 		= new ArrayList<ProductDetailsBean>();
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
				
			    listTemp = (List<ProductDetailsBean>) this.form.getHashTable().get(this.form.getPageNum());
			    
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
	   List<ProductDetailsBean> 		dataList 			= new ArrayList<ProductDetailsBean>();
	   
	   try{
		   pageNum					= Integer.parseInt(this.request.getParameter("pageNum"));
		   
		   this.form.setPageNum(pageNum);
		   
		   dataList = (List<ProductDetailsBean>) this.form.getHashTable().get(pageNum);
		   this.form.setDataList(dataList);
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
		   logger.info("[lp_getPage][End]");
	   }
	   
   }
	private void getProductTypeNameList(){
	   logger.info("[getProductTypeNameList][Begin]");
	   
	   String							productTypeName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   jSONArray 				= new JSONArray();
		   
		   logger.info("[getProductTypeNameList] productTypeName 			:: " + productTypeName);
		   
		   
		   list 		= this.productTypeDao.productTypeNameList(productTypeName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductTypeNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductTypeNameList][End]");
	   }
   }
	   
	private void getProductGroupNameList(){
	   logger.info("[getProductGroupNameList][Begin]");
	   
	   String							productTypeName			= null;
	   String							productGroupName		= null;
	   List<ComboBean> 					list 					= null;
	   JSONArray 						jSONArray 				= null;
	   JSONObject 						objDetail 				= null;
    
	   try{
		   jSONArray 				= new JSONArray();
		   productTypeName			= EnjoyUtils.nullToStr(this.request.getParameter("productTypeName"));
		   productGroupName			= EnjoyUtils.nullToStr(this.request.getParameter("productGroupName"));
		   
		   
		   logger.info("[getProductGroupNameList] productTypeName 			:: " + productTypeName);
		   logger.info("[getProductGroupNameList] productGroupName 			:: " + productGroupName);
		   
		   list 		= this.productGroupDao.productGroupNameList(productTypeName, productGroupName, true);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getProductGroupNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getProductGroupNameList][End]");
	   }
}
		
	
	
	
	
}
