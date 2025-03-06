package com.example.demo.event;

import com.example.demo.OrderStatusEnum;
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
public class OrderStatusChangedEvent extends Event {

    private Integer id;

    private Integer orderId;

    private OrderStatusEnum orderStatus;

}
