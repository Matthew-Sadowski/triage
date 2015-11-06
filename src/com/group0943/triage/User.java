package com.group0943.triage;


public class User {
	
	/** The username of user.*/
	protected String username;
	
	/** The password of user.*/
	protected String password;
	
	/** The position of user.*/
	protected String position;
	
	
	/**
	 * Creates and instance of User.
	 * @param username The user's username.
	 * @param password The user's password.
	 * @param position The user's occupation (nurse or physician).
	 */
	public User(String username, String password, String position){
		this.username = username;
		this.password = password;
		this.position = position;
	}

	
	/**
	 * Returns the user name of the user
	 * @return the username of the user
	 */
	public String getUsername(){
		return username;
	}
	/**
	 * Returns the password of the user
	 * @return the password of the user
	 */
	public String getPassword(){
		return password;
	}
	
	
	/**
	 * Returns the position of the user
	 * @return the position of the user
	 */
	public String getPosition(){
		return position;
	}
	
	
}