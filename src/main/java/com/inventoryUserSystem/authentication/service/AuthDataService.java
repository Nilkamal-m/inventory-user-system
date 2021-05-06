package com.inventoryUserSystem.authentication.service;

import java.security.NoSuchAlgorithmException;

import com.inventoryUserSystem.authentication.entity.User;

public interface AuthDataService {

	User findByUsername(String username);
	
	void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException;
	
	void createUserProfile(User user) throws NoSuchAlgorithmException;
	
}
