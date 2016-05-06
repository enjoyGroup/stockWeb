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
import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.bean.ComparePriceBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.dao.ComparePriceDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ComparePriceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ComparePriceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ComparePriceServlet.class);
	
    private static final String 	FORM_NAME 				= "comparePriceForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ComparePriceDao					dao							= null;
    private CompanyVendorDao				companyVendorDao			= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private ComparePriceForm				form						= null;
    
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
             this.form               		= (ComparePriceForm)session.getAttribute(FORM_NAME);
             this.dao						= new ComparePriceDao();
             this.companyVendorDao			= new CompanyVendorDao();
             this.productDetailsDao			= new ProductDetailsDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new")) this.form = new ComparePriceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				request.setAttribute("target", Constants.PAGE_URL +"/ComparePriceScn.jsp");
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
 			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}else if(pageAction.equals("getProductDetailByName")){
				this.getProductDetailByName();
			}else if(pageAction.equals("getVendorNameList")){
				this.getVendorNameList();
			}else if(pageAction.equals("branchNameList")){
				this.branchNameList();
			}else if(pageAction.equals("getCompanyVendorDetail")){
				this.getCompanyVendorDetail();
			}else if(pageAction.equals("lookup")){
				this.onSearch();
				this.form.setPageAction(pageAction);
				request.setAttribute("target", Constants.PAGE_URL +"/ComparePriceScn.jsp");
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
		ComparePriceBean			comparePriceBean			= null;
		String						newSeq						= null;
		
		try{
			
			comparePriceBean 		= new ComparePriceBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 		:: " + newSeq);
			
			comparePriceBean.setRowStatus(ComparePriceForm.NEW);
			comparePriceBean.setSeq(newSeq);
			
			this.form.getComparePriceList().add(comparePriceBean);
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
		
		JSONObject 						obj 					= null;
		String 							productCode				= null;
		String 							vendorCode				= null;
		String 							vendorName				= null;
		String							branchName				= null;
		String 							quantity				= null;
		String 							price					= null;
		String 							seq						= null;
		List<ComparePriceBean> 			comparePriceList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productCode 			= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			vendorCode 				= EnjoyUtil.nullToStr(request.getParameter("vendorCode"));
			vendorName 				= EnjoyUtil.nullToStr(request.getParameter("vendorName"));
			branchName 				= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			price 					= EnjoyUtil.nullToStr(request.getParameter("price"));
			comparePriceList		= this.form.getComparePriceList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] productCode 	:: " + productCode);
			logger.info("[updateRecord] vendorCode 		:: " + vendorCode);
			logger.info("[updateRecord] vendorName 		:: " + vendorName);
			logger.info("[updateRecord] branchName 		:: " + branchName);
			logger.info("[updateRecord] quantity 		:: " + quantity);
			logger.info("[updateRecord] price 			:: " + price);
			
			for(ComparePriceBean bean:comparePriceList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductCode		(productCode);
					bean.setVendorCode		(vendorCode);
					bean.setVendorName		(vendorName);
					bean.setBranchName		(branchName);
					bean.setQuantity		(quantity);
					bean.setPrice			(price);
					
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
		
		JSONObject 							obj 					= null;
		String 								seq						= null;
		List<ComparePriceBean> 				comparePriceList		= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			comparePriceList		= this.form.getComparePriceList();
			
			for(int i=0;i<comparePriceList.size();i++){
				ComparePriceBean bean = comparePriceList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(!bean.getRowStatus().equals(ComparePriceForm.NEW)){
						bean.setRowStatus(ComparePriceForm.DEL);
					}else{
						comparePriceList.remove(i);
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
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		SessionFactory 						sessionFactory				= null;
		Session 							session						= null;
		JSONObject 							obj 						= null;
		List<ComparePriceBean> 				comparePriceList			= null;
		int									seq							= 1;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			comparePriceList			= this.form.getComparePriceList();
			
			session.beginTransaction();
			
			this.dao.deleteCompareprice(session, this.form.getProductCode());
			
			for(ComparePriceBean bean:comparePriceList){
				if(!bean.getRowStatus().equals(ComparePriceForm.DEL)){
					bean.setSeq(EnjoyUtils.nullToStr(seq));
					this.dao.insertCompareprice(session, bean);
					seq++;
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
		
		ComparePriceBean 					comparePriceBean		= null;
		SessionFactory 						sessionFactory			= null;
		Session 							session					= null;
		List<ComparePriceBean> 				comparePriceList		= null;
		String								productCode				= null;
		String								productName				= null;
		JSONObject 							obj 					= null;
		String								seqTemp					= null;

		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();			
			obj 						= new JSONObject();
			productCode					= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
			productName					= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
			
			logger.info("[onSearch] productCode 	:: " + productCode);
			logger.info("[onSearch] productName 	:: " + productName);
			
			this.form.setProductCode(productCode);
			this.form.setProductName(productName);
			this.form.setChk(true);
			
			session.beginTransaction();
			
			comparePriceBean				= new ComparePriceBean();
			comparePriceBean.setProductCode(productCode);
			
			
			comparePriceList	 		= this.dao.searchByCriteria(session, comparePriceBean);
			
			for(ComparePriceBean bean:comparePriceList){
				seqTemp = bean.getSeq();
			}
			
			this.form.setComparePriceList(comparePriceList);
			
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
	   
	private void getProductNameList(){
	   logger.info("[getProductNameList][Begin]");
	   
	   String							productName				= null;
	   List<ComboBean> 					list 					= null;
	   JSONArray 						jSONArray 				= null;
	   JSONObject 						objDetail 				= null;
	
	   try{
		   jSONArray 				= new JSONArray();
		   productName				= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   
		   logger.info("[getProductNameList] productName 				:: " + productName);
		   
		   list 		= this.productDetailsDao.productNameList(productName, null, null, true);
		   
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
	
	private void getVendorNameList(){
		logger.info("[getVendorNameList][Begin]");
	   
	   String							vendorName				= null;
	   List<ComboBean> 					list 					= null;
	   JSONArray 						jSONArray 				= null;
	   JSONObject 						objDetail 				= null;
	
	   try{
		   jSONArray 				= new JSONArray();
		   vendorName				= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
		   
		   logger.info("[getVendorNameList] vendorName 				:: " + vendorName);
		   
		   list 		= this.companyVendorDao.vendorNameList(vendorName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.error(e);
	   }finally{
		   logger.info("[getVendorNameList][End]");
	   }
	}
	
	private void branchNameList(){
		logger.info("[branchNameList][Begin]");
	   
		String							vendorName				= null;
		String							branchName				= null;
		List<ComboBean> 				list 					= null;
		JSONArray 						jSONArray 				= null;
		JSONObject 						objDetail 				= null;
	
		try{
			jSONArray 				= new JSONArray();
			vendorName				= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
			branchName				= EnjoyUtils.nullToStr(this.request.getParameter("branchName"));
		   
			logger.info("[branchNameList] vendorName 				:: " + vendorName);
			logger.info("[branchNameList] branchName 				:: " + branchName);
			
			list 		= this.companyVendorDao.branchNameList(vendorName, branchName);
			
			for(ComboBean bean:list){
				objDetail 		= new JSONObject();
				
				objDetail.put("id"			,bean.getCode());
				objDetail.put("value"		,bean.getDesc());
				
				jSONArray.add(objDetail);
			}
			
			this.enjoyUtil.writeMSG(jSONArray.toString());
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[branchNameList] " + e.getMessage());
		}finally{
			logger.info("[branchNameList][End]");
		}
	}
	
	private void getProductDetailByName(){
	   logger.info("[getProductDetailByName][Begin]");
	   
	   String							productName				= null;
	   JSONObject 						obj		 				= null;
	   ProductmasterBean				productmasterBean		= null;
	
	   try{
		   obj 				= new JSONObject();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   
		   logger.info("[getProductDetailByName] productName 				:: " + productName);
		   
		   productmasterBean 		= this.productDetailsDao.getProductDetailByName(productName);
		   
		   if(productmasterBean!=null){
			   obj.put("productCode"	,productmasterBean.getProductCode());
//			   obj.put("productName"	,productmasterBean.getProductName());
//			   obj.put("inventory"		,productmasterBean.getQuantity());
//			   obj.put("unitCode"		,productmasterBean.getUnitCode());
//			   obj.put("unitName"		,productmasterBean.getUnitName());
		   }else{
			   obj.put("productCode"	,"");
//			   obj.put("productName"	,"");
//			   obj.put("inventory"		,"");
//			   obj.put("unitCode"		,"");
//			   obj.put("unitName"		,"");
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
	
	private void getCompanyVendorDetail(){
	   logger.info("[getCompanyVendorDetail][Begin]");
	   
	   String							vendorName				= null;
	   String							branchName				= null;
	   JSONObject 						obj		 				= null;
	   List<CompanyVendorBean>			companyVendorList		= null;
	   CompanyVendorBean				companyVendorBean		= null;
	
	   try{
		   obj 				= new JSONObject();
		   vendorName		= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
		   branchName		= EnjoyUtils.nullToStr(this.request.getParameter("branchName"));
		   
		   logger.info("[getCompanyVendorDetail] vendorName 				:: " + vendorName);
		   logger.info("[getCompanyVendorDetail] branchName 				:: " + branchName);
		   
		   companyVendorList 		= this.companyVendorDao.getCompanyVendorByName(vendorName, branchName);
		   
		   if(companyVendorList.size()==1){
			   
			   companyVendorBean = companyVendorList.get(0);
			   
			   obj.put("vendorCode", 		companyVendorBean.getVendorCode());
			   obj.put("vendorName", 		companyVendorBean.getVendorName());
			   obj.put("branchName", 		companyVendorBean.getBranchName());
			   obj.put("buildingName", 		companyVendorBean.getBuildingName());
			   obj.put("houseNumber", 		companyVendorBean.getHouseNumber());
			   obj.put("mooNumber", 		companyVendorBean.getMooNumber());
			   obj.put("soiName", 			companyVendorBean.getSoiName());
			   obj.put("streetName", 		companyVendorBean.getStreetName());
			   obj.put("provinceCode", 		companyVendorBean.getProvinceName());
			   obj.put("districtCode", 		companyVendorBean.getProvinceName());
			   obj.put("subdistrictCode", 	companyVendorBean.getProvinceName());
			   obj.put("subdistrictName", 	companyVendorBean.getSubdistrictName());
			   obj.put("districtName", 		companyVendorBean.getDistrictName());
			   obj.put("provinceName", 		companyVendorBean.getProvinceName());
			   obj.put("postcode", 			companyVendorBean.getPostCode());
			   obj.put("tel", 				companyVendorBean.getTel());
			   obj.put("fax", 				companyVendorBean.getFax());
			   obj.put("email", 			companyVendorBean.getEmail());
			   obj.put("remark", 			companyVendorBean.getRemark());
		   }else{
			   obj.put("vendorCode", 		"");
			   obj.put("vendorName", 		vendorName);
			   obj.put("branchName", 		branchName);
			   obj.put("buildingName", 		"");
			   obj.put("houseNumber", 		"");
			   obj.put("mooNumber", 		"");
			   obj.put("soiName", 			"");
			   obj.put("streetName", 		"");
			   obj.put("provinceCode", 		"");
			   obj.put("districtCode", 		"");
			   obj.put("subdistrictCode", 	"");
			   obj.put("subdistrictName", 	"");
			   obj.put("districtName", 		"");
			   obj.put("provinceName", 		"");
			   obj.put("postcode", 			"");
			   obj.put("tel", 				"");
			   obj.put("fax", 				"");
			   obj.put("email", 			"");
			   obj.put("remark", 			"");
		   }
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getCompanyVendorDetail is error");
		   e.printStackTrace();
		   logger.info("[getCompanyVendorDetail] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getCompanyVendorDetail][End]");
	   }
	}

}
