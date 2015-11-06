package com.group0943.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserList {
	
	private List<User> userlist;
	
	/**
	 * Creates an instance of UserList.
	 * @param dir The file directory to load from.
	 * @param fileName The name of the file.
	 * @throws IOException
	 */
	public UserList (File dir, String fileName) throws IOException{
		this.userlist = new ArrayList<User>();
		File file = new File(dir, fileName);
		this.loadUserRecords(file.getPath());
		
	}
	
	/**
	 * Loads the user login information into a list.
	 * @param filePath the file path of the file.
	 * @throws FileNotFoundException if the file can not be found with the
     * given filePath.
	 */
	private void loadUserRecords(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String[] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String username = record[0];
			String password = record[1];
			String position = record[2];
			this.add(new User(username, password ,position));
		
		}
		scanner.close();
	}
	/**
	 * Adds a user to the UserList.
	 * @param user The user to add.
	 */
	public void add(User user){
		userlist.add(user);
			
	}
	/**
	 * Checks if a user in the record.
	 * @param user The user to check.
	 * @return true if user is in the record and false otherwise.
	 */
	public boolean user_in_record(User user){
		for (User u: userlist){
			if(user.getUsername().equals(u.getUsername())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns the list of Users.	
	 * @return the list of Users.
	 */
	public List<User> getUsers(){
		return userlist;
	}
	
	
	
}