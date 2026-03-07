package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Override
    @Query("SELECT j FROM Job j JOIN FETCH j.portfolio as p JOIN FETCH p.owner " +
            "WHERE j.id = :id")
    Optional<Job> findById(Long id);

    @Override
    @Query("SELECT j FROM Job j")
    List<Job> findAll();
}
