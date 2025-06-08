// Product.java
package com.detallesunicos.v1.model;

import jakarta.persistence.*;
import java.util.List;
import com.detallesunicos.v1.model.MediaFile;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaFile> mediaFiles;

    public Product() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<MediaFile> getMediaFiles() { return mediaFiles; }
    public void setMediaFiles(List<MediaFile> mediaFiles) { this.mediaFiles = mediaFiles; }
}
