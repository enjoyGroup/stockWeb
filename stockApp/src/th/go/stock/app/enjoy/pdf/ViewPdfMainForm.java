package th.go.stock.app.enjoy.pdf;

import java.io.ByteArrayOutputStream;

import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.pdf.utils.PdfFormService;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

public class ViewPdfMainForm {
	
	public ByteArrayOutputStream writeTicketPDF(String formName, JSONObject jsonObject, String title) throws Exception{
	    String 					formClass					= null;
		Document 				document					= null;
		PdfWriter 				writer 						= null;
		PdfFormService 			pdfForm 					= null;
		ByteArrayOutputStream 	buffer 						= null;
		
		try{
			System.out.println("formName 	:: " + formName);
			System.out.println("title 		:: " + title);
			
			formClass					= "th.go.stock.app.enjoy.pdf."+formName;
			document 					= new Document(PageSize.A4);

			buffer 						=	new ByteArrayOutputStream();
			writer 						=	PdfWriter.getInstance( document, buffer );
	
			document.addTitle(title);
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
			document.open();
					
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
	
			document.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    formClass					= null;
			document					= null;
			writer 						= null;
			pdfForm 					= null;
			jsonObject 					= null;
		}
		return buffer;
	}
	
	public ByteArrayOutputStream writeTicketPDFA5(String formName, JSONObject jsonObject, String title) throws Exception{
	    String 					formClass					= null;
		Document 				document					= null;
		PdfWriter 				writer 						= null;
		PdfFormService 			pdfForm 					= null;
		ByteArrayOutputStream 	buffer 						= null;
		
		try{
			System.out.println("formName 	:: " + formName);
			System.out.println("title 		:: " + title);
			
			formClass					= "th.go.stock.app.enjoy.pdf."+formName;
			document 					= new Document(PageSize.A5);

			buffer 						=	new ByteArrayOutputStream();
			writer 						=	PdfWriter.getInstance( document, buffer );
	
			document.addTitle(title);
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
			document.open();
					
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
	
			document.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    formClass					= null;
			document					= null;
			writer 						= null;
			pdfForm 					= null;
			jsonObject 					= null;
		}
		return buffer;
	}
	
	public ByteArrayOutputStream writeTicketPDFForBarCode(String formName, JSONObject jsonObject, String title) throws Exception{
	    String 					formClass					= null;
		Document 				document					= null;
		PdfWriter 				writer 						= null;
		PdfFormService 			pdfForm 					= null;
		ByteArrayOutputStream 	buffer 						= null;
		
		try{
			System.out.println("formName 	:: " + formName);
			System.out.println("title 		:: " + title);
			
			formClass					= "th.go.stock.app.enjoy.pdf."+formName;
			document 					= new Document(PageSize.A4, 5f, 5f, 5f, 5f);

			buffer 						=	new ByteArrayOutputStream();
			writer 						=	PdfWriter.getInstance( document, buffer );
	
			document.addTitle(title);
			Class c 					= 	Class.forName(formClass);
			pdfForm 	        		= 	(PdfFormService) c.newInstance();
			document.open();
					
			pdfForm.setJSONObject(writer, jsonObject);
			pdfForm.createForm(document);
	
			document.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		    formClass					= null;
			document					= null;
			writer 						= null;
			pdfForm 					= null;
			jsonObject 					= null;
		}
		return buffer;
	}
	
	
}
