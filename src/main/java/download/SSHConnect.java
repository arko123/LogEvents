package download;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHConnect {

    String user;
    private String host;
    private String password;
    private int port;
    private String path;
    private String regex;
    private int lastModifyDate;

    public SSHConnect(String user, String password, String host, int port, String path, String regex, int lastModifyDate) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.regex = regex;
        this.lastModifyDate = lastModifyDate;
    }


    public void connectToServer() {

        List<String> fileList = new ArrayList<String>();


        try {
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

            Vector filelist = sftpChannel.ls(path);


            for (Object entry : filelist) {
                ChannelSftp.LsEntry lsEntry = getInstance(entry);

                if (lsEntry.getFilename().matches(regex) && lsEntry.getAttrs().getMTime() > lastModifyDate) {
                    fileList.add(lsEntry.getFilename());
                    lastModifyDate = lsEntry.getAttrs().getMTime();
                }
            }

            if (!fileList.isEmpty())
                for (String fName : fileList) {
                    sftpChannel.get(fName, "testFiles/testSSH/" + fName);
                }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private ChannelSftp.LsEntry getInstance(Object obj) {
        return (com.jcraft.jsch.ChannelSftp.LsEntry) obj;

    }

}

