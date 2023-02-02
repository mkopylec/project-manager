package com.github.mkopylec.projectmanager.common.utils.databases

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoDb {

    private MongoTemplate database

    MongoDb(MongoTemplate database) {
        this.database = database
    }

    void clear() {
        database.collectionNames.each { database.remove(new Query(), it) }
    }
}
