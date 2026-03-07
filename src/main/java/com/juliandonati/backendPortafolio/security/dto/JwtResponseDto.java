package com.juliandonati.backendPortafolio.security.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    private final String tokenType = "Bearer ";
    private String accessToken;

    public JwtResponseDto(String aToken) { accessToken = aToken; }
}
