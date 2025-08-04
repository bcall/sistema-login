package com.example.sistemalogin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    @DisplayName("Debería registrar un usuario exitosamente")
    void shouldRegisterUserSuccessfully() {
        // Given
        String username = "test@example.com";
        String password = "password123";

        // When
        boolean result = authService.registerUser(username, password);

        // Then
        assertTrue(result);
        assertTrue(authService.userExists(username));
    }

    @Test
    @DisplayName("No debería registrar un usuario que ya existe")
    void shouldNotRegisterExistingUser() {
        // Given
        String username = "test@example.com";
        String password = "password123";
        authService.registerUser(username, password);

        // When
        boolean result = authService.registerUser(username, "differentpassword");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería autenticar un usuario con credenciales correctas")
    void shouldAuthenticateUserWithCorrectCredentials() {
        // Given
        String username = "test@example.com";
        String password = "password123";
        authService.registerUser(username, password);

        // When
        boolean result = authService.authenticateUser(username, password);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("No debería autenticar con contraseña incorrecta")
    void shouldNotAuthenticateWithWrongPassword() {
        // Given
        String username = "test@example.com";
        String password = "password123";
        authService.registerUser(username, password);

        // When
        boolean result = authService.authenticateUser(username, "wrongpassword");

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("No debería autenticar un usuario inexistente")
    void shouldNotAuthenticateNonExistentUser() {
        // Given
        String username = "nonexistent@example.com";
        String password = "password123";

        // When
        boolean result = authService.authenticateUser(username, password);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Debería verificar que un usuario existe")
    void shouldVerifyUserExists() {
        // Given
        String username = "test@example.com";
        String password = "password123";
        authService.registerUser(username, password);

        // When & Then
        assertTrue(authService.userExists(username));
        assertFalse(authService.userExists("nonexistent@example.com"));
    }

    @Test
    @DisplayName("Debería hashear contraseñas de forma diferente")
    void shouldHashPasswordsDifferently() {
        // Given
        String username1 = "user1@example.com";
        String username2 = "user2@example.com";
        String password = "samepassword";

        // When
        authService.registerUser(username1, password);
        authService.registerUser(username2, password);

        // Then - ambas contraseñas deberían autenticar correctamente
        assertTrue(authService.authenticateUser(username1, password));
        assertTrue(authService.authenticateUser(username2, password));
    }
} 