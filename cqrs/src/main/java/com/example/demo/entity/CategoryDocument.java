package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDocument {

    @Id
    private String id;

    private Integer categoryId;

    private Set<ProductDocument> products;

}
