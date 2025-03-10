package com.cqrs.controller;

import com.cqrs.aggregate.OrderAggregate;
import com.cqrs.command.order.CreateOrderCommand;
import com.cqrs.command.order.UpdateOrderCommand;
import com.cqrs.dto.OrderDTO;
import com.cqrs.event.Event;
import com.cqrs.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderService orderService;

    private OrderAggregate orderAggregate;

    public OrderController(OrderAggregate orderAggregate,
                           OrderService orderService) {
        this.orderAggregate = orderAggregate;
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<Event>> createOrder(@RequestBody OrderDTO orderDTO) {
        CreateOrderCommand createOrderCommand = CreateOrderCommand
                .builder()
                .customerId(orderDTO.getCustomerId())
                .products(orderDTO.getProductIds())
                .build();

        return ResponseEntity.ok(orderAggregate.handleOrderCreateCommand(createOrderCommand));
    }

    @PostMapping("/update")
    public ResponseEntity<List<Event>> updateOrder(@RequestBody OrderDTO orderDTO) {
        UpdateOrderCommand command = UpdateOrderCommand.builder()
                .id(orderDTO.getId())
                .products(orderDTO.getProductIds())
                .orderStatus(orderDTO.getOrderStatus())
                .build();



        return ResponseEntity.ok(orderAggregate.handleOrderUpdateCommand(command));
    }
}
