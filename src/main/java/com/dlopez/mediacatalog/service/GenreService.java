package com.dlopez.mediacatalog.service;

import com.dlopez.mediacatalog.entity.MovieGenre;
import com.dlopez.mediacatalog.repository.GenreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class GenreService {

    @Inject
    private GenreRepository repo;

    public List<MovieGenre> findAll() {
        return repo.findAll();
    }

    @Transactional
    public void save(MovieGenre g) {
        if (g.getId() == null) repo.create(g);
        else repo.update(g);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(id);
    }
}
