package com.example.demo.entity;

import com.example.demo.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderEventStore {

    @Id
    private Integer orderId;

    private List<Event> events;
}
