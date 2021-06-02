package com.ahsoka.SALC.user_model.service;

import com.ahsoka.SALC.user_model.dtos.NewUserRequest;
import com.ahsoka.SALC.user_model.dtos.UserResponse;
import com.ahsoka.SALC.user_model.exceptions.UserNotFoundException;
import com.ahsoka.SALC.user_model.filter.JwtService;
import com.ahsoka.SALC.user_model.persistance.entity.Role;
import com.ahsoka.SALC.user_model.persistance.entity.User;
import com.ahsoka.SALC.user_model.persistance.repository.UserRepository;
import com.ahsoka.SALC.user_model.util.HandlerCSV;
import com.ahsoka.SALC.user_model.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordValidatorService passwordValidatorService;

    public UserService() {
        emailValidator = new EmailValidator();
        passwordEncoder = new BCryptPasswordEncoder();
        passwordValidatorService = new PasswordValidatorService();
    }

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
            return passwordEncoder.matches(password, user.get().getPassword()) ?
                    Optional.of(jwtService.createToken(user.get().getEmail(), user.get().getRole().toString())) :
                    Optional.empty();
        }
        return Optional.empty();
    }

    public Response createUser(User newUser) {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        Optional<User> user = userRepository.findByEmail(newUser.getEmail());

        if(user.isEmpty()) {
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

    public Stream<UserResponse> readAll() {
        List<UserResponse> userResponseStream = new ArrayList<>();
        userRepository.findAll().forEach(user -> userResponseStream.add(new UserResponse(user)));
        return userResponseStream.stream();
    }

    public Optional<UserResponse> readUserByEmail(String email) {
        return emailValidator.test(email) ?
                userRepository.findByEmail(email).stream().map(UserResponse::new).findAny()
                : Optional.empty();
    }

    public Response updateUserEmail(User user, String referenceEmail) {
        Optional<User> presentUser = userRepository.findByEmail(referenceEmail);

        if(presentUser.isPresent()) {
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
        if(emailValidator.test(referenceEmail) && passwordValidatorService.passwordValidate(user.getPassword())) {
            User currentUser = userRepository.findByEmail(currentEmail).get();
            if(referenceEmail.equals(currentEmail) || currentUser.getRole().equals(Role.ROLE_ADMIN)) {
                Optional<User> referenceUser = userRepository.findByEmail(referenceEmail);
                    if(referenceUser.isPresent()) {
                        referenceUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepository.save(referenceUser.get());
                        return Response.OK;
                    }
                return Response.REFERENCE_EMAIL_DOESNT_EXIST;
            }
            return Response.FORBIDDEN;
        }
        return Response.INVALID_EMAIL_FORMAT;
    }

    public Response updateUserRole(User user, String referenceEmail) {
        Optional<User> referenceUser = userRepository.findByEmail(referenceEmail);

        if(referenceUser.isPresent()) {
            referenceUser.get().setRole(user.getRole());
            userRepository.save(referenceUser.get());
            return Response.OK;
        }
        return Response.REFERENCE_EMAIL_DOESNT_EXIST;
    }

    public Response deleteUser(String email) {
        Optional<User> referenceUser = userRepository.findByEmail(email);

        if(referenceUser.isPresent()) {
            userRepository.deleteByEmail(referenceUser.get().getEmail());
            return Response.OK;
        }
        return Response.REFERENCE_EMAIL_DOESNT_EXIST;
    }

    public Response batchUserDelete(List<String> emails) {
        if(emails.isEmpty())
            return Response.EMPTY_REQUEST;

        Set<String> clearEmails = new HashSet<>(emails);

        userRepository.findAll().forEach(user -> {
            if(clearEmails.contains(user.getEmail())) {
                clearEmails.remove(user.getEmail());
                userRepository.deleteByEmail(user.getEmail());
            }
        });

        return Response.OK;
    }

    public Map<UserResponse, Response> batchUserCreate(MultipartFile file) {
        List<NewUserRequest> users;
        HandlerCSV handlerCSV = new HandlerCSV();
        Map<UserResponse, Response> invalidUsers = new HashMap<>();

        if(handlerCSV.hasCSVFormat(file)) {
            try {
                users = handlerCSV.csvToUsers(file.getInputStream());
                for(NewUserRequest userRequest : users) {
                    Response response = createUser(userRequest.toUser());
                    if(!response.equals(Response.OK))
                        invalidUsers.put(new UserResponse(userRequest.toUser()), response);
                }
                return invalidUsers;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

}
