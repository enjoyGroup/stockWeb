package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.Date;
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
import th.go.stock.app.enjoy.bean.ReciveOrdeDetailBean;
import th.go.stock.app.enjoy.bean.ReciveOrderMasterBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.dao.ComparePriceDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ReciveStockDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.ReciveStockMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class ReciveStockMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ReciveStockMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "reciveStockMaintananceForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private ReciveStockDao					dao							= null;
    private ReciveStockMaintananceForm		form						= null;
    private CompanyVendorDao				companyVendorDao			= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private ComparePriceDao					comparePriceDao				= null;
    
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
             this.form               	= (ReciveStockMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new ReciveStockDao();
             this.companyVendorDao		= new CompanyVendorDao();
             this.productDetailsDao		= new ProductDetailsDao();
             this.comparePriceDao		= new ComparePriceDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new ReciveStockMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/ReciveStockMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/ReciveStockMaintananceScn.jsp");
 			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals("newRecord")){
				this.newRecord();
			}else if(pageAction.equals("updateRecord")){
				this.updateRecord();
			}else if(pageAction.equals("deleteRecord")){
				this.deleteRecord();
			}else if(pageAction.equals("getProductNameList")){
				this.getProductNameList();
			}else if(pageAction.equals("getProductDetailByName")){
				this.getProductDetailByName();
			}else if(pageAction.equals("getVendorNameList")){
				this.getVendorNameList();
			}else if(pageAction.equals("getCompanyVendorDetail")){
				this.getCompanyVendorDetail();
			}else if(pageAction.equals("ctrlCreditDay")){
				this.ctrlCreditDay();
			}else if(pageAction.equals("branchNameList")){
				this.branchNameList();
			}else if(pageAction.equals("getPrice")){
				this.getPrice();
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
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		
		ReciveOrderMasterBean 		reciveOrderMasterBean = null;;
		
		try{
			
			this.setRefference();
			this.form.setTitlePage("เพิ่มรายการในสต๊อกสินค้า");
			
			reciveOrderMasterBean = this.form.getReciveOrderMasterBean();
			
			/*Begin set default value*/
			reciveOrderMasterBean.setReciveStatus("1");//สร้างใบสั่งซื้อ
			reciveOrderMasterBean.setReciveType("M");//เงินสด
			reciveOrderMasterBean.setPriceType("V");//มี VAT
			/*End set default value*/
			
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
		
		List<ComboBean>			statusCombo 		= null;
		
		try{
			
			statusCombo = this.dao.getRefReciveOrderStatusCombo();
			
			this.form.setStatusCombo(statusCombo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setStatusCombo is error");
		}finally{
			logger.info("[setStatusCombo][End]");
		}
	}
	
	private void getDetail(String reciveNo) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		ReciveOrderMasterBean 			reciveOrderMasterBean		= null;
		ReciveOrderMasterBean 			reciveOrderMasterBeanDb		= null;
		SessionFactory 					sessionFactory				= null;
		Session 						session						= null;
		String							seqTemp						= null;
		CompanyVendorBean 				companyVendorBean			= null;
		CompanyVendorBean 				companyVendorBeanDb			= null;
		ReciveOrdeDetailBean			reciveOrdeDetailBean		= null;
		List<ReciveOrdeDetailBean> 		reciveOrdeDetailList		= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			reciveNo					= reciveNo.equals("0")?EnjoyUtil.nullToStr(request.getParameter("reciveNo")):reciveNo;
			
			session.beginTransaction();
			
			logger.info("[getDetail] reciveNo :: " + reciveNo);
			
			reciveOrderMasterBean = new ReciveOrderMasterBean();
			reciveOrderMasterBean.setReciveNo(reciveNo);
			
			reciveOrderMasterBeanDb				= this.dao.getReciveOrderMaster(session, reciveOrderMasterBean);
			
			this.form.setPageMode(ReciveStockMaintananceForm.EDIT);
			this.form.setTitlePage("แก้ไขรายการในสต๊อกสินค้า");
			
			logger.info("[getDetail] reciveOrderMasterBeanDb :: " + reciveOrderMasterBeanDb);
			
			if(reciveOrderMasterBeanDb!=null){
				reciveOrdeDetailBean = new ReciveOrdeDetailBean();
				
				reciveOrdeDetailBean.setReciveNo(reciveNo);
				reciveOrdeDetailList = this.dao.getReciveOrdeDetailList(session, reciveOrdeDetailBean);
				
				for(ReciveOrdeDetailBean bean:reciveOrdeDetailList){
					seqTemp = bean.getSeq();
				}
				
				this.form.setReciveOrdeDetailList(reciveOrdeDetailList);
				
				if(seqTemp!=null){
					this.form.setSeqTemp(seqTemp);
				}
				companyVendorBean = new CompanyVendorBean();
				companyVendorBean.setVendorCode(reciveOrderMasterBeanDb.getVendorCode());
				companyVendorBeanDb					= this.companyVendorDao.getCompanyVendor(session, companyVendorBean);
				
				this.form.setCompanyVendorBean		(companyVendorBeanDb);
				this.form.setReciveOrderMasterBean	(reciveOrderMasterBeanDb);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียด Order ใบสั่งของ (Recive Stock) ");
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			session.close();
			
			this.setRefference();
			
			sessionFactory	= null;
			session			= null;
			logger.info("[getDetail][End]");
		}
		
	}
	   
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String						pageMode					= null;
		String				 		reciveNo					= null;
		String				 		reciveDate					= null;
		String				 		reciveType					= null;
		String				 		creditDay					= null;
		String				 		creditExpire				= null;
		String				 		vendorCode					= null;
		String				 		branchName					= null;
		String				 		billNo						= null;
		String				 		priceType					= null;
		String				 		reciveStatus				= null;
		String				 		reciveAmount				= null;
		String				 		reciveDiscount				= null;
		String				 		reciveVat					= null;
		String				 		reciveTotal					= null;
		String						currReciveStatus			= null;
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		JSONObject 					obj 						= null;
		ReciveOrderMasterBean  		reciveOrderMasterBean		= null;
		List<ReciveOrdeDetailBean> 	reciveOrdeDetailList		= null;
		ReciveOrdeDetailBean		bean						= null;
		int							seqDb						= 1;
		ProductmasterBean 			productmasterBean 			= null;
		double						quantity					= 0;
		ComparePriceBean 			comparePriceBean 			= null;
		int							cou							= 0;
		int							comparePriceSeq				= 0;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			reciveNo 					= EnjoyUtil.nullToStr(request.getParameter("reciveNo"));
			reciveDate 					= EnjoyUtil.nullToStr(request.getParameter("reciveDate"));
			reciveType 					= EnjoyUtil.nullToStr(request.getParameter("reciveType"));
			creditDay 					= EnjoyUtil.nullToStr(request.getParameter("creditDay"));
			creditExpire 				= EnjoyUtil.nullToStr(request.getParameter("creditExpire"));
			vendorCode 					= EnjoyUtil.nullToStr(request.getParameter("vendorCode"));
			branchName 					= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			billNo 						= EnjoyUtil.nullToStr(request.getParameter("billNo"));
			priceType 					= EnjoyUtil.nullToStr(request.getParameter("priceType"));
			reciveStatus 				= EnjoyUtil.nullToStr(request.getParameter("reciveStatus"));
			reciveAmount 				= EnjoyUtil.nullToStr(request.getParameter("reciveAmount"));
			reciveDiscount 				= EnjoyUtil.nullToStr(request.getParameter("reciveDiscount"));
			reciveVat 					= EnjoyUtil.nullToStr(request.getParameter("reciveVat"));
			reciveTotal 				= EnjoyUtil.nullToStr(request.getParameter("reciveTotal"));
			currReciveStatus 			= EnjoyUtil.nullToStr(request.getParameter("currReciveStatus"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			reciveOrderMasterBean		= new ReciveOrderMasterBean();
			reciveOrdeDetailList		= this.form.getReciveOrdeDetailList();
			
			session.beginTransaction();
			
			/*Begin Section รายละเอียดใบสั่งซื้อ*/
			if(pageMode.equals(ReciveStockMaintananceForm.NEW)){
				reciveNo = this.dao.genReciveNo();
				
				reciveOrderMasterBean.setReciveNo				(reciveNo);
				reciveOrderMasterBean.setReciveDate				(EnjoyUtils.dateThaiToDb(reciveDate));
				reciveOrderMasterBean.setReciveType				(reciveType);
				reciveOrderMasterBean.setCreditDay				(creditDay);
				reciveOrderMasterBean.setCreditExpire			(EnjoyUtils.dateThaiToDb(creditExpire));
				reciveOrderMasterBean.setVendorCode				(vendorCode);
				reciveOrderMasterBean.setBranchName				(branchName);
				reciveOrderMasterBean.setBillNo					(billNo);
				reciveOrderMasterBean.setPriceType				(priceType);
				reciveOrderMasterBean.setReciveStatus			("1");
				reciveOrderMasterBean.setUserUniqueId			(String.valueOf(this.userBean.getUserUniqueId()));
				reciveOrderMasterBean.setReciveAmount			(reciveAmount);
				reciveOrderMasterBean.setReciveDiscount			(reciveDiscount);
				reciveOrderMasterBean.setReciveVat				(reciveVat);
				reciveOrderMasterBean.setReciveTotal			(reciveTotal);
				
				this.dao.insertReciveordermaster(session, reciveOrderMasterBean);
			}else{
				
				if(reciveStatus.equals("") || reciveStatus.equals("1") || reciveStatus.equals("2")){
					reciveOrderMasterBean.setReciveNo				(reciveNo);
					reciveOrderMasterBean.setReciveDate				(EnjoyUtils.dateThaiToDb(reciveDate));
					reciveOrderMasterBean.setReciveType				(reciveType);
					reciveOrderMasterBean.setCreditDay				(creditDay);
					reciveOrderMasterBean.setCreditExpire			(EnjoyUtils.dateThaiToDb(creditExpire));
					reciveOrderMasterBean.setVendorCode				(vendorCode);
					reciveOrderMasterBean.setBranchName				(branchName);
					reciveOrderMasterBean.setBillNo					(billNo);
					reciveOrderMasterBean.setPriceType				(priceType);
					reciveOrderMasterBean.setReciveStatus			(reciveStatus);
					reciveOrderMasterBean.setUserUniqueId			(String.valueOf(this.userBean.getUserUniqueId()));
					reciveOrderMasterBean.setReciveAmount			(reciveAmount);
					reciveOrderMasterBean.setReciveDiscount			(reciveDiscount);
					reciveOrderMasterBean.setReciveVat				(reciveVat);
					reciveOrderMasterBean.setReciveTotal			(reciveTotal);
					
					this.dao.updateReciveOrderMaster(session, reciveOrderMasterBean);
				}else{
					reciveOrderMasterBean.setReciveNo				(reciveNo);
					reciveOrderMasterBean.setReciveDate				(EnjoyUtils.dateThaiToDb(reciveDate));
					reciveOrderMasterBean.setUserUniqueId			(String.valueOf(this.userBean.getUserUniqueId()));
					reciveOrderMasterBean.setReciveStatus			(reciveStatus);
					
					this.dao.updateReciveOrderMasterSpecial(session, reciveOrderMasterBean);
				}
			}
			/*End Section รายละเอียดใบสั่งซื้อ*/
			
			/*Begin Section รายการสินค้า*/
			if(reciveStatus.equals("") || reciveStatus.equals("1") || reciveStatus.equals("2")){
				this.dao.deleteReciveordedetail(session, reciveNo);
				
				for(int i=0;i<reciveOrdeDetailList.size();i++){
					bean = reciveOrdeDetailList.get(i);
					if(!bean.getRowStatus().equals(ReciveStockMaintananceForm.DEL)){
						bean.setReciveNo(reciveNo);
						bean.setSeqDb(String.valueOf(seqDb));
						this.dao.insertReciveOrdeDetail(session, bean);
						seqDb++;
						
						/*Begin Update เปรียบเทียบราคา*/
						cou = this.comparePriceDao.couVenderInThisProduct(bean.getProductCode(), vendorCode);
						
						if(cou==0){
							comparePriceBean = new ComparePriceBean();
							comparePriceSeq = this.comparePriceDao.getNewSeqInThisProduct(bean.getProductCode());
							
							comparePriceBean.setProductCode	(bean.getProductCode());
							comparePriceBean.setSeq			(EnjoyUtil.nullToStr(comparePriceSeq));
							comparePriceBean.setVendorCode	(vendorCode);
							comparePriceBean.setQuantity	(bean.getQuantity());
							comparePriceBean.setPrice		(bean.getPrice());
							
							this.comparePriceDao.insertCompareprice(session, comparePriceBean);
						}
						/*End Update เปรียบเทียบราคา*/
						
					}
				}
			}else{
				bean = new ReciveOrdeDetailBean();
				
				bean.setReciveNo(reciveNo);
				reciveOrdeDetailList = this.dao.getReciveOrdeDetailList(session, bean);
				
				for(ReciveOrdeDetailBean beanTemp:reciveOrdeDetailList){
					if(reciveStatus.equals("3")){
						productmasterBean 	= new ProductmasterBean();
						quantity			=  EnjoyUtils.parseDouble(this.productDetailsDao.getInventory(beanTemp.getProductCode())) +  EnjoyUtils.parseDouble(beanTemp.getQuantity());
						
						productmasterBean.setProductCode(beanTemp.getProductCode());
						productmasterBean.setQuantity(String.valueOf(quantity));
						
						this.productDetailsDao.updateProductQuantity(session, productmasterBean);
						
						
					}else if(currReciveStatus.equals("3") && reciveStatus.equals("4")){
						productmasterBean 	= new ProductmasterBean();
						quantity			=  EnjoyUtils.parseDouble(this.productDetailsDao.getInventory(beanTemp.getProductCode())) -  EnjoyUtils.parseDouble(beanTemp.getQuantity());
						
						productmasterBean.setProductCode(beanTemp.getProductCode());
						productmasterBean.setQuantity(String.valueOf(quantity));
						
						this.productDetailsDao.updateProductQuantity(session, productmasterBean);
					}
				}
			}
			/*End Section รายการสินค้า*/
			
			session.getTransaction().commit();
			
			obj.put(STATUS, 			SUCCESS);
			obj.put("reciveNo", 		reciveNo);
			
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
		
		JSONObject 				obj 					= null;
		ReciveOrdeDetailBean	reciveOrdeDetailBean	= null;
		String					newSeq					= null;
		
		try{
			
			reciveOrdeDetailBean 	= new ReciveOrdeDetailBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			reciveOrdeDetailBean.setRowStatus(ReciveStockMaintananceForm.NEW);
			reciveOrdeDetailBean.setSeq(newSeq);
			
			this.form.getReciveOrdeDetailList().add(reciveOrdeDetailBean);
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
		String 							productName				= null;
		String 							inventory				= null;
		String 							quantity				= null;
		String 							unitCode				= null;
		String 							unitName				= null;
		String 							price					= null;
		String 							discountRate			= null;
		String 							costPrice				= null;
		String 							seq						= null;
		List<ReciveOrdeDetailBean> 		reciveOrdeDetailList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productCode 			= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			productName 			= EnjoyUtil.nullToStr(request.getParameter("productName"));
			inventory 				= EnjoyUtil.nullToStr(request.getParameter("inventory"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			unitCode 				= EnjoyUtil.nullToStr(request.getParameter("unitCode"));
			unitName 				= EnjoyUtil.nullToStr(request.getParameter("unitName"));
			price 					= EnjoyUtil.nullToStr(request.getParameter("price"));
			discountRate 			= EnjoyUtil.nullToStr(request.getParameter("discountRate"));
			costPrice 				= EnjoyUtil.nullToStr(request.getParameter("costPrice"));
			reciveOrdeDetailList	= this.form.getReciveOrdeDetailList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] productCode 	:: " + productCode);
			logger.info("[updateRecord] productName 	:: " + productName);
			logger.info("[updateRecord] inventory 		:: " + inventory);
			logger.info("[updateRecord] quantity 		:: " + quantity);
			logger.info("[updateRecord] unitCode 		:: " + unitCode);
			logger.info("[updateRecord] unitName 		:: " + unitName);
			logger.info("[updateRecord] price 			:: " + price);
			logger.info("[updateRecord] discountRate 	:: " + discountRate);
			logger.info("[updateRecord] costPrice 		:: " + costPrice);
			
			for(ReciveOrdeDetailBean bean:reciveOrdeDetailList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductCode		(productCode);
					bean.setProductName		(productName);
					bean.setInventory		(inventory);
					bean.setQuantity		(quantity);
					bean.setUnitCode		(unitCode);
					bean.setUnitName		(unitName);
					bean.setPrice			(price);
					bean.setDiscountRate	(discountRate);
					bean.setCostPrice		(costPrice);
					
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
		
		JSONObject 						obj 					= null;
		String 							seq						= null;
		List<ReciveOrdeDetailBean> 		reciveOrdeDetailList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			reciveOrdeDetailList	= this.form.getReciveOrdeDetailList();
			
			for(int i=0;i<reciveOrdeDetailList.size();i++){
				ReciveOrdeDetailBean bean = reciveOrdeDetailList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(ReciveStockMaintananceForm.NEW)){
						reciveOrdeDetailList.remove(i);
					}else{
						bean.setRowStatus(ReciveStockMaintananceForm.DEL);
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
	
	private void getProductDetailByName(){
	   logger.info("[getProductDetailByName][Begin]");
	   
	   String							productName				= null;
	   JSONObject 						obj		 				= null;
	   ProductmasterBean				productmasterBean		= null;
	   String							price					= "0.00";
	   ComparePriceBean 				comparePriceBean 		= null;
	   String							vendorCode				= null;
	   String							quantity				= null;
	
	   try{
		   obj 				= new JSONObject();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   vendorCode		= EnjoyUtils.nullToStr(this.request.getParameter("vendorCode"));
		   quantity			= EnjoyUtils.nullToStr(this.request.getParameter("quantity"));
		   
		   logger.info("[getProductDetailByName] productName 				:: " + productName);
		   
		   productmasterBean 		= this.productDetailsDao.getProductDetailByName(productName);
		   
		   if(productmasterBean!=null){
			   comparePriceBean = new ComparePriceBean();
			   comparePriceBean.setProductCode(productmasterBean.getProductCode());
			   comparePriceBean.setVendorCode(vendorCode);
			   comparePriceBean.setQuantity(quantity);
			   
			   price = this.comparePriceDao.getPrice(comparePriceBean);
			   
			   obj.put("productCode"	,productmasterBean.getProductCode());
			   obj.put("productName"	,productmasterBean.getProductName());
			   obj.put("price"			,price);
			   obj.put("inventory"		,productmasterBean.getQuantity());
			   obj.put("unitCode"		,productmasterBean.getUnitCode());
			   obj.put("unitName"		,productmasterBean.getUnitName());
		   }else{
			   obj.put("productCode"	,"");
			   obj.put("productName"	,"");
			   obj.put("price"			,price);
			   obj.put("inventory"		,"");
			   obj.put("unitCode"		,"");
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
		   logger.info("[getVendorNameList] " + e.getMessage());
	   }finally{
		   logger.info("[getVendorNameList][End]");
	   }
	}
	
	private void branchNameList(){
		logger.info("[branchNameList][Begin]");
	   
	   String							vendorName				= null;
	   String							branchName				= null;
	   List<ComboBean> 					list 					= null;
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
			   /*obj.put("tin", 				companyVendorBean.getTin());
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
			   obj.put("remark", 			companyVendorBean.getRemark());*/
		   }else{
			   obj.put("vendorCode", 		"");
			   obj.put("vendorName", 		vendorName);
			   obj.put("branchName", 		branchName);
			   /*obj.put("tin", 				"");
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
			   obj.put("remark", 			"");*/
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
	   
	private void ctrlCreditDay(){
	   logger.info("[ctrlCreditDay][Begin]");
	   
	   String							creditDay				= null;
	   String							creditExpire			= null;
	   JSONObject 						obj		 				= null;
	
	   try{
		   obj 				= new JSONObject();
		   creditDay		= EnjoyUtils.nullToStr(this.request.getParameter("creditDay"));
		   
		   logger.info("[ctrlCreditDay] creditDay 				:: " + creditDay);
		   
		   creditExpire 		= EnjoyUtils.increaseDate(new Date(), EnjoyUtil.parseInt(creditDay));
		   
		   obj.put("creditExpire", 		creditExpire);
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"ctrlCreditDay is error");
		   e.printStackTrace();
		   logger.info("[ctrlCreditDay] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[ctrlCreditDay][End]");
	   }
	}
	
	private void getPrice() {
		logger.info("[getPrice][Begin]");

		String		 		productCode 		= null;
		String				quantity			= null;
		JSONObject 			obj		 			= null;
		String				price				= "0.00";
		ComparePriceBean 	comparePriceBean 	= null;
		String				vendorCode			= null;
		
		try {
			obj 			= new JSONObject();
			productCode 	= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
			quantity 		= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			vendorCode 		= EnjoyUtil.nullToStr(request.getParameter("vendorCode"));
			
			comparePriceBean = new ComparePriceBean();
			comparePriceBean.setProductCode(productCode);
			comparePriceBean.setQuantity(quantity);
			comparePriceBean.setVendorCode(vendorCode);
			
			price = this.comparePriceDao.getPrice(comparePriceBean);
			
			obj.put("price"	,price);
			obj.put(STATUS		,SUCCESS);
			
		} catch (Exception e) {
			obj.put(STATUS, ERROR);
			obj.put(ERR_MSG, "getPrice is error");
			e.printStackTrace();
			logger.info("[getPrice] " + e.getMessage());
		} finally {
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[getPrice][End]");
		}
	}
	 	
	
	
}

