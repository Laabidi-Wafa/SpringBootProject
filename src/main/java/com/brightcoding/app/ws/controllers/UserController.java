package com.brightcoding.app.ws.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.brightcoding.app.ws.entities.UserEntity;
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
	
	@GetMapping(path="/{id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE}) //localhost:8080/users/id avec un id dynamique cad qui change
	public ResponseEntity<UserResponse> getUser(@PathVariable String id)//il faut le déclarer ici comme paramètre avec le MEME NOM
	{
		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userDto, userResponse);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
    /* -------------------------------------------------------------------------------------------- */
	
	
	@PostMapping(
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws Exception //userRequest contient les infos de l'utilisateur qu'on va créer
	{
		if (userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();//Couche représentation
		BeanUtils.copyProperties(userRequest, userDto);//Couche représentation
		
		UserDto createUser = userService.createUser(userDto);
			
		UserResponse userResponse = new UserResponse();//pour retourner de cette méthode le client enregistré
		BeanUtils.copyProperties(createUser, userResponse);
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	/* -------------------------------------------------------------------------------------------- */
	
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
	
	
	/* -------------------------------------------------------------------------------------------- */
	
	
	@DeleteMapping(path="/{id}") 
	public Object deleteUser(@PathVariable String id)
	{
		userService.deleteUser(id); // execution de méthode deleteUser localisé au niveau de service
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
//presentation layer