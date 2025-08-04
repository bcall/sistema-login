package com.example.sistemalogin.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    @DisplayName("Debería crear AuthResponse con constructor por defecto")
    void shouldCreateAuthResponseWithDefaultConstructor() {
        // Given & When
        AuthResponse response = new AuthResponse();

        // Then
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertNull(response.getMessage());
        assertNull(response.getUsername());
    }

    @Test
    @DisplayName("Debería crear AuthResponse con constructor de success y message")
    void shouldCreateAuthResponseWithSuccessAndMessage() {
        // Given & When
        AuthResponse response = new AuthResponse(true, "Test message");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Test message", response.getMessage());
        assertNull(response.getUsername());
    }

    @Test
    @DisplayName("Debería crear AuthResponse con constructor completo")
    void shouldCreateAuthResponseWithFullConstructor() {
        // Given & When
        AuthResponse response = new AuthResponse(true, "Test message", "test@example.com");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Test message", response.getMessage());
        assertEquals("test@example.com", response.getUsername());
    }

    @Test
    @DisplayName("Debería establecer y obtener valores correctamente")
    void shouldSetAndGetValuesCorrectly() {
        // Given
        AuthResponse response = new AuthResponse();

        // When
        response.setSuccess(true);
        response.setMessage("Updated message");
        response.setUsername("updated@example.com");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Updated message", response.getMessage());
        assertEquals("updated@example.com", response.getUsername());
    }

    @Test
    @DisplayName("Debería crear respuesta de éxito")
    void shouldCreateSuccessResponse() {
        // Given & When
        AuthResponse response = new AuthResponse(true, "Operación exitosa", "user@example.com");

        // Then
        assertTrue(response.isSuccess());
        assertEquals("Operación exitosa", response.getMessage());
        assertEquals("user@example.com", response.getUsername());
    }

    @Test
    @DisplayName("Debería crear respuesta de error")
    void shouldCreateErrorResponse() {
        // Given & When
        AuthResponse response = new AuthResponse(false, "Error en la operación");

        // Then
        assertFalse(response.isSuccess());
        assertEquals("Error en la operación", response.getMessage());
        assertNull(response.getUsername());
    }

    @Test
    @DisplayName("Debería manejar valores null correctamente")
    void shouldHandleNullValuesCorrectly() {
        // Given
        AuthResponse response = new AuthResponse();

        // When
        response.setMessage(null);
        response.setUsername(null);

        // Then
        assertNull(response.getMessage());
        assertNull(response.getUsername());
    }
} 