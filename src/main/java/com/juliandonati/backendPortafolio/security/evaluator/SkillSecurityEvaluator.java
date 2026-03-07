package com.juliandonati.backendPortafolio.security.evaluator;

import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillSecurityEvaluator {
    private final SkillRepository skillRepository;

    public boolean isOwner(Long skillId, String username){
        return skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("No se encontró una habilidad con la id: " + skillId))
                .getPortfolio()
                .getOwner()
                .getUsername().equals(username);
    }
}
