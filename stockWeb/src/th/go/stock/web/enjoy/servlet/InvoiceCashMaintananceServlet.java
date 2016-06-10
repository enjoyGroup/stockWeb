package th.go.stock.web.enjoy.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.InvoiceCashDao;
import th.go.stock.app.enjoy.dao.InvoiceCreditDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ProductQuanHistoryDao;
import th.go.stock.app.enjoy.dao.ProductquantityDao;
import th.go.stock.app.enjoy.dao.RelationUserAndCompanyDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.InvoiceCashMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.model.Userprivilege;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class InvoiceCashMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(InvoiceCashMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "invoiceCashMaintananceForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private InvoiceCashDao					invoiceCashDao				= null;
    private InvoiceCreditDao				invoiceCreditDao			= null;
    private InvoiceCashMaintananceForm		form						= null;
    private CustomerDetailsDao				customerDetailsDao			= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private UserDetailsDao					userDetailsDao				= null;
    private CompanyDetailsDao				companyDetailsDao			= null;
    private ProductquantityDao				productquantityDao			= null;
    private RelationUserAndCompanyDao		relationUserAndCompanyDao	= null;
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
             this.form               		= (InvoiceCashMaintananceForm)session.getAttribute(FORM_NAME);
             this.invoiceCashDao			= new InvoiceCashDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
             this.productDetailsDao			= new ProductDetailsDao();
             this.userDetailsDao			= new UserDetailsDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
             this.productquantityDao		= new ProductquantityDao();
             this.relationUserAndCompanyDao = new RelationUserAndCompanyDao();
             this.invoiceCreditDao 			= new InvoiceCreditDao();
             this.productQuanHistoryDao		= new ProductQuanHistoryDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new InvoiceCashMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/InvoiceCashMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0", "0");
 				request.setAttribute("target", Constants.PAGE_URL +"/InvoiceCashMaintananceScn.jsp");
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
			}else if(pageAction.equals("getCustomerDetail")){
				this.getCustomerDetail();
			}else if(pageAction.equals("getSaleName")){
				this.getSaleName();
			}else if(pageAction.equals("getSaleNameDetail")){
				this.getSaleNameDetail();
			}else if(pageAction.equals("getProductDetailByCode")){
				this.getProductDetailByCode();
			}else if(pageAction.equals("getDiscount")){
				this.getDiscount();
			}else if(pageAction.equals("cancel")){
				this.onCancel();
			}else if(pageAction.equals("print")){
				this.print();
			}else if(pageAction.equals("getInventoryForProduct")){
				this.getInventoryForProduct();
			}else if(pageAction.equals("updateCredit")){
				this.updateCredit();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			productQuanHistoryDao.destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		
		InvoiceCashMasterBean 		invoiceCashMasterBean = null;
		
		try{
			
			this.form.setTitlePage("บันทึกการขายเงินสด");
			
			invoiceCashMasterBean = this.form.getInvoiceCashMasterBean();
			
			invoiceCashMasterBean.setInvoiceDate(EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
			invoiceCashMasterBean.setInvoiceType("N");
			invoiceCashMasterBean.setInvoiceStatus("A");
			invoiceCashMasterBean.setSaleName(this.userBean.getUserFullName());
			invoiceCashMasterBean.setSaleUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
			
			this.setRefference();
			
			
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
			this.setInvoiceStatusCombo();
			this.setCompanyCombo();
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void setInvoiceStatusCombo() throws EnjoyException{
		
		logger.info("[setInvoiceStatusCombo][Begin]");
		
		List<ComboBean>			combo 			= null;
		
		try{
			
			combo 				= new ArrayList<ComboBean>();
			
			combo.add(new ComboBean(""	, "กรุณาระบุ"));
			combo.add(new ComboBean("A"	, "ใช้งานอยู่"));
			combo.add(new ComboBean("C"	, "ยกเลิกการใช้งาน"));
			combo.add(new ComboBean("W"	, "รอสร้างใบ Invoice"));
			
			
			this.form.setInvoiceStatusCombo(combo);
		}
		catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setInvoiceStatusCombo is error");
		}finally{
			logger.info("[setInvoiceStatusCombo][End]");
		}
	}
	
	private void setCompanyCombo() throws EnjoyException{
		
		logger.info("[setCompanyCombo][Begin]");
		
		List<ComboBean>			companyCombo 		= null;
		
		try{
			
			companyCombo = this.relationUserAndCompanyDao.getCompanyList(this.userBean.getUserUniqueId());
			
			if(companyCombo.size() > 1){
				companyCombo.add(0, new ComboBean("", "กรุณาระบุ"));
			}
			
			this.form.setCompanyCombo(companyCombo);
		}
		catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setCompanyCombo is error");
		}finally{
			logger.info("[setCompanyCombo][End]");
		}
	}
	
	private void getDetail(String invoiceCode, String tin) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		InvoiceCashMasterBean 			invoiceCashMasterBean		= null;
		InvoiceCashMasterBean 			invoiceCashMasterBeanDb		= null;
		String							seqTemp						= null;
		CustomerDetailsBean 			customerDetailsBean			= null;
		CustomerDetailsBean 			customerDetailsBeanDb		= null;
		InvoiceCashDetailBean			invoiceCashDetailBean		= null;
		List<InvoiceCashDetailBean> 	invoiceCashDetailList		= null;
		String							updateCredit				= "";
		
		try{
			invoiceCode			= invoiceCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("invoiceCode")):invoiceCode;
			tin					= tin.equals("0")?EnjoyUtil.nullToStr(request.getParameter("tin")):tin;
			updateCredit		= EnjoyUtil.nullToStr(request.getParameter("updateCredit"));
			
			logger.info("[getDetail] invoiceCode  	:: " + invoiceCode);
			logger.info("[getDetail] tin  			:: " + tin);
			logger.info("[getDetail] updateCredit 	:: " + updateCredit);
			
			invoiceCashMasterBean = new InvoiceCashMasterBean();
			invoiceCashMasterBean.setInvoiceCode(invoiceCode);
			invoiceCashMasterBean.setTin		(tin);
			
			invoiceCashMasterBeanDb				= this.invoiceCashDao.getInvoiceCashMaster(invoiceCashMasterBean);
			
			this.form.setPageMode(InvoiceCashMaintananceForm.EDIT);
			
			if(updateCredit.equals("Y")){
				this.form.setTitlePage("ปรับปรุงงบการขายเงินเชื่อ");
			}else{
				this.form.setTitlePage("แก้ไขรายการการขายเงินสด");
			}
			
			this.form.setUpdateCredit(updateCredit);
			
			logger.info("[getDetail] invoiceCashMasterBeanDb :: " + invoiceCashMasterBeanDb);
			
			if(invoiceCashMasterBeanDb!=null){
				invoiceCashDetailBean = new InvoiceCashDetailBean();
				
				invoiceCashDetailBean.setInvoiceCode(invoiceCode);
				invoiceCashDetailBean.setTin		(tin);
				invoiceCashDetailList = this.invoiceCashDao.getInvoiceCashDetailList(invoiceCashDetailBean);
				
				for(InvoiceCashDetailBean bean:invoiceCashDetailList){
					seqTemp = bean.getSeq();
				}
				
				if(seqTemp!=null){
					this.form.setSeqTemp(seqTemp);
				}
				
				if(!EnjoyUtil.nullToStr(invoiceCashMasterBeanDb.getCusCode()).equals("")){
					customerDetailsBean = new CustomerDetailsBean();
					customerDetailsBean.setCusCode(invoiceCashMasterBeanDb.getCusCode());
					customerDetailsBeanDb		= this.customerDetailsDao.getCustomerDetail(customerDetailsBean);
					
				}
				
				if(customerDetailsBeanDb==null){
					customerDetailsBeanDb = new CustomerDetailsBean();
				}
				
				this.form.setCustomerDetailsBean	(customerDetailsBeanDb);
				this.form.setInvoiceCashMasterBean	(invoiceCashMasterBeanDb);
				this.form.setInvoiceCashDetailList	(invoiceCashDetailList);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียด แก้ไขรายการการขายเงินสด");
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			this.setRefference();
			logger.info("[getDetail][End]");
		}
		
	}
	   
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String				 		invoiceCode					= null;
		String				 		invoiceDate					= null;
		String				 		invoiceType					= null;
		String				 		cusCode						= null;
		String				 		branchName					= null;
		String				 		saleUniqueId				= null;
		String				 		saleCommission				= null;
		String				 		invoicePrice				= null;
		String				 		invoicediscount				= null;
		String				 		invoiceDeposit				= null;
		String				 		invoiceVat					= null;
		String				 		invoiceTotal				= null;
