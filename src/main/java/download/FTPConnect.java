package download;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Exchanger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnect {

    private String user;

    public FTPConnect(String user, String server, String pass, String path, String regex, Date modify) {
        this.user = user;
        this.server = server;
        this.pass = pass;
        this.path = path;
        this.regex = regex;
        this.modify = modify;
    }

    private String server;
    private String pass;
    private String path;
    private String regex;
    private Date modify;


    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }



    public void getFiles() {

//        server = "***";
      int port = 21;
//        user = "***";
//        pass = "****";
        FTPClient ftpClient = new FTPClient();
        try {
            List<String> fileList = new ArrayList<String>();

            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            } else {
                System.out.println("LOGGED IN SERVER");
//                System.out.println(ftpClient.listDirectories()[2].getName());
            }

            FTPFile[] serverFileList = ftpClient.listFiles(path);

            for (FTPFile file : serverFileList) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

                String timePart = ftpClient.getModificationTime(path + file.getName().split(" ")[1]);
                Date modifyTime = dateFormat.parse(timePart);

                if (file.getName().matches(regex) && modifyTime.after(modify)) {
                    fileList.add(file.getName());
                }
            }

        } catch (Exception ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }

    }
}

