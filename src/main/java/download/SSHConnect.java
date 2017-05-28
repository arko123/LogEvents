package download;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHConnect {

    String user;
     String host;
     String password;
     int port;
     String path;
     String pathTo;
     String regex;
     long lastModifyDate;

    public SSHConnect(String user, String password, String host, int port, String path, String pathTo, String regex, long lastModifyDate) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.pathTo = pathTo;
        this.regex = regex;
        this.lastModifyDate = lastModifyDate;
    }


    public void connectToServer() {

        List<String> fileList = new ArrayList<String>();


        try {

            ChannelSftp sftpChannel = createConnection(user, host, port);

            @SuppressWarnings("rawtypes")
			Vector filelist = sftpChannel.ls(path);
            System.out.println("Found : "+filelist.size() + " files in"+ path);


            for (Object entry : filelist) {
                ChannelSftp.LsEntry lsEntry = getInstance(entry);
             
                if (lsEntry.getFilename().matches(regex) && lsEntry.getAttrs().getMTime()* 1000L > lastModifyDate) {
                    fileList.add(lsEntry.getFilename());
                    lastModifyDate = lsEntry.getAttrs().getMTime()* 1000L;
                }
            }

            if (!fileList.isEmpty())
                for (String fName : fileList) {
                    sftpChannel.get(path+fName, pathTo + fName);
                    System.out.println("Get: "+fName);
                }
            System.out.println("END SSHConnect ");
        } catch (Exception e) {
            System.out.println("Exception "+e.getMessage());
        }
    }

    public ChannelSftp createConnection(String user, String host, int port) throws JSchException{


            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");

        return sftpChannel;
    }

    private ChannelSftp.LsEntry getInstance(Object obj) {
        return (com.jcraft.jsch.ChannelSftp.LsEntry) obj;

    }

}

