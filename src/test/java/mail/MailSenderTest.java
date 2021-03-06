package mail;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class MailSenderTest {
	String title ;
	String message ;
	MailSender mailSender ;
	

	@Before
	public void mailSenderInit() throws InterruptedException, IOException {
		String mailPropertyFilePath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\mail.properties";
		MailProperties mailProperties= new MailProperties(mailPropertyFilePath);
		System.out.println("------------MAIL_PROPERTIES------------------");
		System.out.println("to: "+mailProperties.to);
		System.out.println("from: "+mailProperties.from);
		System.out.println("host: "+mailProperties.host);
		System.out.println("server: "+mailProperties.server);
		System.out.println("user: "+mailProperties.user);
		System.out.println("password: "+mailProperties.password);
		System.out.println("socketFactoryPort: "+mailProperties.socketFactoryPort);
		System.out.println("socketFactoryClass: "+mailProperties.socketFactoryClass);
		System.out.println("auth: "+mailProperties.auth);
		System.out.println("port: "+mailProperties.port);
		System.out.println("----------------------------------------");	
		
		
		 title = "Test Mail";
		 message = "Hello \n"
				+ "GOGGOGOGOGOGO\n"
				+ "Regards\n"
				+ "Me";
		 mailSender = new MailSender(mailProperties);
	}
	
	@Test
	public void mailSenderTest(){
		try{
			mailSender.sendMail(title, message);
		}catch (Exception e) {
			fail("Send mail error");
		} {
			
		}
	}
	
}
