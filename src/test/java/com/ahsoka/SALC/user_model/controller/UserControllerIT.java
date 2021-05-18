package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.persistance.repository.UserRepository;
import com.ahsoka.SALC.user_model.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private JwtService jwtService;
    private UserController userController;

    @BeforeEach
    void before() {
        jwtService = new JwtService("secret-sign", "es-salc-ucm", 36000);
        userService = new UserService(userRepository, jwtService);
        userController = new UserController(userService);
    }

    @Disabled
    @Test
    void testLogin_withExistingUser() {
        User usuario = new User();
        usuario.setEmail("test_admin@salc.org");
        usuario.setPassword("aPassword");

        assertTrue(userController.login(usuario).isPresent());
    }

    @Disabled
    @Test
    void testLogin_withWrongPassword() {
        User usuario = new User();
        usuario.setEmail("test_admin@salc.org");
        usuario.setPassword("wrongPassword");

        assertTrue(userController.login(usuario).isEmpty());
    }

    @Disabled
    @Test
    void testLogin_withNonExistingUser() {
        User usuario = new User();
        usuario.setEmail("prueba@salc.org");
        usuario.setPassword("admin");

        assertTrue(userController.login(usuario).isEmpty());
    }
}
