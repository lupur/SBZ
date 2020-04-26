package com.sbz.agro.dto;


import com.sbz.agro.enums.Role;
import com.sbz.agro.model.User;

public class GetUserDto {
    Long id;
    String username;
    Role role;
    
    public GetUserDto(){
    	super();
    }
    
    public GetUserDto(User user){
    	this.id = user.getId();
    	this.username = user.getUsername();
    	this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
