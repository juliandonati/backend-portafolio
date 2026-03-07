package com.juliandonati.backendPortafolio.security.controller;

import com.juliandonati.backendPortafolio.security.dto.JwtResponseDto;
import com.juliandonati.backendPortafolio.security.dto.LoginRequestDto;
import com.juliandonati.backendPortafolio.security.dto.RegisterRequestDto;
import com.juliandonati.backendPortafolio.security.jwt.JwtGenerator;
import com.juliandonati.backendPortafolio.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        userService.register(registerRequestDto);

        return new ResponseEntity<>("¡El usuario ha sido creado con éxito!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateUser(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsernameOrEmail(), // Por ahora funciona SOLO con username
                        loginRequestDto.getUnencryptedPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
