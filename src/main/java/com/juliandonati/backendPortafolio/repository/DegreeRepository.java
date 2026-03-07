package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

    @Override
    @Query("SELECT d FROM Degree d JOIN FETCH d.portfolio as p JOIN FETCH p.owner " +
            "WHERE d.id = :id")
    Optional<Degree> findById(Long id);

    @Override
    @Query("SELECT d FROM Degree d")
    List<Degree> findAll();
}
