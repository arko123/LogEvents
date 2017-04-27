package analize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.mail.handlers.message_rfc822;

import mail.MailProperties;
import mail.MailSender;

public class LogAnalizer {
	String logsLocation;
	MailSender mailSender;
	
	public LogAnalizer(String logsLocation, MailProperties mailProperties){
		this.logsLocation=logsLocation;
		this.mailSender= new MailSender(mailProperties);
	}
	
	
	public void run() throws IOException{
		File directory = new File(logsLocation);
		List<File> files = Arrays.asList(directory);
		
		boolean sendEmail = false;
		String title = "[LOG ANALYZER] Raport";
		
		
		StringBuffer messageBuffer = new StringBuffer("");
		
		
		for(File file: files){
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line;
			
			while((line=br.readLine())!=null){
				if(true){
					sendEmail = true;
					messageBuffer.append("\n"+line);
				}
			}
			br.close();
		}
		
		
		
		if(sendEmail)
			sendEmail(title,messageBuffer.toString());
		
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
