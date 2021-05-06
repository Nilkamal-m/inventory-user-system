package com.inventoryUserSystem.authentication.model;

import java.util.List;

public class JwtResponse {
	
	private String jwtToken;
	private List<String> role;
	private String eror;
	
	public JwtResponse() {
		
	}
	
	



	public JwtResponse(String eror) {
		super();
		this.eror = eror;
	}





	public JwtResponse(String jwtToken, List<String> role,String eror) {
		super();
		this.jwtToken = jwtToken;
		this.role = role;
		this.eror = eror;
	}


	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}



	public List<String> getRole() {
		return role;
	}



	public void setRole(List<String> role) {
		this.role = role;
	}



	public String getEror() {
		return eror;
	}



	public void setEror(String eror) {
		this.eror = eror;
	}
	

}
