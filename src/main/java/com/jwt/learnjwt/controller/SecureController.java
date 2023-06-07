package com.jwt.learnjwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('USER')")
public class SecureController {
    @GetMapping("/user")
    public ResponseEntity<String> greeting(){
        return new ResponseEntity<>("Hello from user", HttpStatus.OK);
    }
}
