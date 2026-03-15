package com.juliandonati.backendPortafolio.repository;

import com.juliandonati.backendPortafolio.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Override
    @Query("SELECT j FROM Job j " +
            "WHERE j.id = :id")
    Optional<Job> findById(Long id);

    @Override
    @Query("SELECT j FROM Job j")
    List<Job> findAll();

    @Query("SELECT jobs FROM User u " +
            "JOIN u.ownedPortfolio AS p " +
            "JOIN p.experience AS jobs " +
            "WHERE u.username = :username")
    List<Job> findByOwnerUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN count(jobOwner) > 0 and jobOwner = :username THEN TRUE ELSE FALSE END FROM Job j " +
            "JOIN j.portfolio AS p " +
            "JOIN p.owner AS jobOwner " +
            "WHERE j.id = :id")
    boolean IsJobByIdOwnedByUsername(@Param("id")  Long id, @Param("username") String username);
}
