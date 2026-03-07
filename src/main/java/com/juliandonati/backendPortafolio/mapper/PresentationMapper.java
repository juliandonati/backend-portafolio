package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.Presentation;
import com.juliandonati.backendPortafolio.dto.PresentationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PresentationMapper {
    PresentationDto toDto(Presentation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "portfolio", ignore = true)
    Presentation toEntity(PresentationDto dto);

    Presentation updateEntity(PresentationDto dto, @MappingTarget Presentation entity);
}
