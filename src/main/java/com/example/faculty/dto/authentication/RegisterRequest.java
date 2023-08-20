package com.example.faculty.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String birthday;
}
