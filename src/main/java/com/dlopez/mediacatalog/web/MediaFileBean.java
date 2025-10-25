package com.dlopez.mediacatalog.web;

import com.dlopez.mediacatalog.entity.MediaFile;
import com.dlopez.mediacatalog.entity.MediaTitle;
import com.dlopez.mediacatalog.service.MediaFileService;
import com.dlopez.mediacatalog.util.FacesUtils;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MediaFileBean implements Serializable {

    @Inject
    private MediaFileService service;

    private MediaTitle selectedTitle;
    private List<MediaFile> files;

    /**
     * Carga los archivos asociados al título seleccionado.
     */
    public void loadFiles() {
        if (selectedTitle != null) {
            files = service.findByTitle(selectedTitle);
        }
    }

    /**
     * Maneja la subida de archivos desde el componente <p:fileUpload>.
     */
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        try {
            service.uploadFile(selectedTitle, file);
            FacesUtils.info("Archivo subido correctamente: " + file.getFileName());
            loadFiles();
        } catch (IOException e) {
            FacesUtils.error("Error al subir archivo: " + e.getMessage());
        } catch (Exception e) {
            FacesUtils.error("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Elimina un archivo del sistema y base de datos.
     */
    public void delete(MediaFile mf) {
        try {
            service.delete(mf.getId());
            FacesUtils.info("Archivo eliminado: " + mf.getBlobUrl());
            loadFiles();
        } catch (Exception e) {
            FacesUtils.error("Error al eliminar archivo: " + e.getMessage());
        }
    }

    // Getters y setters
    public MediaTitle getSelectedTitle() {
        return selectedTitle;
    }

    public void setSelectedTitle(MediaTitle selectedTitle) {
        this.selectedTitle = selectedTitle;
        loadFiles();
    }

    public List<MediaFile> getFiles() {
        return files;
    }
}
