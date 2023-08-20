package com.example.faculty.error;

public class CustomException extends RuntimeException {
    public CustomException(String msg) {
        super(msg);
    }
}
