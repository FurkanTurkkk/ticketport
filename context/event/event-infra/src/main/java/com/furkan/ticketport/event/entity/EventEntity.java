package com.furkan.ticketport.event.entity;

import com.furkan.ticketport.event.valueobject.EventStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 200)
    private String normalizedTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 2048)
    private String coverImageRef;

    @Column(length = 120)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private EventStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public EventEntity() {}

    public EventEntity(
            String id,
            String categoryId,
            String title,
            String normalizedTitle,
            String description,
            String coverImageRef,
            String slug,
            EventStatus status,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.normalizedTitle = normalizedTitle;
        this.description = description;
        this.coverImageRef = coverImageRef;
        this.slug = slug;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    public void setNormalizedTitle(String normalizedTitle) {
        this.normalizedTitle = normalizedTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageRef() {
        return coverImageRef;
    }

    public void setCoverImageRef(String coverImageRef) {
        this.coverImageRef = coverImageRef;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
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
