package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.dtos.TokenResponse;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@PreAuthorize("authenticated")
@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    private JwtService jwtService;
    private UserService userService;
    private EmailValidator emailValidator;
    private static final String USERS = "/users/";
    private static final String AUTHENTICATION = "/authentication/";

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = AUTHENTICATION)
    public Optional<TokenResponse> login(@AuthenticationPrincipal User user) {
        Optional<String> token = userService.login(user.getEmail());

        if(token.isPresent())
            return Optional.of(new TokenResponse(token.get()));
        else
            return Optional.empty();
    }

}