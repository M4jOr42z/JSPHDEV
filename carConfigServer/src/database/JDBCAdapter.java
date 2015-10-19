/**
 * @author zhexinq
 * an abstract adapter class for executing commands 
 */

package database;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JDBCAdapter extends AbstractTableModel {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private String[] columnNames = {};
	private ArrayList<Object> rows = new ArrayList<Object>();
	private ResultSetMetaData metaData;
	
	
	// connect to database and create a statement for execute sql
	public JDBCAdapter(String url, String driverName, String user, String passwd) {
		try {
			Class.forName(driverName);
			System.out.println("Openning db connection");
			
			connection = DriverManager.getConnection(url, user, passwd);
			statement = connection.createStatement();
		} 
		catch (ClassNotFoundException e) {
			System.err.println("Cannot find the databaase driver class");
			System.err.println(e);
		}
		catch (SQLException e) {
			System.err.println("Cannot connect to this database");
			System.err.println(e);
		}
	}
	
	// create a table using databaseCreation.sql file that contains sql commands
	public void makeTable(String databaseFile) {
		if (connection == null || statement == null) {
			System.err.println("No connected to database");
			return;
		}
		try {
			StringBuffer command = new StringBuffer();
			BufferedReader in = new BufferedReader(new FileReader(databaseFile));
			String line;
			// read the whole table creation file 
			while ((line = in.readLine()) != null) {
				command.append(line).append("\n");
				if (line.contains(";")) {
					// execute when a statement finished
					statement.executeUpdate(command.toString());
					command = new StringBuffer();
				}
			}
			System.out.println("Finish creating tables");
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found.");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("reading file input error");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.err.println("sql error");
			e.printStackTrace();
		}
	}
	
	// DML operations
	public void executeUpdate(String command) {
		try {
			statement.executeUpdate(command);
		} catch (SQLException e) {
			System.err.println("sql error");
			e.printStackTrace();
		}
	}
	
	// DQL operations
	public void executeQuery(String query) {
        if (connection == null || statement == null) {
            System.err.println("There is no database to execute the query.");
            return;
        }
        try {
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();

            int numberOfColumns =  metaData.getColumnCount();
            columnNames = new String[numberOfColumns];
            // Get the column names and cache them.
            // Then we can close the connection.
            for(int column = 0; column < numberOfColumns; column++) {
                columnNames[column] = metaData.getColumnLabel(column+1);
            }

            // Get all rows.
            rows = new ArrayList<Object>();
            while (resultSet.next()) {
            	ArrayList<Object> newRow = new ArrayList<Object>();
                for (int i = 1; i <= getColumnCount(); i++) {
	            newRow.add(resultSet.getObject(i));
                }
                rows.add(newRow);
            }
            //  close(); Need to copy the metaData, bug in jdbc:odbc driver.
            fireTableChanged(null); // Tell the listeners a new table has arrived.
        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
    }
	
	// close db connections;
    public void close() throws SQLException {
        System.out.println("Closing db connection");
        resultSet.close();
        statement.close();
        connection.close();
    }
    
    // get the the column value of the first row in the result
    public int getPrimaryKey(String tableName, String constraint, String colName) {
    	String sql = "";
    	try {
    		sql = "SELECT * FROM " + tableName + " " + constraint + ";";
	    	resultSet = statement.executeQuery(sql);
	    	while (resultSet.next())
	    		return resultSet.getInt(colName);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	System.out.println("sql: " + sql);
    	return -1;
    }
    
    // get prepared statement
    public PreparedStatement getPreparedStatement(String sql) {
    	try {
    		return connection.prepareStatement(sql);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
    //////////////////////////////////////////////////////////////////////////
    //
    //             Implementation of the TableModel Interface
    //
    //////////////////////////////////////////////////////////////////////////
 // MetaData

    public String getColumnName(int column) {
        if (columnNames[column] != null) {
            return columnNames[column];
        } else {
            return "";
        }
    }

    public Class getColumnClass(int column) {
        int type;
        try {
            type = metaData.getColumnType(column+1);
        }
        catch (SQLException e) {
            return super.getColumnClass(column);
        }

        switch(type) {
        case Types.CHAR:
        case Types.VARCHAR:
        case Types.LONGVARCHAR:
            return String.class;

        case Types.BIT:
            return Boolean.class;

        case Types.TINYINT:
        case Types.SMALLINT:
        case Types.INTEGER:
            return Integer.class;

        case Types.BIGINT:
            return Long.class;

        case Types.FLOAT:
        case Types.DOUBLE:
            return Double.class;

        case Types.DATE:
            return java.sql.Date.class;

        default:
            return Object.class;
        }
    }

    public boolean isCellEditable(int row, int column) {
        try {
            return metaData.isWritable(column+1);
        }
        catch (SQLException e) {
            return false;
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    // Data methods

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int aRow, int aColumn) {
        ArrayList<Object> row = (ArrayList<Object>)rows.get(aRow);
        return row.get(aColumn);
    }

    public String dbRepresentation(int column, Object value) {
        int type;

        if (value == null) {
            return "null";
        }

        try {
            type = metaData.getColumnType(column+1);
        }
        catch (SQLException e) {
            return value.toString();
        }

        switch(type) {
        case Types.INTEGER:
        case Types.DOUBLE:
        case Types.FLOAT:
            return value.toString();
        case Types.BIT:
            return ((Boolean)value).booleanValue() ? "1" : "0";
        case Types.DATE:
            return value.toString(); // This will need some conversion.
        default:
            return "\""+value.toString()+"\"";
        }

    }

    public void setValueAt(Object value, int row, int column) {
        try {
            String tableName = metaData.getTableName(column+1);
            // Some of the drivers seem buggy, tableName should not be null.
            if (tableName == null) {
                System.out.println("Table name returned null.");
            }
            String columnName = getColumnName(column);
            String query =
                "update "+tableName+
                " set "+columnName+" = "+dbRepresentation(column, value)+
                " where ";
            // We don't have a model of the schema so we don't know the
            // primary keys or which columns to lock on. To demonstrate
            // that editing is possible, we'll just lock on everything.
            for(int col = 0; col<getColumnCount(); col++) {
                String colName = getColumnName(col);
                if (colName.equals("")) {
                    continue;
                }
                if (col != 0) {
                    query = query + " and ";
                }
                query = query + colName +" = "+
                    dbRepresentation(col, getValueAt(row, col));
            }
            System.out.println(query);
            System.out.println("Not sending update to database");
            // statement.executeQuery(query);
        }
        catch (SQLException e) {
            //     e.printStackTrace();
            System.err.println("Update failed");
        }
        ArrayList<Object> dataRow = (ArrayList<Object>)rows.get(row);
        dataRow.set(column, value);

    }
	
	// unit test
	public static void main(String[] args) {
		JDBCAdapter adapter = new JDBCAdapter("jdbc:mysql://localhost:3306/", "com.mysql.jdbc.Driver", "root", "");
		JFrame frame;
		adapter.executeUpdate("use autos;");
		frame = new JFrame("Table: " + "autos");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);}});
		String query = "SELECT * FROM " + "autos WHERE uniName='honda accord'";
		adapter.executeQuery(query);
//		int pKey = adapter.getPrimaryKey(query, "auto_id");
//		System.out.println("Primary key: " + pKey);

//
		// Create the table
		JTable tableView = new JTable(adapter);

		JScrollPane scrollpane = new JScrollPane(tableView);
		scrollpane.setPreferredSize(new Dimension(700, 300));

		frame.getContentPane().add(scrollpane);
		frame.pack();
		frame.setVisible(true);
	}

}
