package th.go.stock.app.enjoy.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ConfigFile {
	private static final String PADING_CUS_CODE   	= "pading.cusCode";
	private static final String PADING_RECIVE_NO   	= "pading.reciveNo";
	private static final String PADING_INVOICE_CODE = "pading.invoiceCode";
	private static final String PATH_LOG   			= "path.log";
	private static final String OPEN_LOG   			= "open.log";
	private static final String VAT   				= "system.vat";
	private static ConfigFile configFile;
	private static Properties properties ;
	

	public ConfigFile(String fileName) throws Exception {
		try {
			properties = new Properties(); 
			properties.load(new FileInputStream(fileName)); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("IOException");
		}
	} 	
	public static void init(String fileName) throws Exception{
		if (configFile == null) {
			configFile = new ConfigFile(fileName);
		}
	}
	public static Properties getProperties(){
		return properties;
	}
	
	public static String getText( String arg ){
		String result = ConfigFile.getProperties().getProperty( arg );		
	    result = EnjoyUtils.convertDataThai(result);
//System.out.println("result  :: " + result);	
		return result;
	}
	
	public static String getPadingCusCode() {
		return getText(PADING_CUS_CODE);
	}
	
	public static String getPadingReciveNo() {
		return getText(PADING_RECIVE_NO);
	}
	
	public static String getPadingInvoiceCode() {
		return getText(PADING_INVOICE_CODE);
	}
	
	public static String getPathLog() {
		return getText(PATH_LOG);
	}
	
	public static String getOpenLog() {
		return getText(OPEN_LOG);
	}
	public static String getVat() {
		return getText(VAT);
	}
}
