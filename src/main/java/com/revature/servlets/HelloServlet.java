/**
 * 
 */
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Aleksandar Antonov Generic Servlet Abstract Class best showcases
 *         lifecycle of a Servlet
 *
 */
public class HelloServlet extends GenericServlet {



	/**
	 * generated serialVersionUID (unique ID)
	 */
	private static final long serialVersionUID = -1758450729088371407L;

	/**
	 * 
	 */
	public HelloServlet() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * log4j - a logging tool for Java; using this instead of basic sys.out
	 * replacing System.out.ln with log.trace
	 */
	private static Logger log = Logger.getLogger(LoadViewServlet.class);
	static int count = 0;

	//LIFECYCLE STEP 1 - INIT
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		log.trace("INITIALIZING GENERIC SERVLET");
	}
	
	//LIFECYCLE STEP 2 - SERVICE
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		log.trace("IN HELLO SERVLET (GENERIC SERVLET) SERVICE METHOD");

		//get servlet params and add to our response
		String context = getServletContext().getInitParameter("environmentVar");
		String config = getServletConfig().getInitParameter("helloConfig");
		//write response
		String respText = "<h1>Hello Servlet World!</h1><br>"
				+ "This is a response from our Generic Servlet class <br>" 
				+ "Request Count: " + ++count + "<br>" //inline pre-script increment <- dynamic data change
				+ "Servlet Context: " + context + "<br>"
				+ "Servlet Config: " + config;
		
		PrintWriter writer = res.getWriter(); // Prints formatted reps of objects to a text-output stream
		res.setContentType("text/html");
		writer.write(respText);
	}

	//LIFECYCLE STEP 3 - DESTROY -> Does not throw anything
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		log.trace("DESTROYING GENERIC SERVLET");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
