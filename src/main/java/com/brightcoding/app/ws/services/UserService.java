package com.brightcoding.app.ws.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.brightcoding.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	
    /* -------------------------------------------------------------------------------------------- */
	
	UserDto createUser(UserDto userDto); //methode nommée createUser va recevoir un objet de type userDto 
	
    /* -------------------------------------------------------------------------------------------- */
	
	List<UserDto> getUsers(int page, int limit , String search, int status);	//le retour de cette methode va etre un objet de type userDto
	
    /* -------------------------------------------------------------------------------------------- */
	
	//De préférable d'utiliser des classes qui implimentent les interfaces
	UserDto getUser(String email);
	
    /* -------------------------------------------------------------------------------------------- */
	
	UserDto getUserByUserId(String userId);
	
    /* -------------------------------------------------------------------------------------------- */
	
	UserDto	updateUser(String userId, UserDto userDto);
	
    /* -------------------------------------------------------------------------------------------- */
	
	void deleteUser(String id);
	
    /* -------------------------------------------------------------------------------------------- */
}
