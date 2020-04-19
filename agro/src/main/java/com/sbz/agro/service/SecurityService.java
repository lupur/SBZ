package com.sbz.agro.service;

public interface SecurityService {

	String findLoggedInUsername();
	void autologin(String username, String password);
}
