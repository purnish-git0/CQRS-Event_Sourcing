package com.example.demo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductAddedInCategoryEvent extends Event {

    private Integer categoryId;

    private Integer productId;
}
