package com.github.mkopylec.projectmanager.infrastructure.persistence;

import org.modelmapper.ModelMapper;

import org.springframework.data.mongodb.core.MongoTemplate;

abstract class MongoDbRepository {

    private MongoTemplate database;
    private ModelMapper mapper;

    MongoDbRepository(MongoTemplate database, ModelMapper mapper) {
        this.database = database;
        this.mapper = mapper;
    }

    MongoTemplate getDatabase() {
        return database;
    }

    ModelMapper getMapper() {
        return mapper;
    }
}
