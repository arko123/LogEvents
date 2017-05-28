package analize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import mail.MailProperties;
import mail.MailSender;

public class LogAnalyzer {
	String logsLocation;
	MailSender mailSender;
	String find = "test";
	
	public LogAnalyzer(String logsLocation, MailProperties mailProperties){
		this.logsLocation=logsLocation;
		this.mailSender= new MailSender(mailProperties);
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
					if(line.contains(find)){
						sendEmail = true;
						messageBuffer.append("\n"+line);
						System.out.println("Event found "+ find + "in  file: "+file.getName());
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
