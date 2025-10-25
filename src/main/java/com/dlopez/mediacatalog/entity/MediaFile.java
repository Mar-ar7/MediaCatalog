package com.dlopez.mediacatalog.entity;
import com.dlopez.mediacatalog.enums.FileType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "media_files")
public class MediaFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_file_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "media_title_id", nullable = false)
    private MediaTitle mediaTitle;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false, length = 30)
    private FileType fileType;

    @NotNull
    @Size(max = 500)
    @Column(name = "blob_url", nullable = false, length = 500)
    private String blobUrl;

    @Size(max = 100)
    private String etag;

    @Size(max = 50)
    private String contentType;

    private Long sizeBytes;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Size(max = 50)
    @Column(name = "uploaded_by")
    private String uploadedBy;

    @PrePersist
    public void onUpload() {
        this.uploadedAt = LocalDateTime.now();
    }

    // === GETTERS & SETTERS ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MediaTitle getMediaTitle() { return mediaTitle; }
    public void setMediaTitle(MediaTitle mediaTitle) { this.mediaTitle = mediaTitle; }

    public FileType getFileType() { return fileType; }
    public void setFileType(FileType fileType) { this.fileType = fileType; }

    public String getBlobUrl() { return blobUrl; }
    public void setBlobUrl(String blobUrl) { this.blobUrl = blobUrl; }

    public String getEtag() { return etag; }
    public void setEtag(String etag) { this.etag = etag; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
}
