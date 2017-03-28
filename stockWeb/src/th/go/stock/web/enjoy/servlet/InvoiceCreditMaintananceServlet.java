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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.ProductquantityBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.InvoiceCashDao;
import th.go.stock.app.enjoy.dao.InvoiceCreditDao;
import th.go.stock.app.enjoy.dao.ProductDetailsDao;
import th.go.stock.app.enjoy.dao.ProductQuanHistoryDao;
import th.go.stock.app.enjoy.dao.ProductquantityDao;
import th.go.stock.app.enjoy.dao.UserDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.InvoiceCreditMaintananceForm;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.pdf.ViewPdfMainForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class InvoiceCreditMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(InvoiceCreditMaintananceServlet.class);
	
    private static final String 	FORM_NAME 			= "invoiceCreditMaintananceForm";
    private static final String 	CREDIT 				= "CR";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private InvoiceCreditDao				invoiceCreditDao			= null;
    private InvoiceCreditMaintananceForm	form						= null;
    private CustomerDetailsDao				customerDetailsDao			= null;
    private ProductDetailsDao				productDetailsDao			= null;
    private UserDetailsDao					userDetailsDao				= null;
    private CompanyDetailsDao				companyDetailsDao			= null;
    private ProductquantityDao				productquantityDao			= null;
    private InvoiceCashDao					invoiceCashDao				= null;
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
             this.form               		= (InvoiceCreditMaintananceForm)session.getAttribute(FORM_NAME);
             this.invoiceCreditDao			= new InvoiceCreditDao();
             this.customerDetailsDao		= new CustomerDetailsDao();
             this.productDetailsDao			= new ProductDetailsDao();
             this.userDetailsDao			= new UserDetailsDao();
             this.companyDetailsDao			= new CompanyDetailsDao();
             this.productquantityDao		= new ProductquantityDao();
             this.invoiceCashDao			= new InvoiceCashDao();
             this.productQuanHistoryDao		= new ProductQuanHistoryDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new InvoiceCreditMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/InvoiceCreditMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/InvoiceCreditMaintananceScn.jsp");
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
			}else if(pageAction.equals("getProductDetailByCodeDis")){
				this.getProductDetailByCodeDis();
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
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		
		InvoiceCreditMasterBean 		invoiceCreditMasterBean = null;
		
		try{
			
			this.form.setTitlePage("บันทึกการขายเงินเชื่อ");
			
			invoiceCreditMasterBean = this.form.getInvoiceCreditMasterBean();
			
			invoiceCreditMasterBean.setInvoiceDate(EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
			invoiceCreditMasterBean.setInvoiceType("N");
			invoiceCreditMasterBean.setInvoiceStatus("A");
			invoiceCreditMasterBean.setSaleName(this.userBean.getUserFullName());
			invoiceCreditMasterBean.setSaleUniqueId(String.valueOf(this.userBean.getUserUniqueId()));
			
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
			combo.add(new ComboBean("S"	, "รับเงินเรียบร้อยแล้ว"));
			
			this.form.setInvoiceStatusCombo(combo);
		}
		catch(Exception e){
			logger.error(e);
			throw new EnjoyException("setInvoiceStatusCombo is error");
		}finally{
			logger.info("[setInvoiceStatusCombo][End]");
		}
	}
	
	private void getDetail(String invoiceCode) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		InvoiceCreditMasterBean 		invoiceCreditMasterBean		= null;
		InvoiceCreditMasterBean 		invoiceCreditMasterBeanDb	= null;
		String							seqTemp						= null;
		CustomerDetailsBean 			customerDetailsBean			= null;
		CustomerDetailsBean 			customerDetailsBeanDb		= null;
		InvoiceCreditDetailBean			invoiceCreditDetailBean		= null;
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList		= null;
		String 							tin 						= null;
		
		try{
			invoiceCode			= invoiceCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("invoiceCode")):invoiceCode;
			tin					= this.userBean.getTin();
			
			logger.info("[getDetail] invoiceCode 	:: " + invoiceCode);
			logger.info("[getDetail] tin 			:: " + tin);
			
			invoiceCreditMasterBean = new InvoiceCreditMasterBean();
			invoiceCreditMasterBean.setInvoiceCode	(invoiceCode);
			invoiceCreditMasterBean.setTin			(tin);
			
			invoiceCreditMasterBeanDb	= this.invoiceCreditDao.getInvoiceCreditMaster(invoiceCreditMasterBean);
			
			this.form.setPageMode(InvoiceCreditMaintananceForm.EDIT);
			this.form.setTitlePage("แก้ไขรายการการขายเงินเชื่อ");
			
			logger.info("[getDetail] invoiceCreditMasterBeanDb :: " + invoiceCreditMasterBeanDb);
			
			if(invoiceCreditMasterBeanDb!=null){
				invoiceCreditDetailBean = new InvoiceCreditDetailBean();
				
				invoiceCreditDetailBean.setInvoiceCode	(invoiceCode);
				invoiceCreditDetailBean.setTin			(tin);
				invoiceCreditDetailList = this.invoiceCreditDao.getInvoiceCreditDetailList(invoiceCreditDetailBean);
				
				for(InvoiceCreditDetailBean bean:invoiceCreditDetailList){
					seqTemp = bean.getSeq();
				}
				
				if(seqTemp!=null){
					this.form.setSeqTemp(seqTemp);
				}
				
				if(!EnjoyUtil.nullToStr(invoiceCreditMasterBeanDb.getCusCode()).equals("")){
					customerDetailsBean = new CustomerDetailsBean();
					customerDetailsBean.setCusCode(invoiceCreditMasterBeanDb.getCusCode());
					customerDetailsBean.setTin(tin);
					customerDetailsBeanDb		= this.customerDetailsDao.getCustomerDetail(customerDetailsBean);
					
				}
				
				if(customerDetailsBeanDb==null){
					customerDetailsBeanDb = new CustomerDetailsBean();
				}
				
				this.form.setCustomerDetailsBean	(customerDetailsBeanDb);
				this.form.setInvoiceCreditMasterBean	(invoiceCreditMasterBeanDb);
				this.form.setInvoiceCreditDetailList	(invoiceCreditDetailList);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียด แก้ไขรายการการขายเงินเชื่อ");
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
		
//		String				 			invoiceCode					= null;
		String				 			invoiceDate					= null;
		String				 			invoiceType					= null;
		String				 			cusCode						= null;
		String				 			branchName					= null;
		String				 			saleUniqueId				= null;
		String				 			saleCommission				= null;
		String				 			invoicePrice				= null;
		String				 			invoicediscount				= null;
		String				 			invoiceDeposit				= null;
		String				 			invoiceVat					= null;
		String				 			invoiceTotal				= null;
		JSONObject 						obj 						= null;
		InvoiceCreditMasterBean  		invoiceCreditMasterBean		= null;
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList		= null;
		InvoiceCreditDetailBean			bean						= null;
		int								seqDb						= 1;
		double							quantity					= 0;
		ProductquantityBean				productquantityBean			= null;
		String							tin							= null;
		String							remark						= null;
		String							quantityDb					= null;
		String							invoiceCreditCode			= "";
		String							invoiceCashCode				= "";
		InvoiceCashMasterBean  			invoiceCashMasterBean		= null;
		InvoiceCashDetailBean			invoiceCashDetailBean		= null;
		ProductQuanHistoryBean			productQuanHistoryBean		= null;
		ProductmasterBean 				productmasterBean			= null;
		ProductmasterBean 				productmasterBeanDb			= null;
		int								hisCode						= 1;
		boolean							chkFlag						= true;
		
		try{
//			invoiceCode 				= EnjoyUtil.nullToStr(request.getParameter("invoiceCode"));
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
			tin 						= this.userBean.getTin();
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			obj 						= new JSONObject();
			invoiceCreditMasterBean		= new InvoiceCreditMasterBean();
			invoiceCreditDetailList		= this.form.getInvoiceCreditDetailList();
			
			/*Begin Section Master*/
			invoiceCreditCode 	= String.valueOf(this.invoiceCreditDao.genId(invoiceType, tin));
			invoiceCashCode 	= String.valueOf(this.invoiceCashDao.genId(invoiceType, tin));
			
			invoiceCreditMasterBean.setInvoiceCode			(invoiceCreditCode);
			invoiceCreditMasterBean.setInvoiceDate			(EnjoyUtils.dateThaiToDb(invoiceDate));
			invoiceCreditMasterBean.setInvoiceType			(invoiceType);
			invoiceCreditMasterBean.setCusCode				(cusCode);
			invoiceCreditMasterBean.setBranchName			(branchName);
			invoiceCreditMasterBean.setSaleUniqueId			(saleUniqueId);
			invoiceCreditMasterBean.setSaleCommission		(saleCommission);
			invoiceCreditMasterBean.setInvoicePrice			(invoicePrice);
			invoiceCreditMasterBean.setInvoicediscount		(invoicediscount);
			invoiceCreditMasterBean.setInvoiceDeposit		(invoiceDeposit);
			invoiceCreditMasterBean.setInvoiceVat			(invoiceVat);
			invoiceCreditMasterBean.setInvoiceTotal			(invoiceTotal);
			invoiceCreditMasterBean.setInvoiceCash			(invoiceCashCode);
			invoiceCreditMasterBean.setUserUniqueId			(String.valueOf(this.userBean.getUserUniqueId()));
			invoiceCreditMasterBean.setInvoiceStatus		("A");
			invoiceCreditMasterBean.setTin					(tin);
			invoiceCreditMasterBean.setRemark				(remark);
			
			this.invoiceCreditDao.insertInvoiceCreditMaster(invoiceCreditMasterBean);
			/*End Section Master*/
			
			/*Begin detail*/
			this.invoiceCreditDao.deleteInvoiceCreditDetail(invoiceCreditCode, tin);
			for(int i=0;i<invoiceCreditDetailList.size();i++){
				bean = invoiceCreditDetailList.get(i);
				if(!bean.getRowStatus().equals(InvoiceCreditMaintananceForm.DEL)){
					bean.setInvoiceCode	(invoiceCreditCode);
					bean.setTin			(tin);
					bean.setSeqDb		(String.valueOf(seqDb));
					this.invoiceCreditDao.insertInvoiceCreditDetail(bean);
					seqDb++;
					
					/*Begin Section รายการสินค้า*/
					productquantityBean = new ProductquantityBean();
					
					productquantityBean.setProductCode(bean.getProductCode());
					productquantityBean.setTin(tin);
					
					quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
					
					if(quantityDb==null){
						quantity =  EnjoyUtils.parseDouble("0.00");
						productquantityBean.setQuantity(String.valueOf(quantity));
						this.productquantityDao.insertProductquantity(productquantityBean);
					}else{
						quantity = EnjoyUtils.parseDouble(quantityDb) - EnjoyUtils.parseDouble(bean.getQuantity());
						productquantityBean.setQuantity(String.valueOf(quantity));
						this.productquantityDao.updateProductquantity(productquantityBean);
					}
					/*End Section รายการสินค้า*/
					
					/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
					productQuanHistoryBean = new ProductQuanHistoryBean();
					productQuanHistoryBean.setFormRef(invoiceCreditCode);
					
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
					productQuanHistoryBean.setQuantityPlus("0.00");
					productQuanHistoryBean.setQuantityMinus(bean.getQuantity());
					productQuanHistoryBean.setQuantityTotal(String.valueOf(quantity));
					
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
					
				}
			}
			/*End detail*/
			
			/*Begin บันทึกลง table ขายเงิดสด*/
			invoiceCashMasterBean = new InvoiceCashMasterBean();
			
			invoiceCashMasterBean.setInvoiceCode		(invoiceCashCode);
			invoiceCashMasterBean.setInvoiceDate		(EnjoyUtils.dateThaiToDb(invoiceDate));
			invoiceCashMasterBean.setInvoiceType		(invoiceType);
			invoiceCashMasterBean.setCusCode			(cusCode);
			invoiceCashMasterBean.setBranchName			(branchName);
			invoiceCashMasterBean.setSaleUniqueId		(saleUniqueId);
			invoiceCashMasterBean.setSaleCommission		(saleCommission);
			invoiceCashMasterBean.setInvoicePrice		(invoicePrice);
			invoiceCashMasterBean.setInvoicediscount	(invoicediscount);
			invoiceCashMasterBean.setInvoiceDeposit		(invoiceDeposit);
			invoiceCashMasterBean.setInvoiceVat			(invoiceVat);
			invoiceCashMasterBean.setInvoiceTotal		(invoiceTotal);
			invoiceCashMasterBean.setInvoiceCredit		(invoiceCreditCode);
			invoiceCashMasterBean.setUserUniqueId		(String.valueOf(this.userBean.getUserUniqueId()));
			invoiceCashMasterBean.setInvoiceStatus		("W");
			invoiceCashMasterBean.setTin				(tin);
			invoiceCashMasterBean.setRemark				(remark);
			
			this.invoiceCashDao.insertInvoiceCashMaster(invoiceCashMasterBean);
			
			this.invoiceCashDao.deleteInvoiceCashDetail(invoiceCashCode, tin);
			seqDb = 1;
			for(int i=0;i<invoiceCreditDetailList.size();i++){
				bean = invoiceCreditDetailList.get(i);
				if(!bean.getRowStatus().equals(InvoiceCreditMaintananceForm.DEL)){
					invoiceCashDetailBean = new InvoiceCashDetailBean();
					
					invoiceCashDetailBean.setInvoiceCode		(invoiceCashCode);
					invoiceCashDetailBean.setTin				(tin);
					invoiceCashDetailBean.setSeqDb				(String.valueOf(seqDb));
					invoiceCashDetailBean.setProductCode		(bean.getProductCode());
					invoiceCashDetailBean.setQuantity			(bean.getQuantity());
					invoiceCashDetailBean.setPricePerUnit		(bean.getPricePerUnit());
					invoiceCashDetailBean.setDiscount			(bean.getDiscount());
					invoiceCashDetailBean.setPrice				(bean.getPrice());
					
					this.invoiceCashDao.insertInvoiceCashDetail(invoiceCashDetailBean);
					seqDb++;
					
				}
			}
			/*End บันทึกลง table ขายเงิดสด*/
			
			commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("invoiceCode"	,invoiceCreditCode);
			obj.put("tin"			,tin);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.info(e.getMessage());
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
		
		JSONObject 				obj 					= null;
		InvoiceCreditDetailBean	invoiceCreditDetailBean	= null;
		String					newSeq					= null;
		
		try{
			
			invoiceCreditDetailBean 	= new InvoiceCreditDetailBean();
			obj 						= new JSONObject();
			newSeq						= EnjoyUtil.nullToStr(this.request.getParameter("newSeq"));
			
			logger.info("[newRecord] newSeq 			:: " + newSeq);
			
			invoiceCreditDetailBean.setRowStatus(InvoiceCreditMaintananceForm.NEW);
			invoiceCreditDetailBean.setSeq(newSeq);
			
			this.form.getInvoiceCreditDetailList().add(invoiceCreditDetailBean);
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
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList	= null;
		
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
			invoiceCreditDetailList	= this.form.getInvoiceCreditDetailList();
			
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
			
			for(InvoiceCreditDetailBean bean:invoiceCreditDetailList){
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
						bean.setRowStatus(InvoiceCreditMaintananceForm.EDIT);
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
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList	= null;
		
		try{
			
			obj 					= new JSONObject();
			seq 					= EnjoyUtil.nullToStr(request.getParameter("seq"));
			invoiceCreditDetailList	= this.form.getInvoiceCreditDetailList();
			
			for(int i=0;i<invoiceCreditDetailList.size();i++){
				InvoiceCreditDetailBean bean = invoiceCreditDetailList.get(i);
				
				if(bean.getSeq().equals(seq)){
					
					if(bean.getRowStatus().equals(InvoiceCreditMaintananceForm.NEW)){
						invoiceCreditDetailList.remove(i);
					}else{
						bean.setRowStatus(InvoiceCreditMaintananceForm.DEL);
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
	   
	   String			productName				= null;
	   String			tin 					= null;
	   List<ComboBean> 	list 					= null;
	   JSONArray 		jSONArray 				= null;
	   JSONObject 		objDetail 				= null;
	
	   try{
		   jSONArray 		= new JSONArray();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   tin				= this.userBean.getTin();
		   
		   logger.info("[getProductNameList] productName 		:: " + productName);
		   logger.info("[getProductNameList] tin 				:: " + tin);
		   
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
	   String							invoiceDate				= null;
	   String							quantity				= null;
	
	   try{
		   obj 				= new JSONObject();
		   productName		= EnjoyUtils.nullToStr(this.request.getParameter("productName"));
		   groupSalePrice 	= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
		   tin				= this.userBean.getTin();
		   invoiceDate		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDate"));
		   quantity 		= EnjoyUtil.replaceComma(request.getParameter("quantity"));
		   
		   logger.info("[getProductDetailByName] productName 		:: " + productName);
		   logger.info("[getProductDetailByName] groupSalePrice 	:: " + groupSalePrice);
		   logger.info("[getProductDetailByName] tin 				:: " + tin);
		   logger.info("[getProductDetailByName] invoiceDate 		:: " + invoiceDate);
		   logger.info("[getProductDetailByName] quantity 			:: " + quantity);
		   
		   productmasterBean 		= this.productDetailsDao.getProductDetailByName(productName, tin);
		   
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
			   
			   discount = this.productDetailsDao.getQuanDiscount(productmasterBean.getProductCode(), quantity, invoiceDate, tin, CREDIT);
			   
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
	   
	   String				cusCode					= null;
	   String				tin 					= null;
	   JSONObject 			obj		 				= null;
	   CustomerDetailsBean	customerDetailsBean		= null;
	   CustomerDetailsBean	customerDetailsBeanDb	= null;
	
	   try{
		   obj 			= new JSONObject();
		   cusCode		= EnjoyUtils.nullToStr(this.request.getParameter("cusCode"));
		   tin			= this.userBean.getTin();
		   
		   logger.info("[getCustomerDetail] cusCode :: " + cusCode);
		   logger.info("[getCustomerDetail] tin 	:: " + tin);
		   
		   customerDetailsBean = new CustomerDetailsBean();
		   customerDetailsBean.setCusCode	(cusCode);
		   customerDetailsBean.setTin		(tin);
		   
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
	   
	   String			saleName			= null;
	   String			tin 				= null;
	   List<ComboBean> 	list 				= null;
       JSONArray 		jSONArray 			= null;
       JSONObject 		objDetail 			= null;
       
	   try{
		   saleName		= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
		   tin			= this.userBean.getTin();
		   jSONArray 	= new JSONArray();
		   
		   logger.info("[getSaleName] saleName 	:: " + saleName);
		   logger.info("[getSaleName] tin 		:: " + tin);
		   
		   list 		= this.userDetailsDao.userFullNameList(saleName, tin, userBean.getUserUniqueId());
		   
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
	   String							saleUniqueId			= "";
	   List<UserPrivilegeBean> 			listUserprivilege   	= null;
	   Hashtable<String, String> 		fUserprivilege			= null;
	   UserDetailsBean 					userDetailsBeanDb		= null;
	   String							tin						= null;
	
	   try{
		   obj 					= new JSONObject();
		   saleName				= EnjoyUtils.nullToStr(this.request.getParameter("saleName"));
		   fUserprivilege		= new Hashtable<String, String>();
		   tin					= this.userBean.getTin();
		   
		   logger.info("[getSaleNameDetail] saleName 	:: " + saleName);
		   logger.info("[getSaleNameDetail] tin 		:: " + tin);
		   
		   listUserprivilege 			= this.userDetailsDao.getUserprivilege();
		   for(UserPrivilegeBean userprivilege : listUserprivilege){
			   fUserprivilege.put(userprivilege.getPrivilegeCode() , userprivilege.getPrivilegeName());
		   }
		   
		   userDetailsBeanDb = this.userDetailsDao.getUserdetailByTin(saleName, tin, fUserprivilege);
		   
		   if(userDetailsBeanDb!=null){
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
		   this.enjoyUtil.writeMSG(obj.toString());
		   
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
	   String					discount				= "";
	   String					tin						= null;
	   ProductquantityBean		productquantityBean		= null;
	   String					inventory				= "0.00";
	   String					invoiceDate				= null;
	   String					quantity				= null;
	
	   try{
		   obj 				= new JSONObject();
		   productCode		= EnjoyUtils.nullToStr(this.request.getParameter("productCode"));
		   groupSalePrice 	= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
		   tin				= this.userBean.getTin();
		   invoiceDate		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDate"));
		   quantity 		= EnjoyUtil.replaceComma(request.getParameter("quantity"));
		   
		   logger.info("[getProductDetailByCode] productCode 		:: " + productCode);
		   logger.info("[getProductDetailByCode] groupSalePrice 	:: " + groupSalePrice);
		   logger.info("[getProductDetailByCode] tin 				:: " + tin);
		   logger.info("[getProductDetailByCode] invoiceDate 		:: " + invoiceDate);
		   logger.info("[getProductDetailByCode] quantity 			:: " + quantity);
		   
		   productmasterBean = new ProductmasterBean();
		   productmasterBean.setProductCode(productCode);
		   productmasterBean.setTin(tin);
		   
		   productmasterBeanDb 		= this.productDetailsDao.getProductDetail(productmasterBean);
		   
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
			   
			   discount = this.productDetailsDao.getQuanDiscount(productmasterBean.getProductCode(), quantity, invoiceDate, tin, CREDIT);
			   
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
		String			invoiceDate		= null;
		String			tin				= null;
		
		try {
			obj 			= new JSONObject();
			productCode 	= EnjoyUtil.nullToStr(this.request.getParameter("productCode"));
			quantity 		= EnjoyUtil.replaceComma(request.getParameter("quantity"));
			invoiceDate		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDate"));
			tin				= this.userBean.getTin();
			
			discount = this.productDetailsDao.getQuanDiscount(productCode, quantity, invoiceDate, tin, CREDIT);
			
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
		
		String				 			invoiceCode					= null;
		JSONObject 						obj 						= null;
		InvoiceCreditMasterBean  		invoiceCreditMasterBean		= null;
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList		= null;
		InvoiceCreditDetailBean			bean						= null;
		double							quantity					= 0;
		ProductquantityBean				productquantityBean			= null;
		String							tin							= null;
		String							quantityDb					= null;
		ProductQuanHistoryBean			productQuanHistoryBean		= null;
		ProductmasterBean 				productmasterBean			= null;
		ProductmasterBean 				productmasterBeanDb			= null;
		int								hisCode						= 1;
		boolean							chkFlag						= true;
		
		try{
			obj 						= new JSONObject();
			invoiceCreditDetailList		= this.form.getInvoiceCreditDetailList();
			invoiceCode 				= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode"));
			tin 						= this.userBean.getTin();
			
			if(!invoiceCode.equals("")){
				invoiceCreditMasterBean		= new InvoiceCreditMasterBean();
				invoiceCreditMasterBean.setInvoiceStatus		("C");
				invoiceCreditMasterBean.setInvoiceCode			(invoiceCode);
				invoiceCreditMasterBean.setTin					(tin);
				
				this.invoiceCreditDao.updateInvoiceCreditMasterStatus(invoiceCreditMasterBean);
				
				/*Begin manage รายการสินค้า*/
				for(int i=0;i<invoiceCreditDetailList.size();i++){
					bean = invoiceCreditDetailList.get(i);
					if(!bean.getRowStatus().equals(InvoiceCreditMaintananceForm.DEL)){
						
						/*Begin Section รายการสินค้า*/
						productquantityBean = new ProductquantityBean();
						
						productquantityBean.setProductCode(bean.getProductCode());
						productquantityBean.setTin(tin);
						
						quantityDb = this.productquantityDao.getProductquantity(productquantityBean);
						
						if(quantityDb==null){
							quantity =  EnjoyUtils.parseDouble("0.00");
							productquantityBean.setQuantity(String.valueOf(quantity));
							this.productquantityDao.insertProductquantity(productquantityBean);
						}else{
							quantity			= EnjoyUtils.parseDouble(quantityDb) +  EnjoyUtils.parseDouble(bean.getQuantity());
							productquantityBean.setQuantity(String.valueOf(quantity));
							this.productquantityDao.updateProductquantity(productquantityBean);
						}
						/*End Section รายการสินค้า*/
						
						/*Begin ส่วนประวัตเพิ่มลดสินค้า*/
						productQuanHistoryBean = new ProductQuanHistoryBean();
						productQuanHistoryBean.setFormRef(invoiceCode);
						
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
						productQuanHistoryBean.setQuantityPlus(bean.getQuantity());
						productQuanHistoryBean.setQuantityMinus("0.00");
						productQuanHistoryBean.setQuantityTotal(String.valueOf(quantity));
						
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
						
					}
				}
				/*End manage รายการสินค้า*/
			}
			
			commit();
			
			obj.put(STATUS			,SUCCESS);
			obj.put("invoiceCode"	,invoiceCode);
			obj.put("tin"			,tin);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onCancel is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			logger.info("[onCancel][End]");
		}
	}	
	
	private void print(){
		logger.info("[print][Begin]");
		   
	   	JSONObject 						jsonObject 				= new JSONObject();
		JSONArray 						jSONArray 				= new JSONArray();
		JSONObject 						objDetail 				= null;
		CompanyDetailsBean 				companyDetailsBean		= new CompanyDetailsBean();
		CompanyDetailsBean 				companyDetailsDb		= null;
		CustomerDetailsBean 			customerDetailsBean		= new CustomerDetailsBean();
		CustomerDetailsBean 			customerDetailsDb		= null;
		InvoiceCreditMasterBean 		invoiceCreditMasterBean = new InvoiceCreditMasterBean();
		InvoiceCreditMasterBean 		invoiceCreditMasterDb 	= null;
		String							cusCode					= null;
		String							tin						= null;
		String							invoiceCode				= null;
		InvoiceCreditDetailBean 		invoiceCreditDetailBean	= new InvoiceCreditDetailBean();
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList = null;
		ViewPdfMainForm					viewPdfMainForm			= null;
		DataOutput 						output 					= null;
		ByteArrayOutputStream			buffer					= null;
		byte[] 							bytes					= null;
		String							invoiceType				= null;
		String							vatDis					= null;
	 
		try{
			invoiceCode 		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceCode"));
			tin					= this.userBean.getTin();
			viewPdfMainForm		= new ViewPdfMainForm();
			vatDis	 			= ConfigFile.getVat() + "%";
   
		    /*Begin รายละเอียดใบกำกับภาษี*/
			invoiceCreditMasterBean.setInvoiceCode	(invoiceCode);
			invoiceCreditMasterBean.setTin			(tin);
			invoiceCreditMasterDb = invoiceCreditDao.getInvoiceCreditMaster(invoiceCreditMasterBean);
			objDetail = new JSONObject();
			objDetail.put("invoiceCode"		, invoiceCreditMasterDb.getInvoiceCode());
			objDetail.put("invoiceDate"		, invoiceCreditMasterDb.getInvoiceDate());
			
			invoiceType = invoiceCreditMasterDb.getInvoiceType();
			objDetail.put("invoiceType"		, invoiceType);
			
			cusCode = invoiceCreditMasterDb.getCusCode();
			objDetail.put("cusCode"			, cusCode);
			objDetail.put("branchName"		, invoiceCreditMasterDb.getBranchName());
			objDetail.put("saleUniqueId"	, invoiceCreditMasterDb.getSaleUniqueId());
			objDetail.put("saleName"		, invoiceCreditMasterDb.getSaleName());
			objDetail.put("saleCommission"	, invoiceCreditMasterDb.getSaleCommission());
			objDetail.put("invoicePrice"	, invoiceCreditMasterDb.getInvoicePrice());
			objDetail.put("invoicediscount"	, invoiceCreditMasterDb.getInvoicediscount());
			objDetail.put("invoiceDeposit"	, invoiceCreditMasterDb.getInvoiceDeposit());
			objDetail.put("invoiceVat"		, invoiceCreditMasterDb.getInvoiceVat());
			objDetail.put("invoiceTotal"	, invoiceCreditMasterDb.getInvoiceTotal());
			objDetail.put("userUniqueId"	, invoiceCreditMasterDb.getUserUniqueId());
			objDetail.put("invoiceCash"		, invoiceCreditMasterDb.getInvoiceCash());
			objDetail.put("invoiceStatus"	, invoiceCreditMasterDb.getInvoiceStatus());
			objDetail.put("invoiceTypeDesc"	, invoiceCreditMasterDb.getInvoiceTypeDesc());
			objDetail.put("remark"			, invoiceCreditMasterDb.getRemark());
			objDetail.put("tin"				, tin);
			objDetail.put("vatDis"			,vatDis);
			
			jsonObject.put("invoiceCreditMaster"	,objDetail);
			/*End รายละเอียดใบกำกับภาษี*/
	
			/*Begin รายละเอียดรายการที่ขาย*/
			invoiceCreditDetailBean.setInvoiceCode	(invoiceCode);
			invoiceCreditDetailBean.setTin			(tin);
			invoiceCreditDetailList = invoiceCreditDao.getInvoiceCreditDetailList(invoiceCreditDetailBean);
			for(InvoiceCreditDetailBean vo:invoiceCreditDetailList){
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
			
			jsonObject.put("invoiceCreditDetailList"	,jSONArray);
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
				customerDetailsBean.setTin(tin);
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
//			String pdfName = "";
//			if("V".equals(invoiceType)){
//				pdfName = "FullSlipCreditPdfForm";
//			}else{
//				pdfName = "FullSlipCreditNoVatPdfForm";
//			}
			String pdfName = "FullSlipCreditPdfForm";//หน้าตาบิลเครดิตแบบมี vat และไม่มี vat หน้าตาเหมือนกัน
			
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
		   tin				= this.userBean.getTin();
		   
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
	
	private void getProductDetailByCodeDis(){
	   logger.info("[getProductDetailByCodeDis][Begin]");
	   
	   String					productCodeDis			= null;
	   String				 	groupSalePrice			= null;
	   JSONObject 				obj		 				= null;
	   ProductmasterBean		productmasterBeanDb		= null;
	   String					pricePerUnit			= "";
	   String					discount				= "";
	   String					tin						= null;
	   ProductquantityBean		productquantityBean		= null;
	   String					inventory				= "0.00";
	   String					invoiceDate				= null;
	   String					quantity				= null;
	
	   try{
		   obj 				= new JSONObject();
		   productCodeDis	= EnjoyUtils.nullToStr(this.request.getParameter("productCodeDis"));
		   groupSalePrice 	= EnjoyUtil.nullToStr(request.getParameter("groupSalePrice"));
		   tin				= this.userBean.getTin();
		   invoiceDate		= EnjoyUtils.nullToStr(this.request.getParameter("invoiceDate"));
		   quantity 		= EnjoyUtil.replaceComma(request.getParameter("quantity"));
		   
		   logger.info("[getProductDetailByCodeDis] productCodeDis 	:: " + productCodeDis);
		   logger.info("[getProductDetailByCodeDis] groupSalePrice 	:: " + groupSalePrice);
		   logger.info("[getProductDetailByCodeDis] tin 				:: " + tin);
		   logger.info("[getProductDetailByCodeDis] invoiceDate 		:: " + invoiceDate);
		   logger.info("[getProductDetailByCodeDis] quantity 			:: " + quantity);
		   
		   productmasterBeanDb 		= this.productDetailsDao.getProductDetailByProductCodeDis(tin, productCodeDis);
		   
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
			   
			   discount = this.productDetailsDao.getQuanDiscount(productmasterBeanDb.getProductCode(), quantity, invoiceDate, tin, CREDIT);
			   
			   obj.put("productCode"	,productmasterBeanDb.getProductCode());
			   obj.put("productName"	,productmasterBeanDb.getProductName());
			   obj.put("pricePerUnit"	,pricePerUnit);
			   
			   productquantityBean = new ProductquantityBean();
			   productquantityBean.setTin(tin);
			   productquantityBean.setProductCode(productmasterBeanDb.getProductCode());
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
		   obj.put(ERR_MSG, 	"getProductDetailByCodeDis is error");
		   e.printStackTrace();
		   logger.info("[getProductDetailByCodeDis] " + e.getMessage());
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[getProductDetailByCodeDis][End]");
	   }
	}

	@Override
	public void destroySession() {
		this.invoiceCreditDao.destroySession();
        this.customerDetailsDao.destroySession();
        this.productDetailsDao.destroySession();
        this.userDetailsDao.destroySession();
        this.companyDetailsDao.destroySession();
        this.productquantityDao.destroySession();
        this.invoiceCashDao.destroySession();
        this.productQuanHistoryDao.destroySession();
	}

	@Override
	public void commit() {
		this.invoiceCreditDao.commit();
        this.customerDetailsDao.commit();
        this.productDetailsDao.commit();
        this.userDetailsDao.commit();
        this.companyDetailsDao.commit();
        this.productquantityDao.commit();
        this.invoiceCashDao.commit();
        this.productQuanHistoryDao.commit();
	}

	@Override
	public void rollback() {
		this.invoiceCreditDao.rollback();
        this.customerDetailsDao.rollback();
        this.productDetailsDao.rollback();
        this.userDetailsDao.rollback();
        this.companyDetailsDao.rollback();
        this.productquantityDao.rollback();
        this.invoiceCashDao.rollback();
        this.productQuanHistoryDao.rollback();
	}
	
}











