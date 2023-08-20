package com.example.faculty.service;

import com.example.faculty.dto.Response;
import com.example.faculty.dto.authentication.RegisterRequest;
import com.example.faculty.dto.user.UserResponse;
import com.example.faculty.entity.User;
import com.example.faculty.error.CustomException;
import com.example.faculty.repository.UserRepository;
import com.example.faculty.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserResponse getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            RegisterRequest loginUserResponse =  RegisterRequest
                                                        .builder()
                                                        .email(user.get().getEmail())
                                                        .password(user.get().getPassword())
                                                        .phone(user.get().getPhone())
                                                        .birthday(user.get().getBirthday())
                                                        .id(user.get().getId())
                                                        .fullName(user.get().getFullName())
                                                        .build();
            return new UserResponse("Success", loginUserResponse);
        } else {
            throw new CustomException("User not found!");
        }
    }

    public Response updateUserInfo(RegisterRequest request, String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).get();
        if ((request.getEmail() != null && !request.getEmail().equals(email)) || (request.getId() != null && !request.getId().equals(user.getId()))) {
            throw new CustomException("Can not change email or student identity!");
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setFullName(request.getFullName());
        user.setBirthday(request.getBirthday());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return Response
                .builder()
                .msg("Success updated user " + user.getEmail())
                .build();
    }

    public Response changePassword(String password, String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).get();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return Response
                .builder()
                .msg("Success updated password of user " + user.getEmail())
                .build();

    }
}
