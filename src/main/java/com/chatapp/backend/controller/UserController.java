package com.chatapp.backend.controller;

import com.chatapp.backend.DTO.RegisterUserDTO;
import com.chatapp.backend.exception.ApplicationException;
import com.chatapp.backend.model.User;
import com.chatapp.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(){
        return service.getCurrentUser();
    }
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDTO user) {
        return service.registerUser(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers(@RequestParam String search) {
        return service.getAllUser(search);
    }



}
