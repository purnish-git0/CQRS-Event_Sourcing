package com.cqrs.service;

import com.cqrs.command.order.CreateOrderCommand;
import com.cqrs.command.order.UpdateOrderCommand;
import com.cqrs.entity.OrderDocument;
import com.cqrs.event.Event;
import com.cqrs.event.OrderCreatedEvent;
import com.cqrs.projection.OrderProjector;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    private OrderProjector orderProjector;

    public OrderService(OrderProjector orderProjector) {
        this.orderProjector = orderProjector;
    }

    public void createOrder(CreateOrderCommand createOrderCommand) {
        OrderDocument orderDocument = new OrderDocument();
        Event orderCreatedEvent = new OrderCreatedEvent();

    }

    public void updateOrder(UpdateOrderCommand updateOrderCommand) {

    }
}
