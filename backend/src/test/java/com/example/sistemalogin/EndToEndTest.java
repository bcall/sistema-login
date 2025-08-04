package com.example.sistemalogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class EndToEndTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Flujo completo: Registro -> Login exitoso")
    void shouldCompleteFullRegistrationAndLoginFlow() throws Exception {
        // Given
        String username = "e2e@example.com";
        String password = "password123";

        // Step 1: Register user
        String registerBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Usuario registrado exitosamente"))
                .andExpect(jsonPath("$.username").value(username));

        // Step 2: Login with same credentials
        String loginBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso"))
                .andExpect(jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("Flujo completo: Registro -> Login con contraseña incorrecta")
    void shouldCompleteRegistrationAndFailedLoginFlow() throws Exception {
        // Given
        String username = "e2efail@example.com";
        String correctPassword = "password123";
        String wrongPassword = "wrongpassword";

        // Step 1: Register user
        String registerBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, correctPassword);
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        // Step 2: Try to login with wrong password
        String loginBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, wrongPassword);
        
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }

    @Test
    @DisplayName("Flujo completo: Múltiples registros y logins")
    void shouldHandleMultipleUsersRegistrationAndLogin() throws Exception {
        // Given
        String[] users = {
            "user1@example.com",
            "user2@example.com", 
            "user3@example.com"
        };
        String password = "password123";

        // Step 1: Register all users
        for (String username : users) {
            String registerBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            
            mockMvc.perform(post("/api/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registerBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.username").value(username));
        }

        // Step 2: Login all users
        for (String username : users) {
            String loginBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            
            mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.username").value(username));
        }
    }

    @Test
    @DisplayName("Flujo completo: Validación de contraseñas de diferentes longitudes")
    void shouldValidatePasswordLengthsCorrectly() throws Exception {
        // Given
        String username = "passwordtest@example.com";
        String[] shortPasswords = {"123", "abc", "12"};
        String validPassword = "password123";

        // Step 1: Try to register with short passwords
        for (String shortPassword : shortPasswords) {
            String registerBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, shortPassword);
            
            mockMvc.perform(post("/api/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registerBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("La contraseña debe tener al menos 6 caracteres"));
        }

        // Step 2: Register with valid password
        String validRegisterBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, validPassword);
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRegisterBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
} 