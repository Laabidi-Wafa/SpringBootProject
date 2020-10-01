package com.brightcoding.app.ws.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.brightcoding.app.ws.exceptions.UserException;
import com.brightcoding.app.ws.requests.UserRequest;
import com.brightcoding.app.ws.responses.ErrorMessages;
import com.brightcoding.app.ws.responses.UserResponse;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.dto.UserDto;

@RequestMapping("/users") //localhost:8080/users
@RestController
public class UserController {
	//pour créer un objet de type userService, il faut faire une instance de ce type
	@Autowired
	UserService userService ; //l'injection de dépendance a la place de new
	
	
	/* -----------------------------------------------GetUser--------------------------------------------- */
	
	
	@GetMapping(path="/{id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) //localhost:8080/users/id avec un id dynamique cad qui change
	public ResponseEntity<UserResponse> getUser(@PathVariable String id)//il faut le déclarer ici comme paramètre avec le MEME NOM
	{
		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userDto, userResponse);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
    /* ----------------------------------------GetAllUsers---------------------------------------------------- */
	
	
	
	
	@GetMapping(produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<UserResponse> getAllUsers(@RequestParam(value="page", 
	defaultValue="1") int page,@RequestParam(value="limit",defaultValue="15") int limit){ // pour utiliser la pagination il faut spécifier la pages et le nombre d'utilisateur maximal /page
		
		List<UserResponse> usersResponse = new ArrayList<>(); // création de la liste qui va contenir les utilisateurs
		
		List<UserDto> users = userService.getUsers(page,limit);
		
		for ( UserDto userDto : users ) { //users copie a chaque fois le contenu de userDto
			
			UserResponse user = new UserResponse();
			
			BeanUtils.copyProperties(userDto, user); //user copie a chaque fois le contenu de userDto
			
			usersResponse.add(user); // on va ajouter le user a la liste des users
			
		}
		
		return usersResponse;
	}
	
	
	
	/* --------------------------------------CreateUser------------------------------------------------------ */
	
	
	@PostMapping(
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception //userRequest contient les infos de l'utilisateur qu'on va créer
	{
		if (userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);
		
		//UserDto userDto = new UserDto();//Couche représentation
		
		//BeanUtils.copyProperties(userRequest, userDto);//Couche représentation
		UserDto createUser = userService.createUser(userDto);
			
		
		//BeanUtils.copyProperties(createUser, userResponse); on a supprimé cette ecriture puisque on a la remplacé avec le modelMapper qui va retourné bs un objet de type userResponse
		UserResponse userResponse =  modelMapper.map(createUser, UserResponse.class);
		
		
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);
	}
	
	/* ------------------------------------------UpdateUser-------------------------------------------------- */
	
	
	
	/*Pour modifier un utilisateur il faut passer en paramètre l'id de l'utilisateur qu'on va modifier*/
	/*Le RequestBody va contenir les infos qui sont localises au niveau de userRequest et qu'on va les modifier cad on va juste modifier les*/
	/* les attributs qui sont au niveau de userRequest*/
	
	@PutMapping(path="/{id}",
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	) 
	public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest)//path variable cad springboot va connaitre que cette paramètre va recevoir le contenu de segment dynamique
	{												// qui a le meme nom BIEN SUR
		
		UserDto userDto = new UserDto();//Couche représentation
		
		BeanUtils.copyProperties(userRequest, userDto);//Couche représentation on va copier les infos de userRequest vers userDto
		
		UserDto updateUser = userService.updateUser(id, userDto);
			
		UserResponse userResponse = new UserResponse();//pour retourner de cette méthode le client enregistré
		BeanUtils.copyProperties(updateUser, userResponse);
		return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
	}
	
	
	/* ------------------------------------------DeleteUser-------------------------------------------------- */
	
	
	@DeleteMapping(path="/{id}") 
	public Object deleteUser(@PathVariable String id)
	{
		userService.deleteUser(id); // execution de méthode deleteUser localisé au niveau de service
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
//presentation layer