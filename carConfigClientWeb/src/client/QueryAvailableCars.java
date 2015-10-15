/**
 * @author qiuzhexin
 * 				- spawn a thread to query available cars.
 */

package client;

import java.util.ArrayList;

import model.Automobile;

public class QueryAvailableCars extends DefaultSocketClient {
	// for sharing among query threads 
	private ArrayList<String> queriedCars = new ArrayList<String>();
	
	// connect to default server
	public QueryAvailableCars() {
		super();
	}
	
	// connect to specified server
	public QueryAvailableCars(String iHost, int iPort) {
		super(iHost, iPort);
	}
	
	/* handle query models request */
	public void handleSession() {
		Object o;
		String msg;
		
		// send query_models request
		sendOutput("query_models");
		// read OK response
		while((o = readInput()) == null);
		msg = (String)o;
		if (!msg.equals("OK"))
			return;
		// read list of available models
		o = readInput();

		queriedCars = (ArrayList<String>) o;
		if (queriedCars.size() == 0) {
			System.out.println("No available models yet");
			return;
		}
		System.out.println("Available models:");
		for (int i = 0; i < queriedCars.size(); i++) {
			System.out.printf("%d: %s\n", i, queriedCars.get(i));
		}
		
	}
	
	/* return the static variable for use */
	public ArrayList<String> getQueriedCars() {
		return queriedCars;
	}
	
	/* unit test */
	public static void main(String[] args) {
		QueryAvailableCars query = new QueryAvailableCars();
		query.start();
		try {
		query.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> modelsLastTimeQueried = query.getQueriedCars();
		for (String model:modelsLastTimeQueried) {
			System.out.println("available: " + model);
		}
	}
	
}
