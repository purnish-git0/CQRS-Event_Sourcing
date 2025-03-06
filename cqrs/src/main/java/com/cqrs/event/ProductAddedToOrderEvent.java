package com.cqrs.event;

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
public class ProductAddedToOrderEvent extends Event{

    private Integer orderId;

    private Integer productId;
}
