package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import com.jcraft.jsch.*;
import java.io.InputStreamReader;
import java.util.Scanner;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() {
		//connectServer();
		//connectServer2();
		//downloadRemoteFile();
		//uploadFileToRemote();
		//deleteRemoteFile();
		//createFolder();
		deleteFolder();
		return "Welcome to SSH examples...";
	}
	
	public void connectServer() {
	    String host="ec2-52-206-61-127.compute-1.amazonaws.com";
	    String user="ubuntu";
	    String password="ubuntu";
	    String command1="ls -ltr";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";
	    try{
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
	    	Session session=jsch.getSession(user, host, 22);	
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");			
	    	session.setConfig(config);
			session.setPassword(password);
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command1);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}
	
	public void connectServer2() {
	    String host="ec2-52-206-61-127.compute-1.amazonaws.com";
	    String user="ubuntu";
	    String password="ubuntu";
	    String command1="sudo apt-get update";
		String command2="sudo apt-get -y install apache2";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";
	    try{
	    	
	    	java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
	    	JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
	    	Session session=jsch.getSession(user, host, 22);	
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");			
	    	session.setConfig(config);
			session.setPassword(password);
	    	session.connect();
	    	System.out.println("Connected");
	    	
	    	Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command1);
			((ChannelExec)channel).setCommand(command2);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}
	
	public void downloadRemoteFile() {
        String user = "ubuntu";
        String password = "ubuntu";
        String host = "ec2-54-162-253-206.compute-1.amazonaws.com";
        int port = 22;
        String remoteFile = "/home/ubuntu/test.txt";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";

        try {
            JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            sftpChannel.get(remoteFile, "C:/AWSFiles");
			System.out.println("Remote file downloaded to C:/AWSFiles");
            InputStream inputStream = sftpChannel.get(remoteFile);

            try (Scanner scanner = new Scanner(new InputStreamReader(inputStream))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            }
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
	
	public void uploadFileToRemote() {
        String user = "ubuntu";
        String password = "ubuntu";
        String host = "ec2-54-162-253-206.compute-1.amazonaws.com";
        int port = 22;
        String remoteFile = "/home/ubuntu";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";

        try {
            JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            sftpChannel.put("C:/AWSFiles/test2.txt", remoteFile);
			System.out.println("File is upload to remote path /home/ubuntu");
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
	
	public void deleteRemoteFile() {
        String user = "ubuntu";
        String password = "ubuntu";
        String host = "ec2-54-162-253-206.compute-1.amazonaws.com";
        int port = 22;
        String remoteFile = "/home/ubuntu/test2.txt";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";

        try {
            JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            sftpChannel.rm(remoteFile);
			System.out.println("Remote file is deleted");
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
	
	public void createFolder() {
        String user = "ubuntu";
        String password = "ubuntu";
        String host = "ec2-18-212-41-247.compute-1.amazonaws.com";
        int port = 22;
        String folderPath = "/home/ubuntu/folder1";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";

        try {
            JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            sftpChannel.mkdir(folderPath);
			System.out.println("Remote folder is created");
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
	
	public void deleteFolder() {
        String user = "ubuntu";
        String password = "ubuntu";
        String host = "ec2-18-212-41-247.compute-1.amazonaws.com";
        int port = 22;
        String folderPath = "/home/ubuntu/folder1";
		String privateKeyPath = "C:/AWS/VasuKeyLatest6.pem";

        try {
            JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            System.out.println("Connection established.");
            System.out.println("Crating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("SFTP Channel created.");
            sftpChannel.rmdir(folderPath);
			System.out.println("Remote folder is deleted");
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
	
}
