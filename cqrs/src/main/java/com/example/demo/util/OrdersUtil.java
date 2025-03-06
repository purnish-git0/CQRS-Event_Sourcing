package com.example.demo.util;

import com.example.demo.entity.OrderEventStore;
import com.example.demo.event.Event;
import com.example.demo.event.ProductAddedToOrderEvent;
import com.example.demo.event.ProductRemovedFromOrderEvent;
import com.example.demo.repository.read.OrderEventRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrdersUtil {

    private OrderEventRepository orderEventRepository;

    public OrdersUtil(OrderEventRepository orderEventRepository) {
        this.orderEventRepository = orderEventRepository;
    }

    public Set<Integer> recreateOrderDocumentProductIdsFromEvents(Integer orderId) {
        OrderEventStore orderEventStore = orderEventRepository.findById(orderId).get();

        Set<Integer> retVal = new HashSet<>();

        for(Event event: orderEventStore.getEvents()) {
            if(event instanceof ProductAddedToOrderEvent) {
                apply(orderId, (ProductAddedToOrderEvent) event, retVal);
            }
            if(event instanceof ProductRemovedFromOrderEvent) {
                apply(orderId, (ProductRemovedFromOrderEvent) event, retVal);
            }
        }

        return retVal;
    }

    private void apply(Integer orderId, ProductAddedToOrderEvent productAddedToOrderEvent,
                       Set<Integer> retVal) {
        retVal.add(productAddedToOrderEvent.getProductId());
    }

    private void apply(Integer orderId,
                       ProductRemovedFromOrderEvent productRemovedFromOrderEvent,
                       Set<Integer> retVal) {
        retVal.remove(productRemovedFromOrderEvent.getProductId());
    }




}
