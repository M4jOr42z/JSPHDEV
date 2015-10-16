/**
 * @author zhexinq
 * a servlet that handles all requests at the client side
 * as a dispatcher
 */

package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Automobile;

@WebServlet("/dispatcher")
public class WebRequestDispatcher extends ProxyWebRequestDispatcher 
	implements WebRequestDispatcherInterface  {
	
	/* processing the GET request */
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException {
		// target request dispatched to
		String target = "index.jsp";
		RequestDispatcher dispatcher;
		String reqType = request.getParameter("requestType");
		// remember user activities
		HttpSession session = request.getSession(true);
		Automobile auto;
		
		switch (reqType) {
		// upload a car file
		case "upload":
			String filePath = request.getParameter("filePath");
			String fileType = request.getParameter("fileType");
			handleUploadRequest(filePath, fileType);
			break;
		// display a list of available cars	
		case "autoList":
			target = "/models.jsp";
			ArrayList<String> autos = queryListOfAutos();
			request.setAttribute("models", autos);
			dispatcher = getServletContext().getRequestDispatcher(target);
			dispatcher.forward(request, response);
			break;
		// select a car and request options
		case "requestOptions":
			target = "/options.jsp";
			int carId = Integer.parseInt(request.getParameter("models"));
			auto = requestAutomobile(carId);
			request.setAttribute("auto", auto);
			session.setAttribute("autoSelected", auto);
			dispatcher = getServletContext().getRequestDispatcher(target);
			dispatcher.forward(request, response);
			break;
		// select options and find out result
		case "choiceResult":
			target = "/results.jsp";
			auto = (Automobile)session.getAttribute("autoSelected");
			setAutoOptions(auto, request);
			request.setAttribute("auto", auto);
			session.setAttribute("autoSelected", auto);
			dispatcher = getServletContext().getRequestDispatcher(target);
			dispatcher.forward(request, response);
			break;
		default:
			System.out.println("unknow request!");
		}
	}
	
	/* processing the POST request (temporarily as the same) */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
		
}
