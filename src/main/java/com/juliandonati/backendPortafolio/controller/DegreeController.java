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
    private final UserService userService;

    private final DegreeMapper degreeMapper;



    @GetMapping("/list/{ownerUsername}")
    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    public ResponseEntity<List<DegreeDto>> getAllDegreesByOwner(@PathVariable String ownerUsername){
        List<DegreeDto> degreeDtos = portfolioService.findByOwner(userService.findByUsername(ownerUsername)).getDegrees()
                .stream().map(degreeMapper::toDto)
                .toList();

        return ResponseEntity.ok(degreeDtos);
    }

    @PostMapping("/{ownerUsername}")
    @PreAuthorize("authentication.name == #ownerUsername or hasRole('ADMIN')")
    public ResponseEntity<List<DegreeDto>> createDegree(@PathVariable String ownerUsername, @Valid @RequestBody DegreeDto degreeDto){
        Portfolio portfolio = portfolioService.findByOwner(userService.findByUsername(ownerUsername));
        Degree degree = degreeMapper.toEntity(degreeDto);

        portfolio.addDegree(degree);

        List<DegreeDto> updatedDegreeList = portfolioService.save(portfolio)
                .getDegrees()
                .stream().map(degreeMapper::toDto).toList();

        return new ResponseEntity<>(updatedDegreeList, HttpStatus.CREATED);
    }

    @PutMapping("/{degreeId}")
    @PreAuthorize("@degreeSecurityEvaluator.isOwner(#degreeId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<DegreeDto> updateDegree(@PathVariable Long degreeId, @Valid @RequestBody DegreeDto degreeDto){
        DegreeDto updatedDegree = degreeService.update(degreeDto, degreeId);

        return new ResponseEntity<>(updatedDegree, HttpStatus.OK);
    }

    @DeleteMapping("/{degreeId}")
    @PreAuthorize("@degreeSecurityEvaluator.isOwner(#degreeId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDegree(@PathVariable Long degreeId){
        degreeService.deleteById(degreeId);

        return ResponseEntity.noContent().build();
    }
}
