package th.go.stock.app.enjoy.pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.pdf.header.FullSlipCashNoVatHeader;
import th.go.stock.app.enjoy.pdf.utils.EnjoyItext;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class FullSlipCashNoVatPdfForm extends EnjoyItext implements PdfFormService {
	
	private PdfWriter 				writer;
	private JSONObject 				formDataObj;
	private FullSlipCashNoVatHeader header;
	
	private void setWriter(PdfWriter writer) {
		this.writer = writer;
	}

	public void setJSONObject(PdfWriter writer, JSONObject jsonObject) {
		this.formDataObj  	= jsonObject;
		this.header			= new FullSlipCashNoVatHeader(jsonObject);
		setWriter(writer);
		writer.setPageEvent(this.header);
		
	}
	
	public Document createForm(Document document) {
		System.out.println("[FullSlipCashNoVatPdfForm][createForm][Begin]");
		
		try{
			document.add(this.genHeader1());
			
			JSONObject  customerDetails		= (JSONObject) this.formDataObj.get("customerDetails");
			if(null!=customerDetails){
				document.add(this.genCustomerDetail(customerDetails));
			}
			document.add(this.brLine());
			document.add(this.genDetail());
			document.add(this.brLine());
			document.add(this.brLine());
			document.add(this.genTotalCost());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.brLine());
//			document.add(this.genFooter());
			
		}
		catch(DocumentException de){
			de.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("[FullSlipCashNoVatPdfForm][createForm][End]");
		}
		
		return document;
	}
	
	private PdfPTable genHeader1() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {22f,28f ,25f,25f};
		PdfPTable 	table 				= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  	= this.formDataObj;
		JSONObject  invoiceCashMaster	= (JSONObject) jsonObjectMain.get("invoiceCashMaster");
		
		table.addCell(setCellWB("ใบกำกับภาษี/ใบเสร็จรับเงิน", getFont10Bold(), 4, Element.ALIGN_CENTER, 0));
		
		table.addCell(setCellWB("เลขที่ใบเสร็จ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceCode"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("วันที่ใบเสร็จ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceDate"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genCustomerDetail(JSONObject customerDetails) throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {12f,88f};
		PdfPTable 	table 				= new PdfPTable(widths);
		
		table.addCell(setCellWB("ชื่อลูกค้า    : ", getFont8Bold(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB(getText(customerDetails, "cusName") + " " + getText(customerDetails, "cusSurname"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("ที่อยู่ลูกค้า  : ", getFont8Bold(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB(getText(customerDetails, "address"), getFont8(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genDetail() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 				= {7f ,28f ,17f ,16f ,16f ,16f};
		PdfPTable 	table 					= new PdfPTable(widths);
		JSONObject 	jsonObjectMain  		= this.formDataObj;
		JSONArray 	invoiceCashDetailList 	= (JSONArray) jsonObjectMain.get("invoiceCashDetailList");
//		JSONObject  invoiceCashMaster		= (JSONObject) jsonObjectMain.get("invoiceCashMaster");
		JSONObject 	invoiceCashDetail  		= null;
		
		table.addCell(setCell("ลำดับ", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("สินค้า", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ปริมาณ", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ราคาต่อหน่วย", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ส่วนลด(%)", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCell("ราคาขาย", getFont8Bold(), 1, 1, Element.ALIGN_CENTER));
		
		for(int i=0;i<invoiceCashDetailList.size();i++){
			invoiceCashDetail = (JSONObject) invoiceCashDetailList.get(i);
			table.addCell(setCell(String.valueOf((i+1)),   getFont8(), 1, 1, Element.ALIGN_CENTER));
			table.addCell(setCell(getText(invoiceCashDetail, "productName"),   getFont8(), 1, 1, Element.ALIGN_LEFT));
			table.addCell(setCell(getText(invoiceCashDetail, "quantity") + " " + getText(invoiceCashDetail, "unitName"),   getFont8(), 1, 1, Element.ALIGN_CENTER));
			table.addCell(setCell(getText(invoiceCashDetail, "pricePerUnit"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
			table.addCell(setCell(getText(invoiceCashDetail, "discount"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
			table.addCell(setCell(getText(invoiceCashDetail, "price"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
		}
		
//		table.addCell(setCell("จำนวนเงินรวมทั้งสิ้น",   getFont8Bold(), 5, 1, Element.ALIGN_RIGHT));
//		table.addCell(setCell(getText(invoiceCashMaster, "invoiceTotal"),   getFont8(), 1, 1, Element.ALIGN_RIGHT));
		
		table.setHeaderRows(1);
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genTotalCost() throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 			= {10f ,55f ,24f ,16f};
		PdfPTable 	table 				= new PdfPTable(widths);
		float[] 	sub_w	 			= {1};
		PdfPTable 	tableSub 			= new PdfPTable(sub_w);
		JSONObject 	jsonObjectMain  	= this.formDataObj;
		JSONObject  invoiceCashMaster	= (JSONObject) jsonObjectMain.get("invoiceCashMaster");
//		String		vat					= ConfigFile.getVat() + "%";
		
		table.addCell(setCellWB("รวมจำรวนเงิน ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoicePrice"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.addCell(setCellWB("หักส่วนลด ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoicediscount"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
//		table.addCell(setCellWB("ภาษีมูลค่าเพิ่ม " + vat + " ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
//		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceVat"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.addCell(setCellWB("หักมัดจำ ", getFont8Bold(), 3, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceDeposit"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		tableSub.addCell(setCell(EnjoyUtils.displayAmountThai(getText(invoiceCashMaster, "invoiceTotal")), getFont8(), 1, 1, Element.ALIGN_CENTER));
		table.addCell(setCellWB(tableSub, 2, Element.ALIGN_LEFT, 0, false, false));
		table.addCell(setCellWB("จำนวนเงินรวมทั้งสิ้น ", getFont8Bold(), 1, Element.ALIGN_RIGHT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "invoiceTotal"), getFont8(), 1, Element.ALIGN_RIGHT, 0));
		
		table.addCell(setCellWB("", getFont8Bold(), 4, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("หมายเหต ", getFont8Bold(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB(getText(invoiceCashMaster, "remark"), getFont8(), 3, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
//	private PdfPTable genFooter() throws DocumentException, MalformedURLException, IOException {
//		float[] 	widths	 	= {75f, 25f};
//		PdfPTable 	table 		= new PdfPTable(widths);
//		
//		table.addCell(setCellWB("ผู้รับเงิน (ลายเซ็นต์)", getFont8(), 1, Element.ALIGN_RIGHT, 0));
//		table.addCell(setCellWB("ผู้รับสินค้า (ลายเซ็นต์)", getFont8(), 1, Element.ALIGN_RIGHT, 0));
//		
//		table.setWidthPercentage(100);
//	
//		return table;
//	}

	public PdfPTable brLine() throws DocumentException, MalformedURLException, IOException {
		
		PdfPTable 	table 			= new PdfPTable(1);
		
		table.addCell(setCellWB("", getFont12Bold(), 1, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
}
