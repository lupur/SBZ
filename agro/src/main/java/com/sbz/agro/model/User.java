package com.sbz.agro.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.sbz.agro.enums.Role;

@Entity
@Table(name="agro_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private Role role;
	
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
	public String getPassword() {
        return password;
    }
	public void setPassword(String password) {
        this.password = password;
    }
	public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
