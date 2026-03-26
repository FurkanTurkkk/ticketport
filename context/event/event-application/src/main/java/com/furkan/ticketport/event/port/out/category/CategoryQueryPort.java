package com.furkan.ticketport.event.port.out.category;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.valueobject.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryPort {

    List<Category> findAll();

    Optional<Category> findById(CategoryId categoryId);
}
