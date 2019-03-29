package com.revature.main;

import com.revature.data.UserDAO;
import com.revature.service.TestUserService;

public class App {
	
	/*
	 * Instead of our main method here we would have our servlets, which would send calls to our Java functions,
	 * like the business login implemented in our UserService
	 */
	public static void main(String[] args) {
		
		UserDAO dao = new UserDAO(); 
		System.out.println(dao.getUsers() + "\n");
		 
		
		TestUserService service = new TestUserService();
		System.out.println(service.logIn("genesisb", "123"));

		//Created new constructor that only takes in username, password, and bio since our ID's are auto-generated
		//will return null if the user already exists under name
		//System.out.println(service.addUser(new User("testuser", "admin", "admin test user")));
		//System.out.println(service.addUser(new TestUser("testuser7", "admin", "admin test user")));
		
		
		
	}

}

/*
 * Packaged as jar -> starting point is main
 * Packaged as war -> starting point is web.xml
 */
