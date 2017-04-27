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

public class LogAnalyzer {
	String logsLocation;
	MailSender mailSender;
	
	public LogAnalyzer(String logsLocation, MailProperties mailProperties){
		this.logsLocation=logsLocation;
		this.mailSender= new MailSender(mailProperties);
	}
	
	
	public void run() throws IOException{
		File directory = new File(logsLocation);
		List<File> files = Arrays.asList(directory.listFiles());
		
		boolean sendEmail = false;
		String title = "[LOG ANALYZER] Raport";
		
		
		StringBuffer messageBuffer = new StringBuffer("");
		

		System.out.println("----------------LOG ANALYZER---------------------");
		for(File file: files){
			System.out.println("File analize: "+file.getName());
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line;
			
			while((line=br.readLine())!=null){
				if(line.contains("s")){
					sendEmail = true;
					messageBuffer.append("\n"+line);
				}
			}
			br.close();
			System.out.println("-------------------------------------");
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
