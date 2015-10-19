/**
 * @author zhexinq
 * Empty class that just deals with interfaces
 */
package adapter;

import scale.EditAutoSync;
import server.AutoServer;

public class BuildAuto extends ProxyAutomobile implements 
CreateAuto, UpdateAuto, FixAuto, EditAutoSync, AutoServer, DeleteAuto {
	
}
