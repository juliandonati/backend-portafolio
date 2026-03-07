package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {
}
