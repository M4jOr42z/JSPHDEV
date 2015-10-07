/**
 * @author zhexinq
 * Automotive class for definition of car model
 * 
 * each car model has a set of properties: 
 * color, transmission, brakes/traction control, airbags, moonroof, AND a base price.
 * each property has a set of options, AND a name
 * each option has a price, AND a name.
 * 
 * users of Automotive objects can read the property values using print method,
 * and modify the properties using update/delete methods
 */
package model;

import java.io.*;
import java.util.ArrayList;

public class Automobile implements Serializable {
	private static final long serialVersionUID = -6602495310727640916L;
	private String make;
	private String model;
	private int basePrice;
	private ArrayList<OptionSet> opsets;
	
	/* constructor */
	// in default a Focus Wagon ZTW model, there are 5 OptionSets, base price $18445
	public Automobile() {
		this.make = "unknown";
		this.model = "unknown";
		this.basePrice = 0;
		this.opsets = new ArrayList<OptionSet>();
	}
	public Automobile(String make, String model, int price) {
		this.make = make;
		this.model = model;
		this.basePrice = price;
		this.opsets = new ArrayList<OptionSet>();
	}
	
	/* getters */
	public String getMake() {
		return this.make;
	}
	public String getModel() {
		return this.model;
	}
	public int getBasePrice() {
		return this.basePrice;
	}
	public ArrayList<OptionSet> getOptionSets() {
		return this.opsets;
	}
	
