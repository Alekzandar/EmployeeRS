/**
 * 
 */
package com.revature.service;

import java.util.List;

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
	 * Retrieve reimbursement from User
	 */
	public List<Reimbursement> getUserReimbursement(String username){
		List<Reimbursement> r = dao.getByUsername(username);
		return r;
	}

}
