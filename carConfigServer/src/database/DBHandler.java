package database;

import adapter.BuildAuto;
import model.Automobile;
import util.FileIO;
import util.Properties;

public class DBHandler extends ProxyDBHandler implements DataBaseOperationInterface {
	
	
	public static void main(String[] args) {
		DBHandler handler = new DBHandler();		
		
		/* preload with a bunch of car files for testing */
		BuildAuto autoFactory = new BuildAuto();
		FileIO io = new FileIO();
		Properties p = io.parseProps("props/aucura.txt");
		autoFactory.loadPropsToAuto(p);
//		handler.updateAutoOptionSetNameInDB(auto.getUniqueName(), "Color", "Colour");
//		handler.updateAutoOptionPriceInDB(auto.getUniqueName(), "Colour", "Black", 100000);
//		handler.deleteAutoInDB(auto.getUniqueName());
	}
}
