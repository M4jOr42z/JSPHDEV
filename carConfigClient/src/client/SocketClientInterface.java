/**
 * @author zhexinq
 * interface for defaut socket client
 */

package client;

public interface SocketClientInterface {
	boolean openConnection();
	void handleSession();
	void closeSession();
}
