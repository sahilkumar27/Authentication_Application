package com.substring.auth.auth_app_backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class GlobalExceptionHandler {

    //resource not found exception handler :: method
    public ResponseEntity<ErrorResponse>handleResourceNotFoundException(ResourceNotFoundException exception){
        return null;
    }
}
