package com.juliandonati.backendPortafolio.service;

import com.juliandonati.backendPortafolio.dto.SkillDto;

import java.util.List;

public interface SkillService extends PortfolioComponentService<SkillDto>  {
    List<SkillDto> findSkillsByOwnerUsername(String username);
}
