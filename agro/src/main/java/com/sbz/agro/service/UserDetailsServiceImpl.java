package com.sbz.agro.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbz.agro.enums.Role;
import com.sbz.agro.model.User;
import com.sbz.agro.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		//TODO refactor me, please
		if(user.getRole() == Role.ADMIN)
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.EXPERT.toString()));
		}
		else if(user.getRole() == Role.EXPERT)
		{
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.EXPERT.toString()));
		}
		
		grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.toString()));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
}
