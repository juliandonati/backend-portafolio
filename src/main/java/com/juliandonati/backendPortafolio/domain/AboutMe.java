package com.juliandonati.backendPortafolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_mes")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutMe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false)
    private String description;

    private String bgImgUrl;

    private String buttonText;
    private String buttonUrl;

    @OneToOne(mappedBy = "aboutMe")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Portfolio portfolio;
}
