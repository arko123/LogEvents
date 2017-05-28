package main;

import java.io.IOException;

public class LogEventsTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		//String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_local.properties"; 
		//String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_http.properties"; 
		String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_ssh.properties"; 
		
		String mailPropertyFilePath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\mail.properties";
		 
		String analizePropertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\analize.properties"; 
		
		
		LogEvents logEvents = new LogEvents(propertiesPath,mailPropertyFilePath,analizePropertiesPath);
		logEvents.run();
	}

}
