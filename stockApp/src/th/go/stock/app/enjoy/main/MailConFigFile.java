package th.go.stock.app.enjoy.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MailConFigFile {
	private static final String MAIL_USER   		= "mail.user";
	private static final String MAIL_PWD   			= "mail.password";
	
	private static MailConFigFile 	configFile;
	private static Properties 		properties ;
	

	public MailConFigFile(String fileName) throws Exception {
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
			configFile = new MailConFigFile(fileName);
		}
	}
	public static Properties getProperties(){
		return properties;
	}
	
	public static String getText( String arg ){
		String result = MailConFigFile.getProperties().getProperty( arg );
		return result;
	}
	
	public static String getMAIL_USER() {
		return getText(MAIL_USER);
	}
	
	public static String getMAIL_PWD() {
		return getText(MAIL_PWD);
	}

}
