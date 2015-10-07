/**
 * @author zhexinq
 * interface for a threaded socket client
 */

package server;

public interface SocketClientInterface {
	boolean openConnection();
	void handleSession();
	void closeSession();
}
