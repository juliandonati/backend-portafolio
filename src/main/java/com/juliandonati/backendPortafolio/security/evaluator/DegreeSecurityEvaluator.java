package com.juliandonati.backendPortafolio.security.evaluator;

import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.repository.DegreeRepository;
import com.juliandonati.backendPortafolio.security.service.UserService;
import com.juliandonati.backendPortafolio.service.DegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DegreeSecurityEvaluator {
    private final DegreeRepository degreeRepository;

    public boolean isOwner(Long degreeId, String username){
        return degreeRepository.isDegreeByIdOwnedByUsername(degreeId, username);
    }
}
