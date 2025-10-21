package com.magnolia.cione.pur;



import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Class UserDTO.<br>
 * The JSON structure:<br>
 * {<br>
	  "username": "string",<br>
	  "password": "string",<br>
	  "email": "string",<br>
	  "name": "string",<br>
	  "firstSurname": "string",<br>
	  "secondSurname": "string",<br>
	  "address": "string",<br>
	  "documentType": "string",<br>
	  "nifNiePassport": "string",<br>
	  "contactNumber": "string",<br>
	  "birthDate": "2018-08-27T06:43:02.189Z",<br>
	  "genre": "string",<br>
	  "occupation": "string",<br>
	  "userIdentificationBinFile": "string",<br>
	  "identificationExpires": "string"<br>
	}<br>
 */
public class UserDTO {
	
	@NotEmpty
	@NotNull
    private String username;

	@NotEmpty
	@NotNull
    private String password;

	@NotEmpty
	@NotNull
    private String email;
    
    private String name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    
}
