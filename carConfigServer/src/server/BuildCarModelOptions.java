/**
 * @author zhexinq
 * Server uploading request handler class:
 * 1. handler thread created by tied to a client socket
 * 2. handlesession deals with a properties receive/verification transaction
 * 3. handler thread then closes client socket and exit
 */

package server;

import util.Properties;
import adapter.BuildAuto;
import java.net.*;
import java.nio.file.Files;
import java.io.*;

public class BuildCarModelOptions extends DefaultSocketClient {
	private BuildAuto autoFactory;
	
	/* constructor */
	public BuildCarModelOptions(Socket client, BuildAuto autoFactory) {
		super(client);
		this.autoFactory = autoFactory;
	}
	
	public void handleSession() {
		Object o;
		
		// read client request 
		String request = (String) readInput();
		switch (request) {
		/* handle an upload request */
		case "upload":
			String fileType;
			
			// send to client for ready
			sendOutput("OK");
			// wait for client to send file type and object
			o = readInput();
			if (o != null) {
				fileType = (String) o;
				while ((o = readInput()) == null);
				if (fileType.equals("props")) {
					// read a props object from client
					Properties p = (Properties) o;
					// build an Auto Object from this props file
					if (autoFactory.loadPropsToAuto(p))
						sendOutput("SUC");
					else
						sendOutput("FAIL");
				} else {
					// read a normal text file
					byte[] contents = (byte[]) o;
					File f = new File("normal.txt");
					try {
					// write contents to disk file
						Files.write(f.toPath(), contents);
					} catch (IOException e) {
						System.out.println("Cannot write normal text to server disk");
					}
					// build an Auto Object from normal text file
					if (autoFactory.buildAuto("normal.txt"))
						sendOutput("SUC");
					else
						sendOutput("FAIL");
				}
			}
			break;
		/* handle a configuration request */
		case "config":
			// send a list of available models
			// sendOutput(xxx);
			// read a selection and send the auto object
			// sendOutput(xxx);	
			break;
		default:
			System.out.println("do not understand request: " + request);
		}

	}
	
}