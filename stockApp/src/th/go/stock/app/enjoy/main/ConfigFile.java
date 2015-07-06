package th.go.stock.app.enjoy.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ConfigFile {
	private static final String PADING_SEAT_NO   	= "pading.seatNo";
	private static final String PADING_VERNUM_ID   	= "pading.verifyNum";
	private static final String PADING_ZONE_ID   	= "pading.fieldZoneId";
	private static final String PADING_BOOK_PRICE   = "pading.bookingPrices";
	private static final String PADING_TICKET_ID   	= "pading.ticketId";
	private static final String FILE_PATH   		= "file.path";
	private static final String FILE_NAME   		= "file.name";
	private static final String FILE_EXT   			= "file.extends";
	private static final String PATH_LOG   			= "path.log";
	private static final String OPEN_LOG   			= "open.log";
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
	
	public static String getPadingSeatNo() {
		return getText(PADING_SEAT_NO);
	}
	
	public static String getVerifyNum() {
		return getText(PADING_VERNUM_ID);
	}
	
	public static String getPadingZoneId() {
		return getText(PADING_ZONE_ID);
	}
	
	public static String getPadingBookPrice() {
		return getText(PADING_BOOK_PRICE);
	}
	
	public static String getPadingTicketId() {
		return getText(PADING_TICKET_ID);
	}
	
	public static String getFilePath() {
		return getText(FILE_PATH);
	}
	
	public static String getFileName() {
		return getText(FILE_NAME);
	}
	
	public static String getFileExt() {
		return getText(FILE_EXT);
	}
	
	public static String getPathLog() {
		return getText(PATH_LOG);
	}
	
	public static String getOpenLog() {
		return getText(OPEN_LOG);
	}
}
