package com.revature.main;

import com.revature.data.ReimbursementDAO;
import com.revature.service.UserService;

public class App {
	
	/*
	 * Instead of our main method here we would have our servlets, which would send calls to our Java functions,
	 * like the business login implemented in our UserService
	 */
	public static void main(String[] args) {
		
		//UserDAO dao = new UserDAO(); 
		//System.out.println("Users: "+ dao.getUsers() + "\n");
		
		//ReimbursementDAO reimb = new ReimbursementDAO();
		//System.out.println("Reimbursements: " + reimb.getReimbursements() + "\n");
		 
		UserService service = new UserService();
		System.out.println(service.logIn("testusername", "testpass"));
		
		//TestUserService serviceB = new TestUserService();
		//System.out.println(serviceB.logIn("genesisb", "123"));

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
