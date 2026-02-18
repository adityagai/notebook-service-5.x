package com.notebook.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.notebook.core.NotebookStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "notebooks")
@NamedQuery(name = "Notebook.findAll", query = "SELECT n FROM Notebook n ORDER BY n.createdAt DESC")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @NotBlank
    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @Enumerated(EnumType.STRING)
    @JsonProperty
    private NotebookStatus status;

    @JsonProperty
    private Instant createdAt;

    @JsonProperty
    private Instant updatedAt;

    public Notebook() {
        // Jackson deserialization
    }

    public Notebook(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = NotebookStatus.ACTIVE;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = NotebookStatus.ACTIVE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotebookStatus getStatus() {
        return status;
    }

    public void setStatus(NotebookStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
