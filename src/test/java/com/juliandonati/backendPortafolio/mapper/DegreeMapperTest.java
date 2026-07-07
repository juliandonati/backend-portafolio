package com.juliandonati.backendPortafolio.mapper;

import com.juliandonati.backendPortafolio.domain.Degree;
import com.juliandonati.backendPortafolio.dto.DegreeDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DegreeMapperTest {
    DegreeMapper degreeMapper = Mappers.getMapper(DegreeMapper.class);

    @Test
    void testMapDegreeEntityToDegreeDtoSuccessfully() {
        Long mockId = 1L;
        String mockName = "Ingeniería en Sistemas", mockDesc = "En la Universidad Pichango Tecno";
        LocalDate mockStartDate = LocalDate.now();
        String mockImgUrl = "http://www.degreeimg.com";
        Degree mockDegree = new Degree(mockId,mockName,mockDesc,mockStartDate,null,mockImgUrl,null);

        DegreeDto result = degreeMapper.toDto(mockDegree);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockStartDate,result.getStartDate()),
                ()->assertNull(result.getEndDate()),
                ()->assertEquals(mockImgUrl,result.getImgUrl())
                );
    }

    @Test
    void testMapDegreeDtoToDegreeEntitySuccessfully() {
        Long mockId = 1L;
        String mockName = "Ingeniería en Sistemas", mockDesc = "En la Universidad Pichango Tecno";
        LocalDate mockStartDate = LocalDate.now();
        String mockImgUrl = "http://www.degreeimg.com";
        DegreeDto mockDegreeDto = new DegreeDto(mockId,mockName,mockDesc,mockStartDate,null,mockImgUrl);

        Degree result = degreeMapper.toEntity(mockDegreeDto);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockName,result.getName()),
                ()->assertEquals(mockDesc,result.getDescription()),
                ()->assertEquals(mockStartDate,result.getStartDate()),
                ()->assertNull(result.getEndDate()),
                ()->assertEquals(mockImgUrl,result.getImgUrl())
        );
    }

    @Test
    void testUpdateDegreeEntitySuccessfully() {
        Long mockId = 1L;
        String mockOldName = "Ingeniería en Sistemas", mockOldDesc = "En la Universidad Pichango Tecno";
        LocalDate mockOldStartDate = LocalDate.now();
        String mockOldImgUrl = "http://www.degreeimg.com";
        Degree mockOldDegree = new Degree(mockId, mockOldName, mockOldDesc, mockOldStartDate, null, mockOldImgUrl, null);

        String mockNewName = "Ingeniería en Sistemas de Información", mockNewDesc = "En la universidad 'Pichango Tecno'";
        LocalDate mockNewStartDate = LocalDate.of(2010,11,10), mockNewEndDate = LocalDate.of(2016,12,21);
        String mockNewImgUrl = "http://newdegreeimg.org";
        // id = null, para verificar que no se sobreescriba sobre la id vieja, que nunca se va a actualizar.
        DegreeDto mockNewDegreeDto = new DegreeDto(null,mockNewName,mockNewDesc,mockNewStartDate,mockNewEndDate,mockNewImgUrl);

        Degree result = degreeMapper.updateEntity(mockNewDegreeDto,mockOldDegree);

        assertAll("Validando los campos tras el mapeo...",
                ()->assertNotNull(result),
                ()->assertEquals(mockId,result.getId()),
                ()->assertEquals(mockNewName,result.getName()),
                ()->assertEquals(mockNewDesc,result.getDescription()),
                ()->assertEquals(mockNewStartDate,result.getStartDate()),
                ()->assertEquals(mockNewEndDate,result.getEndDate()),
                ()->assertEquals(mockNewImgUrl,result.getImgUrl())
        );
    }
}