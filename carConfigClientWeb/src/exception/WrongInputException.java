/**
 * @author zhexinq
 * exception for input file name not found
 */
package exception;

public class WrongInputException extends AutoException {

	public WrongInputException(int errno) {
		super(errno);
	}

}
