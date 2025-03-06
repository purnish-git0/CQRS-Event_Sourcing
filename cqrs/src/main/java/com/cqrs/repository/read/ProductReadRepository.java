package com.cqrs.repository.read;

import com.cqrs.entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductReadRepository extends MongoRepository<ProductDocument, Integer> {
}
