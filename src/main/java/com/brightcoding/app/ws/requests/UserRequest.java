package com.brightcoding.app.ws.requests;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//l'utilisateur va ajouter un utilisateur a la base de donnees avec ces informations
public class UserRequest {
	
	/*****************************************************Attribut firstName***********************************************************************/
	
	
	@NotBlank(message="Ce champ ne doit pas etre vide les espaces ne sont pas considérés comme caractères !!")
	@Size(min=3 , message ="Le firstName doit contenir au moins 3 caractères")
	private String firstName;
	
	
	/*******************************************************Attribut lastName********************************************************************/
	
	
	@NotBlank(message="Ce champ ne doit pas etre vide les espaces ne sont pas considérés comme caractères !!")
	@Size(min=3 , message ="Le LastName doit contenir au moins 3 caractères")
	private String lastName;
	
	
	/********************************************************Attribut Email********************************************************************/
	
	
	@NotBlank(message="Ce champ ne doit pas etre vide les espaces ne sont pas considérés comme caractères !!")
	@Email(message="La format de l'email doit etre <quelquechose@quelquechose.quelquechose> ")
	private String email;
	
	
	/**********************************************************Attribut Password******************************************************************/
	
	
	@NotNull(message="Ce champ ne doit pas etre null!")
	@Size(min=8, message="Le password doit contenir au moins 8 caractères ")
	@Size(max=12, message="Le password doit contenir au max 12 caractères ")
	@Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message="le password doit contenir une lettre majuscule, des lettres miniscules et chiffres")
	private String password;
	
	
	/****************************************************************************************************************************/
	
	
	private List<AddressRequest> addresses; //liste des objets

	
	/****************************************************************************************************************************/
	
	
	public List<AddressRequest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressRequest> addresses) {
		this.addresses = addresses;
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

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		
		this.password = password;
	}

}
