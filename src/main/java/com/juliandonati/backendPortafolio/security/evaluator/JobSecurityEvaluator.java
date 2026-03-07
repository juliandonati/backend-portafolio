package com.juliandonati.backendPortafolio.security.evaluator;

import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobSecurityEvaluator {
    private final JobRepository jobRepository;

    public boolean isOwner(Long jobId, String username){
        return jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("No existe una experiencia laboral de id: " + jobId))
                .getPortfolio()
                .getOwner().getUsername().equals(username);
    }
}
