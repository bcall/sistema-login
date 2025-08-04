package com.example.sistemalogin.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Debería crear User con constructor por defecto")
    void shouldCreateUserWithDefaultConstructor() {
        // Given & When
        User user = new User();

        // Then
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getHashedPassword());
    }

    @Test
    @DisplayName("Debería crear User con constructor completo")
    void shouldCreateUserWithFullConstructor() {
        // Given & When
        User user = new User("test@example.com", "hashedPassword123");

        // Then
        assertEquals("test@example.com", user.getUsername());
        assertEquals("hashedPassword123", user.getHashedPassword());
    }

    @Test
    @DisplayName("Debería establecer y obtener username correctamente")
    void shouldSetAndGetUsernameCorrectly() {
        // Given
        User user = new User();

        // When
        user.setUsername("newuser@example.com");

        // Then
        assertEquals("newuser@example.com", user.getUsername());
    }

    @Test
    @DisplayName("Debería establecer y obtener hashedPassword correctamente")
    void shouldSetAndGetHashedPasswordCorrectly() {
        // Given
        User user = new User();

        // When
        user.setHashedPassword("newHashedPassword456");

        // Then
        assertEquals("newHashedPassword456", user.getHashedPassword());
    }

    @Test
    @DisplayName("Debería manejar valores null correctamente")
    void shouldHandleNullValuesCorrectly() {
        // Given
        User user = new User();

        // When
        user.setUsername(null);
        user.setHashedPassword(null);

        // Then
        assertNull(user.getUsername());
        assertNull(user.getHashedPassword());
    }

    @Test
    @DisplayName("Debería crear múltiples usuarios con diferentes datos")
    void shouldCreateMultipleUsersWithDifferentData() {
        // Given & When
        User user1 = new User("user1@example.com", "hash1");
        User user2 = new User("user2@example.com", "hash2");

        // Then
        assertEquals("user1@example.com", user1.getUsername());
        assertEquals("hash1", user1.getHashedPassword());
        assertEquals("user2@example.com", user2.getUsername());
        assertEquals("hash2", user2.getHashedPassword());
        assertNotEquals(user1.getUsername(), user2.getUsername());
        assertNotEquals(user1.getHashedPassword(), user2.getHashedPassword());
    }

    @Test
    @DisplayName("Debería actualizar datos del usuario")
    void shouldUpdateUserData() {
        // Given
        User user = new User("original@example.com", "originalHash");

        // When
        user.setUsername("updated@example.com");
        user.setHashedPassword("updatedHash");

        // Then
        assertEquals("updated@example.com", user.getUsername());
        assertEquals("updatedHash", user.getHashedPassword());
    }
} 