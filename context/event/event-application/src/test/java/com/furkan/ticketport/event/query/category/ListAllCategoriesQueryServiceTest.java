package com.furkan.ticketport.event.query.category;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.testsupport.InMemoryCategoryRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListAllCategoriesQueryServiceTest {

    @Test
    void listsCopy() {
        InMemoryCategoryRepository repo = new InMemoryCategoryRepository();
        repo.seed(Category.create(CategoryId.valueOf("c1"), CategoryType.MOVIE));
        repo.seed(Category.create(CategoryId.valueOf("c2"), CategoryType.TRIP));
        ListAllCategoriesQueryService q = new ListAllCategoriesQueryService(repo);
        assertEquals(2, q.listAll().size());
    }
}
