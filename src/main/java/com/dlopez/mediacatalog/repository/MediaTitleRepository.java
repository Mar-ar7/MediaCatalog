package com.dlopez.mediacatalog.repository;

import com.dlopez.mediacatalog.entity.MediaTitle;
import com.dlopez.mediacatalog.enums.TitleType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class MediaTitleRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(MediaTitle title) {
        em.persist(title);
    }

    public MediaTitle findById(Long id) {
        return em.find(MediaTitle.class, id);
    }

    public List<MediaTitle> findAll() {
        return em.createQuery("SELECT t FROM MediaTitle t ORDER BY t.releaseYear DESC", MediaTitle.class)
                .getResultList();
    }

    public List<MediaTitle> findByType(TitleType type) {
        return em.createQuery("SELECT t FROM MediaTitle t WHERE t.titleType = :type ORDER BY t.releaseYear DESC", MediaTitle.class)
                .setParameter("type", type)
                .getResultList();
    }

    public List<MediaTitle> findByGenre(String genreName) {
        return em.createQuery("""
            SELECT t FROM MediaTitle t
            JOIN t.genres g
            WHERE LOWER(g.name) = LOWER(:genre)
            ORDER BY t.titleName
        """, MediaTitle.class)
                .setParameter("genre", genreName)
                .getResultList();
    }

    public List<MediaTitle> findByYear(int year) {
        return em.createQuery("SELECT t FROM MediaTitle t WHERE t.releaseYear = :year", MediaTitle.class)
                .setParameter("year", year)
                .getResultList();
    }

    public void update(MediaTitle title) {
        em.merge(title);
    }

    public void delete(Long id) {
        MediaTitle t = findById(id);
        if (t != null) {
            em.remove(t);
        }
    }
}
