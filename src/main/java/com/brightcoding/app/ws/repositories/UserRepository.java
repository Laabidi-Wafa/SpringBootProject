package com.brightcoding.app.ws.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.brightcoding.app.ws.entities.UserEntity;
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
		UserEntity findByEmail(String email); //pour verifier si l'email existe ou non
		
		UserEntity findByUserId(String userId);
}
