package com.villo.alkemychallenge.entities;

import com.villo.alkemychallenge.events.listeners.EntityWithImageListener;
import com.villo.alkemychallenge.utils.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(EntityWithImageListener.class)
@Table(name = "MOVIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Movie implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IMAGE", unique = true, length = Constants.MAX_SIZE_IMAGE)
    private String image;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDate creationDate;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PARTICIPATIONS",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHARACTER_ID"))
    private Set<Character> characters = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "MOVIES_GENRES",
            joinColumns = @JoinColumn(name = "MOVIE_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
    private Set<Genre> genres = new HashSet<>();

    @Override
    @SneakyThrows
    public Object clone() {
        return super.clone();
    }
}
