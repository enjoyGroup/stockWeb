package th.go.stock.app.enjoy.pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.pdf.utils.EnjoyItext;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class FullSlipCashPdfForm extends EnjoyItext implements PdfFormService {
	
	private PdfWriter 	writer;
	private JSONObject 	formDataObj;
	
	private void setWriter(PdfWriter writer) {
		this.writer = writer;
	}

	public void setJSONObject(PdfWriter writer, JSONObject jsonObject) {
		this.formDataObj  	= jsonObject;
		setWriter(writer);
		
	}
	
	public Document createForm(Document document) {
		System.out.println("[FullSlipCashPdfForm][createForm][Begin]");
		
		try{
			document.add(this.genHeader());
			document.add(this.brLine());
			document.add(this.genHeader1());
			document.add(this.genCustomerDetail());
			document.add(this.brLine());
			document.add(this.genDetail());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.genTotalCost());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.genFooter());
			
		}
		catch(DocumentException de){
			de.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("[FullSlipCashPdfForm][createForm][End]");
		}
		
		return document;
	}
	
	private PdfPTable genHeader() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {1};
		PdfPTable 	table 			= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  = this.formDataObj;
		JSONObject  companyDetails	= (JSONObject) jsonObjectMain.get("companyDetails");
		String		address			= "";
		
		address = " โทร." + getText(companyDetails, "tel") 
				+ " Fax." + getText(companyDetails, "fax") 
				+ " Email." + getText(companyDetails, "email");
		
		table.addCell(setCellWB(getText(companyDetails, "companyName"), getFont11Bold(), 1, Element.ALIGN_CENTER, 0));
		table.addCell(setCellWB(getText(companyDetails, "address"), getFont8(), 1, Element.ALIGN_CENTER, 0));
		table.addCell(setCellWB(address, getFont8(), 1, Element.ALIGN_CENTER, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genHeader1() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {15f,15f ,40f ,15f,15f};
		PdfPTable 	table 				= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  	= this.formDataObj;
		JSONObject  invoiceCashMaster	= (JSONObject) jsonObjectMain.get("invoiceCashMaster");
		
		table.addCell(setCellWB("เลขประจำตัวผู้เสียภาษี", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "tin"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("ใบกำกับภาษี/ใบเสร็จรับเงิน", getFont10Bold(), 1, Element.ALIGN_CENTER, 0));
		table.addCell(setCellWB("เลขที่ใบเสร็จ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceCode"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("วันที่ใบเสร็จ", getFont8Bold(), 4, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceDate"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genCustomerDetail() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {15f,85f};
		PdfPTable 	table 				= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  	= this.formDataObj;
		JSONObject  customerDetails		= (JSONObject) jsonObjectMain.get("customerDetails");
		
		if(customerDetails!=null){
			table.addCell(setCellWB("ชื่อลูกค้า ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
			table.addCell(setCellWB(getText(customerDetails, "cusName") + " " + getText(customerDetails, "cusSurname"), getFont8(), 1, Element.ALIGN_LEFT, 0));
			table.addCell(setCellWB("ที่อยู่ลูกค้า ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
			table.addCell(setCellWB(getText(customerDetails, "address"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		}else{
			table.addCell(setCellWB("ชื่อลูกค้า ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
			table.addCell(setCellWB("-", getFont8(), 1, Element.ALIGN_LEFT, 0));
			table.addCell(setCellWB("ที่อยู่ลูกค้า ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
			table.addCell(setCellWB("-", getFont8(), 1, Element.ALIGN_LEFT, 0));
		}
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genDetail() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 				= {5f ,50f ,15f ,15f ,15f};
		PdfPTable 	table 					= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  		= this.formDataObj;
		JSONArray 	invoiceCashDetailList 	= (JSONArray) jsonObjectMain.get("invoiceCashDetailList");
		JSONObject 	invoiceCashDetail  		= null;
		
		table.addCell(setCell("ลำดับ", getFont10Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("รายละเอียด", getFont10Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("จำนวน", getFont10Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ราคาต่อหน่วย", getFont10Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("จำนวนเงิน", getFont10Bold(), 1, 1, Element.ALIGN_CENTER));
		
		for(int i=0;i<invoiceCashDetailList.size();i++){
			invoiceCashDetail = (JSONObject) invoiceCashDetailList.get(i);
			table.addCell(setCell(String.valueOf((i+1)),   getFont8(), 1, 1, Element.ALIGN_CENTER));
			table.addCell(setCell(getText(invoiceCashDetail, "productName"),   getFont8(), 1, 1, Element.ALIGN_LEFT));
			table.addCell(setCell(getText(invoiceCashDetail, "quantity"),   getFont8(), 1, 1, Element.ALIGN_CENTER));
			table.addCell(setCell(getText(invoiceCashDetail, "pricePerUnit"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
			table.addCell(setCell(getText(invoiceCashDetail, "price"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
		}

		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genTotalCost() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {8f ,62f ,20f ,15f};
		PdfPTable 	table 				= new PdfPTable(widths);
		float[] 	sub_w	 			= {1};
		PdfPTable 	tableSub 			= new PdfPTable(sub_w);
		JSONObject 	jsonObjectMain  	= this.formDataObj;
		JSONObject  invoiceCashMaster	= (JSONObject) jsonObjectMain.get("invoiceCashMaster");
		
		table.addCell(setCellWB("รวมจำรวนเงิน ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoicePrice"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.addCell(setCellWB("ภาษีมูลค่าเพิ่ม 7% ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceVat"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		tableSub.addCell(setCell(EnjoyUtils.displayAmountThai(getText(invoiceCashMaster, "invoiceTotal")), getFont7(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCellWB(tableSub, 2, Element.ALIGN_LEFT, 0, false, false));
		table.addCell(setCellWB("จำนวนเงินรวมทั้งสิ้น ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceTotal"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.addCell(setCellWB("หมายเหต ", getFont8Bold(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "remark"), getFont8(), 3, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genFooter() throws DocumentException, MalformedURLException, IOException {
		float[] 	widths	 	= {50f, 30f, 20f};
		PdfPTable 	table 		= new PdfPTable(widths);
		
		table.addCell(setCellWB("", getFont8(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB("ผู้รับเงิน (ลายเซ็นต์)", getFont8(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB("ผู้รับสินค้า (ลายเซ็นต์)", getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}

	public PdfPTable brLine() throws DocumentException, MalformedURLException, IOException {
		
		PdfPTable 	table 			= new PdfPTable(1);
		
		table.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
}
