package com.ahsoka.SALC.user_model.persistance.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@EqualsAndHashCode
@NoArgsConstructor
@Document(indexName = "users", shards = 1, createIndex = false)
public class User implements UserDetails {

    @Getter @Setter private String id;
    @Getter @Setter private String email;
    @Getter @Setter private String password;
    @Getter @Setter private Role role;
    @Getter @Setter private boolean locked;
    @Getter @Setter private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}