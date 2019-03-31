package com.revature.service;

import com.revature.data.UserDAO;
import com.revature.pojos.User;

/*
 * CRUD functionality in DAO, anything else in our service.
 * 
 */
public class UserService {
	
	static UserDAO dao = new UserDAO();
	
	/*
	 * Validate user credential by username and "Log In" if valid username and password.
	 * Otherwise return null.
	 */

	public boolean logIn(String username, String password) {
		User u = dao.getByUsername(username);
		if(u == null) {
			return false;
		} else {
			if(u.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	/*
	 * public TestUser addUser(TestUser u) { if(dao.getByUsername(u.getUsername())
	 * == null){ //now we know the username is unique; can add user return
	 * dao.addUser(u); } else { //user already exists return null; } }
	 */
	
}
