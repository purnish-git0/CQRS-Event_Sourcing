package com.cqrs.repository.read;

import com.cqrs.entity.CustomerDocument;
import com.cqrs.entity.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerReadRepository extends MongoRepository<CustomerDocument, Integer> {


    @Query(value = "{'id': ?1}")
    public List<OrderDocument> getOrderDocsByCustomerId(Integer customerId);


}
