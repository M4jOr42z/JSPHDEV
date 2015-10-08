/**
 * @author zhexinq
 * client configuration class
 *		1. to prompt user for a list of available models
 * 		2. allows user to select a model and enter respective options
 * 		3. display selected options for a model
 */

package client;

import java.util.ArrayList;
import java.util.Scanner;

import adapter.BuildAuto;
import model.Automobile;

public class SelectCarOption extends DefaultSocketClient {
	private Scanner userInput;

	/* use default server address and port */
	public SelectCarOption() {
		super();
		userInput = new Scanner(System.in);
	}
	
	/* choose a server to connect */
	public SelectCarOption(String strHost, int iPort) {
		super(strHost, iPort);
		userInput = new Scanner(System.in);
	}
	
	/* handle user car configuration */
	public void handleSession() {
		Object o;
		String msg, userIn;
		Automobile autoForConfig;
		ArrayList<String> models;
		int selectID;
		
		// send config request
		sendOutput("config");
		// read OK response
		while((o = readInput()) == null);
		msg = (String)o;
		if (!msg.equals("OK"))
			return;
		// read list of available models
		o = readInput();
		models = (ArrayList<String>) o;
		if (models.size() == 0) {
			System.out.println("No available models yet");
			return;
		}
		System.out.println("Available models:");
		for (int i = 0; i < models.size(); i++) {
			System.out.printf("%d: %s\n", i, models.get(i));
		}
		System.out.println("Select a model for configuring:");
		userIn = userInput.nextLine();
		while (true) {
			if (userIn.equals("q"))
				return;
			try {
				selectID = Integer.parseInt(userIn);
				break;
			} catch (Exception e) {
				System.out.println("Not a number continue or type q to quit");
			}
		}
		// send the selection to server
		sendOutput(userIn);
		// receive selected auto
		autoForConfig = (Automobile) readInput();
		System.out.println("Receive an auto:");
		autoForConfig.printInfo();
		// let the user configure a car
		System.out.println("Choose an option by typing in OptionSetName and OptionName seperated by space: ");
		while (true) {
			userIn = userInput.nextLine();
			if (userIn.equals("q"))
				break;
			try {
				String[] setAndOption = userIn.split(",");
				String setName = setAndOption[0].trim();
				String optName = setAndOption[1].trim();
				autoForConfig.setOptionChoice(setName, optName);
				System.out.println("Made a choice!");
			} catch (Exception e) {
				System.out.println("Invalid choice. (correct e.g.: Color, Dark Blue)\ntype q to finsh choosing");
			}
		}
		// display the selected result
		System.out.println("Result after configuration:");
		autoForConfig.printInfo();
	}
	
	/* test the selection client */
	public static void main(String[] args) {
		System.out.println("Starting selection client");
		SelectCarOption configCar = new SelectCarOption();
		configCar.start();
	}
}
