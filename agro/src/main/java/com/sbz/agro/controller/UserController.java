package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.ChangeRoleDto;
import com.sbz.agro.dto.LoginResponseDto;
import com.sbz.agro.dto.UserLoginDto;
import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.model.User;
import com.sbz.agro.service.AuthService;
import com.sbz.agro.service.UserService;

@RestController
@RequestMapping("/users")
@SuppressWarnings("rawtypes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registartion(@RequestBody UserRegistrationDto userDto, BindingResult bindingResult) {
        if (!userService.registerNewUser(userDto)) {
            return ResponseEntity.badRequest().build();
        }

        UserLoginDto userLogin = new UserLoginDto(userDto.getUsername(), userDto.getPassword());
        LoginResponseDto userResponse = userService.login(userLogin);
       
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLoginDto user) {
    	LoginResponseDto userLogin = userService.login(user);
        if (userLogin == null) return ResponseEntity.badRequest().body("Wrong credentials");
			
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("Token") String token) {
        if (userService.isUserLoggedIn(token)) {
            userService.logout(token);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Not even singed in - you cannot sing out");
        }
    }

    @GetMapping()
    public ResponseEntity getAllUsers(@RequestHeader("Token") String token) {
        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity changeRole(@RequestHeader("Token") String token, @RequestBody ChangeRoleDto changeRole) {
    	if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    	if(changeRole == null || changeRole.getId() == null || changeRole.getRole() == null)
            return ResponseEntity.badRequest().body("Bad request");

    	if(userService.setRole(changeRole.getId(), changeRole.getRole())) {
    		return ResponseEntity.ok().build();
    	} else {
            return ResponseEntity.badRequest().body("User with given id does not exists");
    	}
    }
}
