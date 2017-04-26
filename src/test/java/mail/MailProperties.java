package mail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MailProperties {
	public String to;
	public String from;
	public String host;
	public String server;
	public String user;
	public String password;
	public String socketFactoryPort;
	public String socketFactoryClass;
	public String auth;
	public String port;

	public MailProperties(String mailPropertyFilePath){
		loadProperties(mailPropertyFilePath);
	}

	private void loadProperties(String mailPropertyFilePath) {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(mailPropertyFilePath);

			// load a properties file
			prop.load(input);

			to = prop.getProperty(MailPropertyTags.TO, null);
			from = prop.getProperty(MailPropertyTags.FROM, null);
			host = prop.getProperty(MailPropertyTags.HOST, null);
			server = prop.getProperty(MailPropertyTags.SERVER, null);
			user = prop.getProperty(MailPropertyTags.USER, null);
			password = prop.getProperty(MailPropertyTags.PASSWORD, null);
			socketFactoryPort = prop.getProperty(MailPropertyTags.SOCKETFACTORYPORT, null);
			socketFactoryClass = prop.getProperty(MailPropertyTags.SOCKETFACTORYCLASS, null);
			auth = prop.getProperty(MailPropertyTags.AUTH, null);
			port = prop.getProperty(MailPropertyTags.PORT, null);
		
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
