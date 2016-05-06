package th.go.stock.app.enjoy.pdf;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.pdf.utils.EnjoyItext;
import th.go.stock.app.enjoy.pdf.utils.PdfConfig;
import th.go.stock.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ProductBarcodePdfForm extends EnjoyItext implements PdfFormService {
	
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
		System.out.println("[ProductBarcodePdfForm][createForm][Begin]");
		
		JSONArray		detailList			= null;
		
		try{
			
			detailList			= (JSONArray) this.formDataObj.get("detailList");
			
			document.add(this.genDetail(detailList));
			
		}
		catch(DocumentException de){
			de.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			System.out.println("[ProductBarcodePdfForm][createForm][End]");
		}
		
		return document;
	}
	
	private PdfPTable genDetail(JSONArray detailList) throws DocumentException, MalformedURLException, IOException {
		
		float[] 		widths	 		= {25f ,25f ,25f, 25f};
		PdfPTable 		table 			= new PdfPTable(widths);
		float[] 		subW1	 		= {1};
		PdfPTable 		subTab1 		= null;
		JSONObject		detail			= null;
		String			productCode		= null;
		String			productName		= null;
		PdfContentByte 	cb 				= writer.getDirectContent();
		Barcode128 		code128 		= null;
		Image			barCode			= null;
		BaseFont 		bfComic 		= BaseFont.createFont(PdfConfig.FONTNAME, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
		String			printType		= (String) this.formDataObj.get("printType");
		int				maxRow			= 18;
		int				row				= 1;
		int				col				= 4;
		int				mod				= 0;
		int				couColumn		= 1;
		
		/*พิมพ์ซ้ำรายการเดิม*/
		if("A".equals(printType)){
			
			if(detailList.size() > 0){
				mod = detailList.size()%col;
				
				while(row < maxRow){
					for(int i=0;i<detailList.size();i++){
						detail = (JSONObject) detailList.get(i);
						productCode		= (String) detail.get("productCode");
						productName		= (String) detail.get("productName");
						
						System.out.println("[genDetail] productCode :: " + productCode);
						System.out.println("[genDetail] productName :: " + productName);
						
						subTab1 		= new PdfPTable(subW1);
						subTab1.addCell(setCellWB(productName, getFont6Bold(), 1, Element.ALIGN_CENTER, 0));
						
						code128 		= new Barcode128();
						code128.setCode(productCode);
						
						code128.setTextAlignment(Element.ALIGN_CENTER);
						code128.setFont(bfComic);
						code128.setSize(6);
						barCode = code128.createImageWithBarcode(cb, null, null);
						barCode.setBorder(0);
						subTab1.addCell(setCellWB(barCode, 1, Element.ALIGN_CENTER, 0, false, false));
						
						table.addCell(setCell(subTab1, 1));
						
						if(couColumn==4){
							couColumn = 1;
							row++;
						}else{
							couColumn++;
						}
						
					}
				}
				
				if(mod > 0){
//					table.addCell(setCell("", getFont3(), (col-mod),1, Element.ALIGN_LEFT));
					table.addCell(setCellWB("", getFont3(), (col-mod), Element.ALIGN_LEFT, 0));
				}
				
			}else{
				table.addCell(setCellWB("", getFont3(), col, Element.ALIGN_LEFT, 0));
			}
		}else{
			if(detailList.size() > 0){
				mod = detailList.size()%col;
				
				for(int i=0;i<detailList.size();i++){
					detail = (JSONObject) detailList.get(i);
					productCode		= (String) detail.get("productCode");
					productName		= (String) detail.get("productName");
					
					System.out.println("[genDetail] productCode :: " + productCode);
					System.out.println("[genDetail] productName :: " + productName);
					
					subTab1 		= new PdfPTable(subW1);
					subTab1.addCell(setCellWB(productName, getFont6Bold(), 1, Element.ALIGN_CENTER, 0));
					
					code128 		= new Barcode128();
					code128.setCode(productCode);
					
					code128.setTextAlignment(Element.ALIGN_CENTER);
					code128.setFont(bfComic);
					code128.setSize(6);
					barCode = code128.createImageWithBarcode(cb, null, null);
					barCode.setBorder(0);
					subTab1.addCell(setCellWB(barCode, 1, Element.ALIGN_CENTER, 0, false, false));
					
					table.addCell(setCell(subTab1, 1));
					
					if(couColumn==4){
						couColumn = 1;
					}else{
						couColumn++;
					}
				}
				
				if(mod > 0){
//					table.addCell(setCell("", getFont3(), (col-mod),1, Element.ALIGN_LEFT));
					table.addCell(setCellWB("", getFont3(), (col-mod), Element.ALIGN_LEFT, 0));
				}
				
			}else{
				table.addCell(setCellWB("", getFont3(), col, Element.ALIGN_LEFT, 0));
			}
		}
		
		
		
		table.setWidthPercentage(100);
	
		return table;
	}
	
}
