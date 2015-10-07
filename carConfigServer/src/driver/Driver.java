/**
 * @author zhexinq
 * driver class for testing server/client communication
 */

package driver;

import java.io.*;
import java.net.*;
import util.Properties;

public class Driver {
	
	public static void main(String[] args) {
		Socket client = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Properties p = null;
		
		try {
			in = new ObjectInputStream(new FileInputStream("props.dat"));
			p = (Properties)in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client = new Socket("127.0.0.1", 18641);
		} catch (IOException e) {
			System.out.println("cannot connect to server");
		}
		try {
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			System.out.println("Cannot connect I/O streams to server");
		}
		try {
			out.writeObject("upload");
			Object fromServer;
			while ((fromServer = in.readObject()) == null);
			String s = (String)fromServer;
			System.out.println("from server: " + s);
			out.writeObject(p);
			out.close();
			in.close();
		} catch (Exception e) {
			System.out.print("can't write to sever");
		}
	}

}
