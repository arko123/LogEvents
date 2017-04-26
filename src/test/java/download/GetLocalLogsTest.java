package download;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetLocalLogsTest {

	public static void main(String[] args) throws ParseException {
		String pathFrom = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\from\\";
		String pathTo ="C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\to\\";
		String fileNameRegex= "^local.log(.\\d{4}-\\d{2}-\\d{2}$)*";
	
		GetLocalLogs getter = new GetLocalLogs(pathFrom,pathTo,fileNameRegex);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    String userInput = "2017-04-26T12:44:06";
	    Date date = formatter.parse(userInput);	
		System.out.println("date: "+date);	
		
		getter.getLatestLogs(date, 15);
	}

}
