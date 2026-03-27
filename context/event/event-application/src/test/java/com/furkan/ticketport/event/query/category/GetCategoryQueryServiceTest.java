package com.furkan.ticketport.event.query.category;

import com.furkan.ticketport.event.exception.CategoryNotFoundException;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.testsupport.InMemoryCategoryRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetCategoryQueryServiceTest {

    @Test
    void returnsExisting() {
        InMemoryCategoryRepository repo = new InMemoryCategoryRepository();
        Category c = Category.create(CategoryId.valueOf("cid1"), CategoryType.MOVIE);
        repo.seed(c);
        GetCategoryQueryService q = new GetCategoryQueryService(repo);
        assertEquals(CategoryType.MOVIE, q.getById(CategoryId.valueOf("cid1")).categoryType());
    }

    @Test
    void throwsWhenMissing() {
        assertThrows(
                CategoryNotFoundException.class,
                () ->
                        new GetCategoryQueryService(new InMemoryCategoryRepository())
                                .getById(CategoryId.valueOf("none")));
    }
}
