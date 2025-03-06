package com.cqrs.entity;

import com.cqrs.OrderStatusEnum;
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
public class OrderDocument {

    @Id
    private Integer id;

    private Set<ProductDocument> products;

    private CustomerDocument customerDocument;

    private OrderStatusEnum orderStatus;
}
