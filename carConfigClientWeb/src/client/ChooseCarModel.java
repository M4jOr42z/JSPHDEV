/**
 * @author zhexinq
 * client car choose a car class
 * 		- spawn a thread to request a car model from the server
 */

package client;

import java.util.ArrayList;
import java.util.Scanner;

import adapter.BuildAuto;
import model.Automobile;

public class ChooseCarModel extends DefaultSocketClient {
	private int selectionId;
	private Automobile retrivedModel;

	/* use default server address and port */
	public ChooseCarModel(int selectionId) {
		super();
		this.selectionId = selectionId;
	}
	
	/* choose a server to connect */
	public ChooseCarModel(String strHost, int iPort, int selectionId) {
		super(strHost, iPort);
		this.selectionId = selectionId;
	}
	
	/* handle user car configuration */
	public void handleSession() {
		Object o;
		String msg;
		
		// send config request
		sendOutput("config");
		// read OK response
		while((o = readInput()) == null);
		msg = (String)o;
		if (!msg.equals("OK"))
			return;
		// send the selection id to select a model
		sendOutput(selectionId);
		retrivedModel = (Automobile) readInput();
		System.out.println("Receive an auto:");
		retrivedModel.printInfo();
	}
	
	/* provide last retrieved car model */
	public Automobile getRetrievedCarModel() {
		return retrivedModel;
	}
	
	/* test the selection client */
	public static void main(String[] args) {
		ChooseCarModel choose = new ChooseCarModel(0);
		choose.start();
		try {
			choose.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Automobile auto = choose.getRetrievedCarModel();
		System.out.println("get the car in a thread!!");
		auto.printInfo();
	}
}
