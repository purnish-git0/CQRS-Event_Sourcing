package com.cqrs;

import com.cqrs.event.Event;
import com.cqrs.event.ProductAddedToCategoryEvent;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveCallback;

public class AfterEventSaveCallback implements AfterSaveCallback<Event> {


    @Override
    public Event onAfterSave(Event event, Document document, String collection) {
        if(event instanceof ProductAddedToCategoryEvent) {
//            if()
        }
        return null;
    }
}
