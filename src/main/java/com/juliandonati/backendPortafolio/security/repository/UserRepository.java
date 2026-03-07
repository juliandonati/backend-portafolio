package com.juliandonati.backendPortafolio.security.repository;

import com.juliandonati.backendPortafolio.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.ownedPortfolios LEFT JOIN FETCH u.modifiablePortfolios " +
            "WHERE u.username = :username")
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles LEFT JOIN FETCH u.ownedPortfolios LEFT JOIN FETCH u.modifiablePortfolios " +
            "WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    void deleteByUsername(String username);
    void deleteByEmail(String email);
}
