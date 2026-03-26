package com.furkan.ticketport.event.port.in.category;

import com.furkan.ticketport.event.model.Category;

import java.util.List;

public interface ListAllCategoriesQuery {

    List<Category> listAll();
}