//		String				 		invoiceCredit				= null;
//		String						invoiceStatus				= null;
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		JSONObject 					obj 						= null;
		InvoiceCashMasterBean  		invoiceCashMasterBean		= null;
		List<InvoiceCashDetailBean> invoiceCashDetailList		= null;
		InvoiceCashDetailBean		bean						= null;
		int							seqDb						= 1;
		double						quantity					= 0;
		ProductquantityBean			productquantityBean			= null;
		String						tin							= null;
		String						remark						= null;
		String						quantityDb					= null;
		ProductQuanHistoryBean		productQuanHistoryBean		= null;
		ProductmasterBean 			productmasterBean			= null;
		ProductmasterBean 			productmasterBeanDb			= null;
		
		try{
			invoiceCode 				= EnjoyUtil.nullToStr(request.getParameter("invoiceCode"));
			invoiceDate 				= EnjoyUtil.nullToStr(request.getParameter("invoiceDate"));
			invoiceType 				= EnjoyUtil.nullToStr(request.getParameter("invoiceType"));
			cusCode 					= EnjoyUtil.nullToStr(request.getParameter("cusCode"));
			branchName 					= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			saleUniqueId 				= EnjoyUtil.nullToStr(request.getParameter("saleUniqueId"));
			saleCommission 				= EnjoyUtil.nullToStr(request.getParameter("saleCommission"));
			invoicePrice 				= EnjoyUtil.nullToStr(request.getParameter("invoicePrice"));
			invoicediscount 			= EnjoyUtil.nullToStr(request.getParameter("invoicediscount"));
			invoiceDeposit 				= EnjoyUtil.nullToStr(request.getParameter("invoiceDeposit"));
			invoiceVat 					= EnjoyUtil.nullToStr(request.getParameter("invoiceVat"));
			invoiceTotal 				= EnjoyUtil.nullToStr(request.getParameter("invoiceTotal"));
//			invoiceCredit 				= EnjoyUtil.nullToStr(request.getParameter("invoiceCredit"));
//			invoiceStatus 				= EnjoyUtil.nullToStr(request.getParameter("invoiceStatus"));
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			invoiceCashMasterBean		= new InvoiceCashMasterBean();
			invoiceCashDetailList		= this.form.getInvoiceCashDetailList();
			
			session.beginTransaction();
			
			/*Begin Section รายละเอียดใบสั่งซื้อ*/
			invoiceCode = String.valueOf(this.invoiceCashDao.genId(invoiceType, tin));
			
			invoiceCashMasterBean.setInvoiceCode			(invoiceCode);
			invoiceCashMasterBean.setInvoiceDate			(EnjoyUtils.dateThaiToDb(invoiceDate));
			invoiceCashMasterBean.setInvoiceType			(invoiceType);
			invoiceCashMasterBean.setCusCode				(cusCode);
			invoiceCashMasterBean.setBranchName				(branchName);
			invoiceCashMasterBean.setSaleUniqueId			(saleUniqueId);
			invoiceCashMasterBean.setSaleCommission			(saleCommission);
			invoiceCashMasterBean.setInvoicePrice			(invoicePrice);
			invoiceCashMasterBean.setInvoicediscount		(invoicediscount);
			invoiceCashMasterBean.setInvoiceDeposit			(invoiceDeposit);
			invoiceCashMasterBean.setInvoiceVat				(invoiceVat);
			invoiceCashMasterBean.setInvoiceTotal			(invoiceTotal);
			invoiceCashMasterBean.setInvoiceCredit			("");
			invoiceCashMasterBean.setUserUniqueId			(String.valueOf(this.userBean.getUserUniqueId()));
			invoiceCashMasterBean.setInvoiceStatus			("A");
			invoiceCashMasterBean.setTin					(tin);
			invoiceCashMasterBean.setRemark					(remark);
			
			this.invoiceCashDao.insertInvoiceCashMaster(session, invoiceCashMasterBean);
			
			/*Begin manage รายการสินค้า*/
			this.invoiceCashDao.deleteInvoiceCashDetail(session, invoiceCode, tin);
			for(int i=0;i<invoiceCashDetailList.size();i++){
				bean = invoiceCashDetailList.get(i);
				if(!bean.getRowStatus().equals(InvoiceCashMaintananceForm.DEL)){
					bean.setInvoiceCode	(invoiceCode);
					bean.setTin			(tin);
					bean.setSeqDb		(String.valueOf(seqDb));
					this.invoiceCashDao.insertInvoiceCashDetail(session, bean);
					seqDb++;
					
					/*Begin Section รายการสินค้า*/
					productquantityBean = new ProductquantityBean();
					
					productquantityBean.setProductCode(bean.getProductCode());
					productquantityBean.setTin(tin);
					
					quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
					
					if(quantityDb==null){
						quantity =  EnjoyUtils.parseDouble("0.00");
						productquantityBean.setQuantity(String.valueOf(quantity));
						this.productquantityDao.insertProductquantity(session, productquantityBean);
					}else{
						quantity = EnjoyUtils.parseDouble(quantityDb) - EnjoyUtils.parseDouble(bean.getQuantity());
						productquantityBean.setQuantity(String.valueOf(quantity));
						this.productquantityDao.updateProductquantity(session, productquantityBean);
					}
					/*End Section รายการสินค้า*/
					
					/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
					productQuanHistoryBean = new ProductQuanHistoryBean();
					productQuanHistoryBean.setFormRef(invoiceCode);
					
					productmasterBean = new ProductmasterBean();
					productmasterBean.setProductCode(bean.getProductCode());
					productmasterBeanDb = productDetailsDao.getProductDetail(session, productmasterBean);
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
					productQuanHistoryBean.setQuantityPlus("0.00");
					productQuanHistoryBean.setQuantityMinus(bean.getQuantity());
					productQuanHistoryBean.setQuantityTotal(String.valueOf(quantity));
					
					productQuanHistoryDao.insert(session, productQuanHistoryBean);
					/*End ส่วนประวัตเพิ่มลดสินค้า*/
					
				}
			}
			/*End manage รายการสินค้า*/
			
			/*End Section รายละเอียดใบสั่งซื้อ*/
			
			session.getTransaction().commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("invoiceCode"	,invoiceCode);
			obj.put("tin"			,tin);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.error(e);
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
		InvoiceCashDetailBean	invoiceCashDetailBean	= null;
		String					newSeq					= null;
		
		try{
			
			invoiceCashDetailBean 	= new InvoiceCashDetailBean();
			obj 					= new JSONObject();
			newSeq					= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			invoiceCashDetailBean.setRowStatus(InvoiceCashMaintananceForm.NEW);
			invoiceCashDetailBean.setSeq(newSeq);
			
			this.form.getInvoiceCashDetailList().add(invoiceCashDetailBean);
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
		String 							pricePerUnit			= null;
		String 							discount				= null;
		String 							price					= null;
		String 							seq						= null;
		List<InvoiceCashDetailBean> 	invoiceCashDetailList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			productCode 			= EnjoyUtil.nullToStr(request.getParameter("productCode"));
			productName 			= EnjoyUtil.nullToStr(request.getParameter("productName"));
			inventory 				= EnjoyUtil.nullToStr(request.getParameter("inventory"));
			quantity 				= EnjoyUtil.nullToStr(request.getParameter("quantity"));
			unitCode 				= EnjoyUtil.nullToStr(request.getParameter("unitCode"));
			unitName 				= EnjoyUtil.nullToStr(request.getParameter("unitName"));
			pricePerUnit 			= EnjoyUtil.nullToStr(request.getParameter("pricePerUnit"));
			discount 				= EnjoyUtil.nullToStr(request.getParameter("discount"));
			price 					= EnjoyUtil.nullToStr(request.getParameter("price"));
			invoiceCashDetailList	= this.form.getInvoiceCashDetailList();
			
			logger.info("[updateRecord] seq 			:: " + seq);
			logger.info("[updateRecord] productCode 	:: " + productCode);
			logger.info("[updateRecord] productName 	:: " + productName);
			logger.info("[updateRecord] inventory 		:: " + inventory);
			logger.info("[updateRecord] quantity 		:: " + quantity);
			logger.info("[updateRecord] unitCode 		:: " + unitCode);
			logger.info("[updateRecord] unitName 		:: " + unitName);
			logger.info("[updateRecord] pricePerUnit 	:: " + pricePerUnit);
			logger.info("[updateRecord] discount 		:: " + discount);
			logger.info("[updateRecord] price 			:: " + price);
			
			for(InvoiceCashDetailBean bean:invoiceCashDetailList){
				if(bean.getSeq().equals(seq)){
					
					bean.setProductCode		(productCode);
					bean.setProductName		(productName);
					bean.setInventory		(inventory);
					bean.setQuantity		(quantity);
					bean.setUnitCode		(unitCode);
					bean.setUnitName		(unitName);
					bean.setPricePerUnit	(pricePerUnit);
					bean.setDiscount		(discount);
					bean.setPrice			(price);
					
					if(bean.getRowStatus().equals("")){
						bean.setRowStatus(InvoiceCashMaintananceForm.EDIT);
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
		
		JSONObject 						obj 					= null;
		String 							seq						= null;
		List<InvoiceCashDetailBean> 	invoiceCashDetailList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			invoiceCashDetailList	= this.form.getInvoiceCashDetailList();
			
			for(int i=0;i<invoiceCashDetailList.size();i++){
				InvoiceCashDetailBean bean = invoiceCashDetailList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(InvoiceCashMaintananceForm.NEW)){
						invoiceCashDetailList.remove(i);
					}else{
						bean.setRowStatus(InvoiceCashMaintananceForm.DEL);
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
	   
	   String						productName				= null;
	   List<ComboBean> 				list 					= null;
	   JSONArray 					jSONArray 				= null;
	   JSONObject 					objDetail 				= null;
	
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
	   String				 			groupSalePrice			= null;
	   JSONObject 						obj		 				= null;
	   ProductmasterBean				productmasterBean		= null;
	   String							pricePerUnit			= "";
	   String							discount				= "";
	   String							tin						= null;
	   ProductquantityBean				productquantityBean		= null;
	   String							inventory				= "0.00";
	
	   try{
		   obj 				= new JSONObject();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   groupSalePrice 	= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
		   tin				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
		   
		   logger.info("[getProductDetailByName] productName 				:: " + productName);
		   logger.info("[getProductDetailByName] groupSalePrice 			:: " + groupSalePrice);
		   
		   productmasterBean 		= this.productDetailsDao.getProductDetailByName(productName);
		   
		   if(productmasterBean!=null && !tin.equals("")){
			   
			   if(groupSalePrice.equals("2")){
				   pricePerUnit = productmasterBean.getSalePrice2();
			   }else if(groupSalePrice.equals("3")){
				   pricePerUnit = productmasterBean.getSalePrice3();
			   }else if(groupSalePrice.equals("4")){
				   pricePerUnit = productmasterBean.getSalePrice4();
			   }else if(groupSalePrice.equals("5")){
				   pricePerUnit = productmasterBean.getSalePrice5();
			   }else{
				   pricePerUnit = productmasterBean.getSalePrice1();
			   }
			   
			   discount = this.productDetailsDao.getQuanDiscount(productmasterBean.getProductCode(), "1");
			   
			   obj.put("productCode"	,productmasterBean.getProductCode());
			   obj.put("productName"	,productmasterBean.getProductName());
			   obj.put("pricePerUnit"	,pricePerUnit);
			   
			   productquantityBean = new ProductquantityBean();
			   productquantityBean.setTin(tin);
			   productquantityBean.setProductCode(productmasterBean.getProductCode());
			   inventory = EnjoyUtils.convertFloatToDisplay(this.productquantityDao.getProductquantity(productquantityBean), 2);
			   
			   obj.put("inventory"		,inventory);
			   obj.put("unitCode"		,productmasterBean.getUnitCode());
			   obj.put("unitName"		,productmasterBean.getUnitName());
			   obj.put("discount"		,discount);
		   }else{
			   obj.put("productCode"	,"");
			   obj.put("productName"	,"");
			   obj.put("pricePerUnit"	,"");
			   obj.put("inventory"		,inventory);
			   obj.put("unitCode"		,"");
			   obj.put("unitName"		,"");
			   obj.put("discount"		,"0.00");
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
	
	private void getCustomerDetail(){
	   logger.info("[getCustomerDetail][Begin]");
	   
	   String							cusCode					= null;
	   JSONObject 						obj		 				= null;
	   CustomerDetailsBean				customerDetailsBean		= null;
	   CustomerDetailsBean				customerDetailsBeanDb	= null;
	
	   try{
		   obj 				= new JSONObject();
		   cusCode			= EnjoyUtils.nullToStr(this.request.getParameter("cusCode"));
		   
		   logger.info("[getCustomerDetail] cusCode 				:: " + cusCode);
		   
		   customerDetailsBean = new CustomerDetailsBean();
		   customerDetailsBean.setCusCode	(cusCode);
		   
		   customerDetailsBeanDb 		= this.customerDetailsDao.getCustomerDetail(customerDetailsBean);
		   
		   if(customerDetailsBeanDb!=null){
			   obj.put("fullName"		,customerDetailsBeanDb.getFullName());
			   obj.put("cusCode"		,customerDetailsBeanDb.getCusCode());
			   obj.put("cusGroupCode"	,customerDetailsBeanDb.getCusGroupCode());
			   obj.put("groupSalePrice"	,customerDetailsBeanDb.getGroupSalePrice());
		   }else{
			   obj.put("fullName"		,"");
			   obj.put("cusCode"		,"");
			   obj.put("cusGroupCode"	,"");
			   obj.put("groupSalePrice"	,"");
		   }
		   
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getCustomerDetail is error");
		   e.printStackTrace();
		   logger.error(e);
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getCustomerDetail][End]");
	   }
	}
	
	private void getSaleName(){
	   logger.info("[getSaleName][Begin]");
	   
	   String							saleName			= null;
	   List<ComboBean> 					list 					= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       
	   try{
		   saleName			= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
		   jSONArray 		= new JSONArray();
		   
		   logger.info("[getSaleName] saleName 			:: " + saleName);
		   
		   list 		= this.userDetailsDao.userFullNameList(saleName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[getSaleName] " + e.getMessage());
	   }finally{
		   logger.info("[getSaleName][End]");
	   }
	}	
	
	private void getSaleNameDetail(){
	   logger.info("[getSaleNameDetail][Begin]");
	   
	   String							saleName				= null;
	   JSONObject 						obj		 				= null;
	   SessionFactory 					sessionFactory			= null;
	   Session 							session					= null;
	   String							saleUniqueId			= "";
	   List<UserDetailsBean> 			userDetailsList 		= null;
	   List<Userprivilege> 				listUserprivilege   	= null;
	   Hashtable<String, String> 		fUserprivilege			= null;
	   UserDetailsBean 					userDetailsBean			= null;
	   UserDetailsBean 					userDetailsBeanDb		= null;
	   
	
	   try{
		   obj 					= new JSONObject();
		   sessionFactory 		= HibernateUtil.getSessionFactory();
		   session 				= sessionFactory.openSession();
		   saleName				= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
		   listUserprivilege   	= new ArrayList<Userprivilege>();
		   fUserprivilege		= new Hashtable<String, String>();
		   
		   logger.info("[getSaleNameDetail] saleName 				:: " + saleName);
		   
		   listUserprivilege 			= this.userDetailsDao.getUserprivilege();
		   for(Userprivilege userprivilege : listUserprivilege){
			   fUserprivilege.put(userprivilege.getPrivilegeCode() , userprivilege.getPrivilegeName());
		   }
		   
		   userDetailsBean				= new UserDetailsBean();
			
		   userDetailsBean.setUserName	(saleName);
//		   userDetailsBean.setUserId	("***");
//		   userDetailsBean.setUserStatus("***");
		   
		   userDetailsList	 		= this.userDetailsDao.getListUserdetail(session, userDetailsBean, fUserprivilege);
		   
		   if(userDetailsList.size()==1){
			   userDetailsBeanDb = userDetailsList.get(0);
			   
			   saleUniqueId = EnjoyUtils.nullToStr(userDetailsBeanDb.getUserUniqueId());
		   }
		   
		   obj.put("saleUniqueId"	,saleUniqueId);
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getSaleNameDetail is error");
		   e.printStackTrace();
		   logger.error(e);
	   }finally{
		   session.flush();
		   session.clear();
		   session.close();
		   
		   this.enjoyUtil.writeMSG(obj.toString());
		   
		   sessionFactory	= null;
		   session			= null;
		   logger.info("[getSaleNameDetail][End]");
	   }
	}	
	
	private void getProductDetailByCode(){
	   logger.info("[getProductDetailByCode][Begin]");
	   
	   String					productCode				= null;
	   String				 	groupSalePrice			= null;
	   JSONObject 				obj		 				= null;
	   ProductmasterBean		productmasterBean		= null;
	   ProductmasterBean		productmasterBeanDb		= null;
	   String					pricePerUnit			= "";
	   SessionFactory 			sessionFactory			= null;
	   Session 					session					= null;
	   String					discount				= "";
	   String					tin						= null;
	   ProductquantityBean		productquantityBean		= null;
	   String					inventory				= "0.00";
	
	   try{
		   obj 				= new JSONObject();
		   productCode		= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
		   groupSalePrice 	= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
		   sessionFactory 	= HibernateUtil.getSessionFactory();
		   session 			= sessionFactory.openSession();
		   tin				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
		   
		   logger.info("[getProductDetailByCode] productCode 				:: " + productCode);
		   logger.info("[getProductDetailByCode] groupSalePrice 			:: " + groupSalePrice);
		   
		   productmasterBean = new ProductmasterBean();
		   productmasterBean.setProductCode(productCode);
		   
		   productmasterBeanDb 		= this.productDetailsDao.getProductDetail(session, productmasterBean);
		   
		   if(productmasterBeanDb!=null && !tin.equals("")){
			   
			   if(groupSalePrice.equals("2")){
				   pricePerUnit = productmasterBeanDb.getSalePrice2();
			   }else if(groupSalePrice.equals("3")){
				   pricePerUnit = productmasterBeanDb.getSalePrice3();
			   }else if(groupSalePrice.equals("4")){
				   pricePerUnit = productmasterBeanDb.getSalePrice4();
			   }else if(groupSalePrice.equals("5")){
				   pricePerUnit = productmasterBeanDb.getSalePrice5();
			   }else{
				   pricePerUnit = productmasterBeanDb.getSalePrice1();
			   }
			   
			   discount = this.productDetailsDao.getQuanDiscount(productmasterBean.getProductCode(), "1");
			   
			   obj.put("productCode"	,productmasterBeanDb.getProductCode());
			   obj.put("productName"	,productmasterBeanDb.getProductName());
			   obj.put("pricePerUnit"	,pricePerUnit);
			   
			   productquantityBean = new ProductquantityBean();
			   productquantityBean.setTin(tin);
			   productquantityBean.setProductCode(productmasterBean.getProductCode());
			   inventory = EnjoyUtils.convertFloatToDisplay(this.productquantityDao.getProductquantity(productquantityBean), 2);
			   
			   obj.put("inventory"		,inventory);
			   obj.put("unitCode"		,productmasterBeanDb.getUnitCode());
			   obj.put("unitName"		,productmasterBeanDb.getUnitName());
			   obj.put("discount"		,discount);
			   
		   }else{
			   obj.put("productCode"	,"");
			   obj.put("productName"	,"");
			   obj.put("pricePerUnit"	,"");
			   obj.put("inventory"		,inventory);
			   obj.put("unitCode"		,"");
			   obj.put("unitName"		,"");
			   obj.put("discount"		,"0.00");
		   }
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getProductDetailByCode is error");
		   e.printStackTrace();
		   logger.info("[getProductDetailByCode] " + e.getMessage());
	   }finally{
		   session.close();
		   sessionFactory		= null;
		   session				= null;
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getProductDetailByCode][End]");
	   }
	}
	
	private void getDiscount() {
		logger.info("[getDiscount][Begin]");

		String		 	productCode 	= null;
		String			quantity		= null;
		JSONObject 		obj		 		= null;
		String 			discount 		= "";
		
		try {
			obj 			= new JSONObject();
			productCode 	= EnjoyUtil.nullToStr(this.request.getParameter("productCode"));
			quantity 		= EnjoyUtil.replaceComma(request.getParameter("quantity"));
			
			discount = this.productDetailsDao.getQuanDiscount(productCode, quantity);
			
			obj.put("discount"	,discount);
			obj.put(STATUS		,SUCCESS);
			
		} catch (Exception e) {
			obj.put(STATUS, ERROR);
			obj.put(ERR_MSG, "getDiscount is error");
			e.printStackTrace();
			logger.error("[getDiscount] " + e);
		} finally {
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[getDiscount][End]");
		}
	}
	   
	private void onCancel() throws EnjoyException{
		logger.info("[onCancel][Begin]");
		
		String				 		invoiceCode					= null;
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		JSONObject 					obj 						= null;
		InvoiceCashMasterBean  		invoiceCashMasterBean		= null;
		List<InvoiceCashDetailBean> invoiceCashDetailList		= null;
		InvoiceCashDetailBean		bean						= null;
		double						quantity					= 0;
		ProductquantityBean			productquantityBean			= null;
		String						tin							= null;
		String						quantityDb					= null;
		String						invoiceCredit				= null;
		InvoiceCreditMasterBean  	invoiceCreditMasterBean		= null;
		ProductQuanHistoryBean		productQuanHistoryBean		= null;
		ProductmasterBean 			productmasterBean			= null;
		ProductmasterBean 			productmasterBeanDb			= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			invoiceCashDetailList		= this.form.getInvoiceCashDetailList();
			invoiceCode 				= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode"));
			tin 						= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
			invoiceCredit 				= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCredit"));
			
			logger.info("[onCancel] invoiceCode 	:: " + invoiceCode);
			logger.info("[onCancel] tin 			:: " + tin);
			logger.info("[onCancel] invoiceCredit 	:: " + invoiceCredit);
			
			session.beginTransaction();
			
			if(!invoiceCode.equals("")){
				invoiceCashMasterBean		= new InvoiceCashMasterBean();
				invoiceCashMasterBean.setInvoiceStatus			("C");
				invoiceCashMasterBean.setInvoiceCode			(invoiceCode);
				invoiceCashMasterBean.setTin					(tin);
				
				this.invoiceCashDao.updateInvoiceCashMasterStatus(session, invoiceCashMasterBean);
				
				/*Begin manage รายการสินค้า*/
				for(int i=0;i<invoiceCashDetailList.size();i++){
					bean = invoiceCashDetailList.get(i);
					if(!bean.getRowStatus().equals(InvoiceCashMaintananceForm.DEL)){
						
						/*Begin Section รายการสินค้า*/
						productquantityBean = new ProductquantityBean();
						
						productquantityBean.setProductCode(bean.getProductCode());
						productquantityBean.setTin(tin);
						
						quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
						
						if(quantityDb==null){
							quantity =  EnjoyUtils.parseDouble("0.00");
							productquantityBean.setQuantity(String.valueOf(quantity));
							this.productquantityDao.insertProductquantity(session, productquantityBean);
						}else{
							quantity			= EnjoyUtils.parseDouble(quantityDb) +  EnjoyUtils.parseDouble(bean.getQuantity());
							productquantityBean.setQuantity(String.valueOf(quantity));
							this.productquantityDao.updateProductquantity(session, productquantityBean);
						}
						/*End Section รายการสินค้า*/
						
						/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
						productQuanHistoryBean = new ProductQuanHistoryBean();
						productQuanHistoryBean.setFormRef(invoiceCode);
						
						productmasterBean = new ProductmasterBean();
						productmasterBean.setProductCode(bean.getProductCode());
						productmasterBeanDb = productDetailsDao.getProductDetail(session, productmasterBean);
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
						productQuanHistoryBean.setQuantityPlus(bean.getQuantity());
						productQuanHistoryBean.setQuantityMinus("0.00");
						productQuanHistoryBean.setQuantityTotal(String.valueOf(quantity));
						
						productQuanHistoryDao.insert(session, productQuanHistoryBean);
						/*End ส่วนประวัตเพิ่มลดสินค้า*/
					}
				}
				/*End manage รายการสินค้า*/
				
				if(!invoiceCredit.equals("")){
					invoiceCreditMasterBean		= new InvoiceCreditMasterBean();
					invoiceCreditMasterBean.setInvoiceStatus		("C");
					invoiceCreditMasterBean.setInvoiceCode			(invoiceCredit);
					
					this.invoiceCreditDao.updateInvoiceCreditMasterStatus(session, invoiceCreditMasterBean);
				}
				
			}
			
			session.getTransaction().commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("invoiceCode"	,invoiceCode);
			obj.put("tin"			,tin);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onCancel is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onCancel][End]");
		}
	}	
	
	private void print(){
		logger.info("[print][Begin]");
		   
	   	JSONObject 					jsonObject 				= new JSONObject();
		JSONArray 					jSONArray 				= new JSONArray();
		JSONObject 					objDetail 				= null;
		CompanyDetailsBean 			companyDetailsBean		= new CompanyDetailsBean();
		CompanyDetailsBean 			companyDetailsDb		= null;
		CustomerDetailsBean 		customerDetailsBean		= new CustomerDetailsBean();
		CustomerDetailsBean 		customerDetailsDb		= null;
		InvoiceCashMasterBean 		invoiceCashMasterBean 	= new InvoiceCashMasterBean();
		InvoiceCashMasterBean 		invoiceCashMasterDb 	= null;
		String						cusCode					= null;
		String						tin						= null;
		String						invoiceCode				= null;
		InvoiceCashDetailBean 		invoiceCashDetailBean	= new InvoiceCashDetailBean();
		List<InvoiceCashDetailBean> invoiceCashDetailList 	= null;
		ViewPdfMainForm				viewPdfMainForm			= null;
		DataOutput 					output 					= null;
		ByteArrayOutputStream		buffer					= null;
		byte[] 						bytes					= null;
		String						invoiceType				= null;
	 
		try{
			invoiceCode 		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode"));
			tin 				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
			viewPdfMainForm		= new ViewPdfMainForm();
			
			logger.info("[print] invoiceCode 	:: " + invoiceCode);
			logger.info("[print] tin 			:: " + tin);
   
		    /*Begin รายละเอียดใบกำกับภาษี*/
			invoiceCashMasterBean.setInvoiceCode(invoiceCode);
			invoiceCashMasterBean.setTin		(tin);
			invoiceCashMasterDb = invoiceCashDao.getInvoiceCashMaster(invoiceCashMasterBean);
			objDetail = new JSONObject();
			objDetail.put("invoiceCode"		, invoiceCashMasterDb.getInvoiceCode());
			objDetail.put("invoiceDate"		, invoiceCashMasterDb.getInvoiceDate());
			
			invoiceType = invoiceCashMasterDb.getInvoiceType();
			objDetail.put("invoiceType"		, invoiceType);
			
			cusCode = invoiceCashMasterDb.getCusCode();
			objDetail.put("cusCode"			, cusCode);
			objDetail.put("branchName"		, invoiceCashMasterDb.getBranchName());
			objDetail.put("saleUniqueId"	, invoiceCashMasterDb.getSaleUniqueId());
			objDetail.put("saleName"		, invoiceCashMasterDb.getSaleName());
			objDetail.put("saleCommission"	, invoiceCashMasterDb.getSaleCommission());
			objDetail.put("invoicePrice"	, invoiceCashMasterDb.getInvoicePrice());
			objDetail.put("invoicediscount"	, invoiceCashMasterDb.getInvoicediscount());
			objDetail.put("invoiceDeposit"	, invoiceCashMasterDb.getInvoiceDeposit());
			objDetail.put("invoiceVat"		, invoiceCashMasterDb.getInvoiceVat());
			objDetail.put("invoiceTotal"	, invoiceCashMasterDb.getInvoiceTotal());
			objDetail.put("userUniqueId"	, invoiceCashMasterDb.getUserUniqueId());
			objDetail.put("invoiceCredit"	, invoiceCashMasterDb.getInvoiceCredit());
			objDetail.put("invoiceStatus"	, invoiceCashMasterDb.getInvoiceStatus());
			objDetail.put("invoiceTypeDesc"	, invoiceCashMasterDb.getInvoiceTypeDesc());
			objDetail.put("remark"			, invoiceCashMasterDb.getRemark());
			objDetail.put("tin"				, tin);
			
			jsonObject.put("invoiceCashMaster"	,objDetail);
			/*End รายละเอียดใบกำกับภาษี*/
	
			/*Begin รายละเอียดรายการที่ขาย*/
			invoiceCashDetailBean.setInvoiceCode(invoiceCode);
			invoiceCashDetailBean.setTin		(tin);
			invoiceCashDetailList = invoiceCashDao.getInvoiceCashDetailList(invoiceCashDetailBean);
			for(InvoiceCashDetailBean vo:invoiceCashDetailList){
				objDetail = new JSONObject();
				objDetail.put("invoiceCode"		, vo.getInvoiceCode());
				objDetail.put("seq"				, vo.getSeq());
				objDetail.put("productCode"		, vo.getProductCode());
				objDetail.put("productName"		, vo.getProductName());
				objDetail.put("quantity"		, vo.getQuantity());
				objDetail.put("pricePerUnit"	, vo.getPricePerUnit());
				objDetail.put("discount"		, vo.getDiscount());
				objDetail.put("price"			, vo.getPrice());
				objDetail.put("unitCode"		, vo.getUnitCode());
				objDetail.put("unitName"		, vo.getUnitName());
				
				jSONArray.add(objDetail);
			}
			
			jsonObject.put("invoiceCashDetailList"	,jSONArray);
			/*End รายละเอียดรายการที่ขาย*/
	
	
			/*Begin รายละเอียดบริษัท*/
			if(tin!=null && !"".equals(tin)){
				companyDetailsBean.setTin(tin);
				companyDetailsDb = companyDetailsDao.getCompanyDetail(companyDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("tin"			, companyDetailsDb.getTin());
				objDetail.put("companyName"	, companyDetailsDb.getCompanyName());
				objDetail.put("address"		, companyDetailsDb.getAddress());
				objDetail.put("tel"			, companyDetailsDb.getTel());
				objDetail.put("fax"			, companyDetailsDb.getFax());
				objDetail.put("email"		, companyDetailsDb.getEmail());
				objDetail.put("remark"		, companyDetailsDb.getRemark());
				
				jsonObject.put("companyDetails"	,objDetail);
			}
			/*ENd รายละเอียดบริษัท*/
	
			/*Begin รายละเอียดลูกค้า*/
			if(cusCode!=null && !"".equals(cusCode)){
				customerDetailsBean.setCusCode(cusCode);
				customerDetailsDb = customerDetailsDao.getCustomerDetail(customerDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("cusCode"		, customerDetailsDb.getCusCode());
				objDetail.put("cusName"		, customerDetailsDb.getCusName());
				objDetail.put("cusSurname"	, customerDetailsDb.getCusSurname());
				objDetail.put("branchName"	, customerDetailsDb.getBranchName());
				objDetail.put("sex"			, customerDetailsDb.getSex());
				objDetail.put("idType"		, customerDetailsDb.getIdType());
				objDetail.put("idNumber"	, customerDetailsDb.getIdNumber());
				objDetail.put("birthDate"	, customerDetailsDb.getBirthDate());
				objDetail.put("religion"	, customerDetailsDb.getReligion());
				objDetail.put("job"			, customerDetailsDb.getJob());
				objDetail.put("address"		, customerDetailsDb.getAddress());
				objDetail.put("tel"			, customerDetailsDb.getTel());
				objDetail.put("fax"			, customerDetailsDb.getFax());
				objDetail.put("email"		, customerDetailsDb.getEmail());
				
				jsonObject.put("customerDetails",	objDetail);
			}
			/*End รายละเอียดลูกค้า*/
	
			jsonObject.put(STATUS, 			SUCCESS);
   
			logger.info("[print] obj.toString() :: " + jsonObject.toString());
			String pdfName = "";
			if("V".equals(invoiceType)){
				pdfName = "FullSlipCashPdfForm";
			}else{
				pdfName = "FullSlipCashNoVatPdfForm";
			}
   
			buffer = viewPdfMainForm.writeTicketPDFA5(pdfName, jsonObject, "ใบเสร็จรับเงิน");
			
			response.setContentType( "application/pdf" );
			response.setHeader("Content-Disposition", "filename=".concat(String.valueOf(pdfName+".pdf")));
			output 	= new DataOutputStream( this.response.getOutputStream() );
			bytes 	= buffer.toByteArray();
	
			this.response.setContentLength(bytes.length);
			for( int i = 0; i < bytes.length; i++ ) { 
				output.writeByte( bytes[i] ); 
			}
	   
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[print] " + e.getMessage());
		}finally{
			logger.info("[print][End]");
		}
	}
	
	private void getInventoryForProduct(){
	   logger.info("[getInventoryForProduct][Begin]");
	   
	   JSONObject 						obj		 				= new JSONObject();
	   JSONObject 						objDetail		 		= null;
	   JSONArray						jsonArray				= new JSONArray();
	   String							tin						= null;
	   ProductquantityBean				productquantityBean		= null;
	   String							inventory				= "0.00";
	   String[]							productCodeArray		= null;
	
	   try{
		   productCodeArray	= this.request.getParameterValues("productCode");
		   tin				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
		   
		   if(productCodeArray!=null && productCodeArray.length>0){
			   for(String productCode:productCodeArray){
				   productquantityBean 	= new ProductquantityBean();
				   objDetail			= new JSONObject();
				   productquantityBean.setTin(tin);
				   productquantityBean.setProductCode(productCode);
				   inventory = EnjoyUtils.convertFloatToDisplay(this.productquantityDao.getProductquantity(productquantityBean), 2);
				   
				   objDetail.put("productCode"	, productCode);
				   objDetail.put("inventory"	, inventory);
				   
				   jsonArray.add(objDetail);
				   
			   }
			   
			   obj.put("flag"			,"Y");
			   obj.put("inventoryList"	,jsonArray);
		   }else{
			   obj.put("flag"			,"N");
		   }
		   
		   obj.put(STATUS, 		SUCCESS);
		   
	   }catch(Exception e){
		   obj.put(STATUS, 		ERROR);
		   obj.put(ERR_MSG, 	"getInventoryForProduct is error");
		   e.printStackTrace();
		   logger.error(e);
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getInventoryForProduct][End]");
	   }
	}
	   
	private void updateCredit() throws EnjoyException{
		logger.info("[updateCredit][Begin]");
		
		String				 		invoiceCode					= null;
		String				 		invoiceCredit				= null;
		String						tin							= null;	
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		JSONObject 					obj 						= null;
		InvoiceCashMasterBean  		invoiceCashMasterBean		= null;
		InvoiceCreditMasterBean		invoiceCreditMasterBean		= null;
		
		try{
			invoiceCode 				= EnjoyUtil.nullToStr(request.getParameter("invoiceCode"));
			invoiceCredit 				= EnjoyUtil.nullToStr(request.getParameter("invoiceCredit"));
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			
			logger.info("[updateCredit] invoiceCode 	:: " + invoiceCode);
			logger.info("[updateCredit] invoiceCredit 	:: " + invoiceCredit);
			logger.info("[updateCredit] tin 			:: " + tin);
			
			session.beginTransaction();
			
			invoiceCashMasterBean		= new InvoiceCashMasterBean();
			invoiceCashMasterBean.setInvoiceCode			(invoiceCode);
			invoiceCashMasterBean.setInvoiceStatus			("A");
			invoiceCashMasterBean.setTin					(tin);
			
			this.invoiceCashDao.updateInvoiceCashMasterStatus(session, invoiceCashMasterBean);
			
			invoiceCreditMasterBean = new InvoiceCreditMasterBean();
			invoiceCreditMasterBean.setInvoiceCode			(invoiceCredit);
			invoiceCreditMasterBean.setInvoiceStatus		("S");
			invoiceCreditMasterBean.setTin					(tin);
			
			this.invoiceCreditDao.updateInvoiceCreditMasterStatus(session, invoiceCreditMasterBean);
			
			
			session.getTransaction().commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("invoiceCode"	,invoiceCode);
			obj.put("tin"			,tin);
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.error(e);
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"updateCredit is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[updateCredit][End]");
		}
	}	

	
}











