package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.domain.Presentation;
import com.juliandonati.backendPortafolio.dto.PresentationDto;
import com.juliandonati.backendPortafolio.exception.DuplicatedAttributeException;
import com.juliandonati.backendPortafolio.mapper.PresentationMapper;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import com.juliandonati.backendPortafolio.service.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final PresentationService presentationService;

    private final Logger logger = LoggerFactory.getLogger(PresentationController.class);


    @GetMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<PresentationDto> getPresentationByOwner(@PathVariable String ownerUsername){
        logger.debug("Recuperando la presentación del portafolio de: "+ownerUsername);
        PresentationDto presentationDto = presentationService.findByOwnerUsername(ownerUsername);
        logger.info("¡Devolviendo la presentación del portafolio de: "+ownerUsername+'!');

        return ResponseEntity.ok(presentationDto);
    }

    // authentication.name tiene como valor el subject de nuestro JWT
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    @PostMapping("/{ownerUsername}")
    public ResponseEntity<PresentationDto> createPresentation(@PathVariable String ownerUsername,
                                                            @RequestBody @Valid PresentationDto presentationDto){

        logger.debug("Verificando si el portafolio de "+ownerUsername+" existe...");
        if(!presentationService.existsByOwnerUsername(ownerUsername)){
            logger.debug("El portafolio de "+ownerUsername+" existe, buscando portafolio...");
            Portfolio portfolio = portfolioService.findByOwnerUsername(ownerUsername);
            Presentation presentation = presentationMapper.toEntity(presentationDto);
            presentation.setPortfolio(portfolio);
            portfolio.setPresentation(presentation);

            logger.debug("Guardando la nueva presentación en el portafolio de "+ownerUsername);
            portfolioService.save(portfolio);
            logger.info("¡Presentación del portafolio de "+ownerUsername+" creada con éxito!");
            return new ResponseEntity<>(presentationDto, HttpStatus.CREATED);
        }
        else
            throw new DuplicatedAttributeException("Solo puede haber una presentación por usuario.");
    }

    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    @PutMapping("/{ownerUsername}")
    public ResponseEntity<PresentationDto> updatePresentation(@PathVariable String ownerUsername,
                                                              @RequestBody @Valid PresentationDto presentationDto){
        logger.debug("Buscando presentación del portafolio de " + ownerUsername);
        Long presentationId = presentationService.findByOwnerUsername(ownerUsername).getId();
        logger.debug("Se encontró la presentación del portafolio de "+ownerUsername+", actualizando...");
        presentationService.update(presentationDto, presentationId);
        logger.info("¡Presentación del portafolio de "+ownerUsername+" actualizada con éxito!");

        return new ResponseEntity<>(presentationDto, HttpStatus.OK);
    }

    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    @DeleteMapping("/{ownerUsername}")
    public ResponseEntity<Void> deletePresentation(@PathVariable String ownerUsername){
        logger.debug("Eliminando presentación de "+ ownerUsername);
        presentationService.deleteByOwnerUsername(ownerUsername);
        logger.info("¡Presentación del portafolio de "+ownerUsername+" eliminado con éxito!");

        return ResponseEntity.noContent().build();
    }
}
