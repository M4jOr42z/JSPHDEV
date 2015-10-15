/**
 * @author zhexinq
 * Exception class for raising errors when exceeding 40 students
 */
package errorHandling;

public class TooManyStudents extends Exception{

	private static final long serialVersionUID = -346654102048738452L;

	public TooManyStudents() {
		super();
	}
	
}
