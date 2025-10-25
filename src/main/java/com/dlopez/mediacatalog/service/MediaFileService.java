package com.dlopez.mediacatalog.service;

import com.dlopez.mediacatalog.entity.MediaFile;
import com.dlopez.mediacatalog.entity.MediaTitle;
import com.dlopez.mediacatalog.enums.FileType;
import com.dlopez.mediacatalog.repository.MediaFileRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class MediaFileService {

    @Inject
    private MediaFileRepository repo;

    @Inject
    private AzureBlobService azureService;

    /**
     * Sube un archivo (póster o ficha técnica), guarda metadatos en la base de datos.
     */
    @Transactional
    public void uploadFile(MediaTitle title, UploadedFile file) throws IOException {
        if (title == null || file == null) {
            throw new IllegalArgumentException("Debe seleccionar un título y un archivo.");
        }

        // Determinar tipo de archivo
        FileType type = file.getContentType().equalsIgnoreCase("application/pdf")
                ? FileType.TECHNICAL_SHEET
                : FileType.POSTER;

        // Carpeta según tipo
        String folder = (type == FileType.POSTER) ? "posters" : "fichas";

        // Subir archivo (simulación local o Azure real)
        String blobUrl = azureService.uploadFile(file, title.getTitleName(), folder);

        // Crear registro en base de datos
        MediaFile mf = new MediaFile();
        mf.setMediaTitle(title);
        mf.setFileType(type);
        mf.setBlobUrl(blobUrl);
        mf.setContentType(file.getContentType());
        mf.setSizeBytes(file.getSize());
        mf.setUploadedBy("admin"); // temporal, podrías vincular con usuario en sesión

        repo.create(mf);
    }

    /**
     * Lista todos los archivos asociados a un título.
     */
    public List<MediaFile> findByTitle(MediaTitle title) {
        return repo.findByTitle(title);
    }

    /**
     * Elimina un archivo por ID (lógica extendible para borrar en Azure también).
     */
    @Transactional
    public void delete(Long id) {
        repo.delete(id);
    }
}
