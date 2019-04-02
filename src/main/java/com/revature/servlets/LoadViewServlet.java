package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//every ending in .view will be handled by this servlet
@WebServlet("*.view") // declares servlet /loadView mapping to the below
public class LoadViewServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(LoadViewServlet.class);

	/*
	 * Forward our HTML partials as a '.vew' suffixed response
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.trace("In Servlet");

		log.info("SENT TO URI: " + req.getRequestURI());// URI gives us the relevant bit
	
		//Context-root: /employeers/
		String uri = req.getRequestURI();
		String name = req.getRequestURI().substring(12,uri.length()-5);
		log.info("TESTING URI "+ name);

		
		req.getRequestDispatcher("partials/"+name+".html").forward(req, resp);
	}


}