package com.example.faculty.service;

import com.example.faculty.dto.authentication.LoginRequest;
import com.example.faculty.dto.authentication.LoginRespone;
import com.example.faculty.dto.authentication.RegisterRequest;
import com.example.faculty.dto.Response;
import com.example.faculty.entity.User;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.UserRepository;
import com.example.faculty.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Response register(RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());
        if (user.isPresent()) {
            throw new CustomException("User has already existed!");
        }
        User newUser = User
                .builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .id(registerRequest.getId())
                .fullName(registerRequest.getFullName())
                .birthday(registerRequest.getBirthday())
                .phone(registerRequest.getPhone())
                .role(0)
                .status("pending")
                .build();
        userRepository.save(newUser);
        return Response
                .builder()
                .msg("Success added user " + newUser.getEmail() + "!")
                .build();
    }

    public LoginRespone login(LoginRequest loginRequest) {
        try {
            System.out.println(loginRequest.getEmail() + " " + loginRequest.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            User user = userRepository.findByEmail(loginRequest.getEmail()).get();
            RegisterRequest registerRequest = RegisterRequest
                                                .builder()
                                                .email(user.getEmail())
                                                .password(user.getPassword())
                                                .phone(user.getPhone())
                                                .birthday(user.getBirthday())
                                                .id(user.getId())
                                                .fullName(user.getFullName())
                                                .build();
            String token = jwtService.generateToken(user);

            return LoginRespone
                    .builder()
                    .msg(registerRequest)
                    .token(token)
                    .build();
        } catch(BadCredentialsException e) {
            throw new CustomException("Wrong email or password!");
        } catch (DisabledException e) {
            throw new CustomException("User is not accepted!");
        }

    }

}
