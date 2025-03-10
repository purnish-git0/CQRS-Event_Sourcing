package com.cqrs.command.order;


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
public class UpdateOrderCommand {

    private Integer id;

    private OrderStatusEnum orderStatus;

    private Set<Integer> products;

}
