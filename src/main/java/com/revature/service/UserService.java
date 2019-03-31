package com.revature.service;

import com.revature.data.UserDAO;
import com.revature.pojos.TestUser;
import com.revature.pojos.User;

/*
 * CRUD functionality in DAO, anything else in our service.
 * Really only need defined constructor for our pojo classes.
 * 
 * Don't use this as static because eventually we'll use these with dependency injection.
 * 
 */
public class UserService {
	
	static UserDAO dao = new UserDAO();
	
	/*
	 * What this method will do is take the only two credentials present at login (username and password)
	 * and validate that a user with this username exists, then validate said user's password if they do.
	 * 
	 * We can pass in solely a username and pw string, or we could pass in an entire User object with no ID or BIO fields.
	 * All up to you, but just make sure your servlet layer passes the right values in.
	 */

	public User logIn(String username, String password) {
		User u = dao.getByUsername(username);
		if(u == null) {
			return null;
		} else {
			if(u.getPassword().equals(password)) {
				return u;
			} else {
				return null;
			}
		}
	}
	
	
	/*
	 * public TestUser addUser(TestUser u) { if(dao.getByUsername(u.getUsername())
	 * == null){ //now we know the username is unique; can add user return
	 * dao.addUser(u); } else { //user already exists return null; } }
	 */
	
}
