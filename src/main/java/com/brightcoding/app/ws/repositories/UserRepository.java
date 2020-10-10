package com.brightcoding.app.ws.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
//responsable a la communication avec la base de donnees
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brightcoding.app.ws.entities.UserEntity;
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
		UserEntity findByEmail(String email); //pour verifier si l'email existe ou non
		
		UserEntity findByUserId(String userId);
		
		/*
		@Query(value="SELECT * from users", nativeQuery = true)
		Page<UserEntity> findAllUsers(Pageable pageableRequest); //lorsqu'on execute cette méthode cette requette va s'éxécuter
		*/
		
		
		@Query("SELECT user from UserEntity user") //from l'entity who interact with the table in the database
		Page<UserEntity> findAllUsers(Pageable pageableRequest); //lorsqu'on execute cette méthode cette requette va s'éxécuter
		
		/*
		 * 
		 * UNAMED PARAMETERS
		 * 
		 * 
		@Query(value="SELECT * from users u WHERE (u.first_name=?1 OR u.last_name =?1) AND u.email_verification_status=?2", nativeQuery = true) //where le lastName est egal au premier paramètre que je vais passer
		Page<UserEntity> findAllUsersByCriteria(Pageable pageableRequest , String search,int status);	//ou le firstname est egal au premier paramètre que je vais passer 
		 *
		 *
		 */ 

		
		
		/*NAMED PARAMETERS	
		@Query(value="SELECT * from users WHERE (users.first_name= :search OR users.last_name = :search) AND users.email_verification_status= :status", nativeQuery = true) 
		Page<UserEntity> findAllUsersByCriteria(Pageable pageableRequest , @Param("search") String search, @Param("status") int status);												
		 */
		
		
		@Query(value="SELECT * from users u WHERE (u.first_name LIKE %:search% OR u.last_name LIKE %:search%) AND u.email_verification_status= :status", nativeQuery = true) 
		Page<UserEntity> findAllUsersByCriteria(Pageable pageableRequest , @Param("search") String search, @Param("status") int status);

} 
