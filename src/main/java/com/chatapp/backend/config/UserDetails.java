package com.chatapp.backend.config;

import com.chatapp.backend.DAO.UserRepository;
import com.chatapp.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetails implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities;
        User user = userRepository.findOneByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User details not found for the user : " + email);
        } else{
            userName = user.getEmail();
            password = user.getPassword();
            authorities = new ArrayList<>();
        }
        return new org.springframework.security.core.userdetails.User(userName,password,authorities);
    }
}
