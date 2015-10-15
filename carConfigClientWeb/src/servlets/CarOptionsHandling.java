/**
 * @author zhexinq
 * A servlet to display the options for a user upon user selection
 * 
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.ChooseCarModel;
import client.QueryAvailableCars;
import model.Automobile;

@WebServlet("/carOptions")
public class CarOptionsHandling extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		// get user selecrted id
		int selectionId = Integer.parseInt(request.getParameter("models"));
		
		// spawn a thread to choose a car model and wait for result
		ChooseCarModel choose = new ChooseCarModel(selectionId);
		try {
			choose.start();
			choose.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// get the Auto object
		Automobile autoForDisplay = choose.getRetrievedCarModel();
//		// reformat the data
//		String makeAndModel = autoForDisplay.getMake() + " " + autoForDisplay.getModel(); 
//		HashMap<String, String[]> modelOptions = new HashMap<String, String[]>();
//		String[] setNames = autoForDisplay.getOptionSetNames();
//		for (String setName:setNames) {
//			String[] optNames = autoForDisplay.getOptionName(setName);
//			modelOptions.put(setName, optNames);
//		}
//		// for debugging
//		for (String set: modelOptions.keySet()) {
//			System.out.println("set: " + set);
//			for (String opt: modelOptions.get(set))
//				System.out.println("	opt: " + opt);
//		}
		
		// dispatch the request with loaded to options.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("options.jsp");
		request.setAttribute("auto", autoForDisplay);
		request.setAttribute("autoId", selectionId);
		dispatcher.forward(request, response);
		
		
	}

public static void main(String[] args) {
	HashMap<String, String[]> a;
	
}

}
