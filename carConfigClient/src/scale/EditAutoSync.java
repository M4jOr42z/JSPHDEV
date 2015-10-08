/**
 * @author zhexinq
 * API for connecting BuildAuto with EditOption classes to edit an option
 * with multiple threads
 */
package scale;

public interface EditAutoSync {
	public void editSetNameSync(String model, String setName, String newName);
	public void editOptionNameSync(String model, String setName, String optName, String newName);
}
