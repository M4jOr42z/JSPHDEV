/*
 * @author zhexinq
 * Student class contains student ID and scores in quiz taken
 */
package lab2;
import java.util.Random;

import model.Printable;

public class Student extends Printable {
	private int SID;
	private int[] scores;
	public final static int QUIZ_NUM = 5; // number of quiz taken in a term
	public final static int MAX_STU_NUM = 40; // threshold for processing student data
	
	/* Initialize Student properties */
	public Student() {
		this.SID = 0;
		this.scores = new int[Student.QUIZ_NUM];
	}
	
	/* properties setter/getter methods */
	public void setSID(int id) {
		this.SID = id;
	}
	public int getSID() {
		return this.SID;
	}
	
	/* set/get all scores */
	public void setScores(int[] scores) {
		if (scores.length == this.scores.length) {
			for (int i = 0; i < this.scores.length; i++)
				this.scores[i] = scores[i];
		}
	}
	public int[] getScores() {
		return this.scores;
	}
	
	/* set/get score for nth quiz */
	public void setScore(int n, int score) {
		assert(n < Student.QUIZ_NUM && n >= 0);
		this.scores[n] = score;
	}
	public int getScore(int n) {
		assert(n < Student.QUIZ_NUM && n >= 0);
		return this.scores[n];
	}
	
	/* print function for the student's scores */
	@Override
	public void printInfo() {
		System.out.printf("%-12d", this.getSID());
		for (int i = 0; i < Student.QUIZ_NUM; i++)
			System.out.printf("%-12d", this.scores[i]);
		System.out.println();
	}
	
	/* generate some random student data */
	public static void main(String[] args) {
		System.out.println("Generate some random student data");
		Random random = new Random();
		for (int i = 0; i < 60; i++) {
			System.out.printf("%04d ", random.nextInt(10000));
			for (int j = 0; j < 5; j++) {
				System.out.printf("%03d ", random.nextInt(101));
			}
			System.out.println();
		}
	}
}
