/**
 * @author zhexinq
 * AutoServer interface to be implemented by BuildAuto and BuildCarModelOptions
 */
package server;

import util.Properties;
import model.Automobile;

public interface AutoServer {
	public boolean loadPropsToAuto(Properties p);
}
