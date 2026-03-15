package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.PortfolioResponseDto;
import com.juliandonati.backendPortafolio.exception.DuplicatedAttributeException;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.mapper.PortfolioMapper;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.jwt.JwtGenerator;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")

@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final PortfolioMapper portfolioMapper;

    private final UserService userService;

    // TODOS, incluso quienes no están logueados pueden acceder a éste metodo
    @GetMapping("/{ownerUsername}")
    public ResponseEntity<PortfolioResponseDto> getPortfolioByOwner(@PathVariable String ownerUsername){
        PortfolioResponseDto portfolioResponseDto = portfolioMapper.toPortfolioResponseDto(portfolioService.findByOwnerUsername(ownerUsername));

        return ResponseEntity.ok(portfolioResponseDto);
    }

    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    @PostMapping("/{ownerUsername}")
    public ResponseEntity<PortfolioResponseDto> createPortfolio(@PathVariable String ownerUsername){
        if(!portfolioService.existsByOwnerUsername(ownerUsername)){
                User user = userService.findByUsername(ownerUsername);
                Portfolio newPortfolio = new Portfolio();
                newPortfolio.setOwner(user);
                user.setOwnedPortfolio(newPortfolio);

                userService.save(user); // Efecto cascáda. portfolioRepository automáticamente guardará a newPortfolio y se actualizará la JoinTable.

                return ResponseEntity.ok(portfolioMapper.toPortfolioResponseDto(newPortfolio));
        }
        else
            throw new DuplicatedAttributeException("No puede haber más de un portafolio por usuario.");
    }

    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    @DeleteMapping("/{ownerUsername}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String ownerUsername){
        User user = userService.findByUsername(ownerUsername);

        if(user.getOwnedPortfolio() == null)
            throw new ResourceNotFoundException("El usuario no tiene un portfolio");

        user.setOwnedPortfolio(null); // orphanRemoval elimina automaticamente el portfolio
        userService.save(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ownerUsername}/exists")
    public ResponseEntity<Boolean> existsPortfolio(@PathVariable String ownerUsername){
        try{
            boolean exists = portfolioService.existsByOwnerUsername(ownerUsername);
            return ResponseEntity.ok(exists);
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
