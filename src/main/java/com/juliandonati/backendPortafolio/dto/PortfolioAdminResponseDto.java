package com.juliandonati.backendPortafolio.dto;

import com.juliandonati.backendPortafolio.security.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
public class PortfolioAdminResponseDto {
    private Long id;
    private User owner;
    private Set<User> authorizedUsers;
}
