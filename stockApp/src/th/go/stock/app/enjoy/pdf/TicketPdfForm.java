package th.go.stock.app.enjoy.pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.pdf.utils.EnjoyItext;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class TicketPdfForm extends EnjoyItext implements PdfFormService {
	
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
		System.out.println("[SlipPdfForm][createForm][Begin]");
		
		JSONArray		detailList			= null;
		JSONObject		detail				= null;
		
		try{
			
			detailList			= (JSONArray) this.formDataObj.get("detailList");
			
			for(int i=0;i<detailList.size();i++){
				detail = (JSONObject) detailList.get(i);
				
				document.add(this.genHeader(detail));
				document.add(this.brLine(100f));
				document.add(this.genDetail(detail));
				document.add(this.brLine(140f));
				document.add(this.genFooter(detail));
				
				document.newPage();
			}
			
		}
		catch(DocumentException de){
			de.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("[SlipPdfForm][createForm][End]");
		}
		
		return document;
	}
	
	private PdfPTable genHeader(JSONObject detail) throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {60f ,10f ,10f, 10f, 10f};
		PdfPTable 	table 			= new PdfPTable(widths);
		String		gate			= "1";
		String		fieldZoneName	= (String) detail.get("fieldZoneName");
		String		seatRow			= (String) detail.get("seatRow");
		String		seatingNo		= (String) detail.get("seatingNo");
		String		awayTeamNameEN	= (String) detail.get("awayTeamNameEN");
		String		matchTime		= (String) detail.get("matchTime");
		String		matchDate		= (String) detail.get("matchDate");
		
		
		table.addCell(setCellWB("THAI PREMEIER LEAGE", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB("", getFont3(), 4, Element.ALIGN_LEFT, 0));
		
		table.addCell(setCellWB("", getFont3(), 1, Element.ALIGN_LEFT, 0));
		table.addCell(setCellWB(gate, getFont3(), 1, Element.ALIGN_LEFT, 0));//ประตู
		table.addCell(setCellWB(fieldZoneName, getFont3(), 1, Element.ALIGN_LEFT, 0));//อัฒจันทร์
		table.addCell(setCellWB(seatRow, getFont3(), 1, Element.ALIGN_LEFT, 0));//แถว
		table.addCell(setCellWB(seatingNo, getFont3(), 1, Element.ALIGN_LEFT, 0));//ที่นั่ง
		
		table.addCell(setCellWB(awayTeamNameEN + "         " + matchTime + "           " + matchDate, getFont3(), 5, Element.ALIGN_LEFT, 0));
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genDetail(JSONObject detail) throws DocumentException, MalformedURLException, IOException {
		
		float[] 	widths	 		= {1};
		PdfPTable 	table 			= new PdfPTable(widths);
		float[] 	subW1	 		= {10f ,10f ,10f, 10f};
		PdfPTable 	subTab1 		= new PdfPTable(subW1);
		float[] 	subW2	 		= {20f ,20f};
		PdfPTable 	subTab2 		= new PdfPTable(subW2);
		PdfPCell 	cell			= null;
		String		gate			= "1";
		String		fieldZoneName	= (String) detail.get("fieldZoneName");
		String		seatRow			= (String) detail.get("seatRow");
		String		seatingNo		= (String) detail.get("seatingNo");
		String		awayTeamNameEN	= (String) detail.get("awayTeamNameEN");
		String		matchTime		= (String) detail.get("matchTime");
		String		matchDate		= (String) detail.get("matchDate");
		
		//แถว 1
		subTab1.addCell(setCellWB("THAI PREMEIER LEAGE", getFont4Bold(), 4, Element.ALIGN_LEFT, 0));
		
		//แถว 2
		subTab1.addCell(setCellWB(gate, getFont4Bold(), 1, Element.ALIGN_LEFT, 0));
		subTab1.addCell(setCellWB(fieldZoneName, getFont4Bold(), 1, Element.ALIGN_LEFT, 0));
		subTab1.addCell(setCellWB(seatRow, getFont4Bold(), 1, Element.ALIGN_LEFT, 0));
		subTab1.addCell(setCellWB(seatingNo, getFont4Bold(), 1, Element.ALIGN_LEFT, 0));
		
		//แถว 3
		subTab1.addCell(setCellWB(awayTeamNameEN, getFont4Bold(), 4, Element.ALIGN_LEFT, 0));
		
		//แถว 4
		subTab2.addCell(setCellWB(matchTime, getFont3(), 1, Element.ALIGN_LEFT, 0));
		subTab2.addCell(setCellWB(matchDate, getFont3(), 1, Element.ALIGN_LEFT, 0));
		subTab1.addCell(setCell(subTab2, 4, 0));
		
		cell = setCell(subTab1, 1, 0);
		cell.setRotation(270);
		cell.setFixedHeight(100f);
		table.addCell(cell);
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
	private PdfPTable genFooter(JSONObject detail) throws DocumentException, MalformedURLException, IOException {
		
		float[] 		widths	 		= {1};
		PdfPTable 		table 			= new PdfPTable(widths);
		PdfContentByte 	cb 				= writer.getDirectContent();
		Barcode128 		code128 		= new Barcode128();
		String			ticketId		= (String) detail.get("ticketId");
		Image			barCode			= null;
		PdfPCell 		cell 			= null;
		
		code128.setCode(ticketId);
		
		barCode = code128.createImageWithBarcode(cb, null, null);
		barCode.setBorder(0);
		
		cell = new PdfPCell(barCode);
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		table.addCell(cell);
		
		table.setWidthPercentage(100);
	
		return table;
	}

	public PdfPTable brLine(float h) throws DocumentException, MalformedURLException, IOException {
		
		PdfPTable 	table 			= new PdfPTable(1);
		PdfPCell 	cell			= null;
		
		cell	= setCellWB("", getFont3(), 1, Element.ALIGN_LEFT, 0);
		cell.setFixedHeight(h);
		table.addCell(cell);
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
}
