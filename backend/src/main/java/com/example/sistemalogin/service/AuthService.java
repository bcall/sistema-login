package com.example.sistemalogin.service;

import com.example.sistemalogin.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Map<String, User> users = new HashMap<>();
    
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Usuario ya existe
        }
        
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, hashedPassword);
        users.put(username, user);
        return true;
    }
    
    public boolean authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            return false; // Usuario no existe
        }
        
        return passwordEncoder.matches(password, user.getHashedPassword());
    }
    
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
} 