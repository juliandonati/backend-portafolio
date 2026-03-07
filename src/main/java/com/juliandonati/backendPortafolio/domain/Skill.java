package com.juliandonati.backendPortafolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skills")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(length = 50)
    private String level;

    private String imgUrl;

    @Column(length = 50)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Portfolio portfolio;
}
