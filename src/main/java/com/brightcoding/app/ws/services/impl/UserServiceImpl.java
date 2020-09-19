package com.brightcoding.app.ws.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brightcoding.app.ws.entities.UserEntity;
import com.brightcoding.app.ws.repositories.UserRepository;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.dto.UserDto;
@Service //pour que spring boot injecte le service dans la classe userController il faut ajouter cette annotation ici
//puisque cette classe implemente l'interface de service
public class UserServiceImpl implements UserService {
//cette classe va implementer l'interface userService
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
	UserEntity userEntity =new UserEntity();
	
	BeanUtils.copyProperties(user, userEntity);
	
	userEntity.setEncryptedPassword("test password");//valeur pour encrypted password
	userEntity.setUserId("user id test");
	
	
	UserEntity newUser = userRepository.save(userEntity); //persister l'objet
	
	UserDto userDto = new UserDto();
	
	BeanUtils.copyProperties(newUser, userDto);
	
		return userDto;
	}

}
