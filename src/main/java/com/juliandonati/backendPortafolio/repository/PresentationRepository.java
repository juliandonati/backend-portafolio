package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Long> {
}
