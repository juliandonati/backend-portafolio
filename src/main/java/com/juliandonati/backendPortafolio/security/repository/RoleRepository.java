package com.juliandonati.backendPortafolio.security.repository;

import com.juliandonati.backendPortafolio.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r " +
            "WHERE r.name = :name")
    Optional<Role> findByName(String name);
}
