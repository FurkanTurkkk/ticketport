package com.furkan.ticketport.event.query.category;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.port.in.category.ListAllCategoriesQuery;
import com.furkan.ticketport.event.port.out.category.CategoryQueryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllCategoriesQueryService implements ListAllCategoriesQuery {

    private final CategoryQueryPort categoryQueryPort;

    public ListAllCategoriesQueryService(CategoryQueryPort categoryQueryPort) {
        this.categoryQueryPort = categoryQueryPort;
    }

    @Override
    public List<Category> listAll() {
        return List.copyOf(categoryQueryPort.findAll());
    }
}
