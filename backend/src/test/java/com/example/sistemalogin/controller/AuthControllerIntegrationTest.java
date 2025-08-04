package com.example.sistemalogin.controller;

import com.example.sistemalogin.dto.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Tests para el endpoint /api/register
    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/register - Registro exitoso")
    void shouldRegisterUserSuccessfully() throws Exception {
        // Given
        String requestBody = "{\"username\":\"test@example.com\",\"password\":\"password123\"}";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"))
                .andExpect(jsonPath("$.username").value("test@example.com"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/register - Usuario ya existe")
    void shouldNotRegisterExistingUser() throws Exception {
        // Given
        String requestBody = "{\"username\":\"existing@example.com\",\"password\":\"password123\"}";

        // Register user first
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        // Try to register same user again
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario ya existe"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/register - Username vacío")
    void shouldNotRegisterWithEmptyUsername() throws Exception {
        // Given
        String requestBody = "{\"username\":\"\",\"password\":\"password123\"}";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario es obligatorio"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/register - Password vacío")
    void shouldNotRegisterWithEmptyPassword() throws Exception {
        // Given
        String requestBody = "{\"username\":\"test@example.com\",\"password\":\"\"}";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña es obligatoria"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/register - Password muy corta")
    void shouldNotRegisterWithShortPassword() throws Exception {
        // Given
        String requestBody = "{\"username\":\"test@example.com\",\"password\":\"123\"}";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña debe tener al menos 6 caracteres"));
    }

    // Tests para el endpoint /api/login
    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/login - Login exitoso")
    void shouldLoginSuccessfully() throws Exception {
        // Given - Register user first
        String registerBody = "{\"username\":\"login@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
                .andExpect(status().isOk());

        // When & Then - Login with same credentials
        String loginBody = "{\"username\":\"login@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"))
                .andExpect(jsonPath("$.username").value("login@example.com"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/login - Contraseña incorrecta")
    void shouldNotLoginWithWrongPassword() throws Exception {
        // Given - Register user first
        String registerBody = "{\"username\":\"wrongpass@example.com\",\"password\":\"password123\"}";
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
                .andExpect(status().isOk());

        // When & Then - Login with wrong password
        String loginBody = "{\"username\":\"wrongpass@example.com\",\"password\":\"wrongpassword\"}";
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/login - Usuario inexistente")
    void shouldNotLoginWithNonExistentUser() throws Exception {
        // Given
        String loginBody = "{\"username\":\"nonexistent@example.com\",\"password\":\"password123\"}";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/login - Username vacío")
    void shouldNotLoginWithEmptyUsername() throws Exception {
        // Given
        String loginBody = "{\"username\":\"\",\"password\":\"password123\"}";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario es obligatorio"));
    }

    @Test
    @org.junit.jupiter.api.DisplayName("POST /api/login - Password vacío")
    void shouldNotLoginWithEmptyPassword() throws Exception {
        // Given
        String loginBody = "{\"username\":\"test@example.com\",\"password\":\"\"}";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña es obligatoria"));
    }
} 