/**
 * @author zhexinq
 * client side proxy auto, only needs to provide a object to server for building autos
 */

package adapter;

import model.Automobile;
import util.FileIO;
import util.Properties;
import java.io.*;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import exception.WrongInputException;
import scale.*;

public abstract class ProxyAutomobile {
	
	/* in CreateAuto API */
	/* input a file and build an auto object on the server side */
	// accept only two types of file, return null if not files found or types not supported
	public Object buildAuto(String filename, String fileType) {
		FileIO io = new FileIO();
		
		switch (fileType) {
		case "props":
			return io.parseProps(filename);
		case "txt":
			return io.parseNormalText(filename);
		default:
			return null;
		}
			
	}
	
}
