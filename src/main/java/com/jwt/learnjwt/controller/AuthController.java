package com.jwt.learnjwt.controller;

import com.jwt.learnjwt.dto.AuthRequest;
import com.jwt.learnjwt.dto.AuthResponse;
import com.jwt.learnjwt.dto.RegisterRequest;
import com.jwt.learnjwt.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/everyone")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> addUser(@RequestBody RegisterRequest register){
        return new ResponseEntity<>(authService.registerUser(register),HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest){
        return new ResponseEntity<>(authService.authenticateUser(authRequest),HttpStatus.OK);
    }

}
