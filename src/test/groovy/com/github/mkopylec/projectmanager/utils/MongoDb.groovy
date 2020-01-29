package com.github.mkopylec.projectmanager.utils

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class MongoDb {

    private MongoTemplate mongo

    MongoDb(MongoTemplate mongo) {
        this.mongo = mongo
    }

    void clear() {
        mongo.collectionNames.each { mongo.dropCollection(it) }
    }
}
