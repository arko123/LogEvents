package analize;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mail.MailProperties;
import mail.MailSender;

public class LogAnalizer {
	String logsLocation;
	MailSender mailSender;
	
	public LogAnalizer(String logsLocation, MailProperties mailProperties){
		this.logsLocation=logsLocation;
		this.mailSender= new MailSender(mailProperties);
	}
	
	
	public void run(){
		File directory = new File(logsLocation);
		List<File> files = Arrays.asList(directory);
		
		
		
		
		clearFiles(files);
	}
	
	
	private void clearFiles(List<File> files){
		for(File file:files){
			if (file.exists()){
			     file.delete();
			 }  	
		}
	}
	
	private void sendEmail(String title, String message){
		mailSender.sendMail(title, message);
	}
	
}
