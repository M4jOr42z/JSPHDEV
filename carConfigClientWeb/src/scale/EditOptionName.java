/**
 * @author zhexinq
 * thread class for edit option name of an auto object 
 */

package scale;

import model.Automobile;

public class EditOptionName extends Thread {
	Automobile a1;
	String setName;
	String optionName;
	String newName;
	
	public EditOptionName(String tName, Automobile a1, String setName, String optionName, String newName) {
		super(tName);
		this.a1 = a1;
		this.setName = setName;
		this.optionName = optionName;
		this.newName = newName;
	}
	
	/* method to show corruption without synchronization */
	void randomWait() {
		try {
			Thread.currentThread().sleep((long) (3000*Math.random()));
		}
		catch (InterruptedException e) {
			System.out.printf("thread %s intterupted\n", Thread.currentThread().getName());
		}
	}
	
	
	/* the thread will lock a1 before editing
	 * other threads also want to edit the same a1 have to wait
	 * uncomment the synchronized block to make edit operation synchronized
	 */
	public void run() {
//		synchronized (a1) {
			System.out.printf("thread %s: starts editing %s %s's Option name: (%s, %s) -> (%s, %s)\n", 
							   this.getName(), a1.getMake(), a1.getModel(), setName, optionName, setName, newName);
			a1.updateOptionName(setName, optionName, newName);
			randomWait();
			System.out.printf("thread %s: edited Auto %s %s's Option name: (%s, %s) -> (%s, %s)\n", 
							   this.getName(), a1.getMake(), a1.getModel(), setName, optionName, setName, newName);
//		}
	}

}
