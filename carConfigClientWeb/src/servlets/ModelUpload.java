/**
 * @author zhexinq
 * modelUpload servlet to serve a client's request
 * for submitting a car file (provide filePath and fileType)
 * ENCOUNTERED file not found problem, not sure why
 */

package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import client.CarModelOptionsIO;

@WebServlet(name="uploadCar",urlPatterns="/uploadCar")
public class ModelUpload extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {	
	
		// get the file parameters provided by user
		String filePath = request.getParameter("filePath");
		String fileType = request.getParameter("fileType");
		
		// execute an upload thread
		CarModelOptionsIO modelsIo = new CarModelOptionsIO(filePath, fileType);
		try {
			modelsIo.start();
			modelsIo.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// forward to the main page
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

	}
}
