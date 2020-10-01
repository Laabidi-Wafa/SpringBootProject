package com.brightcoding.app.ws.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brightcoding.app.ws.entities.UserEntity;
import com.brightcoding.app.ws.repositories.UserRepository;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.Utils;
import com.brightcoding.app.ws.shared.dto.AddressDto;
import com.brightcoding.app.ws.shared.dto.UserDto;

@Service // pour que spring boot injecte le service dans la classe userController il faut
			// ajouter cette annotation ici
//puisque cette classe implemente l'interface de service
public class UserServiceImpl implements UserService {
//cette classe va implementer l'interface userService

	@Autowired
	UserRepository userRepository; // communique avec la bd : plus précisament avec la table users

	@Autowired
	Utils util; // la classe qui contient la méthode de bcrypted password

	@Autowired // pour nous donner un mdp encrypté composé de chiffres et de caractères
				// aléatoires == methode
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * ---------------------------
	 * createUser-----------------------------------------------------------------
	 */

	@Override
	public UserDto createUser(UserDto user) {
		UserEntity checkUser = userRepository.findByEmail(user.getEmail()); // pour verifier dès le début avant
																			// d'ajouter cet utilisateur que l'email
																			// existe ou pas
		if (checkUser != null)
			throw new RuntimeException("ATTENTION : un utilisateur avec ce mail existe déja !!"); // si l'email existe
																									// il va déclancher
																									// cette exception
		// UserEntity userEntity = new UserEntity(); //lorsqu'on travaille avec
		// modelMapper c pas la peine de créer un nouveau user puisque il va retourner
		// un objet de type userEntity

		// BeanUtils.copyProperties(user, userEntity); // copie les informations de user
		// vers userEntity


		
		for(int i=0; i< user.getAddresses().size(); i++) {
			AddressDto address = user.getAddresses().get(i); // a la premiere itération on va ajouter la premier adresse situé au niveau de Addresses
															// et on va l'affecter a l'objet address
			address.setUser(user); //on ajoute l'utilisateur
			address.setAddressId(util.generateStringId(30));
			user.getAddresses().set(i, address); // on fait la mise a jour au niveau de l'utilisateur pour prend en considération les adresses ajoutés
	
		}			
		
		
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));// valeur pour encrypted
																							// password
		userEntity.setUserId(util.generateStringId(32)); // valeur generee automatiquement avec la longeur de 32
														// caracteres

		UserEntity newUser = userRepository.save(userEntity); // persister l'objet

		//UserDto userDto = new UserDto();

		//BeanUtils.copyProperties(newUser, userDto);
		UserDto userDto = modelMapper.map(newUser, UserDto.class);

		return userDto;
	}

	/*
	 * -------------------------------loadUserByUsername----------------------------
	 * ---------------------------------
	 */

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Recupere l'utilisateur authentifie de la base de données
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	/*
	 * ------------------------------------getUser----------------------------------
	 * ----------------------
	 */

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	/*
	 * ----------------------------------------getUserByUserId----------------------
	 * ------------------------------
	 */
	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;

	}

	/*
	 * ------------------------------------------updateUser-------------------------
	 * -------------------------
	 */

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {

		UserEntity userEntity = userRepository.findByUserId(userId); // on va chercher l'utilisateur par son id

		if (userEntity == null)
			throw new UsernameNotFoundException(userId); // si l'utilisateur n'existe pas on va déclancher cette
															// exeception

		userEntity.setFirstName(userDto.getFirstName()); // on va modifier le first name

		userEntity.setLastName(userDto.getLastName()); // et le last name

		UserEntity userUpdated = userRepository.save(userEntity); // persister la modification a la base de donnees et
																	// on va enregistrer cette modif en userUpdated

		UserDto user = new UserDto(); // on a creer un nouveau user

		BeanUtils.copyProperties(userUpdated, user); // on a copier les donnees dans ce nouveau user

		return user; // on a retourné cet nouveau utilisateur
	}

	/*
	 * ------------------------------------------deleteUser-------------------------
	 * -------------------------
	 */

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId); // on va chercher l'utilisateur par son id

		if (userEntity == null)
			throw new UsernameNotFoundException(userId); // si l'utilisateur n'existe pas on va déclancher cette
															// exeception

		userRepository.delete(userEntity); // va supprimer lentite passé en parametre

	}

	/*
	 * -----------------------------------------getUsers----------------------------
	 * -----------------------
	 */

	@Override
	public List<UserDto> getUsers(int page, int limit) { // l'objectif de cette methode est de recupération de la liste
															// des utilisateurs depuis la table users de la base de
															// donnees

		if (page > 0)
			page -= 1; // si la page sup a 0 on va enlever 1
						// Pour que la page commence avec 1
		List<UserDto> usersDto = new ArrayList<>(); // Respository est celui qui est responsable de communiquer avec la
													// base

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> userPage = userRepository.findAll(pageableRequest);

		List<UserEntity> users = userPage.getContent();

		for (UserEntity userEntity : users) { // users copie a chaque fois le contenu de userDto

			UserDto user = new UserDto();

			BeanUtils.copyProperties(userEntity, user); // user copie a chaque fois le contenu de userDto

			usersDto.add(user); // on va ajouter le user a la liste des users

		}
		return usersDto;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ---------------
	 */
}
