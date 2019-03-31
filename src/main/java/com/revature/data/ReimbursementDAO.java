package com.revature.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.Reimbursement;
import com.revature.util.ConnectionFactory;

/*
 * Data Access Object for generating Reimbursements from our DB Connection
 */
public class ReimbursementDAO {

	/*
	 * Retrieve an ArrayList of Reimbursements with necessary fields
	 */
	public List<Reimbursement> getReimbursements() {
		List<Reimbursement> reimb = new ArrayList<Reimbursement>();
		// try-with-resources block
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT REIMB_ID, REIMB_AMOUNT, REIMB_SUBMITTED, REIMB_RESOLVED, REIMB_DESCRIPTION, \r\n" + 
					"U1.USER_FIRST_NAME || ' ' ||  U1.USER_LAST_NAME AS AUTHOR,\r\n" + 
					"U2.USER_FIRST_NAME || ' ' ||  U2.USER_LAST_NAME AS RESOLVER,\r\n" + 
					"REIMB_STATUS, REIMB_TYPE\r\n" + 
					"FROM ERS_REIMBURSEMENT R\r\n" + 
					"LEFT OUTER JOIN ERS_USERS U1 ON R.REIMB_AUTHOR = U1.ERS_USERS_ID\r\n" + 
					"LEFT OUTER JOIN ERS_USERS U2 ON R.REIMB_RESOLVER = U2.ERS_USERS_ID\r\n" + 
					"LEFT OUTER JOIN ERS_REIMBURSEMENT_TYPE ON R.REIMB_TYPE_ID = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID\r\n" + 
					"LEFT OUTER JOIN ERS_REIMBURSEMENT_STATUS ON R.REIMB_STATUS_ID = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID\r\n" + 
					"ORDER BY REIMB_ID";

			Statement statement = conn.createStatement();

			// RESULTSET interface - represent set of results of a DB query
			ResultSet rs = statement.executeQuery(query);
			
			
			// NOTE: DB Server constraint was returning empty Result Sets
			//			when multiple active connections
			if (!rs.isBeforeFirst() ) {    
			    System.out.println("No Data from ResultSet"); 
			} 
			
			while (rs.next()) {
				Reimbursement temp = new Reimbursement(
						rs.getInt(1), 
						rs.getLong(2), 
						rs.getTimestamp(3),
						rs.getTimestamp(4),
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8), 
						rs.getString(9));
				reimb.add(temp);
				System.out.println("Test" + temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimb;
	}

}
