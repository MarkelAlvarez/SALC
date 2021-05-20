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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    public Response createUser(User newUser) {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        EmailValidator emailValidator = new EmailValidator();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String error = "";

        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()) {
            if(emailValidator.test(newUser.getEmail())) {
                List<Role> roleList = Arrays.stream(Role.values()).toList();
                if(roleList.contains(newUser.getRole())) {
                    newUser.setPassword(passwordEncoder.encode(passwordGeneratorService.generate()));
                    userRepository.save(newUser);
                    return Response.OK;
                } else
                    return Response.INVALID_ROLE;
            } else
                return Response.INVALID_EMAIL_FORMAT;
        } else
            return Response.USER_ALREADY_EXISTS;
    }

    public Stream<User> readAll() {
        return userRepository.findAll().stream();
    }

    public Optional<User> readUserByEmail(String email) {
        EmailValidator emailValidator = new EmailValidator();
        if(emailValidator.test(email))
            return userRepository.findByEmail(email);
        else
            return Optional.empty();
    }

    public Response updateUserEmail(User user, String referenceEmail) {
        EmailValidator emailValidator = new EmailValidator();

        if(userRepository.findByEmail(referenceEmail).isPresent()) {
            if(emailValidator.test(user.getEmail())) {
                if(userRepository.findByEmail(user.getEmail()).isEmpty()) {
                    Optional<User> referenceUser = userRepository.findByEmail(referenceEmail);
                    referenceUser.get().setEmail(user.getEmail());
                    userRepository.save(referenceUser.get());
                    return Response.OK;
                } else
                    return Response.EMAIL_ALREADY_ASSIGNED;
            }
            return Response.INVALID_EMAIL_FORMAT;
        }
            return Response.REFERENCE_EMAIL_DOESNT_EXIST;
    }

    public Response updateUserPassword(User user, String referenceEmail, String currentEmail) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        EmailValidator emailValidator = new EmailValidator();

        if(emailValidator.test(referenceEmail)) {
            Optional<User> currentUser = userRepository.findByEmail(currentEmail);

            if(referenceEmail.equals(currentEmail) || currentUser.get().getRole().equals(Role.ROLE_ADMIN)) {
                if(userRepository.findByEmail(referenceEmail).isPresent()) {
                    Optional<User> referenceUser = userRepository.findByEmail(referenceEmail);
                    if(referenceUser.isPresent()) {
                        referenceUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepository.save(referenceUser.get());
                        return Response.OK;
                    }
                }
                return Response.REFERENCE_EMAIL_DOESNT_EXIST;
            }
            return Response.FORBIDDEN;
        }
        return Response.INVALID_EMAIL_FORMAT;
    }
}
