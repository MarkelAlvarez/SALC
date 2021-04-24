package com.ahsoka.SALC.user_model.persistance.entity;

public enum Role {
    ROLE_ADMIN,
    ROLE_WORKER,
    ROLE_CATALOGER;

    private String name;
    public String getName() {
        return name;
    }
}