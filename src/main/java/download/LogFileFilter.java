package download;

import java.io.File;
import java.util.Date;


public class LogFileFilter  implements java.io.FileFilter {
	Date beginFromDate;
	String fileNameRegex;
	
	public LogFileFilter(String fileNameRegex,Date beginFromDate) {
		this.fileNameRegex=fileNameRegex;
		this.beginFromDate=beginFromDate;
	}
	
	public boolean accept(File arg0) {
		if(arg0.getName().matches(fileNameRegex) && arg0.lastModified() > beginFromDate.getTime())
			return true;
		return false;
	}


}
