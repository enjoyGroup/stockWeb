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
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.HistoryPurchasedByDealerReportDao;
import th.go.stock.app.enjoy.dao.InvoiceCashDao;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTest {

	public static void main(String[] args) {
		try {
//			writePDF("ProductBarcodePdfForm", createJsonObjForProductBarcode(), "D:/motor/PDF/ProductBarcodePdfForm.pdf");
//			writePDF("FullSlipCashPdfForm", createJsonObjForFullSlip(), "D:/motor/PDF/FullSlipCashPdfFormA5.pdf");
//			writePDF("FullSlipCashNoVatPdfForm", createJsonObjForFullSlip(), "D:/motor/PDF/FullSlipCashNoVatPdfFormA5.pdf");
//			writePDF("HistoryPurchasedByDealerPdfForm", createJsonObjForHistoryPurchasedByDealer(), "D:/motor/PDF/HistoryPurchasedByDealerPdfForm.pdf");
			writePDF("UserDetailPdfForm", createJsonObjForUserDetailPdfForm(), "D:/motor/PDF/UserDetailPdfForm.pdf");

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
		    objDetail.put("productCode"		,"001");
		    objDetail.put("productName"		,"เครื่องดูดฝุ่น 1");
		    jSONArray.add(objDetail);
		    
		    objDetail 		= new JSONObject();
		    objDetail.put("productCode"		,"002");
		    objDetail.put("productName"		,"เครื่องดูดฝุ่น 2");
		    jSONArray.add(objDetail);
		    
		    objDetail 		= new JSONObject();
		    objDetail.put("productCode"		,"003");
		    objDetail.put("productName"		,"เครื่องดูดฝุ่น 3");
		    jSONArray.add(objDetail);
		    
		    jsonObject.put("status", "SUCCESS");
		    jsonObject.put("radPrint", "S");
		    jsonObject.put("detailList", jSONArray);
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	private static JSONObject createJsonObjForFullSlip(){
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
	    InvoiceCashDao				invoiceCashDao			= new InvoiceCashDao();
	    CustomerDetailsDao			customerDetailsDao		= new CustomerDetailsDao();
	    CompanyDetailsDao			companyDetailsDao		= new CompanyDetailsDao();
	    
		try{
			invoiceCode = "INC59-00000001";
			
			/*Begin รายละเอียดใบกำกับภาษี*/
			invoiceCashMasterBean.setInvoiceCode(invoiceCode);
			invoiceCashMasterDb = invoiceCashDao.getInvoiceCashMaster(invoiceCashMasterBean);
			objDetail = new JSONObject();
			objDetail.put("invoiceCode"		, invoiceCashMasterDb.getInvoiceCode());
			objDetail.put("invoiceDate"		, invoiceCashMasterDb.getInvoiceDate());
			objDetail.put("invoiceType"		, invoiceCashMasterDb.getInvoiceType());
			
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
			
			tin = invoiceCashMasterDb.getTin();
			objDetail.put("tin"			, tin);
			
			jsonObject.put("invoiceCashMaster"	,objDetail);
			/*End รายละเอียดใบกำกับภาษี*/
			
			/*Begin รายละเอียดรายการที่ขาย*/
			invoiceCashDetailBean.setInvoiceCode(invoiceCode);
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
				customerDetailsBean.setCusCode("CS-00001");
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
			invoiceCashDao.destroySession();
		    customerDetailsDao.destroySession();
		    companyDetailsDao.destroySession();
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
	
}
