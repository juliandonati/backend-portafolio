package com.juliandonati.backendPortafolio.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juliandonati.backendPortafolio.domain.Portfolio;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "owner", cascade =  CascadeType.ALL,  orphanRemoval = true)

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Portfolio ownedPortfolio = null;

    @ManyToMany
    @JoinTable(
            name = "users_portfolios",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "portfolio_id")
    )

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Portfolio> modifiablePortfolios = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
