package com.brightcoding.app.ws.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brightcoding.app.ws.entities.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

}

//data layer
