package com.cqrs.dto;


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
public class OrderDTO {

    private Integer id;

    private Set<Integer> productIds;

    private OrderStatusEnum orderStatus;

    private Integer customerId;
}
