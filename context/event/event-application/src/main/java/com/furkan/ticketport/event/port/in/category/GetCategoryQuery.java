package com.furkan.ticketport.event.port.in.category;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.valueobject.CategoryId;

public interface GetCategoryQuery {

    Category getById(CategoryId categoryId);
}
