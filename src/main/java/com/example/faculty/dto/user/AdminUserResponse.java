package com.example.faculty.dto.user;

import com.example.faculty.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserResponse {
    String msg;
    List<User> data;
}
