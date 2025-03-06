package com.cqrs.query;

import com.cqrs.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderQuery {

    private OrderStatusEnum orderStatusEnum;

    private Integer customerId;

    private Set<Integer> productsId;

}
