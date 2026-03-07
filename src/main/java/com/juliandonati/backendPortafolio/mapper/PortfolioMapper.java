package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.*;
import com.juliandonati.backendPortafolio.dto.*;
import com.juliandonati.backendPortafolio.security.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PortfolioMapper {
    @Autowired
    private PresentationMapper presentationMapper;
    @Autowired
    private AboutMeMapper aboutMeMapper;
    @Autowired
    private DegreeMapper degreeMapper;
    @Autowired
    private JobMapper jobMapper;
    @Autowired
    private SkillMapper skillMapper;

    @Mapping(target = "owner", source = "portfolio.owner", qualifiedByName = "mapOwnerToString")

    @Mapping(target = "presentation", source = "portfolio.presentation", qualifiedByName = "mapPresentationToDto")
    @Mapping(target = "aboutMe", source = "portfolio.aboutMe", qualifiedByName = "mapAboutMeToDto")

    @Mapping(target = "degrees", source = "portfolio.degrees", qualifiedByName = "mapDegreesToDto")
    @Mapping(target = "experience", source = "portfolio.experience", qualifiedByName = "mapExperienceToDto")
    @Mapping(target = "skills", source = "portfolio.skills", qualifiedByName = "mapSkillsToDto")
    public abstract PortfolioResponseDto toPortfolioResponseDto(Portfolio portfolio);

    @Named("mapOwnerToString")
    protected String mapUserToString(User user) {return user.getUsername();}

    @Named("mapPresentationToDto")
    protected PresentationDto mapPresentationToDto(Presentation presentation) {return presentationMapper.toDto(presentation);}

    @Named("mapAboutMeToDto")
    protected AboutMeDto mapAboutMeToDto(AboutMe aboutMe){return aboutMeMapper.toDto(aboutMe);}

    @Named("mapDegreesToDto")
    protected Set<DegreeDto> mapDegreesToDto(Set<Degree> degrees) {return degrees != null ? degrees.stream().map(degreeMapper::toDto).collect(Collectors.toSet()) : null;}

    @Named("mapExperienceToDto")
    protected Set<JobDto> mapExperienceToDto(Set<Job> experience){return experience != null ? experience.stream().map(jobMapper::toDto).collect(Collectors.toSet()) : null;}

    @Named("mapSkillsToDto")
    protected Set<SkillDto> mapSkillsToDto(Set<Skill> skills){return skills != null ? skills.stream().map(skillMapper::toDto).collect(Collectors.toSet()) : null;}
}
