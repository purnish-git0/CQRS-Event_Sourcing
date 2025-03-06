package com.example.demo.aggregate;

import com.example.demo.command.category.CreateCategoryCommand;
import com.example.demo.command.category.UpdateCategoryCommand;
import com.example.demo.entity.CategoryDocument;
import com.example.demo.entity.CategoryEventStore;
import com.example.demo.event.Event;
import com.example.demo.event.ProductAddedInCategoryEvent;
import com.example.demo.event.ProductAddedToCategoryEvent;
import com.example.demo.repository.write.CategoryEventStoreRepository;
import com.example.demo.repository.write.ProductEventStoreRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryAggregate {

    private ProductEventStoreRepository productEventStoreRepository;

    private CategoryEventStoreRepository categoryEventStoreRepository;

    public CategoryAggregate(ProductEventStoreRepository productEventStoreRepository
            , CategoryEventStoreRepository categoryEventStoreRepository) {
        this.productEventStoreRepository = productEventStoreRepository;
        this.categoryEventStoreRepository = categoryEventStoreRepository;
    }

    public List<Event> handleCreateCategory(CreateCategoryCommand createCategoryCommand) {

        List<Event> events = new ArrayList<>();

        Optional.ofNullable(createCategoryCommand.getProductIds())
                .orElse(Set.of())
                .forEach(productId -> {
                    ProductAddedToCategoryEvent productAddedToCategoryEvent
                            = ProductAddedToCategoryEvent.builder()
                            .productId(productId)
                            .categoryId(createCategoryCommand.getCategoryId())
                            .build();

                    events.add(productAddedToCategoryEvent);


                });

        CategoryEventStore categoryEventStore = CategoryEventStore
                .builder()
                .categoryId(createCategoryCommand.getCategoryId())
                .events(createCategoryCommand.getProductIds()
                        .stream().map(productId -> {
                            return ProductAddedInCategoryEvent
                                    .builder()
                                    .categoryId(createCategoryCommand.getCategoryId())
                                    .productId(productId)
                                    .build();
                        }).collect(Collectors.toList()))
                .build();

        categoryEventStoreRepository.save(categoryEventStore);

        return categoryEventStore.getEvents();
    }

    public CategoryDocument handleUpdateCategory(UpdateCategoryCommand updateCategoryCommand) {
        return null;
    }
}
