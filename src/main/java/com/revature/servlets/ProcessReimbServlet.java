package com.revature.servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.data.ReimbursementDAO;

/**
 * Servlet implementation class ProcessReimbServlet
 */
@WebServlet("/processreimb")
public class ProcessReimbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ProcessReimbServlet.class);
	static ReimbursementDAO dao = new ReimbursementDAO();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.info("IN PROCESS REIMB DOPOST");
		
		String stage =  "";
	    ServletInputStream mServletInputStream = request.getInputStream();
	    byte[] httpInData = new byte[request.getContentLength()];
	    int retVal = -1;
	    StringBuilder stringBuilder = new StringBuilder();

	    while ((retVal = mServletInputStream.read(httpInData)) != -1) {
	        for (int i = 0; i < retVal; i++) {
	            stringBuilder.append(Character.toString((char) httpInData[i]));
	        }
	    }

	    stage = stringBuilder.toString();
	    log.info("JUMBLE: " + stage);
	    String type = "";
	    Integer reimbID = null;
	    Matcher matcher = Pattern.compile("([0-9]+)([a-z]+)").matcher(stage);
	    if (matcher.matches()) {
	    	reimbID =  Integer.valueOf(matcher.group(1));
	    	type = matcher.group(2);
	    }
	    log.info("DEJUMBLE: " +reimbID + " , " + type);
	    
	    if(type.equals("approve")) {
	    	dao.approveReimbursement((int) reimbID);
	    }else {
	    	dao.denyReimbursement((int) reimbID);
	    }
	}

}
