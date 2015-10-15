/**
 * @author zhexinq
 * 	a servlet to serve the web page showing all available
 * car models in a drop down box
 */

package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.CarModelOptionsIO;
import client.QueryAvailableCars;

@WebServlet("/modelList")
public class ShowListOfAvailableModels extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {	
		// spawn a query thread and wait for result
		QueryAvailableCars query = new QueryAvailableCars();
		try {
			query.start();
			query.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> models = query.getQueriedCars(); 
		request.setAttribute("models", models);
		
		// forward this models to models.jsp
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/models.jsp");
		dispatcher.forward(request, response);
		
	}
}
