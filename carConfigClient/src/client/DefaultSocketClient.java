/**
 * @author zhexinq
 * a default client to be used by other special-purpos clients
 */

package client;

import java.io.*;
import java.net.*;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket sock;
	private String strHost;
	private int iPort;
	
	/* constructor */
	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}
	
	/* run implementation */
	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}
	
	/* open session implementation */
	public boolean openConnection() {
		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Unable to obtain object stream to/from " + strHost);
			return false;
		}
		return true;
	}
	
	/* handle session imlementation */
	public void handleSession() {
		if (DEBUG)
			System.out.println("Handling session with " + strHost + ":" + iPort);
		// code for user to 1. upload a Properties object, 2. select a car to configure
		
	}
	
	/* close session implementation */
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error closing socket to " + strHost);
		}
	}
	
	/* set the port number to connect to */
	public void setPort(int iPort) {
		this.iPort = iPort;
	}
	
	/* set the host domain name to connect to */
	public void setHost(String strHost) {
		this.strHost = strHost;
	}
	
	public static void main(String args[]) {
		String s = "123132123";
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.dat"));
			out.writeObject(s);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.dat"));
			String a = (String)in.readObject();
			System.out.println(a);
		} catch (Exception e) {
			
		}
	}
	
}
