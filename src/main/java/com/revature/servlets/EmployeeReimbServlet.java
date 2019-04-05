package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.data.ReimbursementDAO;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;
import com.revature.service.ReimbursementService;

@WebServlet("/empreimb")
public class EmployeeReimbServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(EmployeeReimbServlet.class);
	static ReimbursementDAO dao = new ReimbursementDAO();
	static ReimbursementService service = new ReimbursementService();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("ARRIVED in empreimb Servlet doPost");
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(req.getInputStream(), User.class);
		log.info(user.getRole());
		String role1 = "Employee";
		if (user.getRole().equals(role1)) {
			log.info("IN EMPLOYEE DOPOST TEMPLATE");

			log.info(user.getUsername());
			List<Reimbursement> reimbList = service.getUserReimbursement(user.getUsername());

			String out = "";
			out = mapper.writeValueAsString(reimbList);
			log.info("USER REIMB LIST: " + reimbList);

			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			writer.write(out);
		} else {
			log.info("IN MANAGER DO POST TEMPLATE");

			List<Reimbursement> reimbList = dao.getReimbursements();

			String out = "";
			out = mapper.writeValueAsString(reimbList);
			log.info("MANAGER REIMB LIST: " + reimbList);

			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			writer.write(out);

		}
	}
}
