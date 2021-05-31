package com.ahsoka.SALC.user_model.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeleteUserRequest {

    private String email;
    private List<String> emails;

    public DeleteUserRequest() {
        emails = new ArrayList<>();
    }
}