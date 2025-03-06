package com.example.demo.projection;

import com.example.demo.event.Event;
import com.example.demo.event.OrderStatusChangedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProjector {

    public void project(Integer userId, List<Event> events) {
        for(Event event: events) {
            if(event instanceof OrderStatusChangedEvent) {
                apply(userId, (OrderStatusChangedEvent) event);
            }
        }
    }
    public void apply(Integer userId, OrderStatusChangedEvent orderStatusChangedEvent) {

    }
}
