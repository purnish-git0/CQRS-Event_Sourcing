package com.cqrs.projection;

import com.cqrs.entity.OrderDocument;
import com.cqrs.entity.ProductDocument;
import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToOrderEvent;
import com.cqrs.event.ProductCreatedEvent;
import com.cqrs.repository.read.OrderReadRepository;
import com.cqrs.repository.read.ProductReadRepository;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class ProductProjector {

    private ProductReadRepository productReadRepository;

    private OrderReadRepository orderReadRepository;

    public ProductProjector(ProductReadRepository productReadRepository,
                            OrderReadRepository orderReadRepository) {
        this.productReadRepository = productReadRepository;
        this.orderReadRepository = orderReadRepository;
    }

    public void project(Integer productId, List<Event> events) {
        for(Event event: events) {
            if(event instanceof ProductCreatedEvent) {
                apply(productId, (ProductCreatedEvent) event);
            }
            if(event instanceof ProductAddedToOrderEvent) {
                apply(productId, (ProductAddedToOrderEvent) event);
            }
        }
    }

    public void apply(Integer productId, ProductCreatedEvent productCreatedEvent) {

    }

    public void apply(Integer productId, ProductAddedToOrderEvent productAddedToOrderEvent) {
        ProductDocument productDocument = productReadRepository.findById(productId).get();

        OrderDocument orderDocument = orderReadRepository
                .findById(productAddedToOrderEvent.getOrderId()).get();

        orderDocument.getProducts().add(productDocument);
        orderReadRepository.save(orderDocument);
    }






}
