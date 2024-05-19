package com.chatapp.backend.service;

import com.chatapp.backend.DAO.UserRepository;
import com.chatapp.backend.DTO.RegisterUserDTO;
import com.chatapp.backend.exception.ApplicationException;
import com.chatapp.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public ResponseEntity<User> registerUser(RegisterUserDTO user) {

        if(user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            throw new ApplicationException("Please enter all fields", HttpStatus.BAD_REQUEST);
        }


        User userExists = userRepository.findOneByEmail(user.getEmail());

        if(userExists != null)
            throw new ApplicationException("User already exists", HttpStatus.BAD_REQUEST);
        String hashPwd = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getName(),user.getEmail(),hashPwd, user.getPic(), Instant.now());
        try {
            userRepository.save(newUser);
        }
        catch (Exception e){
            throw new ApplicationException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    public ResponseEntity<List<User>> getAllUser(String search){
        User loggedInUser = getCurrentUser().getBody();
        search = search.toUpperCase();
        List<User> users = userRepository.searchUsers(search,loggedInUser.getId());
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findOneByEmail(email);
        return ResponseEntity.status(200).body(user);
    }
}
