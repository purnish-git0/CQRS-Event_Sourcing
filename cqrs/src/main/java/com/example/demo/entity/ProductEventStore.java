package com.example.demo.entity;

import com.example.demo.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductEventStore {

    @Id
    private String id;

    @Indexed(unique = true)
    private Integer productId;

    private List<Event> events;
}
