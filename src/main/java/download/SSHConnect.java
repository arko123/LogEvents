package download;

import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Exchanger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHConnect {


    public static void connectToServer(String user, String password, String host, int port, String path, String reg, String lastModifyDate){

         user = "***";
         password = "***+";
         host = "***";
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
                System.out.println(lsEntry.getFilename());
            }


        } catch(Exception e ){
            System.out.println(e.getMessage());
        }

    }

    private static ChannelSftp.LsEntry getInstance(Object obj){
        return (com.jcraft.jsch.ChannelSftp.LsEntry)obj;

    }

    public static void main(String [] args) throws IOException{
        connectToServer("asd","asd","asd",22,"asd","asd","dsa");

    }

    }

