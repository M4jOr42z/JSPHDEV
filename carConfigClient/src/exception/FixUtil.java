/**
 * @author zhexinq
 * helper class to fix all exceptions raised in util package
 */

package exception;

import java.util.Scanner;

public class FixUtil {
	
	/* fix error when the input file cannot be found or opened */
	// ask the user to retype a correct filename
	// errno 0
	public String fixFileNotFoundException() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a correct name of input file:");
		String filename = scan.nextLine();
		scan.close();
		return filename;
	}
	
	/* fix error when input file header is malformed */
	// e.g. not in the form "description: model, optionset size, base price"
	// errno 1
	public void fixMalformedHeader() {
		System.out.println("Load input file failed, please check the input header is in right form");
	}
	
	/* fix when no base price provided */
	// give a deault price when no provided
	// errno 2
	public String fixMissingBasePrice() {
		Scanner scan = new Scanner(System.in);
		String result = null;
		boolean isNumber = false;
		
		System.out.println("missing base price in the input file, please enter a value:");
		do {
			try {
				result = scan.nextLine();
				Integer.parseInt(result);
				isNumber = true;
			}
			catch (Exception e) {
				System.out.println("Not a number, enter another:");
			}
		} while (!isNumber);
		scan.close();
		return result;
	}
	
	/* fix when no OptionSets size provided */
	// errno 3
	public String fixMissingOptionSetsSize() {
		Scanner scan = new Scanner(System.in);
		String result = null;
		boolean isNumber = false;
		
		System.out.println("missing OptionSets size, please enter one");
		do {
			try {
				result = scan.nextLine();
				Integer.parseInt(result);
				isNumber = true;
			}
			catch (Exception e) {
				System.out.println("Not a number, enter another:");
			}
		} while (!isNumber);
		scan.close();
		return result;
	}
	
	/* fix error when Option price is missing */
	// errno 4
	public String fixMissingOptionPrice() {
		Scanner scan = new Scanner(System.in);
		String result = null;
		boolean isNumber = false;
		
		System.out.println("missing Option price, please enter one");
		do {
			try {
				result = scan.nextLine();
				Integer.parseInt(result);
				isNumber = true;
			}
			catch (Exception e) {
				System.out.println("Not a number, enter another:");
			}
		} while (!isNumber);
		scan.close();
		
		return result;
	}

}
