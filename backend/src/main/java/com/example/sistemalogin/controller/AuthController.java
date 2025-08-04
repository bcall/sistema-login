package com.example.sistemalogin.controller;

import com.example.sistemalogin.dto.AuthResponse;
import com.example.sistemalogin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    public static class AuthRequest {
        public String username;
        public String password;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        if (request.username == null || request.username.trim().isEmpty()) {
            return new AuthResponse(false, "El nombre de usuario es obligatorio");
        }
        
        if (request.password == null || request.password.trim().isEmpty()) {
            return new AuthResponse(false, "La contraseña es obligatoria");
        }
        
        if (request.password.length() < 6) {
            return new AuthResponse(false, "La contraseña debe tener al menos 6 caracteres");
        }
        
        boolean registered = authService.registerUser(request.username.trim(), request.password);
        
        if (registered) {
            return new AuthResponse(true, "Usuario registrado exitosamente", request.username);
        } else {
            return new AuthResponse(false, "El nombre de usuario ya existe");
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        if (request.username == null || request.username.trim().isEmpty()) {
            return new AuthResponse(false, "El nombre de usuario es obligatorio");
        }
        
        if (request.password == null || request.password.trim().isEmpty()) {
            return new AuthResponse(false, "La contraseña es obligatoria");
        }
        
        boolean authenticated = authService.authenticateUser(request.username.trim(), request.password);
        
        if (authenticated) {
            return new AuthResponse(true, "Inicio de sesión exitoso", request.username);
        } else {
            return new AuthResponse(false, "Credenciales incorrectas");
        }
    }
} 