package com.inventoryUserSystem.authentication.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventoryUserSystem.authentication.dao.UserRepository;
import com.inventoryUserSystem.authentication.entity.User;

@Service
public class AuthDataServiceImpl implements AuthDataService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public User findByUsername(String username) {
		Optional<User> result = repository.findByUsername(username);
		
		User user = null;
		
		if(result.isPresent()) {
			user = result.get();
		}
		
		return user;
	}

	@Override
	public void deleteByUsernamePassword(String username, String password) throws NoSuchAlgorithmException {
		repository.deleteByUsernamePassword(username,password);
		
	}

	@Override
	public void createUserProfile(User user) throws NoSuchAlgorithmException {
		repository.createUserProfile(user.getUsername(),user.getPassword(),user.getRole());
		
	}

}
