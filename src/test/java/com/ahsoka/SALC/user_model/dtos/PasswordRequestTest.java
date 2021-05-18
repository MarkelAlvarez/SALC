package com.ahsoka.SALC.user_model.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PasswordRequestTest {
    PasswordRequest passwordRequest;

    @BeforeEach
    void before() {
        passwordRequest = new PasswordRequest("admin@salc.org", "holamundo");
    }

    @Test
    void testPasswordRequest() {
        String expectedMail = "admin@salc.org";
        String expectedPassword = "holamundo";

        assertEquals(expectedMail, passwordRequest.getEmail());
        assertEquals(expectedPassword, passwordRequest.getPassword());
    }
}