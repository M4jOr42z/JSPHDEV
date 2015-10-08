/**
 * @author zhexinq
 * client class:
 * 			- read data from props file to an props object
 * 			- transfers props object from client to server and receives verification
 * 			- using CreateAuto Interface to handle different files
 */
package client;

import java.io.Console;
import java.util.Scanner;
import adapter.BuildAuto;

public class CarModelOptionsIO extends DefaultSocketClient {
	private Scanner userInput;

	/* use default server address and port */
	public CarModelOptionsIO() {
		super();
		userInput = new Scanner(System.in);
	}
	
	/* choose a server to connect */
	public CarModelOptionsIO(String strHost, int iPort) {
		super(strHost, iPort);
		userInput = new Scanner(System.in);
	}
	
	/* upload different types of file for building car model */
	public void handleSession() {
		Object o;
		String msg, userIn;
		BuildAuto autoFact = new BuildAuto();
		
		// send "upload" request
		sendOutput("upload");
		// wait for response
		while ((o = readInput()) == null);
		msg = (String) o;
		if (!msg.equals("OK"))
			return;
		// ask user for file type
		System.out.println("input the file name and file type seperated by space:");
		userIn = userInput.nextLine();
		String[] fNameType = userIn.split(" ");
		// build the object on client side
		o = autoFact.buildAuto(fNameType[0], fNameType[1]);
		if (o != null) {
			// send fileType and file object to server
			sendOutput(fNameType[1]);
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
		CarModelOptionsIO clientIO;
		
		while (true) {
			System.out.println("Type anything to spawn a new upload client, type q to quit");
			String in = mainScanner.nextLine();
			if (in.equals("q"))
				break;
			else {
				clientIO= new CarModelOptionsIO(); 
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
