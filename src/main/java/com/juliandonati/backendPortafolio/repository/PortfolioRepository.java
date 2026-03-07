package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Portfolio;
import com.juliandonati.backendPortafolio.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    @Query("SELECT p FROM Portfolio p JOIN FETCH p.owner LEFT JOIN FETCH p.authorizedUsers " +
            "LEFT JOIN FETCH p.presentation " +
            "LEFT JOIN FETCH p.aboutMe " +
            "LEFT JOIN FETCH p.degrees " +
            "LEFT JOIN FETCH p.skills " +
            "LEFT JOIN FETCH p.experience")
    Optional<Portfolio> findByOwner(User owner);
}
