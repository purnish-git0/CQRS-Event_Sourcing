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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ProductAggregate {


    ProductEventStoreRepository productEventStoreRepository;

    private ProductUtil productUtil;

    private MongoTemplate writeMongoTemplate;

    public ProductAggregate(ProductReadRepository productReadRepository,
                            CategoryReadRepository categoryReadRepository,
                            ProductEventStoreRepository productEventStoreRepository,
                            ProductUtil productUtil,
                            @Qualifier("writeMongoTemplate") MongoTemplate writeTemplate) {
        this.productEventStoreRepository = productEventStoreRepository;
        this.productUtil = productUtil;
        this.writeMongoTemplate = writeTemplate;
    }

    public List<Event> handleCreateProduct(CreateProductCommand createProductCommand) {

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

        Update update = new Update();

        update.push("events").each(events);

        Query query = new Query();

        query.addCriteria(Criteria.where("productId").is(createProductCommand.getProductId()));


        writeMongoTemplate.updateFirst(query, update, ProductEventStore.class);


        return events;
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


        Update update = new Update();

        update.push("events").each(events);

        Query query = new Query();

        query.addCriteria(Criteria.where("productId").is(updateProductCommand.getId()));


        writeMongoTemplate.updateFirst(query, update, ProductEventStore.class);

        return events;
    }
}
