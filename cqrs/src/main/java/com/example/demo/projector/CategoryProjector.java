package com.example.demo.projector;

import com.example.demo.entity.CategoryDocument;
import com.example.demo.event.Event;
import com.example.demo.event.ProductAddedToCategoryEvent;
import com.example.demo.event.ProductRemovedFromCategoryEvent;
import com.example.demo.repository.read.CategoryReadRepository;
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
