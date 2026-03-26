package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.mapper.CategoryMapper;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.port.out.category.CategoryQueryPort;
import com.furkan.ticketport.event.repository.CategoryJpaRepository;
import com.furkan.ticketport.event.valueobject.CategoryId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryQueryJpaAdapter implements CategoryQueryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryQueryJpaAdapter(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream().map(CategoryMapper::toDomain).toList();
    }

    @Override
    public Optional<Category> findById(CategoryId categoryId) {
        return categoryJpaRepository.findById(categoryId.asString()).map(CategoryMapper::toDomain);
    }
}
