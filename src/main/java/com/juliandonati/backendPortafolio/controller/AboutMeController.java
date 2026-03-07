package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.AboutMe;
import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.AboutMeDto;
import com.juliandonati.backendPortafolio.exception.DuplicatedAttributeException;
import com.juliandonati.backendPortafolio.mapper.AboutMeMapper;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.AboutMeService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/about-me")

@RequiredArgsConstructor
public class AboutMeController {
    private final AboutMeService aboutMeService;
    private final AboutMeMapper aboutMeMapper;

    private final PortfolioService portfolioService;
    private final UserService userService;

    @GetMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<AboutMeDto> getAboutMeByOwner(@PathVariable String ownerUsername){
        AboutMeDto aboutMeDto = aboutMeMapper.toDto(
                portfolioService.findByOwner(userService.findByUsername(ownerUsername)).getAboutMe()
        );

        return ResponseEntity.ok(aboutMeDto);
    }

    @PostMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<AboutMeDto> createAboutMe(@PathVariable String ownerUsername,
                                                    @RequestBody @Valid AboutMeDto aboutMeDto){
        Portfolio portfolio = portfolioService.findByOwner(userService.findByUsername(ownerUsername));
        if(portfolio.getPresentation() == null){
            AboutMe aboutMe = aboutMeMapper.toEntity(aboutMeDto);

            aboutMe.setPortfolio(portfolio);
            portfolio.setAboutMe(aboutMe);

            portfolioService.save(portfolio);

            return new ResponseEntity<>(aboutMeMapper.toDto(aboutMe), HttpStatus.CREATED);
        }
        else
            throw new DuplicatedAttributeException("No puedes tener más de un apartado 'SOBRE MÍ'");
    }

    @PutMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<String> updateAboutMe(@PathVariable String ownerUsername,
                                                    @RequestBody @Valid AboutMeDto aboutMeDto){
        Long aboutMeId = portfolioService.findByOwner(userService.findByUsername(ownerUsername)).getAboutMe().getId();
        aboutMeService.update(aboutMeDto, aboutMeId);

        return ResponseEntity.ok("¡'SOBRE MÍ' actualizado con éxito!");
    }
}
