package download;

import java.io.IOException;

public class GetLogsOverHttpTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		String fileURL = "http://student.agh.edu.pl/~wbachta/ZTW/Start.txt";
		String saveDir ="C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\to\\";

		GetLogsOverHttp getLogs = new GetLogsOverHttp(fileURL, saveDir);
		getLogs.getLatestLogs();
		
		Thread.sleep(60000);
		
		getLogs.getLatestLogs();
	}

}
