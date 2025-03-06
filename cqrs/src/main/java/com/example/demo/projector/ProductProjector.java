package com.example.demo.projector;

import com.example.demo.entity.ProductDocument;
import com.example.demo.event.Event;
import com.example.demo.event.ProductAddedToCategoryEvent;
import com.example.demo.event.ProductRemovedFromCategoryEvent;
import com.example.demo.repository.read.ProductReadRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public project(Integer productId, List<Event> events) {
        for(Event event: events) {

        }
    }

    private void apply(Integer productId, ProductAddedToCategoryEvent productAddedToCategoryEvent) {
        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(productId));
        List<ProductDocument> productDocuments =
        ProductDocument productDocument =
    }

    private void apply(Integer productId, ProductRemovedFromCategoryEvent productRemovedFromCategoryEvent) {

    }


}
