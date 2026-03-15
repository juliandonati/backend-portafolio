package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
    @Query("SELECT presentation FROM User u " +
            "JOIN FETCH u.ownedPortfolio AS p " +
            "JOIN FETCH p.presentation AS presentation " +
            "WHERE u.username = :username")
    Optional<Presentation> findByOwnerUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN presentation IS NOT NULL THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "JOIN FETCH u.ownedPortfolio AS p " +
            "JOIN FETCH p.presentation AS presentation " +
            "WHERE u.username = :username")
    boolean existsByOwnerUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM Presentation p " +
            "WHERE p.id IN " +
            "(" +
            "SELECT presentation.id FROM User u " +
            "JOIN u.ownedPortfolio AS portfolio " +
            "JOIN portfolio.presentation AS presentation " +
            "WHERE u.username = :username" +
            ")")
    void deleteByOwnerUsername(@Param("username") String username);
}
