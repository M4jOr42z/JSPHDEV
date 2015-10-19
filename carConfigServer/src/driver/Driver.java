/**
 * @author zhexinq
 * driver class for testing persisting LHM in DB
 */

package driver;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFrame;

import adapter.BuildAuto;
import database.ProxyDBHandler;
import util.FileIO;
import util.Properties;

public class Driver {

	
	public static void main(String[] args) {
		BuildAuto autoFactory = new BuildAuto();
		/* load with a bunch of car files, the database should also persist thest files */
		FileIO io = new FileIO();
		Properties p = io.parseProps("props/aucura.txt");
		autoFactory.loadPropsToAuto(p);
		p = io.parseProps("props/corvette.txt");
		autoFactory.loadPropsToAuto(p);
		p = io.parseProps("props/prius.txt");
		autoFactory.loadPropsToAuto(p);
		System.out.println("Loaded 3 car files... type tables names to see the changes..."
				+ " table names: autos, optionsets, options... type c to continue");
		Scanner sc = new Scanner(System.in);
		String line;
		while (!(line = sc.nextLine()).equals("c")) {
			ProxyDBHandler.showTable(line);
		}
		/* change the option price */
		autoFactory.updateOptionPrice("Aucura ILX", "Color", "Black", 99999);
		System.out.println("Change Aucura ILX's Color Option Black's price");
		System.out.println("type tables names to see the changes..."
				+ " table names: autos, optionsets, options... type c to continue");
		while (!(line = sc.nextLine()).equals("c")) {
			ProxyDBHandler.showTable(line);
			System.out.println("type tables names to see the changes..."
					+ " table names: autos, optionsets, options... type c to continue");
		}
		/* change optionset name */
		autoFactory.updateOptionSetName("Chevrolet Corvette STINGRAY", "Transmission", "Super Drivetrain");
		System.out.println("Change Chevrolet Corvette STINGRAY's OptionSet name Transmission");
		System.out.println("type tables names to see the changes..."
				+ " table names: autos, optionsets, options... type c to continue");
		while (!(line = sc.nextLine()).equals("c")) {
			ProxyDBHandler.showTable(line);
		}
		/* delete an auto */
		autoFactory.deleteAuto("Toyota Prius");
		System.out.println("Delete Toyota Prius from the DB");
		System.out.println("type tables names to see the changes..."
				+ " table names: autos, optionsets, options... type c to continue");
		while (!(line = sc.nextLine()).equals("c")) {
			ProxyDBHandler.showTable(line);
		}
	}

}