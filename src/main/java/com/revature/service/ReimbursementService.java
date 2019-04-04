/**
 * 
 */
package com.revature.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.revature.data.ReimbursementDAO;
import com.revature.pojos.Reimbursement;

/**
 * Reimbursement Service layer for Displaying Reimbursements from specific
 * User
 * @author Aleksandar A.
 *
 */
public class ReimbursementService {
	
	static ReimbursementDAO dao = new ReimbursementDAO();
	
	/*
	 * Retrieve Reimbursements submitted by given User
	 */
	public List<Reimbursement> getUserReimbursement(String username){
		List<Reimbursement> r = dao.getByUsername(username);
		return r;
	}

}
