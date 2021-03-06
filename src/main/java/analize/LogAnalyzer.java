package analize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;

import mail.MailProperties;
import mail.MailSender;

public class LogAnalyzer {
	String logsLocation;
	MailSender mailSender;
	String searchRegex ;
	
	
	public LogAnalyzer(String logsLocation, MailProperties mailProperties, AnalizeProperties analizeProperties){
		this.logsLocation=logsLocation;
		this.mailSender= new MailSender(mailProperties);
		this.searchRegex = analizeProperties.search_regex;
	}
	
	
	public void run(){
		File directory = new File(logsLocation);
		List<File> files = Arrays.asList(directory.listFiles());
		
		boolean sendEmail = false;
		String title = "[LOG ANALYZER] Raport";
		
		
		StringBuffer messageBuffer = new StringBuffer("");
		

		System.out.println("----------------LOG ANALYZER---------------------");
		for(File file: files){
			System.out.println("File to analyze: "+file.getName());
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(file));

				String line;
				
				while((line=br.readLine())!=null){
					if(line.matches(searchRegex)){
						sendEmail = true;
						messageBuffer.append("\n"+line);
						System.out.println("Event found "+ searchRegex + "in  file: "+file.getName());
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
	            System.out.println("FileNotFoundException "+ e.fillInStackTrace().toString());
			} catch (IOException e) {
	            System.out.println("IOException " + e.fillInStackTrace().toString());
			}
		}
		

		System.out.println("-------------------------------------");
		
		if(sendEmail)
			try {
				sendEmail(title,messageBuffer.toString());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		
		clearFiles(files);
	}
	
	
	private void clearFiles(List<File> files){
		for(File file:files){
			if (file.exists()){
			     file.delete();
			 }  	
		}
	}
	
	private void sendEmail(String title, String message) throws MessagingException{
		mailSender.sendMail(title, message);
	}
	
}
