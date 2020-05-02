package com.sbz.agro.dto;

import com.sbz.agro.enums.Role;

public class ChangeRoleDto {
	
	private Long id;
	private Role role;
	
	public ChangeRoleDto() {
		super();
	}
	
	public ChangeRoleDto(Long id, Role role) {
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
