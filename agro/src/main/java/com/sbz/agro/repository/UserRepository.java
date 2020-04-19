package com.sbz.agro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbz.agro.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
