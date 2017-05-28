package download;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetLocalLogsTest {
	public String pathFrom ;
	public String pathTo ;
	public String fileNameRegex;
	Date date;	
	GetLocalLogs getter;
	String testFileName;
	String testLine;
	
	
	@Before
	public  void getLocalLogsInit() throws ParseException, FileNotFoundException {
		 pathFrom = "C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\from\\";
		 pathTo ="C:\\Users\\LenovoY580_Wojtek\\git\\LogEvents\\testFiles\\to\\";
		 fileNameRegex= "^local.log(.\\d{4}-\\d{2}-\\d{2}$)*";
		 String userInput = "2017-05-26T12:44:06";
		 testFileName = "local.log";
		 testLine = "test";
	
		getter = new GetLocalLogs(pathFrom,pathTo,fileNameRegex);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    date = formatter.parse(userInput);	
		System.out.println("date: "+date);	
		
		PrintWriter out = new PrintWriter(pathFrom+testFileName);
		out.println(testLine);
		out.close();
		
	}
	
	@Test
	public  void getLocalLogsTest()  {
		getter.getLatestLogs(date, 15);
		
		Scanner sc;
		try {
			sc = new Scanner(new File(pathTo+testFileName));
			if(sc.hasNext()){
				 String line = sc.nextLine(); 
				 assertEquals( testLine, line);
			}
			
		} catch (FileNotFoundException e) {
			fail();
		}
		
	}
	
	
	
	@After
	public void clear(){
		File file = new File(pathTo+testFileName);
		if(file.exists()){
			file.delete();
		}
	}

}