	/* find methods */
	// find an OptionSet reference in Auto by name if exist, else return null
	public OptionSet findOptionSet(String name) {
		for (int i = 0; i < this.opsets.size(); i++) {
			OptionSet opset = this.opsets.get(i);
			if (opset.getName().equals(name))
				return opset;
		}
		return null;
	}
	// find an OptionSet index by name if exist, else return -1
	public int findOptionSetIndex(String name) {
		for (int i = 0; i < this.opsets.size(); i++) {
			if (this.opsets.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}
	// find an Option reference in Auto by name in context of an OptionSet
	// if not exist, return null
	public Option findOption(String setname, String optname) {
		OptionSet opset = findOptionSet(setname);
		if (opset != null) {
			ArrayList<Option> options = opset.getOptions();
			for (Option opt:options) {
				if (opt.getName().equals(optname))
					return opt;
			}
		}
		return null;
	}
		
	/* setter */
	public void setMake(String make) {
		this.make = make;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setBasePrice(int p) {
		this.basePrice = p;
	}
	public void setOptionSet(int n, OptionSet set) {
		if (n >= 0 && n < this.opsets.size())
			this.opsets.set(n, set);
	}
	public void setOptionSet(String setName, OptionSet set) {
		int i = findOptionSetIndex(setName);
		if (i >= 0)
			this.opsets.set(i, set);
	}
	public void setOptionSets(ArrayList<OptionSet> sets) {
		this.opsets = new ArrayList<OptionSet>();
		for (OptionSet set:sets) 
			this.opsets.add(new OptionSet(set));
	}
	public void setOption(String setName, String optName, Option opt) {
		OptionSet set = findOptionSet(setName);
		if (set != null)
			set.setOption(optName, opt);
	}
	
	/* updaters */
	// update a new set to auto
	public void updateNewOptionSet(String setName, String[] setOptions, int[] setPrices) {
		assert((setOptions.length == setPrices.length) && setOptions.length >= 0);
		
		// number of options
		int N = setOptions.length;
		// construct the new OptionSet object for update
		ArrayList<Option> options = new ArrayList<Option>();
		for (int i = 0; i < N; i++) {
			options.add(new Option(setOptions[i], setPrices[i]));
		}	
		OptionSet optset = new OptionSet(options, setName);
		// add the constructed OptionSet back
		this.opsets.add(optset);
	}
	// update an OptionSet by name
	public void updateOptionSet(String setName, String[] setOptions, int[] setPrices) {
		assert(setOptions.length == setPrices.length && setOptions.length >= 0);
		
		OptionSet set = findOptionSet(setName);
		/* proceed if setName exist */
		if (set != null) {
			int N = setOptions.length;
			ArrayList<Option> options = new ArrayList<Option>();
			for (int i = 0; i < N; i++) {
				options.add(new Option(setOptions[i], setPrices[i]));
			}
			OptionSet optset = new OptionSet(options, setName);
			/* find the index of set wanted to be updated */
			int i = findOptionSetIndex(setName);
			/* update the set wanted to be updated */
			this.opsets.set(i, optset);
		}
	}
	// update OptionSet name
	public void updatOptionSetName(String setName, String newName) {
		OptionSet optset = findOptionSet(setName);
		if (optset != null)
			optset.setName(newName);
	}
	// update a new Option into OptionSet
	public void updateNewOption(String setName, String optName, int optPrice) {
		OptionSet set = findOptionSet(setName);
		if (set != null)
			set.addOption(new Option(optName, optPrice));
	}
	// update an Option name in OptionSet
	public void updateOptionName(String setName, String optName, String newName) {
		Option opt = findOption(setName, optName);
		if (opt != null)
			opt.setName(newName);
	}
	// update an Option price in OptionSet by name
	public void updateOptionPrice(String setName, String optName, int optPrice) {
		
		Option opt = findOption(setName, optName);
		if (opt != null)
			opt.setPrice(optPrice);		
	}
	
	/* deleters */
	// delete OptionSet by name
	public void deleteOptionSet(String setName) {
		OptionSet set = findOptionSet(setName);
		if (set != null)
			this.opsets.remove(set);
	}
	// delete an Option in the context of an OptionSet by name
	public void deleteOption(String setName, String optName) {
		OptionSet set = findOptionSet(setName);
		if (set != null) {
			Option opt = findOption(setName, optName);
			if (opt != null)
				set.removeOption(opt);
		}
	}
	
	/* choice methods */
	/* get the choice in an optionset */
	// return null if user didn't choose yet
	public String getOptionChoice(String setName) {
		OptionSet set = findOptionSet(setName);
		if (set != null) {
			return set.getOptionChoice().getName();
		}
		return null;
	}
	/* get choice price of a set */
	// return -1 if user didn't choose yet
	public int getOptionChoicePrice(String setName) {
		OptionSet set = findOptionSet(setName);
		if (set != null)
			return set.getOptionChoice().getPrice();
		return -1;
	}
	/* get total price */
	// if not all options has been made, return -1
	public int getTotalPrice() {
		int total = 0;
		for (OptionSet set:this.opsets) {
			Option choice = set.getOptionChoice();
			if (choice == null) {
				return -1;
			}
			else
				total += choice.getPrice();	
		}
		total += basePrice;
		return total;
	}
	/* choose a particular set, and make a choice */
	public void setOptionChoice(String setName, String optName) {
		OptionSet set = findOptionSet(setName);
		if (set != null) {
			set.setOptionChoice(optName);
		}
	}
	
	/* print */
	public void printInfo() {
		System.out.printf("make: %s, model: %s, base price: %d\n", this.make, this.model, this.basePrice);
		for (OptionSet set:this.opsets) {
			set.printInfo();
		}
		int totalPrice = getTotalPrice();
		if (totalPrice >= 0)
			System.out.printf("Price calculated from user choice: %d", totalPrice);
		else
			System.out.println("Price cannot be shown, since user didn't choose all options yet");
	}
	
	/* unit test */
	public static void main(String[] args) {
		// test Automotive methods
		System.out.println("constructors: ");
		Automobile auto = new Automobile();
		auto.printInfo();
		Automobile auto2 = new Automobile("Honda", "Accord", 19000);
		auto2.printInfo();
		
		// updaters
		System.out.println("update the OptionSet's Options using name");
		String[] mrOptions = {"full", "half"}, brakes = {"standard", "ABS", "Advanced ABS"};
		int[] mrp = {1000, 500}, brk = {0, 615, 1230};
		auto2.updateNewOptionSet("moonroof", mrOptions, mrp);
		auto2.updateNewOptionSet("brakes", brakes, brk);
		auto2.printInfo();
		String[] color = {"red", "green", "blue"};
		int[] cp = {1, 2, 3};
		auto2.updateOptionSet("moonroof", color, cp);
		auto2.printInfo();
		auto2.updatOptionSetName("moonroof", "sunroof");
		auto2.printInfo();
		auto2.updateNewOption("sunroof", "red", 91);
		auto2.printInfo();
		auto2.updateOptionName("sunroof", "red", "purple");
		auto2.printInfo();
		auto2.updateOptionPrice("sunroof", "purple", -55);
		auto2.printInfo();
		System.out.println("");
		
		// setters
		System.out.println("\ntest setters");
		ArrayList<Option> options1 = new ArrayList<Option>();		
		ArrayList<Option> options2 = new ArrayList<Option>();
		ArrayList<OptionSet> optionSets = new ArrayList<OptionSet>();
		options1.add(new Option("automatic", 0));
		options1.add(new Option("manual", -815));
		OptionSet set1 = new OptionSet(options1, "Transmission");
		options2.add(new Option("standard", 0));
		options2.add(new Option("ABS", 400));
		options2.add(new Option("advanced ABS", 1625));
		OptionSet set2 = new OptionSet(options2, "Brakes");
		optionSets.add(set1);
		optionSets.add(set2);
		auto2.setOptionSets(optionSets);
		auto2.printInfo();
		auto2.setOptionSet("Transmission", new OptionSet());
		auto2.printInfo();
		auto2.setOptionSet(0, set1);
		auto2.printInfo();
		auto2.setOption("Transmission", "automatic", new Option("what?", 444));
		auto2.printInfo();
		auto2.printInfo();
		
		// getters
		// finders
		
		// deleters
//		System.out.println("\ntest deleters");
//		auto2.deleteOptionSet("Transmission");
//		auto2.printInfo();
//		auto2.deleteOption("Brakes", "standard");
//		auto2.printInfo();
		
		// test make choice
		String setstr1 = "Brakes";
		String setstr2 = "Transmission";
		auto2.setOptionChoice(setstr1, "ABS");
		auto2.setOptionChoice(setstr2, "manual");
		auto2.printInfo();
		System.out.printf("\nChosen option for set %s: %s %d\n", setstr1, 
							auto2.getOptionChoice(setstr1), auto2.getOptionChoicePrice(setstr1));
		System.out.printf("Chosen option for set %s: %s %d\n", setstr2, 
							auto2.getOptionChoice(setstr2), auto2.getOptionChoicePrice(setstr2));
	}
}
