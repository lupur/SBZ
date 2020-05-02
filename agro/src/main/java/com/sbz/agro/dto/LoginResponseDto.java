package com.sbz.agro.dto;

import com.sbz.agro.model.User;

public class LoginResponseDto {
	Long id;
    String token;
    String role;

    public LoginResponseDto() {
        super();
    }

    public LoginResponseDto(Long id, String token, String role) {
		this.id = id;
    	this.token = token;
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
