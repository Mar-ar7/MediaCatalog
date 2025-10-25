package com.dlopez.mediacatalog.service;

import com.dlopez.mediacatalog.entity.MovieGenre;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@Startup
public class StartupCheck {

    @PersistenceContext(unitName = "mediaCatalogPU")
    EntityManager em;

    @PostConstruct
    public void testConnection() {
        try {
            MovieGenre genre = new MovieGenre();
            genre.setGenreName("Prueba inicial");
            em.persist(genre);
            System.out.println("Conexión con PostgreSQL verificada: registro insertado.");
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}
