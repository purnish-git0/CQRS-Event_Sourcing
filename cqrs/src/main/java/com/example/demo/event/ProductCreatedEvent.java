package com.example.demo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductCreatedEvent extends Event{

    private Integer productId;

    private Integer categoryId;
}
