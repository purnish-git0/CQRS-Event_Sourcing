package com.cqrs.projector;

import com.cqrs.entity.ProductDocument;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToCategoryEvent;
import com.cqrs.event.ProductRemovedFromCategoryEvent;
import com.cqrs.repository.read.ProductReadRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProjector {

    private ProductReadRepository productReadRepository;

    private MongoTemplate readMongoTemplate;

    public ProductProjector(ProductReadRepository productReadRepository
            , @Qualifier("readMongoTemplate") MongoTemplate readTemplate) {
        this.productReadRepository = productReadRepository;
        this.readMongoTemplate = readTemplate;
    }

    public void project(Integer productId, List<Event> events) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productId));

        ProductDocument productDocument = readMongoTemplate.findOne(query, ProductDocument.class);

        for(Event event: events) {

        }
    }

    private void apply(ProductDocument productDocument, ProductAddedToCategoryEvent productAddedToCategoryEvent) {



    }

    private void apply(Integer productId, ProductRemovedFromCategoryEvent productRemovedFromCategoryEvent) {

    }


}
