package com.revature.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.pojos.User;
import com.revature.util.ConnectionFactory;

/*
 * Data Access Object for generating Users from our DB Connection
 */
public class UserDAO {

	
	
	/*
	 * Retrieve an ArrayList of Users with ID, firstname, lastname, username, password, email and role
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();


		//try-with-resources block
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String query = "SELECT ERS_USERS_ID, ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE\r\n" + 
					"FROM ERS_USERS U\r\n" + 
					"LEFT OUTER JOIN ERS_USER_ROLES\r\n" + 
					"ON U.USER_ROLE_ID = ers_user_roles.ers_user_role_id\r\n" + 
					"ORDER BY ERS_USERS_ID";
					
			Statement statement = conn.createStatement();

			//RESULTSET interface - represent set of results of a DB query
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				User temp = new User(
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7));
				users.add(temp);
			}
//rs.close(); assuming they get closed when connection gets closed

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

}
