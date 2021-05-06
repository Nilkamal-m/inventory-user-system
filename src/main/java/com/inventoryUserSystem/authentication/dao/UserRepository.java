package com.inventoryUserSystem.authentication.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventoryUserSystem.authentication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	

	@Query(value = "DELETE FROM user where username = :USERNAME and password = :PASSWORD",
            nativeQuery = true)
    void deleteByUsernamePassword(@Param("USERNAME") String USERNAME, @Param("PASSWORD") String PASSWORD);

	@Modifying
    @Transactional
    @Query(value = "INSERT INTO user (username,password,role) " +
            " VALUES (:USERNAME, :PASSWORD, :ROLE)", nativeQuery = true)
    void createUserProfile(@Param("USERNAME") String USERNAME,
                           @Param("PASSWORD") String PASSWORD,
                           @Param("ROLE") String ROLE);
	
}
