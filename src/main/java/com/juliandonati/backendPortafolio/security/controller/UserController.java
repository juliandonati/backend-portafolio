package com.juliandonati.backendPortafolio.security.controller;

import com.juliandonati.backendPortafolio.security.dto.UserSummaryResponseDto;
import com.juliandonati.backendPortafolio.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserSummaryResponseDto>> getAll(@RequestParam(required = false) String name,
                                               @PageableDefault(page=0, size = 10, sort="username") Pageable pageable) {
        Page<UserSummaryResponseDto> userSummaryResponseDtoPage = userService.findAll(name, pageable);
        return ResponseEntity.ok(userSummaryResponseDtoPage);
    }
}
