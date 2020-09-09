package com.brightcoding.app.ws.services;

import com.brightcoding.app.ws.shared.dto.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDto); //methode nommée createUser va recevoir un objet de type userDto 
										 //le retour de cette methode va etre un objet de type userDto
	
	//De préférable d'utiliser des classes qui implimentent les interfaces

}
