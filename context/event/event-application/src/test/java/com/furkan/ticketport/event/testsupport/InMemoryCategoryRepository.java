package com.furkan.ticketport.event.testsupport;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.port.out.category.CategoryQueryPort;
import com.furkan.ticketport.event.valueobject.CategoryId;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemoryCategoryRepository implements CategoryQueryPort {

    private final Map<String, Category> categories = new LinkedHashMap<>();

    public void seed(Category category) {
        categories.put(category.categoryId().asString(), category);
    }

    @Override
    public List<Category> findAll() {
        return List.copyOf(new ArrayList<>(categories.values()));
    }

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return Optional.ofNullable(categories.get(categoryId.asString()));
    }
}
