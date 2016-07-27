package th.go.stock.app.enjoy.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.MailConFigFile;

public class SendMail {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(SendMail.class);
	
	public void sendMail(String fullName, String userEmail, String userPwd, String email) throws EnjoyException{
		final String username = MailConFigFile.getMAIL_USER();
		final String password = MailConFigFile.getMAIL_PWD();
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(username));
			message.setFrom(new InternetAddress("Enjoy System"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject("แจ้ง password ของคุณ " + fullName);
			message.setText("เรียนคุณ " + fullName
				+ "\n\n E-mail : " + userEmail
				+ "\n\n Password : " + userPwd);
 
			Transport.send(message);
 
			logger.info("Send mail done !!");
 
		} catch (MessagingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการส่ง E-mai (E-mail ปลายทางไม่ถูกต้อง)");
		}catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการส่ง E-mai");
		}
	}
	
}
