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
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(EntityWithImageListener.class)
@Table(name = "CHARACTERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Character implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IMAGE", unique = true, length = Constants.MAX_SIZE_IMAGE)
    private String image;

    @Column(name = "NAME", nullable = false, unique = true, length = Constants.MAX_SIZE_NAME)
    private String name;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "HISTORY", length = Constants.MAX_SIZE_HISTORY)
    private String history;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();

    @Override
    @SneakyThrows
    public Object clone() {
        return super.clone();
    }
}
