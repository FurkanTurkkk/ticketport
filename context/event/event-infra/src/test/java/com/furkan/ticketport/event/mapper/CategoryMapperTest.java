package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.CategoryEntity;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    @Test
    void toDomain() {
        Instant c = Instant.parse("2016-01-01T00:00:00Z");
        Instant u = Instant.parse("2016-01-02T00:00:00Z");
        CategoryEntity entity = new CategoryEntity("cid", CategoryType.THEATRE, c, u);
        Category back = CategoryMapper.toDomain(entity);
        assertEquals("cid", back.categoryId().asString());
        assertEquals(CategoryType.THEATRE, back.categoryType());
    }
}
