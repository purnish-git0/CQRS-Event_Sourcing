package com.cqrs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages =
        "com.cqrs.repository.read",
        mongoTemplateRef = "readMongoTemplate")
public class ReadDatasourceConfig {

}