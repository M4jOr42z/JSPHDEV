/**
 * @author zhexinq
 * utility class that reads in a file, parse it, and stores the result
 * into corresponding properties of an array of students
 * constraints: - fill in only the first 40 (or less than 40) students' data
 * 	 			- raise TooManyStudents if input file >= 40 students
 * 				- raise MalformedInput when a data line does not provide required number of quiz
 * 				- raise NumberFormatException when a data line contains non-number token
 * 				- raise some other exception when performing I/O operation
 * all exceptions are handled accordingly
 */
package lab2;

import utility.ParseStdData;
import java.io.*;
import errorHandling.*;
import java.util.StringTokenizer;

public class Util implements ParseStdData {

	/* implements interface method for specified requirements */
	/* fill in Student array from buffered file according to specified logic */
	@Override
	public void parseAndFill(BufferedReader file, Student[] stu) throws TooManyStudents, MalformedInput {
		String line;
		int stuIndex;
		int SID_token;
		int[] scores_token = new int[Student.QUIZ_NUM];
		try {
			/* ignore the first line */
			line = file.readLine();
			/* not an empty file */
			if (line != null) {	
				stuIndex = 0;
				while (true) {
					/* more than 40 students, throws exception */
					if (stuIndex >= Student.MAX_STU_NUM)
						throw new TooManyStudents();
					line = file.readLine();
					/* end of file encountered */
					if (line == null)
						break;
					StringTokenizer st = new StringTokenizer(line);
					if (st.countTokens() != (Student.QUIZ_NUM + 1))
						throw new MalformedInput();
					SID_token = Integer.parseInt(st.nextToken());
					for (int i = 0; i < Student.QUIZ_NUM; i++)
						scores_token[i] = Integer.parseInt(st.nextToken());
					/* populate the student */
					stu[stuIndex] = new Student();
					/* parse the student ID and quiz scores */
					stu[stuIndex].setSID(SID_token);
					stu[stuIndex].setScores(scores_token);
					stuIndex++;
				}
			}
			file.close();
		}
		catch (IOException e) {
			System.out.println("error -- " + e.toString());
		}
		catch (NumberFormatException e) {
			System.out.println("Data cannot be converted to number");
		}
	}
	
	/* Reads the file and builds student array */
	public static Student[] readFile (String filename, Student[] stu) {
		try {
			/* build input buffered stream for text file */
			FileReader input = new FileReader(filename);
			BufferedReader file = new BufferedReader(input);
			/* create a Util object for parsing data file and filling stu */
			Util ut = new Util();
			ut.parseAndFill(file, stu);
		}
		catch (FileNotFoundException e) {
			System.out.println("No such file!");
		}
		catch (MalformedInput e) {
			System.out.println("input text file is malformed");
		}
		catch (TooManyStudents e) {
			System.out.println(">= 40 students data provided, only consider first 40.");
		}
		/* return the modified Student array */
		return stu;
	}
	
}
