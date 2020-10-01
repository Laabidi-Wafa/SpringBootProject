package com.brightcoding.app.ws.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.brightcoding.app.ws.shared.dto.UserDto;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 8106560824187969451L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 30, nullable = false)
	private String AddressId;

	@Column(length = 20, nullable = false)
	private String city;

	@Column(length = 20, nullable = false)
	private String country;

	@Column(length = 20, nullable = false)
	private String type;

	@Column(length = 50, nullable = false)
	private String street;

	@Column(length = 30, nullable = false)
	private String postal;

	@ManyToOne
	@JoinColumn(name = "users_id") // foreign key qui va etre dans la table addresses
	private UserEntity user; // un utilisateur userDto peut avoir beaucoup d'adresses mais l'adresse peut
							// avoir qu'un seul user relation: ManyToOne

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressId() {
		return AddressId;
	}

	public void setAddressId(String addressId) {
		AddressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}


}
