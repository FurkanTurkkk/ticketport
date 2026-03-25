package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.exception.EventCannotBePublishedException;
import com.furkan.ticketport.event.exception.SlugImmutableAfterPublishException;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;

import java.time.Instant;

public class Event {

    private EventId eventId;
    private CategoryId categoryId;
    private Title title;
    private String normalizedTitle;
    private String description;
    private CoverImageRef coverImage;
    private Slug slug;
    private EventStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Event(
            EventId eventId,
            CategoryId categoryId,
            Title title,
            String description,
            CoverImageRef coverImage,
            Slug slug,
            EventStatus status) {
        this.eventId = eventId;
        this.categoryId = categoryId;
        this.title = title;
        this.normalizedTitle = title.normalizedForDuplicateCheck();
        this.description = description;
        this.coverImage = coverImage;
        this.slug = slug;
        this.status = status;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public static Event create(EventId eventId, CategoryId categoryId, Title title) {
        return new Event(eventId, categoryId, title, null, null, null, EventStatus.DRAFT);
    }

    public static Event createWithDescription(EventId eventId, CategoryId categoryId, Title title, String description) {
        return new Event(eventId, categoryId, title, description, null, null, EventStatus.DRAFT);
    }

    public static Event createWithCover(
            EventId eventId,
            CategoryId categoryId,
            Title title,
            String description,
            CoverImageRef coverImage) {
        return new Event(eventId, categoryId, title, description, coverImage, null, EventStatus.DRAFT);
    }

    public EventId eventId() {
        return eventId;
    }

    public CategoryId categoryId() {
        return categoryId;
    }

    public Title title() {
        return title;
    }

    public String normalizedTitle() {
        return normalizedTitle;
    }

    public String description() {
        return description;
    }

    public CoverImageRef coverImage() {
        return coverImage;
    }

    public Slug slug() {
        return slug;
    }

    public EventStatus status() {
        return status;
    }

    public boolean isPublished() {
        return status == EventStatus.PUBLISHED;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void changeTitle(Title title) {
        this.title = title;
        this.normalizedTitle = title.normalizedForDuplicateCheck();
        refreshUpdatedAt();
    }

    public void changeDescription(String description) {
        this.description = description;
        refreshUpdatedAt();
    }

    public void changeCoverImage(CoverImageRef coverImage) {
        this.coverImage = coverImage;
        refreshUpdatedAt();
    }

    public void changeCategory(CategoryId categoryId) {
        this.categoryId = categoryId;
        refreshUpdatedAt();
    }

    public void assignSlug(Slug slug) {
        if (status == EventStatus.PUBLISHED) {
            throw new SlugImmutableAfterPublishException("Slug cannot be changed after event is published");
        }
        this.slug = slug;
        refreshUpdatedAt();
    }

    public void publish() {
        if (slug == null) {
            throw new EventCannotBePublishedException("Cannot publish event without slug");
        }
        this.status = EventStatus.PUBLISHED;
        refreshUpdatedAt();
    }

    public void unpublishToDraft() {
        this.status = EventStatus.DRAFT;
        refreshUpdatedAt();
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
