package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.dtos.NewUserRequest;
import com.ahsoka.SALC.user_model.dtos.TokenResponse;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.service.Response;
import com.ahsoka.SALC.user_model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = USERS)
    public String createUser(@Valid @RequestBody NewUserRequest user) {
        Response response = userService.createUser(user.toUser());

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_CREATED);
        else {
            return HttpServletResponse.SC_EXPECTATION_FAILED + " " + response.toString();
        }
    }
}