package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.UserElasticSearchContainer;
import com.ahsoka.SALC.user_model.dtos.UserResponse;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.persistance.repository.UserRepository;
import com.ahsoka.SALC.user_model.service.EmailValidator;
import com.ahsoka.SALC.user_model.service.PasswordValidatorService;
import com.ahsoka.SALC.user_model.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    private UserController userController;

    @Container
    private static ElasticsearchContainer elasticsearchContainer = new UserElasticSearchContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    static void destroy() {
        elasticsearchContainer.stop();
    }

    private void fillUserIndex() {
        // Admin user
        User user = new User();
        user.setEmail("test_admin@salc.org");
        user.setRole(Role.ROLE_ADMIN);
        user.setPassword("$2y$10$j.580EBjAsYNds4pJ/DUnuR1ftIoeSt0Bt5Tn87sLPXK2i1FtVSBO");
        userRepository.save(user);
        // Worker user
        user = new User();
        user.setEmail("test_worker@salc.org");
        user.setRole(Role.ROLE_WORKER);
        user.setPassword("$2y$10$7o0gVbIQyE8b6nrPKqFSUeMqaHfZ7UQeInxr9AjFr4CgMcAAB4KhK");
        userRepository.save(user);
    }

    @BeforeEach
    void before() {
        JwtService jwtService = new JwtService("secret-sign", "es-salc-ucm", 36000);
        EmailValidator emailValidator = new EmailValidator();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordValidatorService passwordValidatorService = new PasswordValidatorService();
        UserService userService = new UserService(userRepository, jwtService, emailValidator, passwordEncoder, passwordValidatorService);
        userController = new UserController(userService);
        assertTrue(elasticsearchContainer.isRunning());
        fillUserIndex();
    }

    @Test
    void testLogin_withExistingUser() {
        User usuario = new User();
        usuario.setEmail("test_admin@salc.org");
        usuario.setPassword("aPassword");
        UserResponse userResponse = new UserResponse(usuario);
        assertTrue(userController.login(userResponse).isPresent());
    }

    @Test
    void testLogin_withWrongPassword() {
        User usuario = new User();
        usuario.setEmail("test_admin@salc.org");
        usuario.setPassword("wrongPassword");
        UserResponse userResponse = new UserResponse(usuario);
        assertTrue(userController.login(userResponse).isEmpty());
    }

    @Test
    void testLogin_withNonExistingUser() {
        User usuario = new User();
        usuario.setEmail("prueba@salc.org");
        usuario.setPassword("admin");
        UserResponse userResponse = new UserResponse(usuario);
        assertTrue(userController.login(userResponse).isEmpty());
    }
}
