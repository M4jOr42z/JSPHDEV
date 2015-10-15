/**
 * @author zhexinq
 * servlet to show user configuration resulst
 */


package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.ChooseCarModel;
import model.Automobile;

@WebServlet("/choiceResult")
public class ShowChoiceResult extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		// get the auto object for user selection
		System.out.println("Inside result servlet");
		String id = request.getParameter("autoId");
		
		// get user selecrted id
		int selectionId = Integer.parseInt(id);
		
		// spawn a thread to choose a car model and wait for result
		ChooseCarModel choose = new ChooseCarModel(selectionId);
		try {
			choose.start();
			choose.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// get the Auto object for make choice
		Automobile auto = choose.getRetrievedCarModel();
		for (String setName:auto.getOptionSetNames()) {
			String optChoice = request.getParameter(setName);
			System.out.println("set:::::" + setName);
			System.out.println("choice:::::" + optChoice);
			auto.setOptionChoice(setName, optChoice);
		}
		
		// display the result
		int price = auto.getTotalPrice();
		// forward this models to results.jsp
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/results.jsp");
		request.setAttribute("auto", auto);
		dispatcher.forward(request, response);
		
	}
}