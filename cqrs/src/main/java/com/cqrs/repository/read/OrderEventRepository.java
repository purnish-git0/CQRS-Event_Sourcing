package com.cqrs.repository.read;

import com.cqrs.entity.OrderEventStore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEventStore, Integer> {
}
