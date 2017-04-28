package analyze;

import java.io.IOException;

import analize.LogAnalyzer;
import mail.MailProperties;

public class LogAnalyzerTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		//String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_local.properties"; 
		String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_http.properties"; 
		
		String mailPropertyFilePath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\mail.properties";
		MailProperties mailProperties= new MailProperties(mailPropertyFilePath);
		LogAnalyzer logEvents = new LogAnalyzer(propertiesPath,mailProperties);
		logEvents.run();
	}
}
