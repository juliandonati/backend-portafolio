package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.domain.Presentation;
import com.juliandonati.backendPortafolio.dto.PresentationDto;
import com.juliandonati.backendPortafolio.exception.DuplicatedAttributeException;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.mapper.PresentationMapper;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.jwt.JwtGenerator;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import com.juliandonati.backendPortafolio.service.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/presentation")

@RequiredArgsConstructor
public class PresentationController {
    private final PresentationMapper presentationMapper;

    private final PortfolioService portfolioService;
    private final UserService userService;

    private final JwtGenerator jwtGenerator;
    private final PresentationService presentationService;


    @GetMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<PresentationDto> getPresentationByOwner(@PathVariable String ownerUsername){
        User owner = userService.findByUsername(ownerUsername);
        Presentation presentation = portfolioService.findByOwner(owner).getPresentation();

        return ResponseEntity.ok(presentationMapper.toDto(presentation));
    }

    // authentication.name tiene como valor el subject de nuestro JWT
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    @PostMapping("/{ownerUsername}")
    public ResponseEntity<PresentationDto> createPresentation(@PathVariable String ownerUsername,
                                                            @RequestBody @Valid PresentationDto presentationDto){
        User owner = userService.findByUsername(ownerUsername);
        Portfolio portfolio = portfolioService.findByOwner(owner);
        if(portfolio.getPresentation() == null){
            Presentation presentation = presentationMapper.toEntity(presentationDto);
            presentation.setPortfolio(portfolio);
            portfolio.setPresentation(presentation);

            portfolioService.save(portfolio);
            return ResponseEntity.ok(presentationDto);
        }
        else
            throw new DuplicatedAttributeException("Solo puede haber una presentación por usuario.");
    }

    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    @PutMapping("/{ownerUsername}")
    public ResponseEntity<PresentationDto> updatePresentation(@PathVariable String ownerUsername,
                                                              @RequestBody @Valid PresentationDto presentationDto){
        User owner = userService.findByUsername(ownerUsername);
        Long presentationId = portfolioService.findByOwner(owner).getPresentation().getId();

        presentationService.update(presentationDto, presentationId);

        return new ResponseEntity<>(presentationDto, HttpStatus.CREATED);
    }
}
