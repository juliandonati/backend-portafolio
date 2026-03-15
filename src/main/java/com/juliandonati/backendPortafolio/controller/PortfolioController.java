package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.PortfolioResponseDto;
import com.juliandonati.backendPortafolio.exception.DuplicatedAttributeException;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.mapper.PortfolioMapper;
import com.juliandonati.backendPortafolio.security.domain.User;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    // TODOS, incluso quienes no están logueados pueden acceder a éste metodo
    @GetMapping("/{ownerUsername}")
    public ResponseEntity<PortfolioResponseDto> getPortfolioByOwner(@PathVariable String ownerUsername){
        logger.debug("Recuperando el portafolio de: "+ownerUsername);
        PortfolioResponseDto portfolioResponseDto = portfolioMapper.toPortfolioResponseDto(portfolioService.findByOwnerUsername(ownerUsername));
        logger.info("¡Portafolio de "+ownerUsername+" recuperado con éxito!");

        return ResponseEntity.ok(portfolioResponseDto);
    }

    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    @PostMapping("/{ownerUsername}")
    public ResponseEntity<PortfolioResponseDto> createPortfolio(@PathVariable String ownerUsername){
        logger.debug("Verificando si existe el portafolio de "+ownerUsername);
        if(!portfolioService.existsByOwnerUsername(ownerUsername)){
                logger.debug("El portafolio de "+ownerUsername+" no existe, buscando usuario...");
                User user = userService.findByUsername(ownerUsername);
                logger.debug("El usuario de "+ownerUsername+" existe, creando portafolio...");
                Portfolio newPortfolio = new Portfolio();
                newPortfolio.setOwner(user);
                user.setOwnedPortfolio(newPortfolio);

                userService.save(user); // Efecto cascáda. portfolioRepository automáticamente guardará a newPortfolio y se actualizará la JoinTable.
                logger.info("¡Portafolio de "+ownerUsername+" creado con éxito!");
                return ResponseEntity.ok(portfolioMapper.toPortfolioResponseDto(newPortfolio));
        }
        else
            throw new DuplicatedAttributeException("No puede haber más de un portafolio por usuario.");
    }

    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    @DeleteMapping("/{ownerUsername}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String ownerUsername){
        logger.debug("Verificando si existe el usuario de "+ownerUsername);
        User user = userService.findByUsername(ownerUsername);

        logger.debug("Verificando si "+ownerUsername+" tiene un portafolio");
        if(user.getOwnedPortfolio() == null)
            throw new ResourceNotFoundException("El usuario no tiene un portfolio");

        logger.debug(ownerUsername+" tiene un portafolio, eliminando...");
        user.setOwnedPortfolio(null); // orphanRemoval elimina automaticamente el portfolio
        userService.save(user);

        logger.debug("Se guardo el usuario ahora sin portafolio");
        logger.info("¡Portafolio de "+ownerUsername+" eliminado con éxito!");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ownerUsername}/exists")
    public ResponseEntity<Boolean> existsPortfolio(@PathVariable String ownerUsername){
        try{
            logger.debug("Verificando si existe un portafolio de dueño: "+ownerUsername);
            boolean exists = portfolioService.existsByOwnerUsername(ownerUsername);
            logger.info(exists ? "¡Existe!": "No existe." );
            return ResponseEntity.ok(exists);
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
