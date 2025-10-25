package com.dlopez.mediacatalog.repository;

import com.dlopez.mediacatalog.entity.MediaFile;
import com.dlopez.mediacatalog.entity.MediaTitle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class MediaFileRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(MediaFile file) {
        em.persist(file);
    }

    public MediaFile findById(Long id) {
        return em.find(MediaFile.class, id);
    }

    public List<MediaFile> findAll() {
        return em.createQuery("SELECT f FROM MediaFile f ORDER BY f.uploadedAt DESC", MediaFile.class)
                .getResultList();
    }

    public List<MediaFile> findByTitle(MediaTitle title) {
        return em.createQuery("SELECT f FROM MediaFile f WHERE f.mediaTitle = :title ORDER BY f.uploadedAt DESC", MediaFile.class)
                .setParameter("title", title)
                .getResultList();
    }

    public void update(MediaFile file) {
        em.merge(file);
    }

    public void delete(Long id) {
        MediaFile f = findById(id);
        if (f != null) {
            em.remove(f);
        }
    }
}
