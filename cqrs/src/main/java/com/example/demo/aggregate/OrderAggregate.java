package com.example.demo.aggregate;

import com.example.demo.command.order.CreateOrderCommand;
import com.example.demo.command.order.UpdateOrderCommand;
import com.example.demo.entity.OrderEventStore;
import com.example.demo.event.Event;
import com.example.demo.event.OrderAddedToCustomerEvent;
import com.example.demo.event.ProductAddedToOrderEvent;
import com.example.demo.event.ProductRemovedFromOrderEvent;
import com.example.demo.repository.read.OrderEventRepository;
import com.example.demo.util.OrdersUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class OrderAggregate {

    private OrderEventRepository orderEventRepository;

    private OrdersUtil ordersUtil;


    public OrderAggregate() {
    }

    public List<Event> handleOrderCreateCommand(CreateOrderCommand createOrderCommand) {


        OrderAddedToCustomerEvent orderAddedToCustomerEvent =
                OrderAddedToCustomerEvent.builder()
                        .orderId(createOrderCommand.getOrderId())
                        .customerId(createOrderCommand.getCustomerId())
                        .build();

        List<ProductAddedToOrderEvent> productAddedToOrderEvents = new ArrayList<>();
        for(Integer productId: createOrderCommand.getProducts()) {
            ProductAddedToOrderEvent productAddedToOrderEvent = ProductAddedToOrderEvent
                    .builder()
                    .orderId(createOrderCommand.getOrderId())
                    .productId(productId)
                    .build();

            productAddedToOrderEvents.add(productAddedToOrderEvent);
        }


        List<Event> eventsForOrder = new ArrayList<>();

        eventsForOrder.add(orderAddedToCustomerEvent);

        eventsForOrder.addAll(productAddedToOrderEvents);

        OrderEventStore orderEventStore = OrderEventStore.builder()
                .orderId(createOrderCommand.getOrderId())
                .events(eventsForOrder)
                .build();

        orderEventRepository.save(orderEventStore);

        return orderEventStore.getEvents();
    }

    public List<Event> handleOrderUpdateCommand(UpdateOrderCommand updateOrderCommand) {


        List<Event> orderUpdateEvent = new ArrayList<>();

        Set<Integer> productsRecreatedIds =
                ordersUtil.recreateOrderDocumentProductIdsFromEvents(updateOrderCommand.getId());


        Set<Integer> productIdsToBeAdded = new HashSet<>();
        productIdsToBeAdded.addAll(updateOrderCommand.getProducts());

        Set<Integer> produceIdsToBeRemoved = new HashSet<>();
        produceIdsToBeRemoved.addAll(productsRecreatedIds);

        productIdsToBeAdded.removeAll(productsRecreatedIds);

        produceIdsToBeRemoved.removeAll(updateOrderCommand.getProducts());

        List<Event> productOrderEvents = new ArrayList<>();

        for(Integer productIdToRemove: produceIdsToBeRemoved) {
            ProductRemovedFromOrderEvent productRemovedFromOrderEvent = ProductRemovedFromOrderEvent
                    .builder()
                    .orderId(updateOrderCommand.getId())
                    .productId(productIdToRemove)
                    .build();

            productOrderEvents.add(productRemovedFromOrderEvent);
        }

        for(Integer productIdToAdd: productIdsToBeAdded) {

            ProductAddedToOrderEvent productAddedToOrderEvent = ProductAddedToOrderEvent
                    .builder()
                    .orderId(updateOrderCommand.getId())
                    .productId(productIdToAdd)
                    .build();

            productOrderEvents.add(productAddedToOrderEvent);
        }

        OrderEventStore orderEventStore = OrderEventStore.builder()
                .orderId(updateOrderCommand.getId())
                .events(productOrderEvents)
                .build();

        orderEventRepository.save(orderEventStore);

        return orderEventStore.getEvents();
    }
}
