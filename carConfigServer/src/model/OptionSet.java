/**
 * @author zhexinq
 * OptionSet class that has a name and a set of Options
 */
package model;

import java.io.*;
import java.util.ArrayList;

class OptionSet implements Serializable {
	private static final long serialVersionUID = 5319384422974160381L;
	private String name;
	private ArrayList<Option> options;
	private Option choice;

	/* constructor */
	protected OptionSet() {
		this.name = "";
		this.options = new ArrayList<Option>();
	}
	protected OptionSet(String name) {
		this.name = name;
		this.options = new ArrayList<Option>();
	}
	protected OptionSet(OptionSet opset) {
		this.name = opset.getName();
		this.options = new ArrayList<Option>();
		for (Option opt:opset.getOptions())
			this.options.add(new Option(opt));
	}
	protected OptionSet(ArrayList<Option> options, String name) {
		this.name = name;
		this.options = new ArrayList<Option>();
		for (Option opt:options)
			this.options.add(new Option(opt));
	}
	
	/* getters */
	protected String getName() {
		return this.name;
	}
	protected ArrayList<Option> getOptions(){
		return this.options;
	}
	protected Option getOption(int n) {
		if (n >= 0 || n < this.options.size())
			return this.options.get(n);
		return null;
	}
	protected Option getOption(String name) {
		for (int i = 0; i < this.options.size(); i++) {
			Option opt = this.options.get(i);
			if (opt.getName().equals(name))
				return opt;
		}
		return null;
	}
	protected Option getOptionChoice() {
		return this.choice;
	}
	
	/* setters */
	protected void setName(String n) {
		this.name = n;
	}
	protected void setOptions(ArrayList<Option> opts) {
		this.options = new ArrayList<Option>();
		for (Option opt:opts)
			this.options.add(opt);
	}
	protected void setOption(int n, Option opt) {
		if (n >= 0 && n < options.size()) {
			this.options.set(n, opt);
		}
	}
	protected void setOption(String name, Option opt) {
		int N = this.options.size();
		for (int i = 0; i < N; i++) {
			Option oldopt = this.options.get(i);
			if (oldopt.getName().equals(name)) {
				this.options.set(i, opt);
			}
		}
			
	}
	protected void setOptionChoice(String optionName) {
		for (Option opt:this.options) {
			if (opt.getName().equals(optionName))
				this.choice = opt;
		}
	}
	
	/* add an option */
	protected void addOption(Option opt) {
		this.options.add(opt);
	}
	
	/* remove an option */
	protected void removeOption(Option opt) {
		this.options.remove(opt);
	}
	
	/* print method */
	protected void printInfo() {
		System.out.printf("The OptionSet %s:\n", this.name);
		for (Option opt:this.options) 
			opt.printInfo();
	}

	/* unit test */
	public static void main(String[] args) {
		// test OptionSet methods
		// constructors
		System.out.println("Test constructors");
		OptionSet set = new OptionSet();
		set.printInfo();
		set = new OptionSet("Standatd");
		set.printInfo();
		OptionSet set2;
		set2 = new OptionSet(set);
		set2.printInfo();
		ArrayList<Option> options = new ArrayList<Option>();
		options.add(new Option("Present", 875));
		options.add(new Option("Not present", 0));
		options.add(new Option("Half present", 415));
		set2 = new OptionSet(options, "moonroof");
		set2.printInfo();
		
		// getters
		System.out.println("\nTest getters");
		System.out.println("Name: " + set2.getName());
		for (Option opt:set2.getOptions())
			opt.printInfo();
		Option opt = set2.getOption(0);
		opt.setName("XYZLLL");
		set2.printInfo();
		opt = set2.getOption("Not present");
		opt.setPrice(678);
		set2.printInfo();
		
		// setters
		System.out.println("\nTest setters");
		set2.setName("sunroof");
		set2.printInfo();
		set2.setOption(0, new Option("Premium", 999));
		set2.printInfo();
		set2.setOption("Premium", new Option("Bronze", 123));
		set2.printInfo();
		System.out.println(options.size());
		set2.setOptions(options);
		set2.printInfo();
		
		// add & remove
		Option o = new Option("newOpt", 912);
		set2.addOption(o);
		set2.printInfo();
		set2.removeOption(o);
		set2.printInfo();
		
	}
}
