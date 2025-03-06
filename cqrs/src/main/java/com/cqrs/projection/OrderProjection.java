package com.cqrs.projection;

import com.cqrs.entity.CustomerDocument;
import com.cqrs.entity.OrderDocument;
import com.cqrs.query.OrderByCustomerQuery;
import com.cqrs.query.OrderByIdQuery;
import com.cqrs.repository.read.CustomerReadRepository;
import com.cqrs.repository.read.OrderReadRepository;

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
