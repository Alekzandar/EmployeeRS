package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojos.User;
import com.revature.service.UserService;

@WebServlet("/sendreimb")
public class SendReimbServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(SendReimbServlet.class);
	
	//Make an instance of user service
	static UserService service = new UserService();
	
	
	/*
	 *Take info from request, return user if logged in properly, return null if not proper
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		User inputUser = mapper.readValue(req.getInputStream(), User.class);
		log.info("ADDING Reimb: " + inputUser.toString());
		
		User loggedUser = service.logIn(inputUser.getUsername(), inputUser.getPassword()); //method from user class
		String out = "";
		
		
		if (loggedUser == null) {
			//JSON string DNE - Does Not Exist
			out = mapper.writeValueAsString(null);
		}else {
			
		if (inputUser.getPassword().equals(loggedUser.getPassword())) {
			//success
			out = mapper.writeValueAsString(loggedUser);
			
			//once logged in properly add user to HTTP session
			HttpSession session = req.getSession();
			log.info("CREATED SESSION " + session.getId() + " AT " + session.getCreationTime());
		
			//add user object to our session
			session.setAttribute("user", loggedUser); //logged user
		} else {
			out = mapper.writeValueAsString(null);
		}
	}
		
	
	PrintWriter writer = resp.getWriter();
	resp.setContentType("application/json");
	writer.write(out);
	

}
}

