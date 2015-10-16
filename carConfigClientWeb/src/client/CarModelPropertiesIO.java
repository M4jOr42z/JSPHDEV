/**
 * @author zhexinq
 * client class:
 * 			- read data from props file to an props object
 * 			- transfers props object from client to server and receives verification
 * 			- using CreateAuto Interface to handle different files
 */
package client;

import java.util.Scanner;

import adapter.BuildAuto;

public class CarModelPropertiesIO extends DefaultSocketClient {
	private String filePath, fileType;

	/* use default server address and port */
	public CarModelPropertiesIO(String filePath, String fileType) {
		super();
		this.filePath = filePath;
		this.fileType = fileType;
	}
	
	/* choose a server to connect */
	public CarModelPropertiesIO(String strHost, int iPort, String fileName, String fileType) {
		super(strHost, iPort);
		this.filePath = fileName;
		this.fileType = fileType;
	}
	
	/* upload different types of file for building car model */
	public void handleSession() {
		Object o;
		String msg;
		BuildAuto autoFact = new BuildAuto();
		
		// send "upload" request
		sendOutput("upload");
		// wait for response
		while ((o = readInput()) == null);
		msg = (String) o;
		if (!msg.equals("OK"))
			return;
		// build the object on client side
		o = autoFact.buildAuto(filePath, fileType);
		if (o != null) {
			// send fileType and file object to server
			sendOutput(fileType);
			sendOutput(o);
			// wait for verification
			while ((o = readInput()) == null);
			msg = (String) o;
			if (msg.equals("SUC"))
				System.out.printf("server response code: %s\n", msg);
			else 
				System.out.printf("server response code: %s\n", msg);
		}
	}
	
	/* spawn clients */
	public static void main(String[] args) {
		Scanner mainScanner = new Scanner(System.in);
		CarModelPropertiesIO clientIO;
		
		while (true) {
			System.out.println("Type anything to spawn a new upload client, type q to quit");
			String in = mainScanner.nextLine();
			if (in.equals("q"))
				break;
			else {
				clientIO= new CarModelPropertiesIO("props/corvette.txt", "props"); 
				clientIO.start();
				try {
					/* wait for the client thread to finish */
					clientIO.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			
	}
	
}
