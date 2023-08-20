package com.example.faculty;

import com.example.faculty.error.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler
    public ResponseEntity<CustomException> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request){
        return ResponseEntity.status(403).body(new CustomException("Invalid token"));
    }
}
