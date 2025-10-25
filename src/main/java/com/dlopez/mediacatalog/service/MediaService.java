package com.dlopez.mediacatalog.service;

import com.dlopez.mediacatalog.entity.MediaTitle;
import com.dlopez.mediacatalog.repository.MediaTitleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MediaService {

    @Inject
    private MediaTitleRepository repo;

    public List<MediaTitle> findAll() {
        return repo.findAll();
    }

    public MediaTitle findById(Long id) {
        return repo.findById(id);
    }

    @Transactional
    public void save(MediaTitle m) {
        if (m.getId() == null)
            repo.create(m);
        else
            repo.update(m);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(id);
    }
}
