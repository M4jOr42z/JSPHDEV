/**
 * @author zhexinq
 * Option class
 * name + option price
 */
package model;

import java.io.Serializable;

class Option implements Serializable {
	private static final long serialVersionUID = 7755419606014242069L;
	private String name;
	private int price;
	
	/* constructors */
	protected Option() {
		this.name = "";
		this.price = 0;
	}
	protected Option(String name, int price) {
		this.name = name;
		this.price = price;
	}
	protected Option(Option opt) {
		this.name = opt.getName();
		this.price = opt.getPrice();
	}
	
	/* getters */
	protected String getName() {
		return this.name;
	}
	protected int getPrice() {
		return this.price;
	}
	
	/* setters */
	protected void setName(String name) {
		this.name = name;
	}
	protected void setPrice(int price) {
		this.price = price;
	}
	
	/* print method for properties info */
	protected void printInfo() {
		System.out.printf("Option: %s, $%d\n", this.name, this.price);
	}
	
	/* unit test */
	public static void main(String[] args) {
		System.out.println("Test constructors");
		Option opt = new Option();
		opt.printInfo();
		opt = new Option("Present", 875);
		opt.printInfo();
		Option opt2 = new Option(opt);
		opt2.printInfo();
		
		System.out.println("\nTest getters");
		System.out.println("price: " + opt.getPrice());
		System.out.println("name: " + opt.getName());
		
		System.out.println("\nTest setters");
		opt.setName("Not present");
		opt.setPrice(12345);
		System.out.println("price: " + opt.getPrice());
		System.out.println("name: " + opt.getName());
		
	}
}
