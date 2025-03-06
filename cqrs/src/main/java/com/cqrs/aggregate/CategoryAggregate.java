package com.cqrs.aggregate;

import com.cqrs.command.category.CreateCategoryCommand;
import com.cqrs.command.category.UpdateCategoryCommand;
import com.cqrs.entity.CategoryDocument;
import com.cqrs.entity.CategoryEventStore;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedInCategoryEvent;
import com.cqrs.event.ProductAddedToCategoryEvent;
import com.cqrs.repository.write.CategoryEventStoreRepository;
import com.cqrs.repository.write.ProductEventStoreRepository;
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
