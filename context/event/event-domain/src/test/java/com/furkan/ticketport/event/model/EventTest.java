package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.exception.EventCannotBePublishedException;
import com.furkan.ticketport.event.exception.SlugImmutableAfterPublishException;
import com.furkan.ticketport.event.testsupport.EventTestModels;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    @Test
    void createMinimalDraft() {
        Event e =
                Event.create(
                        EventId.valueOf("e1"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("Concert"));
        assertEquals(EventStatus.DRAFT, e.status());
        assertEquals("Concert", e.title().asString());
        assertNull(e.description());
        assertNull(e.coverImage());
        assertNull(e.slug());
        assertEquals("concert", e.normalizedTitle());
        assertNotNull(e.createdAt());
        assertNotNull(e.updatedAt());
        assertTrue(!e.updatedAt().isBefore(e.createdAt()));
    }

    @Test
    void createWithDescription() {
        Event e =
                Event.createWithDescription(
                        EventId.valueOf("e2"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("Night Show"),
                        " Details ");
        assertEquals(" Details ", e.description());
        assertTrue(e.coverImage() == null);
    }

    @Test
    void createWithCover() {
        Event e =
                Event.createWithCover(
                        EventId.valueOf("e3"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("Covered"),
                        " d ",
                        CoverImageRef.valueOf("https://img/o.jpg"));
        assertEquals(" d ", e.description());
        assertEquals("https://img/o.jpg", e.coverImage().asString());
    }

    @Test
    void createWithCoverStoresDescriptionAsProvided() {
        Event e =
                Event.createWithCover(
                        EventId.valueOf("e4"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("X"),
                        "   ",
                        CoverImageRef.valueOf("https://img/x.jpg"));
        assertEquals("   ", e.description());
    }

    @Test
    void restoreUsesProvidedNormalizedTitleWhenNonBlank() {
        Instant created = Instant.parse("2020-01-01T10:00:00Z");
        Instant updated = Instant.parse("2020-01-02T10:00:00Z");
        Event e =
                Event.restore(
                        EventId.valueOf("e5"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("T"),
                        "custom-norm",
                        null,
                        null,
                        Slug.valueOf("sl"),
                        EventStatus.PUBLISHED,
                        created,
                        updated);
        assertEquals("custom-norm", e.normalizedTitle());
        assertEquals(created, e.createdAt());
        assertEquals(updated, e.updatedAt());
        assertTrue(e.isPublished());
    }

    @Test
    void restoreFallsBackNormalizationWhenStoredNormalizedBlank() {
        Event e =
                Event.restore(
                        EventId.valueOf("e6"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("My  Title"),
                        " ",
                        null,
                        null,
                        null,
                        EventStatus.DRAFT,
                        Instant.now(),
                        Instant.now());
        assertEquals("my title", e.normalizedTitle());
    }

    @Test
    void restoreFallsBackWhenNormalizedNull() {
        Event e =
                Event.restore(
                        EventId.valueOf("e6b"),
                        EventTestModels.SEED_CATEGORY,
                        Title.valueOf("A B"),
                        null,
                        null,
                        null,
                        null,
                        EventStatus.DRAFT,
                        Instant.now(),
                        Instant.now());
        assertEquals("a b", e.normalizedTitle());
    }

    @Test
    void changeTitleDescriptionCoverCategoryUpdatesNormalized() {
        Event e = EventTestModels.draftEvent("Old Name");
        Title newTitle = Title.valueOf("New  Name");
        e.changeTitle(newTitle);
        assertEquals("new name", e.normalizedTitle());
        e.changeDescription("x");
        assertEquals("x", e.description());
        e.changeCoverImage(CoverImageRef.valueOf("https://c.com/a.png"));
        CategoryId alt = CategoryId.valueOf("b0000001-0000-4000-8000-000000000002");
        e.changeCategory(alt);
        assertEquals(alt, e.categoryId());
    }

    @Test
    void assignSlugWhileDraft() {
        Event e = EventTestModels.draftEvent("S");
        e.assignSlug(Slug.valueOf("draft-slug"));
        assertEquals("draft-slug", e.slug().asString());
    }

    @Test
    void assignSlugAfterPublishThrows() {
        Event e = EventTestModels.draftEvent("P");
        e.assignSlug(Slug.valueOf("once"));
        e.publish();
        assertThrows(
                SlugImmutableAfterPublishException.class,
                () -> e.assignSlug(Slug.valueOf("twice")));
    }

    @Test
    void publishRequiresSlug() {
        Event e = EventTestModels.draftEvent("No slug");
        assertThrows(EventCannotBePublishedException.class, () -> e.publish());
    }

    @Test
    void publishUnpublishPublishedLifecycle() {
        Event e = EventTestModels.draftEvent("Lifecycle");
        e.assignSlug(Slug.valueOf("life"));
        e.publish();
        assertEquals(EventStatus.PUBLISHED, e.status());
        assertTrue(e.isPublished());
        e.unpublishToDraft();
        assertEquals(EventStatus.DRAFT, e.status());
        assertFalse(e.isPublished());
    }
}
