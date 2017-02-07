package th.go.stock.app.enjoy.pdf.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.HistoryPurchasedByDealerReportBean;
import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.HistoryPurchasedByDealerReportDao;
import th.go.stock.app.enjoy.dao.InvoiceCashDao;
import th.go.stock.app.enjoy.dao.InvoiceCreditDao;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTest {

	public static void main(String[] args) {
		try {
			writePDF("BillingPdfForm", createJsonObjForBilling(), "D:/motor/PDF/BillingPdfForm.pdf");
//			writePDF("ProductBarcodePdfForm", createJsonObjForProductBarcode(), "D:/motor/PDF/ProductBarcodePdfForm.pdf");
//			writePDF("FullSlipCashPdfForm", createJsonObjForFullSlipCash(), "D:/motor/PDF/FullSlipCashPdfFormA5.pdf");
//			writePDF("FullSlipCreditPdfForm", createJsonObjForFullSlipCredit(), "D:/motor/PDF/FullSlipCreditPdfFormA5.pdf");
//			writePDF("FullSlipCashNoVatPdfForm", createJsonObjForFullSlipCash(), "D:/motor/PDF/FullSlipCashNoVatPdfFormA5.pdf");
//			writePDF("HistoryPurchasedByDealerPdfForm", createJsonObjForHistoryPurchasedByDealer(), "D:/motor/PDF/HistoryPurchasedByDealerPdfForm.pdf");
//			writePDF("UserDetailPdfForm", createJsonObjForUserDetailPdfForm(), "D:/motor/PDF/UserDetailPdfForm.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void writePDF(String formName, JSONObject jsonObject, String pdfPath) throws Exception{
	    String 				formClass					= null;
		Document 			document					= null;
		File 				f 							= null;
		FileOutputStream 	fos 						= null;
		PdfWriter 			writer 						= null;
		PdfFormService 		pdfForm 					= null;
		
		try{
			System.out.println("formName :: " + formName);
			
			formClass					= "th.go.stock.app.enjoy.pdf."+formName;
			document 					= new Document(PageSize.A4);
//			document 					= new Document(PageSize.A4, 5f, 5f, 5f, 5f);
//			document 					= new Document(PageSize.A5);
			f 							= new File(pdfPath);
			fos            				= new FileOutputStream(f.getAbsolutePath());			
			writer 						= PdfWriter.getInstance( document,fos  );
	
			document.addTitle("Strock Form");
			System.out.println(formClass);
	
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
		
			document.open();
			
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
			
			System.out.println("after");
	
			document.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
	
//	public static void writePDFFromFile(String pdfPath) throws Exception{
//		File 				f 							= null;
//		FileOutputStream 	fos 						= null;
//        PdfStamper 			stamp 						= null;
//        InputStream 		inputStream 				= null;
//        PdfReader 			reader 						= null;
//        AcroFields 			form 						= null;
//		
//		try{
//			
//			inputStream 				= (InputStream) new FileInputStream("D://motor//ticket_1.pdf");
//            reader 						= new PdfReader(inputStream);
//            f 							= new File(pdfPath);
//			fos            				= new FileOutputStream(f.getAbsolutePath());
//			stamp 						= new PdfStamper(reader, fos);
//			form 						= stamp.getAcroFields();
//			
//			form.setField("gate", "1");
//			
//			stamp.close();
//			
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			
//		}
//	}
	
	private static JSONObject createJsonObjForProductBarcode(){
		JSONObject 			jsonObject 					= new JSONObject();
		JSONArray 			jSONArray 					= new JSONArray();
	    JSONObject 			objDetail 					= null;
	    
		try{
			objDetail 		= new JSONObject();
		    objDetail.put("productCode"		,"004000200001");
		    objDetail.put("productName"		,"สกรูหัวจมขนาด 1/8 x 3/4");
		    jSONArray.add(objDetail);
		    
		    objDetail 		= new JSONObject();
		    objDetail.put("productCode"		,"004000200002");
		    objDetail.put("productName"		,"สกรูหัวจมขนาด 1/8 x 1");
		    jSONArray.add(objDetail);
		    
		    jsonObject.put("status", "SUCCESS");
		    jsonObject.put("printType", "A");
		    jsonObject.put("detailList", jSONArray);
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
	private static JSONObject createJsonObjForFullSlipCash(){
		   
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
		String						invoiceType				= null;
		String						vatDis					= null;
		InvoiceCashDao 				invoiceCashDao 			= new InvoiceCashDao();
		CompanyDetailsDao 			companyDetailsDao 		= new CompanyDetailsDao();
		CustomerDetailsDao 			customerDetailsDao 		= new CustomerDetailsDao();
	 
		try{
			invoiceCode 		= "INC60-00000001";
			tin 				= "3102400194380";
			vatDis	 			= "7%";
			
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
			objDetail.put("vatDis"			,vatDis);
			
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
				customerDetailsBean.setTin(tin);
				customerDetailsDb = customerDetailsDao.getCustomerDetail(customerDetailsBean);
				objDetail = new JSONObject();
				objDetail.put("cusCode"		, customerDetailsDb.getCusCode());
				objDetail.put("tin"			, customerDetailsDb.getTin());
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
	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonObject;
	}
		
	private static JSONObject createJsonObjForFullSlipCredit(){
		   
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
		String							invoiceType				= null;
		InvoiceCreditDao 				invoiceCreditDao 		= new InvoiceCreditDao();
		CompanyDetailsDao 				companyDetailsDao 		= new CompanyDetailsDao();
		CustomerDetailsDao 				customerDetailsDao 		= new CustomerDetailsDao();
		String							vatDis					= null;
	 
		try{
			invoiceCode 		= "INR60-00000001";
			tin					= "3102400194380";
			vatDis	 			= "7%";//ConfigFile.getVat() + "%";
   
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
   
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return jsonObject;
	}

	
	private static JSONObject createJsonObjForHistoryPurchasedByDealer(){
		JSONObject 									jsonObject 						= new JSONObject();
		JSONArray 									jSONArray 						= new JSONArray();
	    JSONObject 									objDetail 						= null;
	    HistoryPurchasedByDealerReportBean 			bean							= new HistoryPurchasedByDealerReportBean();
	    List<HistoryPurchasedByDealerReportBean> 	historyPurchasedByDealerList 	= null;
	    String										tin								= "";
	    String										reciveDateFrom					= "";
	    String										reciveDateTo					= "";
	    CompanyDetailsBean 							companyDetailsBean				= new CompanyDetailsBean();
	    CompanyDetailsBean 							companyDetailsDb				= null;
	    HistoryPurchasedByDealerReportDao			dao								= new HistoryPurchasedByDealerReportDao();
	    CompanyDetailsDao							companyDetailsDao				= new CompanyDetailsDao();
	    
		try{
			
			tin 			= "3100201067114";
			reciveDateFrom 	= "01/03/2559";
			reciveDateTo 	= "31/03/2559";
			
			jsonObject.put("tin"			,tin);
			jsonObject.put("reciveDateFrom"	,reciveDateFrom);
			jsonObject.put("reciveDateTo"	,reciveDateTo);
			
			
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
			
			/*Begin รายละเอียดรายงาน*/
			bean.setTin(tin);
			bean.setReciveDateFrom(reciveDateFrom);
			bean.setReciveDateTo(reciveDateTo);
			
			historyPurchasedByDealerList = dao.searchByCriteria(bean);
			
			if(historyPurchasedByDealerList!=null){
				for(HistoryPurchasedByDealerReportBean vo:historyPurchasedByDealerList){
					objDetail = new JSONObject();
					
					objDetail.put("vendorName"		,vo.getVendorName());
					objDetail.put("reciveNo"		,vo.getReciveNo());
					objDetail.put("reciveDate"		,vo.getReciveDate());
					objDetail.put("reciveTotal"	,vo.getReciveTotal());
					objDetail.put("reciveDiscount"	,vo.getReciveDiscount());
					
					jSONArray.add(objDetail);
				}
			}
			
			jsonObject.put("historyPurchasedByDealerReportList"	,jSONArray);
			/*End รายละเอียดรายงาน*/
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dao.destroySession();
		    companyDetailsDao.destroySession();
		}
		return jsonObject;
	}
	
	private static JSONObject createJsonObjForUserDetailPdfForm(){
		JSONObject 									jsonObject 						= new JSONObject();
	    
		try{
			
			jsonObject.put("fullName"	,"พิชญาภา เปี่ยมขำดี");
			jsonObject.put("userEmail"	,"pomkmutt@gmail.com");
			jsonObject.put("pwd"		,"CDUqmiYK");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private static JSONObject createJsonObjForBilling(){
		JSONObject 					jsonObject 				= new JSONObject();
		JSONArray 					jSONArray 				= new JSONArray();
	    JSONObject 					objDetail 				= null;
	    CompanyDetailsBean 			companyDetailsBean		= new CompanyDetailsBean();
	    CompanyDetailsBean 			companyDetailsDb		= null;
	    CustomerDetailsBean 		customerDetailsBean		= new CustomerDetailsBean();
	    CustomerDetailsBean 		customerDetailsDb		= null;
	    InvoiceCreditMasterBean 	invoiceCreditMasterBean 	= new InvoiceCreditMasterBean();
	    InvoiceCreditMasterBean 	invoiceCreditMasterSum 	= null;
	    String						cusCode					= null;
	    String						tin						= null;
	    String						invoiceCode				= null;
	    String						invoiceDateFrom				= null;
	    String						invoiceDateTo				= null;
	    String						invoiceType					= null;
	    List<InvoiceCreditMasterBean> invoiceCreditMasterList 	= null;
	    InvoiceCreditDao				invoiceCreditDao			= new InvoiceCreditDao();
	    CustomerDetailsDao			customerDetailsDao		= new CustomerDetailsDao();
	    CompanyDetailsDao			companyDetailsDao		= new CompanyDetailsDao();
	    String						vatDis					= null;
	    
		try{
			
			cusCode 		= EnjoyUtils.nullToStr("CS60-00001");
			invoiceCode		= EnjoyUtils.nullToStr("");
			invoiceType 	= EnjoyUtils.nullToStr("");
			invoiceDateFrom = EnjoyUtils.nullToStr("01/02/2560");
			invoiceDateTo 	= EnjoyUtils.nullToStr("28/02/2560");
			tin	 			= "3102400194380";
			vatDis	 		= "7%";//ConfigFile.getVat() + "%";
			
			/*Begin รายการใบวางบิล*/
			invoiceCreditMasterBean.setCusCode(cusCode);
			invoiceCreditMasterBean.setInvoiceCode(invoiceCode);
			invoiceCreditMasterBean.setInvoiceType(invoiceType);
			invoiceCreditMasterBean.setInvoiceDateForm(invoiceDateFrom);
			invoiceCreditMasterBean.setInvoiceDateTo(invoiceDateTo);
			invoiceCreditMasterBean.setTin(tin);
			invoiceCreditMasterList = invoiceCreditDao.searchForBillingReport(invoiceCreditMasterBean);
			
			for(InvoiceCreditMasterBean vo:invoiceCreditMasterList){
//			for(int i=0;i<100;i++){
//				InvoiceCreditMasterBean vo = invoiceCreditMasterList.get(0);
				objDetail = new JSONObject();
				objDetail.put("invoiceCode"		, vo.getInvoiceCode());
				objDetail.put("invoiceDate"		, vo.getInvoiceDate());
				objDetail.put("invoicePrice"	, vo.getInvoicePrice());
				objDetail.put("invoicediscount"	, vo.getInvoicediscount());
				objDetail.put("invoiceDeposit"	, vo.getInvoiceDeposit());
				objDetail.put("invoiceVat"		, vo.getInvoiceVat());
				objDetail.put("invoiceTotal"	, vo.getInvoiceTotal());
				
				jSONArray.add(objDetail);
			}
			jsonObject.put("invoiceCreditMasterList"	,jSONArray);
			/*End รายการใบวางบิล*/
			
			/*Begin รายละเอียดใบวางบิล*/
			objDetail = new JSONObject();
			invoiceCreditMasterSum = invoiceCreditDao.sumTotalForBillingReport(invoiceCreditMasterBean);
			objDetail.put("sumInvoicePrice"		,invoiceCreditMasterSum.getInvoicePrice());
			objDetail.put("sumInvoicediscount"	,invoiceCreditMasterSum.getInvoicediscount());
			objDetail.put("sumInvoiceDeposit"	,invoiceCreditMasterSum.getInvoiceDeposit());
			objDetail.put("sumInvoiceVat"		,invoiceCreditMasterSum.getInvoiceVat());
			objDetail.put("sumInvoiceTotal"		,invoiceCreditMasterSum.getInvoiceTotal());
			objDetail.put("totalRecord"		,String.valueOf(invoiceCreditMasterList.size()));
			objDetail.put("bullingDate"		,EnjoyUtils.dateToThaiDisplay(EnjoyUtils.currDateThai()));
			objDetail.put("tin"				,tin);
			objDetail.put("vatDis"			,vatDis);
			
			jsonObject.put("billingDetail"	,objDetail);
			/*End รายละเอียดใบวางบิล*/
			
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
		    
		    
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			invoiceCreditDao.destroySession();
		    customerDetailsDao.destroySession();
		    companyDetailsDao.destroySession();
		}
		return jsonObject;
	}
	
}
