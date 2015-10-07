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
import java.io.*;

public class BuildCarModelOptions extends DefaultSocketClient {
	private BuildAuto autoFactory;
	
	/* constructor */
	public BuildCarModelOptions(Socket client, BuildAuto autoFactory) {
		super(client);
		this.autoFactory = autoFactory;
	}
	
	/* handle an upload request */
	public void handleSession() {
		// read client request 
		String request = (String) readInput();
		switch (request) {
		case "upload":
			// send to client for ready
			sendOutput("ready");
			// wait for client to send the object
			Object o;
			while ((o = readInput()) == null);
			// read a props object from client
			Properties p = (Properties) o;
			// build an Auto Object from this props file
			if (autoFactory.loadPropsToAuto(p))
				sendOutput("sent auto model was built!\n");
			else
				sendOutput("built auto model failed!\n");
			break;
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
