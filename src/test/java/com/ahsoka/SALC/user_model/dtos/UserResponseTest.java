package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserResponseTest {

    private UserResponse userResponse;

    @BeforeEach
    void before() {
        User user = new User();
        user.setEmail("test_admin@salc.org");
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword("$2y$10$Ob2Hr6MF.8VYELx0lApNn.GPG/FWC.84CUXOFSBQvPxV.A5FS21Km");
        userResponse = new UserResponse(user);
        System.out.println(user);
        System.out.println(userResponse.toString());
    }

    @Test
    void testUserResponse() {
        Role expectedRole = Role.ROLE_ADMIN;
        String expectedUserEmail = "test_admin@salc.org";
        assertEquals(expectedRole, userResponse.getRole());
        assertEquals(expectedUserEmail, userResponse.getEmail());
    }
}
