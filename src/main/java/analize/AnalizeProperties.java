package analize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AnalizeProperties {
	public String search_regex;
	
	
	public  AnalizeProperties(String analizePropertiesPath){
		loadProperties(analizePropertiesPath);
	}


	private void loadProperties(String analizePropertiesPath) {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(analizePropertiesPath);

			// load a properties file
			prop.load(input);

			search_regex = prop.getProperty(AnalizePropertiesTags.SEARCH_REGEX, null);

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
