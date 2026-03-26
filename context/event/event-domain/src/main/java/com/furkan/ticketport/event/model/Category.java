package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;

import java.time.Instant;

public class Category {
    private CategoryId categoryId;
    private CategoryType type;
    private final Instant createdAt;
    private Instant updatedAt;

    private Category(CategoryId categoryId, CategoryType type) {
        this.categoryId = categoryId;
        this.type = type;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    private Category(
            CategoryId categoryId, CategoryType type, Instant createdAt, Instant updatedAt) {
        this.categoryId = categoryId;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Category create(CategoryId categoryId, CategoryType type) {
        return new Category(categoryId, type);
    }

    /** Persistanstan okuma. */
    public static Category restore(
            CategoryId categoryId, CategoryType type, Instant createdAt, Instant updatedAt) {
        return new Category(categoryId, type, createdAt, updatedAt);
    }

    public CategoryId categoryId() { return categoryId; }
    public CategoryType categoryType() { return type; }
    public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }

    public void changeCategoryType(CategoryType type) {
        this.type = type;
        refreshUpdatedAt();
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
