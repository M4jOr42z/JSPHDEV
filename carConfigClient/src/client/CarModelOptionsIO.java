/**
 * @author zhexinq
 * class in the client for:
 * 			- read data from props file to an props object
 * 			- transfers props object from client to server and receives verification
 * 			- 
 */
package client;

public class CarModelOptionsIO extends DefaultSocketClient {

	public CarModelOptionsIO(String strHost, int iPort) {
		super(strHost, iPort);
	}
	
	
}
