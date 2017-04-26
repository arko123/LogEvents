package download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileUtils {

	public static void tail (Path src, long beginFromLine) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(src.toString()));
		//String buffer to store contents of the file
		StringBuffer sb=new StringBuffer("");		
		//Keep track of the line number
		int linenumber=1;
		String line;

		while((line=br.readLine())!=null)
		{
			//Store each valid line in the string buffer
			if(linenumber>beginFromLine)
				sb.append(line+"\n");
			linenumber++;
		}
		br.close();
		
		

		FileWriter writer = new FileWriter(new File(src.toString()));
		//Write entire string buffer into the file
		writer.write(sb.toString());
		writer.close();
		
	}
	
	public static int getLineNumber(Path src) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(src.toString()));
		int lines = 0;
		while (reader.readLine() != null) lines++;
		reader.close();
		return lines;
	}
}
