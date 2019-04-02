package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.Reimbursement;
import com.revature.pojos.TestUser;
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
				//System.out.println("Test" + temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimb;
	}
	
	/*
	 * Get Reimbursements from a specific username
	 */
	public List<Reimbursement> getByUsername(String username) {
		List<Reimbursement> reimb = new ArrayList<Reimbursement>();
		Reimbursement r = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){  		
			String sql = "SELECT REIMB_ID, REIMB_AMOUNT, REIMB_SUBMITTED, REIMB_RESOLVED, REIMB_DESCRIPTION, \r\n" + 
					"U1.USER_FIRST_NAME || ' ' ||  U1.USER_LAST_NAME AS AUTHOR,\r\n" + 
					"U2.USER_FIRST_NAME || ' ' ||  U2.USER_LAST_NAME AS RESOLVER,\r\n" + 
					"REIMB_STATUS, REIMB_TYPE\r\n" + 
					"FROM ERS_REIMBURSEMENT R\r\n" + 
					"LEFT OUTER JOIN ERS_USERS U1 ON R.REIMB_AUTHOR = U1.ERS_USERS_ID\r\n" + 
					"LEFT OUTER JOIN ERS_USERS U2 ON R.REIMB_RESOLVER = U2.ERS_USERS_ID\r\n" + 
					"LEFT OUTER JOIN ERS_REIMBURSEMENT_TYPE ON R.REIMB_TYPE_ID = ERS_REIMBURSEMENT_TYPE.REIMB_TYPE_ID\r\n" + 
					"LEFT OUTER JOIN ERS_REIMBURSEMENT_STATUS ON R.REIMB_STATUS_ID = ERS_REIMBURSEMENT_STATUS.REIMB_STATUS_ID\r\n" + 
					"WHERE U1.ERS_USERNAME = ? \r\n" + 
					"ORDER BY REIMB_ID";
												
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  username.toLowerCase());
			ResultSet rs = ps.executeQuery(); 		
			
				
			while(rs.next()) { 			
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
				//System.out.println("Test" + temp);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reimb;
	}
	
	/*
	 * Add a Reimbursement Request to ERS_Reimbursement Table
	 */
	public void addReimbursement(Reimbursement reimb) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			//conn.setAutoCommit(); is set to true
			
			String sql = "INSERT INTO ERS_REIMBURSEMENT(REIMB_AMOUNT, REIMB_DESCRIPTION, \r\n" + 
					"    REIMB_AUTHOR, REIMB_RESOLVER, REIMB_STATUS_ID, REIMB_TYPE_ID)\r\n" + 
					"VALUES(?, ?, ?, ?, ?, ?)";
			String[] keyNames = {"REIMB_ID"}; 				//list of auto-generated keys, specify column names
			
			int reimb_author, reimb_resolver, reimb_status_id, reimb_type_id;
			
			
			PreparedStatement ps = conn.prepareStatement(sql, keyNames);				
														
			ps.setLong(1, reimb.getAmount());	
			ps.setString(2, reimb.getDescription());
			ps.setString(3, reimb.getAuthor());
			ps.setString(4, reimb.getResolver());
			ps.setString(5, reimb.getStatus());
			ps.setString(6, reimb.getType());
			
			int numRowsAffected = ps.executeUpdate(); //don't need to set the executeUpdate to anything unless using num rows affected
			
			System.out.println("ADDED" + numRowsAffected + "USER(S) TO DB");
			
			if(numRowsAffected ==1) {
				ResultSet pk = ps.getGeneratedKeys(); 	//result set of primary key
				pk.next();
				reimb.setId(pk.getInt(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
