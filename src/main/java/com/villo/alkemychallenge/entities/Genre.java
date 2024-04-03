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
@EntityListeners(EntityWithImageListener.class)
@Table(name = "GENRES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "IMAGE", unique = true, length = Constants.MAX_SIZE_IMAGE)
    private String image;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private Set<Film> films = new HashSet<>();
}
