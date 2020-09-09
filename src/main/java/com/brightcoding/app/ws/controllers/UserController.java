package com.brightcoding.app.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.brightcoding.app.ws.requests.UserRequest;
import com.brightcoding.app.ws.responses.UserResponse;
import com.brightcoding.app.ws.services.UserService;
import com.brightcoding.app.ws.shared.dto.UserDto;

@RequestMapping("/users") //localhost:8080/users
@RestController
public class UserController {
	//pour créer un objet de type userService, il faut faire une instance de ce type
	@Autowired
	UserService userService ; //l'injection de dépendance a la place de new
	
	@GetMapping
	public String getUser()
	{
		return "get user was called";
	}
	
    /* -------------------------------------------------------------------------------------------- */
	
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest)
	{
		UserDto userDto = new UserDto();//Couche représentation
		BeanUtils.copyProperties(userRequest, userDto);//Couche représentation
		
		UserDto createUser = userService.createUser(userDto);
		
		UserResponse userResponse = new UserResponse();//pour retourner de cette méthode le client enregistré
		BeanUtils.copyProperties(createUser, userResponse);
		return userResponse;
	}
	
	/* -------------------------------------------------------------------------------------------- */
	
	
	@PutMapping
	public String updateUser()
	{
		return "update user was called";
	}
	
	
	/* -------------------------------------------------------------------------------------------- */
	
	
	@DeleteMapping
	public String deleteUser()
	{
		return "delete user was called";
	}
}
//presentation layer