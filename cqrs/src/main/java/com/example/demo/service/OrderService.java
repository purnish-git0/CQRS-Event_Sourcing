package com.example.demo.service;

import com.example.demo.command.order.CreateOrderCommand;
import com.example.demo.command.order.UpdateOrderCommand;
import com.example.demo.entity.OrderDocument;
import com.example.demo.event.Event;
import com.example.demo.event.OrderCreatedEvent;
import com.example.demo.projection.OrderProjector;
import org.springframework.data.mongodb.core.MongoTemplate;
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
