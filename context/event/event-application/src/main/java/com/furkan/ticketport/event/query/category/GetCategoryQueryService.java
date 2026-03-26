package com.furkan.ticketport.event.query.category;

import com.furkan.ticketport.event.exception.CategoryNotFoundException;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.port.in.category.GetCategoryQuery;
import com.furkan.ticketport.event.port.out.category.CategoryQueryPort;
import com.furkan.ticketport.event.valueobject.CategoryId;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryQueryService implements GetCategoryQuery {

    private final CategoryQueryPort categoryQueryPort;

    public GetCategoryQueryService(CategoryQueryPort categoryQueryPort) {
        this.categoryQueryPort = categoryQueryPort;
    }

    @Override
    public Category getById(CategoryId categoryId) {
        return categoryQueryPort
                .findById(categoryId)
                .orElseThrow(
                        () ->
                                new CategoryNotFoundException(
                                        "Category not found: " + categoryId.asString()));
    }
}
