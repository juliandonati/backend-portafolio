package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.Presentation;
import com.juliandonati.backendPortafolio.dto.PresentationDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PresentationMapperTest {
    PresentationMapper presentationMapper = Mappers.getMapper(PresentationMapper.class);

    @Test
    void testMapPresentationEntityToPresentationDtoSuccessfully() {
        Long mockId = 2L;
        String mockName = "Carlos Larralde", mockTitle = "Data Analyst", mockDesc = "Love to code and do sum stuff like that!",
        mockImgUrl = "https://imagendeprueba.net", mockPhoneNumber="54332123543", mockEmail="carlos.larralde2002@yahoo.com.ar";
        Presentation mockPresentation = new Presentation(mockId,mockName,mockTitle,mockDesc,mockImgUrl,mockEmail,mockPhoneNumber,null);

        PresentationDto result = presentationMapper.toDto(mockPresentation);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockTitle,result.getTitle()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockImgUrl,result.getImgUrl()),
                ()->assertEquals(mockEmail,result.getEmail()),
                ()->assertEquals(mockPhoneNumber,result.getPhoneNumber())
        );
    }

    @Test
    void testMapPresentationDtoToPresentationEntitySuccessfully() {
        Long mockId = 2L;
        String mockName = "Carlos Larralde", mockTitle = "Data Analyst", mockDesc = "Love to code and do sum stuff like that!",
                mockImgUrl = "https://imagendeprueba.net", mockPhoneNumber="54332123543", mockEmail="carlos.larralde2002@yahoo.com.ar";
        PresentationDto mockPresentationDto = new PresentationDto(mockId,mockName,mockTitle,mockDesc,mockImgUrl,mockEmail,mockPhoneNumber);

        Presentation result = presentationMapper.toEntity(mockPresentationDto);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockTitle,result.getTitle()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockImgUrl,result.getImgUrl()),
                ()->assertEquals(mockEmail,result.getEmail()),
                ()->assertEquals(mockPhoneNumber,result.getPhoneNumber())
        );
    }

    @Test
    void testUpdatePresentationEntitySuccessfully() {
        Long mockId = 2L;
        String mockOldName = "Carlos Larralde", mockOldTitle = "Data Analyst", mockOldDesc = "Love to code and do sum stuff like that!",
                mockOldImgUrl = "https://imagendeprueba.net", mockOldPhoneNumber="54332123543", mockOldEmail="carlos.larralde2002@yahoo.com.ar";
        Presentation mockOldPresentation = new Presentation(mockId,mockOldName,mockOldTitle,mockOldDesc,mockOldImgUrl,mockOldEmail,mockOldPhoneNumber,null);

        String mockNewName = "Carlos Agustín Larralde", mockNewTitle = "Vibe Coder", mockNewDesc = "Love vibecoding new apps, faster = better!",
        mockNewImgUrl = "https://newtestingimage.org", mockNewPhoneNumber="521234567890", mockNewEmail="carloslarralde@gmail.com";
        PresentationDto mockNewPresentationDto = new PresentationDto(null,mockNewName,mockNewTitle,mockNewDesc,mockNewImgUrl,mockNewEmail,mockNewPhoneNumber);

        Presentation result = presentationMapper.updateEntity(mockNewPresentationDto,mockOldPresentation);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockNewName,result.getName()),
                ()->assertEquals(mockNewTitle,result.getTitle()),
                ()->assertEquals(mockNewDesc,result.getDescription()),
                ()->assertEquals(mockNewImgUrl,result.getImgUrl()),
                ()->assertEquals(mockNewEmail,result.getEmail()),
                ()->assertEquals(mockNewPhoneNumber,result.getPhoneNumber())
        );
    }
}