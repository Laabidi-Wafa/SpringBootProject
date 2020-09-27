package com.brightcoding.app.ws.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.brightcoding.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDto); //methode nommée createUser va recevoir un objet de type userDto 
										 //le retour de cette methode va etre un objet de type userDto
	
	//De préférable d'utiliser des classes qui implimentent les interfaces
	UserDto getUser(String email);
	
	UserDto getUserByUserId(String userId);

}
