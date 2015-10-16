/**
 * @author zhexinq
 * an abstract class to implement all functions of
 * the real web dispatcher
 * 
 */

package servlets;

import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import client.CarModelPropertiesIO;
import client.ChooseCarModel;
import client.QueryAvailableCars;
import model.Automobile;

public abstract class ProxyWebRequestDispatcher extends HttpServlet {
	
	/* method to handle props upload request */
	public void handleUploadRequest(String filePath, String fileType) {
		// spawn a thread to upload the file
		CarModelPropertiesIO modelsIo = new CarModelPropertiesIO(filePath, fileType);
		try {
			modelsIo.start();
			modelsIo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/* method to request a list of available models from server */
	public ArrayList<String> queryListOfAutos() {
		QueryAvailableCars query = new QueryAvailableCars();
		try {
			query.start();
			query.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return query.getQueriedCars();
	}
	
	/* method to make a selection to request an auto object from server */
	public Automobile requestAutomobile(int carId) {
		ChooseCarModel choose = new ChooseCarModel(carId);
		try {
			choose.start();
			choose.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return choose.getRetrievedCarModel();
	}
	
	/* method to set an auto object's options from a web request */
	public void setAutoOptions(Automobile auto, HttpServletRequest request) {
		for (String setName:auto.getOptionSetNames()) {
			String optChoice = request.getParameter(setName);
			System.out.println("set:::::" + setName);
			System.out.println("choice:::::" + optChoice);
			auto.setOptionChoice(setName, optChoice);
		}
	}
		
}
