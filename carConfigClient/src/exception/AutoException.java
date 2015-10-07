/**
 * @author zhexinq
 * custom exception class for handling exceptions that could happen
 * during I/O operations
 * 
 */

package exception;

import java.io.*;
import java.util.Date;

public class AutoException extends Exception {
	int errno;
	String errmsg;
	static final int[] ERROR_NUMS  = {0, 1, 2, 3, 4};
	static final String[] ERROR_MSGS = {"Wrong input filename.", "Malformed header", "missing base price", 
										"missing OptionSets size", "mssing Option price"};
	/* build specific exception */
	public AutoException(int errno) {
		if (errno < ERROR_NUMS.length) {
			this.errno = errno;
			this.errmsg = ERROR_MSGS[errno];
		}
		else
			System.out.println("error number does not exist.");
	}
	
	/* get error number of the exception */
	public int getErrno() {
		return errno;
	}
	/* get error message of the exception */
	public String getErrmsg() {
		return errmsg;
	}
	
	/* set error number of the exception */
	public void setErrno(int errno) {
		this.errno = errno;
	}
	/* set error message of the exception */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	/* log tiemstep and corresponding exception into a file */
	public void recordLog(String filename, AutoException e) {
		Date date = new Date();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filename, true));
			bw.newLine();
			bw.write(date.toString());
			bw.write(" errno: ");
			bw.write(e.getErrno());
			bw.write(" errmsg: ");
			bw.write(e.getErrmsg());
		}
		catch (IOException e2) {
			e2.printStackTrace();
			System.out.println("log file name is wrong.");
		}
		finally {
			try {
				bw.close();
			}
			catch (IOException e2) {
				System.out.println("log file cannot be closed");
			}
		}
	}
	
	/* fix method to fix any exceptions in the entire application */
	public String fix(int errno) {
		FixUtil f1 = new FixUtil();
		String result = null;
		
		switch (errno) {
		case 0: result = f1.fixFileNotFoundException();break;
		case 1: f1.fixMalformedHeader();break;
		case 2: result = f1.fixMissingBasePrice();break;
		case 3: result = f1.fixMissingOptionSetsSize();break; 
		case 4: result = f1.fixMissingOptionPrice();break;
		}
		return result;
	}
	
	/* print out the error information */
	public void printInfo() {
		System.out.printf("(error num: %d, error message: %s)\n", errno, errmsg);
	}
	
	public static void main(String[] args) {
		try {
			throw new AutoException(0);
		}
		catch (Exception e) {
			try {
				throw new AutoException(1);
			}
			catch (AutoException e1) {
				System.out.println(e1.getErrmsg());
			}
		}
	}
	
}
