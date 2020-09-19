package com.brightcoding.app.ws.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brightcoding.app.ws.entities.UserEntity;
import com.brightcoding.app.ws.repositories.UserRepository;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.Utils;
import com.brightcoding.app.ws.shared.dto.UserDto;


@Service //pour que spring boot injecte le service dans la classe userController il faut ajouter cette annotation ici
//puisque cette classe implemente l'interface de service
public class UserServiceImpl implements UserService {
//cette classe va implementer l'interface userService
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils util;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
	UserEntity checkUser =	userRepository.findByEmail(user.getEmail()); //pour verifier dès le début avant d'ajouter cet utilisateur que l'email existe ou pas
		if (checkUser != null) throw new RuntimeException("ATTENTION : un utilisateur avec ce mail existe déja !!"); //si l'email existe il va déclancher cette exception
	UserEntity userEntity =new UserEntity();
	
	BeanUtils.copyProperties(user, userEntity);
	
	userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));//valeur pour encrypted password
	userEntity.setUserId(util.generateUserId(32)); //valeur generee automatiquement avec la longeur de 32 caracteres
	
	
	UserEntity newUser = userRepository.save(userEntity); //persister l'objet
	
	UserDto userDto = new UserDto();
	
	BeanUtils.copyProperties(newUser, userDto);
	
		return userDto;
	}

}
