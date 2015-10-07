/**
 * @author zhexinq
 * exception for no valid option price data
 */
package exception;

public class MissingOptionPriceException extends AutoException {

	public MissingOptionPriceException(int errno) {
		super(errno);
	}

}
