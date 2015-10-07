/**
 * @author zhexinq
 * Interface for providing contracts for building an Auto object
 * and print Auto object info
 */

package adapter;

import util.Properties;

public interface CreateAuto {
	public void buildAuto(String filename);
	public void printAuto(String modelName);
	
}
