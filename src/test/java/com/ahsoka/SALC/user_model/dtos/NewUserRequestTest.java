package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewUserRequestTest {

    private NewUserRequest newUserRequest;

    @BeforeEach
    void before() {
        newUserRequest = new NewUserRequest("test@salc.org", Role.ROLE_ADMIN);
    }

    @Test
    void testNewUserRequest() {
        Role expectedRole = Role.ROLE_ADMIN;
        String expectedUserEmail = "test@salc.org";
        assertEquals(expectedRole, newUserRequest.getRole());
        assertEquals(expectedUserEmail, newUserRequest.getEmail());
    }
}
