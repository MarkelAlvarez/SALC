package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.dtos.TokenResponse;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    private EmailValidator emailValidator;
    private static final String USERS = "/users/";
    private static final String AUTHENTICATION = "/authentication/";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = AUTHENTICATION)
    public Optional<TokenResponse> login(@RequestBody User user) {
        Optional<String> token = userService.login(user.getEmail(), user.getPassword());

        if(token.isPresent())
            return Optional.of(new TokenResponse(token.get()));
        else
            return Optional.empty();
    }

}