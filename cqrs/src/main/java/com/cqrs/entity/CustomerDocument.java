package com.cqrs.entity;

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
public class CustomerDocument {

    @Id
    private Integer id;

    private Set<OrderDocument> orders;

}
