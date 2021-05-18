package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserControllerIT {

    private UserController userController = new UserController();

    @Test
    void testExisteMail() {
        User usuario = new User();
        usuario.setEmail("test@salc.org");
        usuario.setPassword("holamundo");
        usuario.setRole(Role.ROLE_ADMIN);

        assertTrue(userController.login(usuario).isPresent());
    }

    @Test
    void testNoExisteMail() {
        User usuario = new User();
        usuario.setEmail("prueba@salc.org");
        usuario.setPassword("holamundo");
        usuario.setRole(Role.ROLE_ADMIN);

        assertTrue(userController.login(usuario).isPresent());
    }
}
