package com.juliandonati.backendPortafolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "presentations")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 50)
    private String title;
    private String description;

    private String imgUrl;

    @OneToOne(mappedBy = "presentation")

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Portfolio portfolio;
}
