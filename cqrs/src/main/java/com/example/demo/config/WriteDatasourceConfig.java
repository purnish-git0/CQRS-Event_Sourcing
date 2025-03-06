package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages =
        "com.example.demo.repository.write",
        mongoTemplateRef = "writeMongoTemplate")
public class WriteDatasourceConfig {

}