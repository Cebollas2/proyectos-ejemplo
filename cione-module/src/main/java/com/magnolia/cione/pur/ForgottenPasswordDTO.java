package com.magnolia.cione.pur;

/**
 * The Class ForgottenPasswordDTO.<br>
 * The JSON structure:<br>
 * {<br>
	  "username": "string",<br>
	  "email": "string"<br>
	}<br>
 */
public class ForgottenPasswordDTO {
	
	private String username;
	
	private String email;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
