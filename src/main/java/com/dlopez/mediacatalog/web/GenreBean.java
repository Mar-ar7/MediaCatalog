package com.dlopez.mediacatalog.web;

import com.dlopez.mediacatalog.entity.MovieGenre;
import com.dlopez.mediacatalog.service.GenreService;
import com.dlopez.mediacatalog.util.FacesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class GenreBean implements Serializable {

    @Inject
    private GenreService genreService;

    private List<MovieGenre> genres;
    private MovieGenre selected;

    @PostConstruct
    public void init() {
        loadGenres();
    }

    public void loadGenres() {
        genres = genreService.findAll();
    }

    public void prepareNew() {
        selected = new MovieGenre();
    }

    public void save() {
        try {
            genreService.save(selected);
            FacesUtils.info("Género guardado correctamente");
            loadGenres();
        } catch (Exception e) {
            FacesUtils.error("Error al guardar: " + e.getMessage());
        }
    }

    public void delete(MovieGenre g) {
        try {
            genreService.delete(g.getId());
            FacesUtils.info("Género eliminado");
            loadGenres();
        } catch (Exception e) {
            FacesUtils.error("No se pudo eliminar: " + e.getMessage());
        }
    }

    // Getters y setters
    public List<MovieGenre> getGenres() { return genres; }
    public MovieGenre getSelected() { return selected; }
    public void setSelected(MovieGenre selected) { this.selected = selected; }
}
