package com.brightcoding.app.ws.services.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	UserRepository userRepository; //communique avec la bd : plus précisament avec la table users
	
	@Autowired
	Utils util; //la classe qui contient la méthode de bcrypted password
	
	@Autowired //pour nous donner un mdp encrypté composé de chiffres et de caractères aléatoires == methode
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
	UserEntity checkUser =	userRepository.findByEmail(user.getEmail()); //pour verifier dès le début avant d'ajouter cet utilisateur que l'email existe ou pas
		if (checkUser != null) throw new RuntimeException("ATTENTION : un utilisateur avec ce mail existe déja !!"); //si l'email existe il va déclancher cette exception
	UserEntity userEntity =new UserEntity(); // un nouveau utilisateur
	
	BeanUtils.copyProperties(user, userEntity); //copie les informations de user vers userEntity 
	
	userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));//valeur pour encrypted password
	userEntity.setUserId(util.generateUserId(32)); //valeur generee automatiquement avec la longeur de 32 caracteres
	
	
	UserEntity newUser = userRepository.save(userEntity); //persister l'objet
	
	UserDto userDto = new UserDto();
	
	BeanUtils.copyProperties(newUser, userDto);
	
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Recupere l'utilisateur authentifie de la base de donnees
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

}
