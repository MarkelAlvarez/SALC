package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UpdateUserRequestTest {
    UpdateUserRequest updateUserRequest;

    @BeforeEach
    void before() {
        updateUserRequest = new UpdateUserRequest("admin@salc.org", "holamundo", Role.ROLE_ADMIN);
    }

    @Test
    void testPasswordRequest() {
        String expectedMail = "admin@salc.org";
        String expectedPassword = "holamundo";
        Role expectedRole = Role.ROLE_ADMIN;

        assertEquals(expectedMail, updateUserRequest.getEmail());
        assertEquals(expectedPassword, updateUserRequest.getPassword());
        assertEquals(expectedRole, updateUserRequest.getRole());
    }
}