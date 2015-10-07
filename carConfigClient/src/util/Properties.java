/**
 * @author zhexinq
 * object to load a properties file corresponding to a car model
 * the car info is stored in a simple hash map
 * CarModel=X
 * CarMake=X
 * BasePrice=X
 * OptionSet1=X
 * OptionSet1Option1=X,P
 * OptionSet1Option2=X,P
 * OptionSet2=X
 * ...
 * The server then can parse this properties object using:
 * 1. CarModel->CarMake->BasePrice
 * 2. OptionSet[i]Option[j]
 */

package util;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Properties implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> carProperties;
	
	public Properties() {
		carProperties = new HashMap<String, String>();
	}
	
	// for loading a car properties file into an object for transferring through the network
	public void load(String filename) throws FileNotFoundException {
		String[] keyValue;
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		try {
			String oneLine = reader.readLine();
			
			while (oneLine != null) {
				keyValue = oneLine.split("=");
				if (keyValue.length != 2) {
					System.out.println("error input file");
					return;
				}
				else
					carProperties.put(keyValue[0], keyValue[1]);
				oneLine = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("read from file failed.");
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println("Unable to close properties file");
			}
		}
	}
	
	// getter method for getting value of each key
	public String getProperties(String name) {
		return carProperties.get(name);
	}
	
	// print properties
	public void printInfo() {
		Iterator<Entry<String, String>> iter = carProperties.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>)iter.next();
			System.out.printf("%s = %s\n", pair.getKey(), pair.getValue());
		}
	}
}
