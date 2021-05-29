package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewUserRequest {

    @NotNull
    @NotBlank(message = "Email is mandatory!")
    private String email;

    @NotNull
    private Role role;

    public User toUser(){
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        return user;
    }
}