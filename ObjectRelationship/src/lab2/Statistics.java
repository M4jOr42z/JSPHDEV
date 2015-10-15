/**
 * @author zhexinq
 * Statistics class for computing statistical data from
 * a bunch of students
 */

package lab2;

import model.Printable;

public class Statistics extends Printable {
	private int[] lowscores;
	private int[] highscores;
	private float[] avgscores;
	
	/* initialize arrays that stores the statistics */
	public Statistics() {
		this.lowscores = new int[Student.QUIZ_NUM];
		this.highscores = new int[Student.QUIZ_NUM];
		this.avgscores = new float[Student.QUIZ_NUM];
	}
	
	/* find the lowest scores for all quiz and store it in lowscores */
	public void findlow(Student[] a) {
		int stuNum = computeStuNum(a);
		if (stuNum > 0) {	
			for (int i = 0; i < Student.QUIZ_NUM; i++) {
				int lowestscore = a[0].getScore(i);
				for (int j = 0; j < stuNum; j++) {
					if (lowestscore > a[j].getScore(i)) 
						lowestscore = a[j].getScore(i);
				}
				this.lowscores[i] = lowestscore;
			}
		}
	}
	
	/* find the highest scores for all quiz and store it in highscores */
	public void findhigh(Student[] a) {
		int stuNum = computeStuNum(a);
		if (stuNum > 0) {
			for (int i = 0; i < Student.QUIZ_NUM; i++) {
				int highestscore = a[0].getScore(i);
				for (int j = 0; j < stuNum; j++) {
					if (highestscore < a[j].getScore(i))
						highestscore = a[j].getScore(i);
				}
				this.highscores[i] = highestscore;
			}
		}
	}
	
	/* find the average scores for all quiz and store it in avgscores */
	public void findavg(Student[] a) {
		int stuNum = computeStuNum(a);
		if (stuNum > 0) {
			for (int i = 0; i < Student.QUIZ_NUM; i++) {
				int sum = 0;
				for (int j = 0; j < stuNum; j++) {
					sum += a[j].getScore(i);
				}
				this.avgscores[i] = (float)(sum) / stuNum;
			}	
		}
	}
	
	/* print out formatted statistics for scores */
	@Override
	public void printInfo() {
		System.out.printf("%-12s", "High Score");
		for (int i = 0; i < Student.QUIZ_NUM; i++)
			System.out.printf("%-12d", this.highscores[i]);
		System.out.println();
		System.out.printf("%-12s", "Low Score");
		for (int i = 0; i < Student.QUIZ_NUM; i++)
			System.out.printf("%-12d", this.lowscores[i]);
		System.out.println();
		System.out.printf("%-12s", "Average");
		for (int i = 0; i < Student.QUIZ_NUM; i++)
			System.out.printf("%-12.1f", this.avgscores[i]);	
	}
	
	/* helper method to compute how many students */
	private int computeStuNum(Student[] a) {
		int stuNum = 0;
		while (stuNum < a.length && a[stuNum] != null)
			stuNum++;
		return stuNum;
	}
}
