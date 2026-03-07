package com.juliandonati.backendPortafolio.security.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleResponseDto {
    private String name;
    private String description;
    private Set<String> users;
}
