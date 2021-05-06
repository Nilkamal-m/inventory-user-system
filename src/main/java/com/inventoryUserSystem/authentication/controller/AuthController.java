package com.inventoryUserSystem.authentication.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.inventoryUserSystem.authentication.entity.User;
import com.inventoryUserSystem.authentication.model.AccountCreationRequest;
import com.inventoryUserSystem.authentication.model.AccountCreationResponse;
import com.inventoryUserSystem.authentication.model.JwtRequest;
import com.inventoryUserSystem.authentication.model.JwtResponse;
import com.inventoryUserSystem.authentication.service.AuthDataService;
import com.inventoryUserSystem.authentication.util.JwtUtil;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
	
	@Autowired
	private AuthDataService authDataService;
	
	@Autowired
	UserDetailsService myUserDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("/test")
	public ResponseEntity<?> test() {
        return ResponseEntity.ok("success");
    }
	
	@PostMapping("/signup")
	public ResponseEntity<?> createAccount(@RequestBody AccountCreationRequest accountCreationRequest) throws Exception{
		
		if(authDataService.findByUsername(accountCreationRequest.getUsername()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new AccountCreationResponse("failure", "Username already exist"));
		}
		
		authDataService.createUserProfile(new User(accountCreationRequest.getUsername(), accountCreationRequest.getPassword(), accountCreationRequest.getRole()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new AccountCreationResponse("Success", null));
		
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate (@RequestBody JwtRequest jwtRequest) throws Exception {
		try {
			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
					);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new JwtResponse("Incorrect username or password."));
		} catch(Exception e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JwtResponse("Username does not exist."));
		}
		
		final UserDetails userDetails =
				myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
				
		final String token = jwtUtil.generateToken(userDetails);
		List<String> role = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(token,role, null));
	}
	
	
}
