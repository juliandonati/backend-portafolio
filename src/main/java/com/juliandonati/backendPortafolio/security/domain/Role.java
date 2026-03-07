package com.juliandonati.backendPortafolio.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;
    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy="roles")

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

}
