package com.juliandonati.backendPortafolio.service;

import com.juliandonati.backendPortafolio.dto.DegreeDto;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;

import java.util.List;

public interface DegreeService extends PortfolioComponentService<DegreeDto> {
    public List<DegreeDto> findByOwnerUsername(String username) throws ResourceNotFoundException;
    public String findImgUrlByDegreeId(Long id) throws ResourceNotFoundException;
    public List<String> findImgUrlsByOwnerUsername(String ownerUsername) throws ResourceNotFoundException;
    public String findOwnerUsernameByDegreeId(Long id) throws ResourceNotFoundException;
}
