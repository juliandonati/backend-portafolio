package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.Skill;
import com.juliandonati.backendPortafolio.dto.SkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillDto toDto(Skill skill);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "portfolio", ignore = true)
    Skill toEntity(SkillDto dto);

    Skill updateEntity(SkillDto dto, @MappingTarget Skill entity);
}
