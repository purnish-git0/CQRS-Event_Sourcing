package com.cqrs.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.mongodb.WriteConcern;

@Configuration
class MongoDbConfig {

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017?uuidRepresentation=STANDARD");

    }

    @Bean(name = {"readMongoTemplate", "mongoTemplate"})
    MongoOperations readMongoTemplate(MongoClient mongoClient) {

        return new MongoTemplate(mongoClient, "readdb");
    }

    @Bean(name = {"writeMongoTemplate"})
    MongoOperations writeMongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "writedb");
    }
}