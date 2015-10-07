/**
 * @author zhexinq
 * a default client socket to be used by the server to handle
 * different requests
 */

package server;

import java.io.*;
import java.net.*;

import adapter.BuildAuto;
import util.Properties;

public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket clientSock;
	
	/* set the client socket for communication */
	public DefaultSocketClient(Socket clientSock) {
			this.clientSock = clientSock;
	}
	
	/* open a server session to handle client connection */
	public boolean openConnection() {
		/* establish object streams for communication */
		try {
			reader = new ObjectInputStream(clientSock.getInputStream());
			writer = new ObjectOutputStream(clientSock.getOutputStream());
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Unable to obtain object stream to/from client");
			return false;
		}
		return true;
	}
	
	/* handle session to be overriden for different requests */
	public void handleSession() {

	}
	
	/* send object to the client */
	public void sendOutput(Object o) {
		try {
			writer.writeObject(o);
		} catch (IOException e) {
			System.out.println("Write object to client failed.");
		}
	}
	
	/* receive object from the client */
	public Object readInput() {
		Object o = null;
		try {
			o = reader.readObject();
		} catch(ClassNotFoundException e) {
			if (DEBUG)
				System.out.println("can not find class to load received object from client");
		} catch(IOException e) {
			if (DEBUG)
				System.out.println("Receive object from client failed.");
		}
		return o;
	}
	
	/* run implementation */
	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}
	
	/* close session implementation */
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			clientSock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error closing client socket when session ends");
		}
	}
	
	
	/* send output to client socket */
	
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
