package com.sbz.agro.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sbz.agro.enums.Role;

@Entity
@Table(name = "agro_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Role role;
    @ElementCollection
    private Set<String> tokens = new HashSet<>();
    @OneToMany(mappedBy="owner")
    private List<Land> lands;
    
    public User() {
    	
    }

    public User(String username, String password, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
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

    public Set<String> getTokens() {
        return tokens;
    }

    public List<Land> getLands() {
		return lands;
	}

	public void setLands(List<Land> lands) {
		this.lands = lands;
	}

	public void AddToken(String token) {
        for (String t : tokens) {
            if (t.equals(token))
                return;
        }
        tokens.add(token);
    }

    public void RemoveToken(String token) {
        for (String t : tokens) {
            if (t.equals(token)) {
                tokens.remove(t);
                return;
            }
        }
    }

    public boolean HasToken(String token) {
        for (String t : tokens) {
            if (t.equals(token)) {
                return true;
            }
        }
        return false;
    }
}
