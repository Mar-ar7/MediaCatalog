package com.dlopez.mediacatalog.repository;

import com.dlopez.mediacatalog.entity.MovieGenre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class GenreRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(MovieGenre genre) {
        em.persist(genre);
    }

    public MovieGenre findById(Long id) {
        return em.find(MovieGenre.class, id);
    }

    public List<MovieGenre> findAll() {
        return em.createQuery("SELECT g FROM MovieGenre g ORDER BY g.name", MovieGenre.class)
                .getResultList();
    }

    public MovieGenre findByName(String name) {
        var result = em.createQuery("SELECT g FROM MovieGenre g WHERE LOWER(g.name) = LOWER(:name)", MovieGenre.class)
                .setParameter("name", name)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public void update(MovieGenre genre) {
        em.merge(genre);
    }

    public void delete(Long id) {
        MovieGenre g = findById(id);
        if (g != null) {
            em.remove(g);
        }
    }
}
