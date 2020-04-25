package com.sbz.agro.dto;

import com.sbz.agro.enums.Role;

public class GetUserDto {
    String username;
    Role role;

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
