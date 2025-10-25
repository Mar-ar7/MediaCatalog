package com.dlopez.mediacatalog.web;

import com.dlopez.mediacatalog.entity.MediaTitle;
import com.dlopez.mediacatalog.entity.MovieGenre;
import com.dlopez.mediacatalog.enums.TitleType;
import com.dlopez.mediacatalog.service.GenreService;
import com.dlopez.mediacatalog.service.MediaService;
import com.dlopez.mediacatalog.util.FacesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MediaBean implements Serializable {

    @Inject
    private MediaService mediaService;

    @Inject
    private GenreService genreService;

    private List<MediaTitle> titles;
    private List<MovieGenre> allGenres;
    private MediaTitle selected;

    @PostConstruct
    public void init() {
        loadTitles();
        allGenres = genreService.findAll();
    }

    public void loadTitles() {
        titles = mediaService.findAll();
    }

    public void prepareNew() {
        selected = new MediaTitle();
    }

    public void save() {
        try {
            mediaService.save(selected);
            FacesUtils.info("Título guardado correctamente");
            loadTitles();
        } catch (Exception e) {
            FacesUtils.error("Error al guardar: " + e.getMessage());
        }
    }

    public void delete(MediaTitle m) {
        try {
            mediaService.delete(m.getId());
            FacesUtils.info("Título eliminado");
            loadTitles();
        } catch (Exception e) {
            FacesUtils.error("No se pudo eliminar: " + e.getMessage());
        }
    }

    public TitleType[] getTypes() {
        return TitleType.values();
    }

    // Getters y Setters
    public List<MediaTitle> getTitles() { return titles; }
    public MediaTitle getSelected() { return selected; }
    public void setSelected(MediaTitle selected) { this.selected = selected; }
    public List<MovieGenre> getAllGenres() { return allGenres; }
}
