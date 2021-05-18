package com.ahsoka.SALC.user_model.dtos;

import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class NewUserRequest {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private Role role;

    public User toUser(){
        User user = new User();
        setEmail(email);
        setRole(role);

        return user;
    }
	
}