/**
 * @author zhexinq
 * main server to process all kinds of client requests
 * default port: 4444
 */
package server;

import java.net.*;
import java.io.IOException;
import adapter.BuildAuto;
import util.FileIO;
import util.Properties;

public class MainServer {
	final static int iPort = 18641;
	
	public static void main (String[] args) {
		ServerSocket serverSock = null;
		Socket clientSock = null;
		BuildCarModelOptions handler;
		BuildAuto autoFactory = new BuildAuto();
		
		/* preload with a bunch of car files for testing */
		FileIO io = new FileIO();
		Properties p = io.parseProps("props/aucura.txt");
		autoFactory.loadPropsToAuto(p);
		p = io.parseProps("props/corvette.txt");
		autoFactory.loadPropsToAuto(p);
		p = io.parseProps("props/prius.txt");
		autoFactory.loadPropsToAuto(p);
		
		/* create a listening socket */
		while (serverSock == null) {
			try {
				serverSock = new ServerSocket(iPort);
				serverSock.setReuseAddress(true);
			} catch (IOException e) {
				System.out.println("Cannot listen to " + iPort);
				System.exit(1);
			}
		}
		/* start listening incoming connections */
		while (true) {
			System.out.println("Waiting...");
			try{
				clientSock = serverSock.accept();
				handler = new BuildCarModelOptions(clientSock, autoFactory);
				handler.start();
			} catch (Exception e) {
				System.out.println("Cannot accept client!");
			}

		}
	}
}