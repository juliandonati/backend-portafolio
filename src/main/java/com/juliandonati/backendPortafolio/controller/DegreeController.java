package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Degree;
import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.DegreeDto;
import com.juliandonati.backendPortafolio.mapper.DegreeMapper;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.DegreeService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/degrees")

@RequiredArgsConstructor
public class DegreeController {
    private final DegreeService degreeService;
    private final PortfolioService portfolioService;

    private final DegreeMapper degreeMapper;

    private final Logger logger = LoggerFactory.getLogger(DegreeController.class);



    @GetMapping("/list/{ownerUsername}")
    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    public ResponseEntity<List<DegreeDto>> getAllDegreesByOwner(@PathVariable String ownerUsername){
        logger.debug("Recuperando los títulos académicos de "+ownerUsername);
        List<DegreeDto> degreeDtos = degreeService.findByOwnerUsername(ownerUsername);
        logger.info("¡Devolviendo los títulos académicos de "+ownerUsername+'!');
        return ResponseEntity.ok(degreeDtos);
    }

    @PostMapping("/{ownerUsername}")
    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    public ResponseEntity<List<DegreeDto>> createDegree(@PathVariable String ownerUsername, @Valid @RequestBody DegreeDto degreeDto){
        logger.debug("Buscando portafolio de "+ownerUsername);
        Portfolio portfolio = portfolioService.findByOwnerUsername(ownerUsername);
        Degree degree = degreeMapper.toEntity(degreeDto);

        portfolio.addDegree(degree);

        logger.debug("Guardando el nuevo título académico en el portafolio de "+ownerUsername);
        List<DegreeDto> updatedDegreeList = portfolioService.save(portfolio)
                .getDegrees()
                .stream().map(degreeMapper::toDto).toList();

        logger.info("¡El nuevo título académico de "+ownerUsername+" ha sido creado correctamente!");
        return new ResponseEntity<>(updatedDegreeList, HttpStatus.CREATED);
    }

    @PutMapping("/{degreeId}")
    @PreAuthorize("@degreeSecurityEvaluator.isOwner(#degreeId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<DegreeDto> updateDegree(@PathVariable Long degreeId, @Valid @RequestBody DegreeDto degreeDto){
        logger.debug("Actualizando título académico de id: "+degreeId);
        DegreeDto updatedDegree = degreeService.update(degreeDto, degreeId);
        logger.info("¡Título académico de id: "+degreeId+" actualizado con éxito!");

        return new ResponseEntity<>(updatedDegree, HttpStatus.OK);
    }

    @DeleteMapping("/{degreeId}")
    @PreAuthorize("@degreeSecurityEvaluator.isOwner(#degreeId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDegree(@PathVariable Long degreeId){
        logger.debug("Eliminando título académico de id: "+degreeId);
        degreeService.deleteById(degreeId);
        logger.info("¡Título académico de id: "+degreeId+" eliminado con éxito!");

        return ResponseEntity.noContent().build();
    }
}
