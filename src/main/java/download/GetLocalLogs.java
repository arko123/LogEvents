package download;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;



public class GetLocalLogs {
	private String pathFrom;
	private String pathTo;
	private String fileNameRegex;
	private Date lastDownloadDate;
	private long lastDownloadLine;
	
	public GetLocalLogs(String pathFrom, String pathTo, String fileNameRegex) {
		this.pathFrom=pathFrom;
		this.pathTo=pathTo;
		this.fileNameRegex=fileNameRegex;
	}
	
	public void getLatestLogs(){
		if(lastDownloadDate!=null)
			this.getLatestLogs(lastDownloadDate, lastDownloadLine);
		else
			this.getLatestLogs(new Date(), 0);
	}
	
	public void getLatestLogs(Date beginFromDate,long beginFromLine){
		File directory = new File(pathFrom);

		FileFilter filter1 =  new LogFileFilter(fileNameRegex,beginFromDate);
		List<File> files = findFiles(directory,filter1);
		
		wypiszZnalezionePliki(files);
		download(files,beginFromDate,beginFromLine);
	}
	
	
	//----------------------------------------------------------------------------
	
	
	private List<File> findFiles(File directory, FileFilter fileFilter){
		//find and sort files by modyficationDate -> oldest files are first. 
		File[] files = directory.listFiles(fileFilter);
		Arrays.sort(files, new Comparator<File>(){
		    public int compare(File f1, File f2){
		        return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
		    } });
		return Arrays.asList(files);
	}
	
	private void wypiszZnalezionePliki(List<File> files){
		for(File file:files){
			System.out.println("Plik: "+file.getName()+" lastModified: "+new Date(file.lastModified()) +" mils: "+file.lastModified());
		}
	}
	
	private void download(List<File> files, Date beginFromDate, long beginFromLine){
	    try {
	    	int lastLinetmp = 0;
	    	long lastModifiedtmp = 0;
	    	for(File file:files){
    			Path source =Paths.get(pathFrom+file.getName());
				Path target=Paths.get(pathTo+file.getName());
/*					System.out.println("target " + target.toString());	
				System.out.println("source " + source.toString());	*/
				Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    			System.out.println("Downloaded file: "+file.getName());
    			
	    		BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
	    		if(attr.creationTime().toMillis()<beginFromDate.getTime()){
	    			//Plik z dopisanymi logami po ostatnim pobraniu
	    			FileUtils.tail(target,beginFromLine);
	    			System.out.println("Converted part of file: "+file.getName());
	    		}
	    		if(lastModifiedtmp<file.lastModified()){
	    			lastModifiedtmp=file.lastModified();
	    			lastLinetmp= FileUtils.getLineNumber(target);
	    		}
		    }
	    	lastDownloadDate= new Date();
	    	lastDownloadLine= lastLinetmp;
	    } 
	    catch (IOException e) {
	    	System.out.println("oops error! " + e.getMessage());	
	    }
	}  
}
