/**
 * @author zhexinq
 * abstract class to implement database handler's business methods
 */

package database;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Automobile;

public abstract class ProxyDBHandler {
	private static JDBCAdapter adapter; // for connecting to a particular database
	private final static String defaultUrl = "jdbc:mysql://localhost:3306/";
	private final static String defualtDriver = "com.mysql.jdbc.Driver";
	private final static String defaultUsr = "root";
	private final static String defaultPsswrd = "";
	private final static String defaultIniFile = "create_database.sql";
	private static boolean showChanges = true;
	
	// create default empty tables and connect to default database
	public ProxyDBHandler() {
		adapter = new JDBCAdapter(defaultUrl, defualtDriver, defaultUsr, defaultPsswrd);
		adapter.makeTable(defaultIniFile);
	}
	
	// connect to database server and create a database with the file
	public void createDB(String url, String driver, String usr, String psswd, String dbFile) {
		adapter = new JDBCAdapter(url, driver, usr, psswd);
		adapter.makeTable(dbFile);
	}
	
	// connect to already existed database
	public void connetToDB(String url, String driver, String usr, String psswd) {
		adapter = new JDBCAdapter(url, driver, usr, psswd);
	}
	
	// create an Auto entry in DB's autos table
	public void createAutoInDB(Automobile auto) {
		if (adapter == null) {
			System.err.println("No connected DB.");
			return;
		}
		// insert autos table values
		PreparedStatement pstmt = adapter.getPreparedStatement("INSERT INTO autos (uniName, base_price) VALUES (?, ?)");
		try {
			pstmt.setString(1, auto.getUniqueName());
			pstmt.setInt(2, auto.getBasePrice());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// insert optionset table values
		String constraint = "WHERE uniName='" + auto.getUniqueName() + "'";
		int auto_id = adapter.getPrimaryKey("autos", constraint, "auto_id");
		for (String setName:auto.getOptionSetNames()) {
			pstmt = adapter.getPreparedStatement("INSERT INTO optionsets (set_name, auto_id) VALUES (?, ?)");
			try {
				pstmt.setString(1, setName);
				pstmt.setInt(2, auto_id);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// insert options table values
			constraint = "WHERE auto_id=" + auto_id + " AND set_name='" + setName + "'";
			int set_id = adapter.getPrimaryKey("optionsets", constraint, "set_id");
			for (String optName:auto.getOptionNames(setName)) {
				pstmt = adapter.getPreparedStatement("INSERT INTO options (opt_name, opt_price, set_id) VALUES (?, ?, ?)");
				try {
					pstmt.setString(1, optName);
					pstmt.setInt(2, auto.getOptionPrice(setName, optName));
					pstmt.setInt(3, set_id);
					pstmt.executeUpdate();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	// update an Auto entry in DB for setName
	public void updateAutoOptionSetNameInDB(String autoUniName, String setName, String newName) {
		if (adapter == null) {
			System.err.println("No connected DB.");
			return;
		}
		// find auto_id for the optionSet
		String constraint = "WHERE uniName='" + autoUniName + "'";
		int auto_id = adapter.getPrimaryKey("autos", constraint, "auto_id");
		PreparedStatement pstmt = adapter.getPreparedStatement("UPDATE optionsets SET set_name=? WHERE set_name=? AND auto_id=?");
		try {
			pstmt.setString(1, newName);
			pstmt.setString(2, setName);
			pstmt.setInt(3, auto_id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// update an Auto entry price in DB 
	public void updateAutoOptionPriceInDB(String autoUniName, String setName, 
										  String optionName, int newPrice) {
		if (adapter == null) {
			System.err.println("No connected DB.");
			return;
		}
		// find set_id for the option
		String constraint = "WHERE uniName='" + autoUniName + "'";
		int auto_id = adapter.getPrimaryKey("autos", constraint, "auto_id");
		constraint = "WHERE auto_id=" + auto_id + " AND " + "set_name='" + setName + "'";
		System.out.println("constraint: " + constraint);
		int set_id = adapter.getPrimaryKey("optionsets", constraint, "set_id");
		PreparedStatement pstmt = adapter.getPreparedStatement("UPDATE options SET opt_price=? WHERE opt_name=? AND set_id=?");
		try {
			pstmt.setInt(1, newPrice);
			pstmt.setString(2, optionName);
			pstmt.setInt(3, set_id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// delete an auto object from the DB
	public void deleteAutoInDB(String autoUniName) {
		if (adapter == null) {
			System.err.println("No connected DB.");
			return;
		}
		String deleteSql = "DELETE FROM autos WHERE uniName='" + autoUniName + "';";
		adapter.executeUpdate(deleteSql);
	}
	
	// show the table status after CRUD operations on autos in LHM
	public static void showTable(String tableName) {
		JFrame frame;
		frame = new JFrame("Table: " + tableName);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {;}});
		String query = "SELECT * FROM " + tableName;
		adapter.executeQuery(query);
		// Create the table
		JTable tableView = new JTable(adapter);

		JScrollPane scrollpane = new JScrollPane(tableView);
		scrollpane.setPreferredSize(new Dimension(700, 300));

		frame.getContentPane().add(scrollpane);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
	}
}
