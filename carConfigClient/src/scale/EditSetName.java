/**
 * @author zhexinq
 * thread class for editing a setName of a Auto model
 * since each thread has only one run method, I find it
 * be more clear to create different thread classes to
 * do differennt jobs
 */

package scale;

import model.Automobile;

public class EditSetName extends Thread {
	private Automobile a1;
	private String setName;
	private String newName;

	public EditSetName(String tName, Automobile a1, String setName, String newName) {
		super(tName);
		this.a1 = a1;
		this.setName = setName;
		this.newName = newName;
	}
	
	/* the thread will lock a1 before editing
	 * other threads also want to edit the same a1 have to wait
	 */
	public void run() {
		synchronized (a1) {
			a1.updatOptionSetName(setName, newName);
			System.out.printf("Edit Auto %s %s's (set name: %s) to (new name: %s)\n", 
							   a1.getMake(), a1.getModel(), setName, newName);		
		}
	}

}
