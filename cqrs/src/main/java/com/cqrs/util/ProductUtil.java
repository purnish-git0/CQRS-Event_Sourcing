package com.cqrs.util;

import com.cqrs.entity.ProductDocument;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToCategoryEvent;
import com.cqrs.event.ProductRemovedFromCategoryEvent;
import com.cqrs.event.ProductRemovedFromOrderEvent;
import com.cqrs.repository.read.CategoryReadRepository;
import com.cqrs.repository.write.ProductEventStoreRepository;
import com.cqrs.repository.read.ProductReadRepository;
import com.cqrs.snapshot.ProductSnapshot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductUtil {

    private ProductEventStoreRepository productEventStoreRepository;

    private ProductReadRepository productReadRepository;

    private MongoTemplate readMongoTemplate;



    public ProductUtil(ProductEventStoreRepository productEventStoreRepository
                    , @Qualifier("readMongoTemplate") MongoTemplate readTemplate){
        this.productEventStoreRepository = productEventStoreRepository;
        this.readMongoTemplate = readTemplate;
    }

    public Set<Integer> recreateProductCategoriesFromEventStore(Integer productId,
                                                                List<Event> events) {

        ProductDocument productDocument = ProductDocument.builder()
                .id(productId)
                .build();

        Set<Integer> categoryIds = new HashSet<>();


        for(Event event: events) {
            if(event instanceof ProductAddedToCategoryEvent) {
                if(((ProductAddedToCategoryEvent) event).getProductId().equals(productId)) {
                    categoryIds.add(((ProductAddedToCategoryEvent) event).getCategoryId());
                }
            }
            if(event instanceof ProductRemovedFromOrderEvent) {
                if (((ProductRemovedFromOrderEvent) event).getProductId().equals(productId)) {
                    categoryIds.remove(((ProductRemovedFromOrderEvent) event).getProductId());
                }
            }
        }

        return categoryIds;
    }

    public ProductDocument recreateProductFromEvents(Integer productId, List<Event> events) {
        ProductDocument productDocument = ProductDocument.builder()
                .id(productId)
                .build();

        for(Event event: events) {
            if(event instanceof ProductAddedToCategoryEvent) {

            }
            if(event instanceof ProductRemovedFromCategoryEvent) {

            }
        }

        return null;
    }

    public ProductSnapshot createSnapshot(Integer productId,
                                          List<Event> events) {


        Query query = new Query();

        query.addCriteria(Criteria.where("productId").is(productId));


        ProductSnapshot previousSnapshot = readMongoTemplate.findOne(query, ProductSnapshot.class);

        Set<Integer> categorySetOfProduct = new HashSet<>();

        for(Event event: events) {
            if(event instanceof ProductAddedToCategoryEvent) {
                if(((ProductAddedToCategoryEvent) event).getProductId().equals(previousSnapshot.getProductId())) {
                    categorySetOfProduct.add(((ProductAddedToCategoryEvent) event).getCategoryId());
                }
            }
            if(event instanceof ProductRemovedFromCategoryEvent) {
                if (((ProductRemovedFromCategoryEvent) event).getProductId().equals(previousSnapshot.getProductId())) {
                    categorySetOfProduct.remove(((ProductRemovedFromCategoryEvent) event).getCategoryId());
                }
            }
        }

        Update update = new Update();
        update.set("categorySet", categorySetOfProduct);



        readMongoTemplate.updateFirst(query, update, ProductSnapshot.class);

        Query querySnapshot = new Query();

        querySnapshot.addCriteria(Criteria.where("productId").is(productId));

        return readMongoTemplate.findOne(query, ProductSnapshot.class);


    }

}
