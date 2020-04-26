package com.sbz.agro.dto;

import com.sbz.agro.model.User;

public class UserLoginDto {
    String username;
    String password;

    public UserLoginDto() {
    	super();
    }
    
    public UserLoginDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
    
    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
