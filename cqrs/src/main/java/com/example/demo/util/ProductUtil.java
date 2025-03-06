package com.example.demo.util;

import com.example.demo.entity.ProductDocument;
import com.example.demo.event.Event;
import com.example.demo.event.ProductAddedToCategoryEvent;
import com.example.demo.event.ProductRemovedFromCategoryEvent;
import com.example.demo.event.ProductRemovedFromOrderEvent;
import com.example.demo.repository.write.ProductEventStoreRepository;
import com.example.demo.repository.read.ProductReadRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProductUtil {

    private ProductEventStoreRepository productEventStoreRepository;

    private ProductReadRepository productReadRepository;

    public ProductUtil(ProductEventStoreRepository productEventStoreRepository){
        this.productEventStoreRepository = productEventStoreRepository;
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

}
