package com.dlopez.mediacatalog.entity;
import com.dlopez.mediacatalog.enums.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "media_titles")
public class MediaTitle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_title_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 150)
    @Column(name = "title_name", nullable = false, length = 150)
    private String titleName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "title_type", nullable = false, length = 20)
    private TitleType titleType;

    @NotNull
    @Min(1900)
    @Max(2100)
    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Size(max = 1000)
    @Column(name = "synopsis", length = 1000)
    private String synopsis;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // RELACIONES
    @ManyToMany
    @JoinTable(
            name = "media_title_genres",
            joinColumns = @JoinColumn(name = "media_title_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_genre_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"media_title_id", "movie_genre_id"})
    )
    private Set<MovieGenre> genres = new HashSet<>();

    @OneToMany(mappedBy = "mediaTitle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MediaFile> files = new HashSet<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // === GETTERS & SETTERS ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitleName() { return titleName; }
    public void setTitleName(String titleName) { this.titleName = titleName; }

    public TitleType getTitleType() { return titleType; }
    public void setTitleType(TitleType titleType) { this.titleType = titleType; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Set<MovieGenre> getGenres() { return genres; }
    public void setGenres(Set<MovieGenre> genres) { this.genres = genres; }

    public Set<MediaFile> getFiles() { return files; }
    public void setFiles(Set<MediaFile> files) { this.files = files; }
}
