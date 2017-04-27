package analize;

import java.io.IOException;

import mail.MailProperties;
import main.LogEvents;

public class LogAnalizerTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		//String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_local.properties"; 
		String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_http.properties"; 
		
		String mailPropertyFilePath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\mail.properties";
		MailProperties mailProperties= new MailProperties(mailPropertyFilePath);
		LogAnalyzer logEvents = new LogAnalyzer(propertiesPath,mailProperties);
		logEvents.run();
	}
}
