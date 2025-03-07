package com.cqrs.snapshot;

import com.cqrs.entity.CategoryDocument;
import com.cqrs.entity.OrderDocument;
import com.cqrs.entity.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductSnapshot {

    @Id
    private String id;

    @Indexed(unique = true)
    private Integer productId;


    private String name;

    private LinkedHashSet<CategoryDocument> categorySet;

    private Set<OrderDocument> orders;

}
