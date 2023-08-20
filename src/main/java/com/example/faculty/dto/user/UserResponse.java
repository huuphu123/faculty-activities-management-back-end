package com.example.faculty.dto.user;

import com.example.faculty.dto.authentication.RegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String msg;
    private RegisterRequest data;

}
