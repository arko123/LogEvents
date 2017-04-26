package main;

import java.io.IOException;

import download.GetLocalLogs;
import download.GetLogsOverHttp;

/**
 * Created by arek on 24.04.2017.
 */
public class LogEvents {
	private DownloadConfiguration downloadConfiguration;
	
	LogEvents(String downloadConfFilePath){
		this.downloadConfiguration= new DownloadConfiguration(downloadConfFilePath);

		System.out.println("------------PROPERTIES------------------");
		System.out.println("user: "+downloadConfiguration.user);
		System.out.println("password: "+downloadConfiguration.password);
		System.out.println("runFrequency: "+downloadConfiguration.runFrequency);
		System.out.println("fileNameRegex: "+downloadConfiguration.fileNameRegex);
		System.out.println("getlogsFrom: "+downloadConfiguration.getlogsFrom);
		System.out.println("saveLogsIn: "+downloadConfiguration.saveLogsIn);
		System.out.println("connectionType: "+downloadConfiguration.connectionType);
		System.out.println("----------------------------------------");
	}
	
	public void run() throws InterruptedException, IOException{
		switch(downloadConfiguration.connectionType){
			case SSH :
				runSSH();
				break;
			case FTP :
				runFTP();
				break;
			case HTTP :
				runHTTP();
				break;
			case LOCALHOST :
				runLOCALHOST();
				break;
			default :
				throw new IllegalArgumentException("Invalid connectionType: ");
		}
	}

	private void runLOCALHOST() throws InterruptedException {
		System.out.println("runLOCALHOST");
		GetLocalLogs getter = new GetLocalLogs(downloadConfiguration.getlogsFrom,downloadConfiguration.saveLogsIn,downloadConfiguration.fileNameRegex);
		
		do{
			getter.getLatestLogs();
			Thread.sleep(10000 * downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		}while(downloadConfiguration.runFrequency!=0 );
	
	}

	private void runHTTP() throws IOException, InterruptedException {
		System.out.println("runHTTP");
		GetLogsOverHttp getter = new GetLogsOverHttp(downloadConfiguration.getlogsFrom, downloadConfiguration.saveLogsIn);
		
		do{
			getter.getLatestLogs();
			Thread.sleep(10000 * downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		}while(downloadConfiguration.runFrequency!=0 );
	}

	private void runFTP() {
		// TODO Auto-generated method stub
		
	}

	private void runSSH() {
		// TODO Auto-generated method stub
		
	}
	
	

}
