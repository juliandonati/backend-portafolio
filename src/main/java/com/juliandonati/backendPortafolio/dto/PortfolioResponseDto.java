package com.juliandonati.backendPortafolio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class PortfolioResponseDto {
    private String owner;
    private PresentationDto presentation;
    private AboutMeDto aboutMe;
    private Set<DegreeDto> degrees;
    private Set<SkillDto> skills;
    private Set<JobDto> experience;
}
