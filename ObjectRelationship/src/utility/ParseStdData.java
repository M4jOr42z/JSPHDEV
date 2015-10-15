/**
 * @author zhexinq
 * utility interface provides function to 
 *  - parse scores data and returns a filled Student array according to
 *    specific implementation. Can throw TooManyStudents exception
 */
package utility;

import java.io.BufferedReader;
import errorHandling.*;
import lab2.Student;

public interface ParseStdData {
	public void parseAndFill(BufferedReader file, Student[] stu) throws TooManyStudents, MalformedInput;
}
