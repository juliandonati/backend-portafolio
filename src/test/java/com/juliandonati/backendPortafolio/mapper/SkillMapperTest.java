package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.Skill;
import com.juliandonati.backendPortafolio.dto.SkillDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class SkillMapperTest {
    SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);

    @Test
    void testMapSkillEntityToSkillDtoSuccessfully() {
        Long mockId = 14L;
        String mockName = "Spring Boot", mockDesc = "I know how to build enterprise apps with Spring Boot.",
                mockLevel = "Intermediate", mockImgUrl = "https://skillimage.com/springboot", mockCategory= "Back-end Coding";
        Skill mockSkill = new Skill(mockId,mockName,mockDesc,mockLevel,mockImgUrl,mockCategory,null);

        SkillDto result = skillMapper.toDto(mockSkill);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockLevel,result.getLevel()),
                ()->assertEquals(mockImgUrl,result.getImgUrl()),
                ()->assertEquals(mockCategory,result.getCategory())
        );
    }

    @Test
    void testMapSkillDtoToSkillEntitySuccessfully() {
        Long mockId = 14L;
        String mockName = "Spring Boot", mockDesc = "I know how to build enterprise apps with Spring Boot.",
                mockLevel = "Intermediate", mockImgUrl = "https://skillimage.com/springboot", mockCategory= "Back-end Coding";
        SkillDto mockSkillDto = new SkillDto(mockId,mockName,mockDesc,mockLevel,mockImgUrl,mockCategory);

        Skill result = skillMapper.toEntity(mockSkillDto);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockLevel,result.getLevel()),
                ()->assertEquals(mockImgUrl,result.getImgUrl()),
                ()->assertEquals(mockCategory,result.getCategory())
        );
    }

    @Test
    void testUpdateSkillEntitySuccessfully() {
        Long mockId = 14L;
        String mockOldName = "Spring Boot", mockOldDesc = "I know how to build enterprise apps with Spring Boot.",
                mockOldLevel = "Intermediate", mockOldImgUrl = "https://skillimage.com/springboot", mockOldCategory= "Back-end Coding";
        Skill mockOldSkill = new Skill(mockId,mockOldName,mockOldDesc,mockOldLevel,mockOldImgUrl,mockOldCategory,null);

        String mockNewName = "GoLang", mockNewDesc = "I know hot to build enterprise apps with GoLang", mockNewLevel = "Basic",
                mockNewImgUrl = "https://skillimage.com/golang", mockNewCategory = "Back-End Epic Coding";
        SkillDto mockNewSkillDto = new SkillDto(null,mockNewName,mockNewDesc,mockNewLevel,mockNewImgUrl,mockNewCategory);

        Skill result = skillMapper.updateEntity(mockNewSkillDto,mockOldSkill);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockNewName,result.getName()),
                ()->assertEquals(mockNewDesc,result.getDescription()),
                ()->assertEquals(mockNewLevel,result.getLevel()),
                ()->assertEquals(mockNewImgUrl,result.getImgUrl()),
                ()->assertEquals(mockNewCategory,result.getCategory())
        );
    }
}