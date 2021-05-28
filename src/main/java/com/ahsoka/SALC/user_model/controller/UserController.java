package com.ahsoka.SALC.user_model.controller;

import com.ahsoka.SALC.user_model.dtos.DeleteUserRequest;
import com.ahsoka.SALC.user_model.dtos.NewUserRequest;
import com.ahsoka.SALC.user_model.dtos.TokenResponse;
import com.ahsoka.SALC.user_model.dtos.UpdateUserRequest;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.service.UserService;
import com.ahsoka.SALC.user_model.util.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
    private static final String USERS = "/users/";
    private static final String AUTHENTICATION = "/authentication/";

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
        else
            return HttpServletResponse.SC_EXPECTATION_FAILED + " " + response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = USERS)
    public Stream<User> readAll() {
        return userService.readAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = USERS + "email/")
    public Optional<User> readUserByEmail(@RequestParam String email) {
        return userService.readUserByEmail(email);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = USERS + "email/")
    public String updateUserEmail(@RequestBody UpdateUserRequest userRequest, @RequestParam String referenceEmail) {
        Response response = userService.updateUserEmail(userRequest.toUser(), referenceEmail);

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_OK);
        else if(response.equals(Response.REFERENCE_EMAIL_DOESNT_EXIST))
            return HttpServletResponse.SC_NO_CONTENT + " " + response;
        else
            return HttpServletResponse.SC_CONFLICT + " " + response;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER', 'ROLE_CATALOGER', 'AUTHENTICATED')")
    @PatchMapping(value = USERS + "password/")
    public String updateUserPassword(@RequestBody UpdateUserRequest userRequest, @RequestParam String referenceEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Response response = userService.updateUserPassword(userRequest.toUser(), referenceEmail, auth.getName());

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_OK);
        else if(response.equals(Response.REFERENCE_EMAIL_DOESNT_EXIST))
            return HttpServletResponse.SC_NO_CONTENT + " " + response;
        else
            return String.valueOf(HttpServletResponse.SC_FORBIDDEN);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = USERS + "role/")
    public String updateUserRole(@RequestBody UpdateUserRequest userRequest, @RequestParam String referenceEmail) {
        Response response = userService.updateUserRole(userRequest.toUser(), referenceEmail);

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_OK);
        else
            return HttpServletResponse.SC_NO_CONTENT + " " + response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = USERS + "delete/")
    public String deleteUser(@RequestBody DeleteUserRequest userRequest) {
        Response response = userService.deleteUser(userRequest.getEmail());

        if(response.equals(Response.OK))
            return String.valueOf(HttpServletResponse.SC_OK);
        else
            return HttpServletResponse.SC_NO_CONTENT + " " + response;
    }
}