package com.inventoryUserSystem.authentication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inventoryUserSystem.authentication.MyUserDetails;
import com.inventoryUserSystem.authentication.dao.UserRepository;
import com.inventoryUserSystem.authentication.entity.User;
 

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user =repository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("User Not Found :"+username));
		
		
		
		return user.map(MyUserDetails :: new).get();
	}

}
