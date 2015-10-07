/**
 * @author zhexinq
 * interface for a threaded socket client
 */

package client;

public interface SocketClientInterface {
	boolean openConnection();
	void handleSession();
	void closeSession();
}
