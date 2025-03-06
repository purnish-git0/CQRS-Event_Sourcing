package com.example.demo.projection;

import com.example.demo.entity.CustomerDocument;
import com.example.demo.entity.OrderDocument;
import com.example.demo.query.OrderByCustomerQuery;
import com.example.demo.query.OrderByIdQuery;
import com.example.demo.repository.read.CustomerReadRepository;
import com.example.demo.repository.read.OrderReadRepository;

import java.util.Set;

public class OrderProjection {

    private final OrderReadRepository orderReadRepository;

    private final CustomerReadRepository customerReadRepository;

    public OrderProjection(OrderReadRepository orderReadRepository,
                           CustomerReadRepository customerReadRepository) {
        this.orderReadRepository = orderReadRepository;
        this.customerReadRepository = customerReadRepository;
    }

    public Set<OrderDocument> handle(OrderByCustomerQuery orderByCustomerQuery) {
        CustomerDocument customerDocument = customerReadRepository.findById(orderByCustomerQuery.getCustomerId()).get();
        return customerDocument.getOrders();
    }

    public OrderDocument handle(OrderByIdQuery orderByIdQuery) {
        return orderReadRepository.findById(orderByIdQuery.getId()).get();
    }




}
