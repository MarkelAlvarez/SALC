package com.ahsoka.SALC.user_model.service;

import com.ahsoka.SALC.user_model.exceptions.UserNotFoundException;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.persistance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email not found: " + email));
        return this.userBuilder(user.getEmail(), user.getPassword(), new Role[]{Role.AUTHENTICATED}, user.isEnabled());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String email, String password, Role[] roles,
                                                                           boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return new org.springframework.security.core.userdetails.User(email, password, active, true,
                true, true, authorities);
    }

    public Optional<String> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(password, user.get().getPassword()) ?
                    Optional.of(jwtService.createToken(user.get().getEmail(), user.get().getRole().toString())) :
                    Optional.empty();
        }

        return Optional.empty();
    }
}
