package com.villo.alkemychallenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CHARACTERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "HISTORY")
    private String history;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER)
    private Set<Film> films = new HashSet<>();
}
