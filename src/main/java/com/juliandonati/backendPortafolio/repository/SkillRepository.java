package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Override
    @Query("SELECT s FROM Skill s JOIN FETCH s.portfolio as p JOIN FETCH p.owner " +
            "WHERE s.id = :id")
    Optional<Skill> findById(Long id);

    @Override
    @Query("SELECT s FROM Skill s")
    List<Skill> findAll();
}
