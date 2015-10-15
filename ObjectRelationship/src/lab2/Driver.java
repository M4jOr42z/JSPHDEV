/**
 * @author zhexinq
 * Driver program processes a set of test files, populate student array, and display
 * corresponding statistics. If more than 40 student data in the file, consider only
 * the first 40. If data file is malformed, process only the data before the first 
 * malformed entry encountered.
 * 11 test cases:
 * 			correct form:
 * 			- empty text file
 * 			- header + 0 student
 * 			- header + 1 student
 * 			- header + less than 40 students
 * 			- header + 40 students
 * 			- header + more than 40 students
 * 			malformed:
 * 			- students have fewer quiz than 5
 *			- students have more quiz than 5
 *			- students have SID not a number
 *			- student's score is not a number
 *			- quote text
 */
package lab2;

public class Driver {

	public static void main(String[] args) {
		String[] firstlineOutput = {"Stud", "Q1", "Q2", "Q3", "Q4", "Q5"};
		String[] testFiles = {"empty.txt", "0std.txt", "1std.txt", "15std.txt", "40std.txt",
							  "75std.txt", "notEnoughQuiz.txt", "tooManyQuiz.txt", "sidNotANum.txt",
							  "scoreNotANum.txt", "quote.txt"};
		Student[] lab2;
		for (int j = 0; j < 11; j++) {
			lab2 = new Student[40];
			System.out.printf("%s:\n", testFiles[j]);
			// populate the student array
			lab2 = Util.readFile(testFiles[j], lab2);
			Statistics statlab2 = new Statistics();
			statlab2.findlow(lab2);
			statlab2.findhigh(lab2);
			statlab2.findavg(lab2);
			for (int i = 0; i < 6; i++)
				System.out.printf("%-12s", firstlineOutput[i]);
			System.out.println();
			for (int i = 0; i < lab2.length && lab2[i] != null; i++) 
				lab2[i].printInfo();
			System.out.println();
			statlab2.printInfo();
			System.out.println("\n");
		}
	}

}
