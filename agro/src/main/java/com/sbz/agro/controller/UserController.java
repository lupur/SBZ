package com.sbz.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbz.agro.dto.UserRegistrationDto;
import com.sbz.agro.model.User;
import com.sbz.agro.service.SecurityService;
import com.sbz.agro.service.UserService;
import com.sbz.agro.validator.UserValidator;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value="/registration")
    public String registration(Model model)
    {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping(value="/registration",
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registartion(@RequestBody() UserRegistrationDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().toString());
        }
        userService.registerNewUser(userDto);
        return ResponseEntity.ok().build();
    }
}
