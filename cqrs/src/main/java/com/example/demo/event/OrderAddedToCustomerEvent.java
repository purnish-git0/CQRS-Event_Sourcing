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
public class OrderAddedToCustomerEvent extends Event {

    private Integer id;

    private Integer orderId;

    private Integer customerId;
}
