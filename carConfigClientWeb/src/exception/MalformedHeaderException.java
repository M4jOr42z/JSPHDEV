/**
 * @author zhexinq
 * exception for malformed input header
 */
package exception;

public class MalformedHeaderException extends AutoException {

	public MalformedHeaderException(int errno) {
		super(errno);
	}

}
