package download;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetLogsOverHttpTest {
	String dirURL ;
	String fileName ;
	String saveDir;
	GetLogsOverHttp getLogs;
	
	@Before
	public  void init(String[] args) throws IOException, InterruptedException {
		 dirURL = "http://student.agh.edu.pl/~wbachta/ZTW/";
		 fileName = "Start.txt";
		 saveDir ="C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\to\\";

		 getLogs = new GetLogsOverHttp(saveDir+fileName, saveDir);
		
	}
	
	@Test
	public  void getLocalLogsTest()  {
		
		Scanner sc;
		try {
			getLogs.getLatestLogs();
			sc = new Scanner(new File(saveDir+fileName));
			if(sc.hasNext()){
				 String line = sc.nextLine(); 
				 assertTrue( true);
			}
			
		} catch (IOException e) {
			fail();
		}
		
	}

}
