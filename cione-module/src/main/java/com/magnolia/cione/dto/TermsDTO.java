package com.magnolia.cione.dto;

public class TermsDTO {
	
	private boolean checkterms;
	private boolean checksubscription;
	private String mail;
	private String currentUser;
	
	
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public boolean isCheckterms() {
		return checkterms;
	}
	public void setCheckterms(boolean checkterms) {
		this.checkterms = checkterms;
	}
	public boolean isChecksubscription() {
		return checksubscription;
	}
	public void setChecksubscription(boolean checksubscription) {
		this.checksubscription = checksubscription;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}

}
