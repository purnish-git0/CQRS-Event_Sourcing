package com.cqrs.entity;

import com.cqrs.event.Event;
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
public class CategoryEventStore {

    @Id
    private String id;

    private Integer categoryId;

    private List<Event> events;
}
