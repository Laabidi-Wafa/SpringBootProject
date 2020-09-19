package com.brightcoding.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity(name="users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763827745308343856L;
	
	//pour que l'id devient auto-incrimenté il faut ajouter l annotation id 
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)	
	private String userId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	
	@Column(nullable=false, length=50)
	private String lastName;
	
	@Column(nullable=false, length=120, unique=true)
	private String email;
	
	@Column(nullable=false)	
	private String encryptedPassword;
	
	@Column(nullable=true)
	private String emailVerificationToken;
	
	@Column(nullable=false)
	private Boolean emailVerficationStatus = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerficationStatus() {
		return emailVerficationStatus;
	}

	public void setEmailVerficationStatus(Boolean emailVerficationStatus) {
		this.emailVerficationStatus = emailVerficationStatus;
	}
}
//data layer