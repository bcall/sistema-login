package com.example.sistemalogin.controller;

import com.example.sistemalogin.dto.AuthResponse;
import com.example.sistemalogin.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    // Tests para /api/register
    @Test
    @DisplayName("POST /api/register - Registro exitoso")
    void shouldRegisterUserSuccessfully() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "password123";

        when(authService.registerUser("test@example.com", "password123")).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"))
                .andExpect(jsonPath("$.username").value("test@example.com"));
    }

    @Test
    @DisplayName("POST /api/register - Usuario ya existe")
    void shouldNotRegisterExistingUser() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "existing@example.com";
        request.password = "password123";

        when(authService.registerUser("existing@example.com", "password123")).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario ya existe"));
    }

    @Test
    @DisplayName("POST /api/register - Username vacío")
    void shouldNotRegisterWithEmptyUsername() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "";
        request.password = "password123";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario es obligatorio"));
    }

    @Test
    @DisplayName("POST /api/register - Password vacío")
    void shouldNotRegisterWithEmptyPassword() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña es obligatoria"));
    }

    @Test
    @DisplayName("POST /api/register - Password muy corta")
    void shouldNotRegisterWithShortPassword() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "123";

        // When & Then
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña debe tener al menos 6 caracteres"));
    }

    // Tests para /api/login
    @Test
    @DisplayName("POST /api/login - Login exitoso")
    void shouldLoginSuccessfully() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "password123";

        when(authService.authenticateUser("test@example.com", "password123")).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"))
                .andExpect(jsonPath("$.username").value("test@example.com"));
    }

    @Test
    @DisplayName("POST /api/login - Credenciales incorrectas")
    void shouldNotLoginWithWrongCredentials() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "wrongpassword";

        when(authService.authenticateUser("test@example.com", "wrongpassword")).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    @DisplayName("POST /api/login - Username vacío")
    void shouldNotLoginWithEmptyUsername() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "";
        request.password = "password123";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario es obligatorio"));
    }

    @Test
    @DisplayName("POST /api/login - Password vacío")
    void shouldNotLoginWithEmptyPassword() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = "";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña es obligatoria"));
    }

    @Test
    @DisplayName("POST /api/login - Username null")
    void shouldNotLoginWithNullUsername() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = null;
        request.password = "password123";

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El nombre de usuario es obligatorio"));
    }

    @Test
    @DisplayName("POST /api/login - Password null")
    void shouldNotLoginWithNullPassword() throws Exception {
        // Given
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "test@example.com";
        request.password = null;

        // When & Then
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("La contraseña es obligatoria"));
    }
} 