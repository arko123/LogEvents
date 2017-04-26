package mail;

import java.io.IOException;

public class MailSenderTest {

	

	
	public static void main(String[] args) throws InterruptedException, IOException {
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
		
		
		String title = "Test Mail";
		String message = "Hello \n"
				+ "GOGGOGOGOGOGO\n"
				+ "Regards\n"
				+ "Me";
		MailSender mailSender = new MailSender(mailProperties);
		mailSender.sendMail(title, message);
	}
}
