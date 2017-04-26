package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DownloadConfiguration {
	public String user;
	public String password;
	public ConnectionType connectionType;
	public String getlogsFrom;
	public String saveLogsIn;
	public String fileNameRegex;
	public long runFrequency;
	
	
	public DownloadConfiguration(String downloadConfFilePath) {
		loadProperties(downloadConfFilePath);
	}


	private void loadProperties(String downloadConfFilePath) {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(downloadConfFilePath);

			// load a properties file
			prop.load(input);

			user = prop.getProperty(DownloadConfigurationTags.USER, null);
			password = prop.getProperty(DownloadConfigurationTags.PASSWORD, null);
			getlogsFrom = prop.getProperty(DownloadConfigurationTags.GET_LOGS_FROM, null);
			saveLogsIn = prop.getProperty(DownloadConfigurationTags.SEVE_LOGS_IN, null);
			fileNameRegex = prop.getProperty(DownloadConfigurationTags.FILE_NAME_REGEX, null);
			runFrequency = Long.parseLong(prop.getProperty(DownloadConfigurationTags.RUN_FREQUENCY, "0"));
			switch(prop.getProperty(DownloadConfigurationTags.CONNECTION_TYPE, null)){
				case "SSH" :
					connectionType = ConnectionType.SSH;
					break;
				case "FTP" :
					connectionType = ConnectionType.FTP;
					break;
				case "HTTP" :
					connectionType = ConnectionType.HTTP;
					break;
				case "LOCALHOST" :
					connectionType = ConnectionType.LOCALHOST;
					break;
				default :
					throw new IllegalArgumentException("Invalid connectionType: ");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
