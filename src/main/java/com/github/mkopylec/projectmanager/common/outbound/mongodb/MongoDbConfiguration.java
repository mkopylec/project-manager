package com.github.mkopylec.projectmanager.common.outbound.mongodb;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
class MongoDbConfiguration {

    private final MongoDbProperties properties;

    MongoDbConfiguration(MongoDbProperties properties) {
        this.properties = properties;
    }

    @Bean
    MongoClientSettingsBuilderCustomizer mongoDbSettings() {
        return settings -> settings.applyToSocketSettings(it -> {
            it.connectTimeout((int) properties.getTimeout().getConnect().toMillis(), MILLISECONDS);
            it.readTimeout((int) properties.getTimeout().getRead().toMillis(), MILLISECONDS);
        });
    }
}
