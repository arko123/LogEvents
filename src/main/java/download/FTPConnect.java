package download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnect {



    public FTPConnect(String user, String server, String pass, String path, String regex, Date modify) {
        this.user = user;
        this.server = server;
        this.pass = pass;
        this.path = path;
        this.regex = regex;
        this.modify = modify;
    }
    String user;
     String server;
     String pass;
     String path;
     String regex;
     Date modify;


    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    public FTPClient createConnection(String server, String user, String pass) throws IOException{
        FTPClient ftpClient = new FTPClient();

        ftpClient.connect(server, 21);
        showServerReply(ftpClient);
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            System.out.println("Operation failed. Server reply code: " + replyCode);
            return ftpClient;
        }
        boolean success = ftpClient.login(user, pass);
        showServerReply(ftpClient);
        if (!success) {
            System.out.println("Could not login to the server");
            return ftpClient;
        } else {
            System.out.println("LOGGED IN SERVER");
        }



        return ftpClient;

    }



    public void getFiles() throws IOException{


        FTPClient ftpClient = createConnection(server, user, pass);
        try {
            List<String> fileList = new ArrayList<String>();


            FTPFile[] serverFileList = ftpClient.listFiles(path);

            for (FTPFile file : serverFileList) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

                String timePart = ftpClient.getModificationTime(path + file.getName().split(" ")[1]);
                Date modifyTime = dateFormat.parse(timePart);

                if (file.getName().matches(regex) && modifyTime.after(modify)) {
                    fileList.add(file.getName());
                }
            }

            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir"));

            if(!fileList.isEmpty()){
                for(String name: fileList) {
                    ftpClient.retrieveFile(path+name, fos);
                }
            }

        } catch (Exception ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }

    }
}

