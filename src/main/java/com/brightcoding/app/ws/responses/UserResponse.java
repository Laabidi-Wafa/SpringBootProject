package com.brightcoding.app.ws.responses;
//On va afficher ses informations de l utilisateur pour des raisons de sécurité 
//puisque on veut pas afficher tous les informations de l'utilisateur
//donc c'est la reponse qui va contenir juste le firstname,lastname et l'email de l'utilisateur
public class UserResponse {
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String firstName;
	private String lastName;
	private String email;


}
