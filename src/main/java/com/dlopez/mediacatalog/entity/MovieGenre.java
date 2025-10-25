package com.dlopez.mediacatalog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie_genres")
public class MovieGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_genre_id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "genre_name", nullable = false, unique = true, length = 50)
    private String genreName;

    @ManyToMany(mappedBy = "genres")
    private Set<MediaTitle> titles = new HashSet<>();

    // === GETTERS & SETTERS ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

    public Set<MediaTitle> getTitles() { return titles; }
    public void setTitles(Set<MediaTitle> titles) { this.titles = titles; }
}
