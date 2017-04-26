package download;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class SSHConnect {


    public static void connectToServer(String user, String password, String host, int port, String path, String regex, int lastModifyDate){

        List<String> fileList = new ArrayList<String>();

         user = "***";
         password = "****";
         host = "****";
         port=22;
        path = "/home/eaiibgrp/awozniak";

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



            for(Object entry : filelist) {
                ChannelSftp.LsEntry lsEntry = getInstance(entry);

                if(lsEntry.getFilename().matches(regex) && lsEntry.getAttrs().getMTime()>lastModifyDate) {
                    fileList.add(lsEntry.getFilename());
                    lastModifyDate= lsEntry.getAttrs().getMTime();
                }
                System.out.println(lsEntry.getFilename());
            }




            if(fileList.isEmpty())
                for(String fName: fileList){
                    sftpChannel.get(fName,fName);
                }




        } catch(Exception e ){
            System.out.println(e.getMessage());
        }
//    return fileList;
    }

    private static ChannelSftp.LsEntry getInstance(Object obj){
        return (com.jcraft.jsch.ChannelSftp.LsEntry)obj;

    }


    }

