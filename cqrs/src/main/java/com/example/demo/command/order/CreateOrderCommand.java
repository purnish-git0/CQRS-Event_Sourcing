package com.example.demo.command.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateOrderCommand {

    private Integer orderId;

    private Integer customerId;

    private Set<Integer> products;
}
