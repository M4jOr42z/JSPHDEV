/**
 * @author zhexinq
 * Database interface that declares a set of methods
 * to sync the CRUD operations in LHM of ProxyAutomobile
 * into a database
 */

package database;

import model.Automobile;

public interface DataBaseOperationInterface {
	public void createDB(String url, String driver, String usr, String psswd, String dbFile);
	public void connetToDB(String url, String driver, String usr, String psswd);
	public void createAutoInDB(Automobile auto);
	public void updateAutoOptionSetNameInDB(String autoUniName, String setName, String newName);
	public void updateAutoOptionPriceInDB(String autoUniName, String setName, 
										  String optionName, int newPrice);
	public void deleteAutoInDB(String autoUniName);
}
