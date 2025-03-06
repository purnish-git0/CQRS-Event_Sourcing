package com.cqrs.projector;

import com.cqrs.entity.CategoryDocument;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToCategoryEvent;
import com.cqrs.event.ProductRemovedFromCategoryEvent;
import com.cqrs.repository.read.CategoryReadRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryProjector {

    private CategoryReadRepository categoryReadRepository;

    private MongoTemplate
    public CategoryProjector(CategoryReadRepository categoryReadRepository) {
        this.categoryReadRepository = categoryReadRepository;
    }

    public void project(Integer categoryId, List<Event> events) {
        for(Event event: events) {
            if(event instanceof ProductAddedToCategoryEvent) {
                apply(categoryId, (ProductAddedToCategoryEvent) event);
            }
            if(event instanceof ProductRemovedFromCategoryEvent) {
                apply(categoryId, (ProductRemovedFromCategoryEvent) event);
            }

        }

    }

    private void apply(Integer categoryId, ProductAddedToCategoryEvent event) {
        CategoryDocument categoryDocument = categoryReadRepository.fin
    }

    private void apply(Integer categoryId, ProductRemovedFromCategoryEvent event) {

    }




}
