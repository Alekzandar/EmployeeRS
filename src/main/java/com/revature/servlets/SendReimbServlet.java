package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojos.Reimbursement;
import com.revature.service.UserService;

@WebServlet("/sendreimb")
public class SendReimbServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(SendReimbServlet.class);

	// Make an instance of user service
	static UserService service = new UserService();

	/*
	 * Take info from request, return user if logged in properly, return null if not
	 * proper
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		log.info("ARRIVED in sendReimb doPost");
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement sendReimb = mapper.readValue(req.getInputStream(), Reimbursement.class);
		log.info("ADDING Reimb: " + sendReimb.toString());

		PrintWriter writer = resp.getWriter();
		resp.setContentType("application/json");

	}
}
