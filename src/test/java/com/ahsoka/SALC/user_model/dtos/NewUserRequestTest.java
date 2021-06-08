package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testToUser() {
        User expectedUser = new User();
        expectedUser.setEmail("test@salc.org");
        expectedUser.setRole(Role.ROLE_ADMIN);
        assertEquals(expectedUser, newUserRequest.toUser());
    }
}
