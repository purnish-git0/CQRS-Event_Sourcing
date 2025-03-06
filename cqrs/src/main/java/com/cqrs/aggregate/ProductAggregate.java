package com.cqrs.aggregate;

import com.cqrs.command.product.CreateProductCommand;
import com.cqrs.command.product.UpdateProductCommand;
import com.cqrs.entity.ProductDocument;
import com.cqrs.entity.ProductEventStore;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToCategoryEvent;
import com.cqrs.event.ProductRemovedFromCategoryEvent;
import com.cqrs.repository.read.CategoryReadRepository;
import com.cqrs.repository.read.ProductReadRepository;
import com.cqrs.repository.write.ProductEventStoreRepository;
import com.cqrs.util.ProductUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProductAggregate {


    ProductEventStoreRepository productEventStoreRepository;

    private ProductUtil productUtil;

    public ProductAggregate(ProductReadRepository productReadRepository,
                            CategoryReadRepository categoryReadRepository,
                            ProductEventStoreRepository productEventStoreRepository,
                            ProductUtil productUtil) {
        this.productEventStoreRepository = productEventStoreRepository;
        this.productUtil = productUtil;
    }

    public ProductDocument handleCreateProduct(CreateProductCommand createProductCommand) {

        List<Event> events = new ArrayList<>();


        Optional.ofNullable(createProductCommand.getCategoryIds())
                .orElse(Set.of())
                .stream().forEach(categoryId -> {
                    ProductAddedToCategoryEvent productAddedToCategoryEvent =
                            ProductAddedToCategoryEvent.builder()
                                    .categoryId(categoryId)
                                    .productId(createProductCommand.getProductId())
                                    .build();

                    events.add(productAddedToCategoryEvent);
                });

        ProductEventStore productEventStore = ProductEventStore.builder()
                .productId(createProductCommand.getProductId())
                .events(events)
                .build();


        productEventStoreRepository.save(productEventStore);


        return null;
    }

    public List<Event> handleUpdateProduct(UpdateProductCommand updateProductCommand) {

        ProductEventStore productEventStore = productEventStoreRepository
                .findById(updateProductCommand.getId())
                .get();

        Set<Integer> existingCategoryIds =
                productUtil.recreateProductCategoriesFromEventStore(productEventStore.getProductId(),
                productEventStore.getEvents());

        List<Event> events = new ArrayList<>();

        updateProductCommand.getCategoryIds().stream().filter(categoryId -> {
            return !existingCategoryIds.contains(categoryId);
        }).forEach(addedCategoryId -> {
            ProductAddedToCategoryEvent productAddedToCategoryEvent =
                    ProductAddedToCategoryEvent.builder()
                            .productId(updateProductCommand.getId())
                            .categoryId(addedCategoryId)
                            .build();

            events.add(productAddedToCategoryEvent);

        });

        existingCategoryIds.stream()
                .filter(existingCategoryId -> {
                    return !updateProductCommand.getCategoryIds().contains(existingCategoryId);
                }).forEach(removedCategoryId -> {
                    ProductRemovedFromCategoryEvent productRemovedFromCategoryEvent =
                            ProductRemovedFromCategoryEvent.builder()
                                    .categoryId(removedCategoryId)
                                    .productId(updateProductCommand.getId())
                                    .build();

                    events.add(productRemovedFromCategoryEvent);
                });


        productEventStore.getEvents().addAll(events);


        productEventStoreRepository.save(productEventStore);

        return productEventStore.getEvents();
    }
}
