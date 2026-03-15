package com.juliandonati.backendPortafolio.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSummaryResponseDto {
    String username, displayName;
}
