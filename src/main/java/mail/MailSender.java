package mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {
	private MailProperties mailProperties;
	
	public MailSender(MailProperties mailProperties){
		this.mailProperties=mailProperties;
	}
	
	
	public void sendMail(String title,String message) throws MessagingException{
	      // Get system properties
	      Properties properties = System.getProperties();
	      // Setup mail server
	      properties.put("mail.smtp.host", mailProperties.host);
	      properties.put("mail.smtp.socketFactory.port", mailProperties.socketFactoryPort);
	      properties.put("mail.smtp.socketFactory.class",
	    		  mailProperties.socketFactoryClass);
	      properties.put("mail.smtp.auth", mailProperties.auth);
	      properties.put("mail.smtp.port", mailProperties.port); 
	      
	      // Get the default Session object.
	      Session session = Session.getInstance(properties,
	    		  new javax.mail.Authenticator() {
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				return new PasswordAuthentication(mailProperties.user, mailProperties.password);
	    			}
	    		  });

	      try {
	         // Create a default MimeMessage object.
	         Message mimeMessage = new MimeMessage(session);
	         // Set From: header field of the header.
	         mimeMessage.setFrom(new InternetAddress(mailProperties.from));
	         // Set To: header field of the header.
	         mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailProperties.to));
	         // Set Subject: header field
	         mimeMessage.setSubject(title);
	         // Now set the actual message
	         mimeMessage.setText(message);

	         // Send message
	         Transport.send(mimeMessage);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {	         
	         System.out.println("MAILERROR....\n"+mex.fillInStackTrace().toString());
	         throw(mex);
	      }		
	}
	
}
