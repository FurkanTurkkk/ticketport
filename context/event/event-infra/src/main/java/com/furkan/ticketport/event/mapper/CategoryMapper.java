package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.CategoryEntity;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.valueobject.CategoryId;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static Category toDomain(CategoryEntity entity) {
        return Category.restore(
                CategoryId.valueOf(entity.getId()),
                entity.getType(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
