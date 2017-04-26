package download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetLogsOverHttp {

/*	Pobież wskazany plik. 
	Jeżeli plik jest większy od ostatnio pobranego to usun logi pobrane wczesniej. 
	Jezeli mniejszy pobiez cały plik. */
	
    private static final int BUFFER_SIZE = 4096;
	private String fileURL;
	private String saveDir;
	private long  lastfileLength;
	private long lastDownloadLine;
	
	public GetLogsOverHttp(String fileURL, String saveDir) {
		this.fileURL=fileURL;
		this.saveDir=saveDir;
	}
	
	
	
	public void getLatestLogs() throws IOException{
			this.getLatestLogs(lastfileLength, lastDownloadLine);
	}
	
	public void getLatestLogs(long lastfileLenght,long beginFromLine) throws IOException{
		String saveFilePath = downloadFile();
		removeUnnesesaryLogs(saveFilePath);
	}



	 private void removeUnnesesaryLogs(String saveFilePath) throws IOException {
		 Path target=Paths.get(saveFilePath);
		 File file = target.toFile();
		 long fileLengthtmp = file.length();
		 long lastLinetmp= FileUtils.getLineNumber(target);
		 
		 if(fileLengthtmp>=lastfileLength){
			 FileUtils.tail(target,lastDownloadLine);
		 }
		 
		 lastfileLength=fileLengthtmp;
		 lastDownloadLine=lastLinetmp;
	}



	 private String downloadFile()throws IOException {
	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            String saveFilePath = saveDir + File.separator + fileName;
	             
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	 
	            outputStream.close();
	            inputStream.close();
	 
	            System.out.println("File downloaded");
	            httpConn.disconnect();
	            return saveFilePath;
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	            httpConn.disconnect();
	            return null;
	        }
	        
	    }

}
