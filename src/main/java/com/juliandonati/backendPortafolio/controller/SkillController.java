package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.SkillDto;
import com.juliandonati.backendPortafolio.mapper.SkillMapper;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import com.juliandonati.backendPortafolio.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")

@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;
    private final SkillMapper skillMapper;

    private final UserService userService;

    private final PortfolioService portfolioService;

    @GetMapping("/list/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getAllSkillsByOwner(@PathVariable String ownerUsername){
        List<SkillDto> skillDtos = portfolioService.findByOwner(userService.findByUsername(ownerUsername))
                .getSkills()
                .stream().map(skillMapper::toDto)
                .toList();

        return ResponseEntity.ok(skillDtos);
    }

    @PostMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> createSkill(@PathVariable String ownerUsername, @Valid @RequestBody SkillDto skillDto){
        Portfolio portfolio = portfolioService.findByOwner(userService.findByUsername(ownerUsername));

        portfolio.addSkill(skillMapper.toEntity(skillDto));
        List<SkillDto> updatedSkillDtos = portfolioService.save(portfolio)
                .getSkills()
                .stream().map(skillMapper::toDto)
                .toList();

        return new ResponseEntity<>(updatedSkillDtos, HttpStatus.CREATED);
    }


    @GetMapping("/{skillId}")
    @PreAuthorize("@skillSecurityEvaluator.isOwner(#skillId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<SkillDto> getSkill(@PathVariable Long skillId){
        SkillDto skillDto = skillService.findById(skillId);

        return ResponseEntity.ok(skillDto);
    }

    @PutMapping("/{skillId}")
    @PreAuthorize("@skillSecurityEvaluator.isOwner(#skillId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Long skillId, @Valid @RequestBody SkillDto skillDto){
        SkillDto updatedSkillDto = skillService.update(skillDto, skillId);

        return ResponseEntity.ok(updatedSkillDto);
    }

    @DeleteMapping("/{skillId}")
    @PreAuthorize("@skillSecurityEvaluator.isOwner(#skillId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long skillId){
        skillService.deleteById(skillId);

        return ResponseEntity.noContent().build();
    }
}
