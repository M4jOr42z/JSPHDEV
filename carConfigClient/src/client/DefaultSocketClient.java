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
	public DefaultSocketClient() {
		setPort(iCAR_PORT);
		setHost(iHOST);
	}
	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}
	
	/* run implementation */
	public void run() {
		System.out.println("In client run");
		if (openConnection()) {
			System.out.println("leave openconnection");
			handleSession();
			closeSession();
		}
	}
	
	/* open session implementation */
	public boolean openConnection() {
		System.out.println("In open connection");
		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Unable to connect to " + strHost);
			return false;
		}
		try {
			System.out.println("before reader writer.");
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream());
			System.out.println("finish reader writer.");
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Unable to obtain object stream to/from " + strHost);
			return false;
		}
		System.out.println("connection succeed");
		return true;
	}
	
	/* handle session implementation */
	public void handleSession() {
		// to be overwritten by specific client socket
	}
	
	/* close session implementation */
	public void closeSession() {
		System.out.println("closing client.");
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
	
	/* send to the connected server socket */
	public void sendOutput(Object o) {
		try {
			writer.writeObject(o);
		} catch (IOException e) {
			System.out.println("write object to server failed");
		}
	}
	
	/* receive from the connected server socket */
	public Object readInput() {
		Object o = null;
		try {
			o = reader.readObject();
		} catch (Exception e) {
			System.out.println("read object from server failed");
		}
		return o;
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
