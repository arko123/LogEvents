package main;

import java.io.IOException;
import java.util.Date;

import analize.LogAnalyzer;
import download.FTPConnect;
import download.GetLocalLogs;
import download.GetLogsOverHttp;
import download.SSHConnect;
import mail.MailProperties;

/**
 * Created by arek on 24.04.2017.
 */
public class LogEvents {
	private DownloadConfiguration downloadConfiguration;
	private MailProperties mailProperties;
	private boolean logsEnabled;
	

	public LogEvents(String downloadConfFilePath,String mailPropertyFilePath){
			this.initProps(downloadConfFilePath, mailPropertyFilePath);
		
	}
	
	private void initProps (String downloadConfFilePath,String mailPropertyFilePath){
		this.downloadConfiguration= new DownloadConfiguration(downloadConfFilePath);

		System.out.println("------------LOGS_PROPERTIES------------------");
		System.out.println("user: "+downloadConfiguration.user);
		System.out.println("password: "+downloadConfiguration.password);
		System.out.println("runFrequency: "+downloadConfiguration.runFrequency);
		System.out.println("fileNameRegex: "+downloadConfiguration.fileNameRegex);
		System.out.println("getlogsFrom: "+downloadConfiguration.getlogsFrom);
		System.out.println("saveLogsIn: "+downloadConfiguration.saveLogsIn);
		System.out.println("connectionType: "+downloadConfiguration.connectionType);
		System.out.println("host: "+downloadConfiguration.host);
		System.out.println("port: "+downloadConfiguration.port);
		System.out.println("----------------------------------------");
		
		this.mailProperties= new MailProperties(mailPropertyFilePath);
		System.out.println("------------MAIL_PROPERTIES------------------");
		System.out.println("to: "+mailProperties.to);
		System.out.println("from: "+mailProperties.from);
		System.out.println("host: "+mailProperties.host);
		System.out.println("server: "+mailProperties.server);
		System.out.println("user: "+mailProperties.user);
		System.out.println("password: "+mailProperties.password);
		System.out.println("socketFactoryPort: "+mailProperties.socketFactoryPort);
		System.out.println("socketFactoryClass: "+mailProperties.socketFactoryClass);
		System.out.println("auth: "+mailProperties.auth);
		System.out.println("port: "+mailProperties.port);
		System.out.println("----------------------------------------");
	}
	
	public void run() throws InterruptedException, IOException{
		switch(downloadConfiguration.connectionType){
			case S:
				runSSH();
				break;
			case F:
				runFTP();
				break;
			case H:
				runHTTP();
				break;
			case L:
				runLOCALHOST();
				break;
			default :
				throw new IllegalArgumentException("Invalid connectionType: ");
		}
	}

	private void runLOCALHOST() throws InterruptedException, IOException {
		System.out.println("runLOCALHOST");
		GetLocalLogs getter = new GetLocalLogs(downloadConfiguration.getlogsFrom,downloadConfiguration.saveLogsIn,downloadConfiguration.fileNameRegex);
		LogAnalyzer logAnalizer = new LogAnalyzer(downloadConfiguration.saveLogsIn,mailProperties);
		
		do{
			getter.getLatestLogs();
			logAnalizer.run();
			Thread.sleep(10000 * downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		}while(downloadConfiguration.runFrequency!=0 );
	
	}

	private void runHTTP() throws IOException, InterruptedException {
		System.out.println("runHTTP");
		GetLogsOverHttp getter = new GetLogsOverHttp(downloadConfiguration.getlogsFrom, downloadConfiguration.saveLogsIn);
		LogAnalyzer logAnalizer = new LogAnalyzer(downloadConfiguration.saveLogsIn,mailProperties);
		
		do{
			getter.getLatestLogs();
			logAnalizer.run();
			Thread.sleep(10000 * downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		}while(downloadConfiguration.runFrequency!=0 );
	}

	private void runSSH() throws IOException, InterruptedException{
		System.out.println("runSSH");
		SSHConnect sshConnect = new SSHConnect(downloadConfiguration.user, downloadConfiguration.password,downloadConfiguration.host,downloadConfiguration.port,downloadConfiguration.getlogsFrom,downloadConfiguration.saveLogsIn,
				downloadConfiguration.fileNameRegex,new Date().getTime());
		LogAnalyzer logAnalizer = new LogAnalyzer(downloadConfiguration.saveLogsIn,mailProperties);

		do{
			sshConnect.connectToServer();
			logAnalizer.run();
			Thread.sleep(1000*downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		} while(downloadConfiguration.runFrequency!=0 );

	}

	private void runFTP() throws IOException, InterruptedException{
		System.out.println("runFTP");
		FTPConnect ftpConnect = new FTPConnect("usrname", "passwd","hostname","path","port", new Date());
		LogAnalyzer logAnalizer = new LogAnalyzer(downloadConfiguration.saveLogsIn,mailProperties);

		do{
			ftpConnect.getFiles();
			logAnalizer.run();
			Thread.sleep(1000*downloadConfiguration.runFrequency);
			System.out.println("-----------SLEEP-----------------");
		} while(downloadConfiguration.runFrequency!=0 );

	}
	

	

}
