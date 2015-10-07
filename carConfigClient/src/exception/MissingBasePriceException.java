/**
 * @author zhexinq
 * exception for missing base price in input file
 */
package exception;

public class MissingBasePriceException extends AutoException {

	public MissingBasePriceException(int errno) {
		super(errno);
	}

}
