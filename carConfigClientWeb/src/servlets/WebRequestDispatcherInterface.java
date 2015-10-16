/**
 * @author zhexinq
 * interface for web request dispatcher business methods
 */

package servlets;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import model.Automobile;

public interface WebRequestDispatcherInterface {
	public void handleUploadRequest(String filePath, String fileType);
	public ArrayList<String> queryListOfAutos();
	public Automobile requestAutomobile(int carId);
	public void setAutoOptions(Automobile auto, HttpServletRequest request);
}
