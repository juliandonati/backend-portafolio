package com.juliandonati.backendPortafolio.controller;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.dto.JobDto;
import com.juliandonati.backendPortafolio.mapper.JobMapper;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.JobService;
import com.juliandonati.backendPortafolio.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/experience")

@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final JobMapper jobMapper;

    private final PortfolioService portfolioService;
    private final UserService userService;

    @GetMapping("/list/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<List<JobDto>> getAllJobsByOwner(@PathVariable String ownerUsername){
        List<JobDto> jobDtos = jobService.findByOwnerUsername(ownerUsername);

        return ResponseEntity.ok(jobDtos);
    }


    @GetMapping("/{jobId}")
    @PreAuthorize("@jobSecurityEvaluator.isOwner(#jobId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long jobId){
        JobDto jobDto = jobService.findById(jobId);

        return ResponseEntity.ok(jobDto);
    }

    @PostMapping("/{ownerUsername}")
    @PreAuthorize("#ownerUsername == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<List<JobDto>> createJob(@PathVariable String ownerUsername, @Valid @RequestBody JobDto jobDto){
        Portfolio portfolio = portfolioService.findByOwnerUsername(ownerUsername);
        portfolio.addExperience(jobMapper.toEntity(jobDto));

        List<JobDto> updatedExperienceDto = portfolioService.save(portfolio)
                .getExperience()
                .stream().map(jobMapper::toDto).toList();

        return new ResponseEntity<>(updatedExperienceDto, HttpStatus.CREATED);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("@jobSecurityEvaluator.isOwner(#jobId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<JobDto> updatedJob(@PathVariable Long jobId, @Valid @RequestBody JobDto jobDto){
        JobDto updatedJob = jobService.update(jobDto, jobId);

        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("@jobSecurityEvaluator.isOwner(#jobId,authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId){
        jobService.deleteById(jobId);

        return ResponseEntity.noContent().build();
    }
}
