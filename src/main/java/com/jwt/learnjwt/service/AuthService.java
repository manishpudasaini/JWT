package com.jwt.learnjwt.service;

import com.jwt.learnjwt.dto.AuthRequest;
import com.jwt.learnjwt.dto.AuthResponse;
import com.jwt.learnjwt.dto.RegisterRequest;
import com.jwt.learnjwt.model.Role;
import com.jwt.learnjwt.model.User;
import com.jwt.learnjwt.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse registerUser(RegisterRequest registerRequest){
        var user = User
                .builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        var  user = userRepo.findByEmail(authRequest.getEmail());
        var jwtToken = jwtService.generateToken(user.get());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
