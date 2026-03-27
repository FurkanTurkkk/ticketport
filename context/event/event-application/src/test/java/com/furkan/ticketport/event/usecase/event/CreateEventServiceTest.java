package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.exception.UniqueSlugAllocationException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventServiceTest {

    @Mock
    private EventQueryPort collidingQuery;

    private final InMemoryEventRepository repository = new InMemoryEventRepository();

    @Test
    void savesDraftWithAutoSlugFromTitle() {
        CreateEventService service = new CreateEventService(repository, repository);

        EventId id =
                service.execute(
                        new CreateEventCommand(
                                EventTestFixtures.SEED_CATEGORY,
                                Title.valueOf("Autumn Jazz"),
                                null,
                                null));

        Event saved = repository.findByEventId(id).orElseThrow();
        assertNotNull(saved.slug());
        assertEquals("autumn-jazz", saved.slug().asString());
    }

    @Test
    void usesDescriptionWithoutCover() {
        CreateEventService service = new CreateEventService(repository, repository);
        EventId id =
                service.execute(
                        new CreateEventCommand(
                                EventTestFixtures.SEED_CATEGORY,
                                Title.valueOf("Only Desc"),
                                "Hello Desc",
                                null));
        Event saved = repository.findByEventId(id).orElseThrow();
        assertEquals("Hello Desc", saved.description());
        assertTrue(saved.coverImage() == null);
    }

    @Test
    void usesDescriptionAndCoverWhenPresent() {
        CreateEventService service = new CreateEventService(repository, repository);
        EventId id =
                service.execute(
                        new CreateEventCommand(
                                EventTestFixtures.SEED_CATEGORY,
                                Title.valueOf("Full"),
                                " About ",
                                "https://cdn.example/img.png"));

        Event saved = repository.findByEventId(id).orElseThrow();
        assertEquals(" About ", saved.description());
        assertEquals("https://cdn.example/img.png", saved.coverImage().asString());
    }

    @Test
    void picksNewSlugWhenBaseIsTaken() {
        Event blocker =
                Event.create(
                        EventId.valueOf("block-id"),
                        EventTestFixtures.SEED_CATEGORY,
                        Title.valueOf("Other"));
        blocker.assignSlug(Slug.valueOf("shared-title"));
        repository.save(blocker);

        CreateEventService service = new CreateEventService(repository, repository);
        EventId id2 =
                service.execute(
                        new CreateEventCommand(
                                EventTestFixtures.SEED_CATEGORY,
                                Title.valueOf("Shared Title"),
                                null,
                                null));

        Event second = repository.findByEventId(id2).orElseThrow();
        assertTrue(secondSlugDiffersFromBase(second, "shared-title"));
    }

    private static boolean secondSlugDiffersFromBase(Event second, String base) {
        return !second.slug().asString().equals(base);
    }

    @Test
    void throwsWhenSlugCannotBeAllocated() {
        Event blocker = EventTestFixtures.draftEvent("Nope");
        blocker.assignSlug(Slug.valueOf("ab"));
        when(collidingQuery.findBySlug(ArgumentMatchers.any()))
                .thenReturn(Optional.of(blocker));
        EventPersistencePort passthrough =
                new EventPersistencePort() {
                    @Override
                    public EventId save(Event event) {
                        return repository.save(event);
                    }

                    @Override
                    public void delete(Event event) {
                        repository.delete(event);
                    }
                };
        CreateEventService service = new CreateEventService(passthrough, collidingQuery);

        assertThrows(
                UniqueSlugAllocationException.class,
                () ->
                        service.execute(
                                new CreateEventCommand(
                                        EventTestFixtures.SEED_CATEGORY,
                                        Title.valueOf("Any Title"),
                                        null,
                                        null)));
    }

    @Test
    void trimsOverlongSlugCandidateBeforeSave() {
        CreateEventService service = new CreateEventService(repository, repository);
        String longTitle = "Word-" + "x".repeat(195);
        EventId id =
                service.execute(
                        new CreateEventCommand(
                                EventTestFixtures.SEED_CATEGORY,
                                Title.valueOf(longTitle),
                                null,
                                null));
        Event saved = repository.findByEventId(id).orElseThrow();
        assertTrue(saved.slug().asString().length() <= 120);
    }

    @Test
    void trimCandidateTruncatesAndRepadsShortSlug() throws Exception {
        Method trim =
                CreateEventService.class.getDeclaredMethod("trimCandidate", String.class);
        trim.setAccessible(true);
        String longIn = "z".repeat(121);
        String truncated = (String) trim.invoke(null, longIn);
        assertEquals(120, truncated.length());
        String shortIn = "q";
        String padded = (String) trim.invoke(null, shortIn);
        assertTrue(padded.startsWith("ev-"));
        assertTrue(padded.length() >= 4);
    }
}
