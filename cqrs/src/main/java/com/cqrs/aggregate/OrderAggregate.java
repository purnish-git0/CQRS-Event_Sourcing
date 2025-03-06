package com.cqrs.aggregate;

import com.cqrs.command.order.CreateOrderCommand;
import com.cqrs.command.order.UpdateOrderCommand;
import com.cqrs.entity.OrderEventStore;
import com.cqrs.event.Event;
import com.cqrs.event.OrderAddedToCustomerEvent;
import com.cqrs.event.ProductAddedToOrderEvent;
import com.cqrs.event.ProductRemovedFromOrderEvent;
import com.cqrs.repository.read.OrderEventRepository;
import com.cqrs.util.OrdersUtil;
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
