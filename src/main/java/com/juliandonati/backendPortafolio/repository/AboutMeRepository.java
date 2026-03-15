package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {
    @Query("SELECT aboutMe FROM User u " +
            "LEFT JOIN FETCH u.ownedPortfolio AS p " +
            "LEFT JOIN FETCH p.aboutMe AS aboutMe " +
            "WHERE u.username = :username")
    Optional<AboutMe> findByOwnerUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN aboutMe IS NOT NULL THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "JOIN FETCH u.ownedPortfolio AS p " +
            "JOIN FETCH p.aboutMe AS aboutMe " +
            "WHERE u.username = :username")
    boolean existsByOwnerUsername(@Param("username") String username);

    @Modifying
    @Query("DELETE FROM AboutMe aboutMe " +
            "WHERE aboutMe.id " +
            "IN " +
            "(SELECT aboutMe.id FROM User u " +
            "JOIN u.ownedPortfolio AS p " +
            "JOIN p.aboutMe AS aboutMe " +
            "WHERE u.username = :username)")
    void deleteByOwnerUsername(@Param("username") String username);
}
