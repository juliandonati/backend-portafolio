package com.juliandonati.backendPortafolio.service;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.exception.ResourceNotFoundException;
import com.juliandonati.backendPortafolio.repository.PortfolioRepository;
import com.juliandonati.backendPortafolio.security.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService{
    private final PortfolioRepository portfolioRepository;


    @Override
    @Transactional(readOnly = true)
    public Portfolio findById(long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró un portfolio con la id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Portfolio findByOwner(User owner) {
        return portfolioRepository.findByOwner(owner).orElseThrow(() -> new ResourceNotFoundException("No se encontró el portfolio de " + owner.getUsername()));
    }

    @Override
    @Transactional
    public Portfolio save(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    @Transactional
    public Portfolio update(Portfolio portfolio, Long id) {
        Portfolio portfolioToUpdate = portfolioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró un portfolio con la id: " + id));

        portfolioToUpdate.setOwner(portfolio.getOwner());
        portfolioToUpdate.setDegrees(portfolio.getDegrees());
        portfolioToUpdate.setExperience(portfolio.getExperience());
        portfolioToUpdate.setSkills(portfolio.getSkills());
        portfolioToUpdate.setPresentation(portfolio.getPresentation());
        portfolioToUpdate.setAboutMe(portfolio.getAboutMe());
        portfolioToUpdate.setAuthorizedUsers(portfolio.getAuthorizedUsers());

        return portfolioRepository.save(portfolioToUpdate);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if(portfolioRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("No se encontró un portfolio con la id: " + id);
        portfolioRepository.deleteById(id);
    }
}
