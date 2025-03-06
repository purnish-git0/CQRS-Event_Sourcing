package com.example.demo.repository.read;

import com.example.demo.entity.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductReadRepository extends MongoRepository<ProductDocument, Integer> {
}
