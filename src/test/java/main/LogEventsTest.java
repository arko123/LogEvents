package main;

import java.io.IOException;

public class LogEventsTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		//String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_local.properties"; 
		String propertiesPath = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\properties\\download_http.properties"; 
		
		LogEvents logEvents = new LogEvents(propertiesPath);

		logEvents.run();
	}

}
